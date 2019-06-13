package parsing;

/**
 * Invalid numbers entered by the user that contained mista
 */

public class InvalidNumberRecord extends NumberRecord {

  private final String queryID;

  public InvalidNumberRecord(String query, String queryID)
  {
    super(query);
    this.queryID = queryID;
  }

  public String getQueryID() {
    return queryID;
  }
}
