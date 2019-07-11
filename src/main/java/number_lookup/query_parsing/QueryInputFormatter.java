package number_lookup.query_parsing;

import java.util.LinkedList;
import number_lookup.number_records.InvalidNumberRecord;
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
    else return query.replace('a', 'A').substring(0, 7);
  }

  /**
   * Iterates over all the queries and extracts those which are invalid
   * @param queryList
   * @return a list of invalid queries
   */
  /*public List<InvalidNumberRecord> findInvalidNumbers(List<String> queryList)
  {
    List<InvalidNumberRecord> invalidNumbers = new ArrayList<>();

    for(int i = 0; i < queryList.size(); i++)
    {
      String query = queryList.get(i);
      if(!isValidQuery(query))
        invalidNumbers.add(new InvalidNumberRecord(query, i + 1 + "", generateErrorMessage(query)));

    }

    return invalidNumbers;
  }*/


  /**
   * @param queryString Original list of csv queries
   * if a query is six digits, add block 1-9 to it. Empty queries are considered permissible but feedback must be returned to user about them (hence, use of a -1 limit)
   * The last element is removed if empty because we do not want to consider empty elements after a concluding comma.
   * @return a sanitized list of all the numbers in the query
   */
  public List<String> prepareQueries(String queryString)
  {
    LinkedList<String> queries = new LinkedList<>(Arrays.asList(queryString
        .trim()
        .replaceAll("[-.\\s]", "")
        .split(",", -1)));

    if(queries.get(queries.size() - 1).length() == 0)
    {
      queries.removeLast();
    }
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


  private List<String> addAllBlockQueries(String query)
  {
    List<String> list = new ArrayList<>();
    if(query.length() == 6)
    {
      list.add(query + "A");
      for(int i = 1; i <= 9; i++)
      {
        list.add(query + i);
      }
    }

    return list;
  }

  @Bean
  public QueryInputFormatter getInputFormatter()
  {
    return this;
  }

}
