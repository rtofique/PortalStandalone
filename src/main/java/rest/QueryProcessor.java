package rest;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;
import javax.cache.Cache.Entry;
import org.apache.ignite.cache.query.QueryCursor;


import java.util.TreeSet;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.ignite.internal.client.thin.ClientServerError;

/**
 * This class implements all the functions that query the ignite database
 */


@Configuration
public class QueryProcessor {


  @Autowired
  IgniteClient igniteClient;

  @Autowired
  QueryOutputFormatter outputFormatter;


  private final String CACHE_NAME = "correlator_rateCenters";


  public QueryProcessor(){};

  /**
   * For debugging purposes, returns all keys in the cache
   * @return
   */
  private String printAllKeys()
  {
    List<String> keys = new ArrayList<>();
    igniteClient.getOrCreateCache(CACHE_NAME).query(new ScanQuery<>(null)).forEach(entry -> keys.add((String) entry.getKey()));

    StringBuilder allKeys = new StringBuilder();

    for(String key : keys)
    {
      allKeys.append(key + ", \n" );
    }

    return allKeys.toString();
  }

  /**
   * Queries the ignite database for the NPANXX number entered by the user. This method is the only one that the REST Controller needs to call.
   * It will validate and format the data and return the result back;
   *
   * @param query Raw NPANXX entered by the user
   * @return String output of all the rate center fields concatenated
   * @throws InvalidInputException
   */
  public String queryNumber(String query) throws InvalidInputException
  {
    if(!isValidQuery(query)) throw new InvalidInputException(query);

    String formattedQuery = formatQuery(query);
    TreeSet<RateCenter> result;
    try
    {
      result = (TreeSet<RateCenter>)igniteClient.cache(CACHE_NAME).get(formattedQuery);
    }
    catch(ClientServerError e)
    {
      result = null;
    }

    if(result == null) return "No value found in database for the key: " + formattedQuery;

    return outputFormatter.formatRateCenterOutput(formattedQuery, result);
  }


  /**
   *
   * @param query
   * @return checks if the proper NPA-NXX format is used
   *
   */
  private boolean isValidQuery(String query)
  {
    return query.matches("\\d{7}") || query.matches("\\d{6}A?");
  }


  /**
   *
   * @param query Raw query entered by the user and forwarded to the QueryProcessor
   * @return Query that is in accordance with the key format in the database: 7 Digits or 6 Digits + A
   *
   */
  private String formatQuery(String query)
  {
    return (query.length() == 6) ? query + "A" : query;
  }

  /**
   * QueryProcesser to be used as a Singleton
   * @return
   */
  @Bean
  public QueryProcessor getProcessor()
  {
    return this;
  }

}
