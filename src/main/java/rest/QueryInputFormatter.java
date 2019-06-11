package rest;

/**
 * This class performs the necessary operations to validate and transform the query so that it is suitable to go into the API
 */

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
   *
   * @param query
   * @return Checks if query is a block query or not
   */
  public boolean isBlockQuery(String query)
  {
    return query.matches("\\d{6}A");
  }


}
