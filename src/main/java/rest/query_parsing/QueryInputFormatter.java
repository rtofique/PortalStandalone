package rest.query_parsing;

import rest.number_records.InvalidNumberRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class performs the necessary operations to validate and transform the query so that it is suitable to go into the API
 */

@Configuration
public class QueryInputFormatter {

  public QueryInputFormatter(){}

  /**
   *
   * @param query
   * @return checks if the proper NPA-NXX format is used
   *
   */
  public boolean isValidQuery(String query)
  {
    return query.matches("\\d{6}A?") || query.matches("\\d{7,10}");
  }

  /**
   *
   * @param query Raw query entered by the user and forwarded to the QueryProcessor
   * @return Query that is in accordance with the key format in the database: 7 Digits or 6 Digits + A
   *
   */
  public String formatQuery(String query)
  {
    if(query.length() == 6) return query + "A";
    else return query.substring(0, 7);
  }

  /**
   * Iterates over all the queries and extracts those which are invalid
   * @param queryList
   * @return a list of invalid queries
   */
  public List<InvalidNumberRecord> findInvalidNumbers(List<String> queryList)
  {
    List<InvalidNumberRecord> invalidNumbers = new ArrayList<>();

    for(int i = 0; i < queryList.size(); i++)
    {
      String query = queryList.get(i);
      if(!isValidQuery(query))
        invalidNumbers.add(new InvalidNumberRecord(query, i + 1 + ""));

    }

    return invalidNumbers;
  }

  /**
   * @param queryString Original list of csv queries
   * @return a sanitized list of all the numbers in the query
   */
  public List<String> prepareQueries(String queryString)
  {
    List<String> queries = Arrays.asList(queryString
        .trim()
        .replaceAll("[-.\\s]", "")
        .split(","));

    return queries;
  }
  /**
   *
   * @param query
   * @return Checks if query is a block query or not
   */
  public boolean isBlockQuery(String query)
  {
    return query.matches("\\d{6}A");
  }

  @Bean
  public QueryInputFormatter getInputFormatter()
  {
    return this;
  }

}
