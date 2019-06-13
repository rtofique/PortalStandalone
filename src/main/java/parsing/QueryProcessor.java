package parsing;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import java.util.TreeSet;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ignite.cache.query.ScanQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.ignite.internal.client.thin.ClientServerError;

/**
 * This class implements all the functions that query the ignite database
 */


@Configuration
public class QueryProcessor {


  @Autowired
  final IgniteClient igniteClient;

  @Autowired
  final QueryInputFormatter queryInputFormatter;

  @Autowired
  final QueryOutputFormatter queryOutputFormatter;

  //needs to be configurable
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
   * Queries the ignite database for the NPANXX number entered by the user. This method is the only one that the REST Controller needs to call.  (in the next iteration, this will be called by a helper to accomodate multiple)
   * It will validate and format the data and return the result back;
   * It performs two checks if a non-Block number was given by converting it into a block number to match the longest prefix
   *
   * @param query Raw number entered by the user
   * @return This returns a ValidNumberRecord which has all the data about the rate centers or null data for unresolved queries
   * @throws InvalidInputException
   */
  private ValidNumberRecord queryNumber(String query)
  {

    String formattedQuery = queryInputFormatter.formatQuery(query);
    TreeSet<RateCenter> result = findIgniteRecord((formattedQuery));

    if(result == null && !queryInputFormatter.isBlockQuery(formattedQuery))
    {
      formattedQuery = queryInputFormatter.formatQuery(formattedQuery.substring(0, 6));
      result = findIgniteRecord((formattedQuery));
    }

    if(result == null) return new ValidNumberRecord(formattedQuery);

    return new ValidNumberRecord(formattedQuery, result);
  }


  /**
   * Main entry point in QueryProcessor. Prepares and validates the queries before calling to check if the queries return a result from the database.
   * If even a single invalid record is found, it will not query the database and instead return all the invalid number objects.
   * If the numbers input by the user are syntactically correct, it will then proceed to search the database and return all the queries as ValidNumberRecords
   *
   * @param userQuery The complete raw query entered by the user
   * @return a list of either InvalidNumberRecords or ValidNumberRecords
   */
  public QueryResultWrapper queryInput(String userQuery)
  {

    List<String> queries = queryInputFormatter.prepareQueries(userQuery);

    //run a check on the validity of the data first and return all the invalid numbers in the list
    List<InvalidNumberRecord> invalidNumbers =  queryInputFormatter.findInvalidNumbers(queries);

    if(!invalidNumbers.isEmpty()) return queryOutputFormatter.generateResultResponse(invalidNumbers);

    List<ValidNumberRecord> outputRecords =  queries
        .stream()
        .map(this::queryNumber)
        .collect(Collectors.toList());

    return queryOutputFormatter.generateResultResponse(outputRecords);

  }


  /**
   * Find the record in the ignite database
   * @param formattedQuery
   * @return A TreeSet of RateCenter objects which can be iterated over. Or a null if the record was not found.
   */
  private TreeSet<RateCenter> findIgniteRecord(String formattedQuery)
  {

    TreeSet<RateCenter> result;

    try
    {
      result = (TreeSet<RateCenter>)igniteClient.cache(CACHE_NAME).get(formattedQuery);
    }
    catch(ClientServerError e)
    {
      result = null;
    }

    return result;
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
