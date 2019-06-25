package number_lookup.number_records;

/**
 * Response to invalid numbers entered by the user
 */

public class InvalidNumberRecord extends NumberRecord {

  private final String queryID;
  private final String status;
  private final String jsonType = "$INVALID";

  public InvalidNumberRecord(String query, String queryID)
  {
    super(query);
    this.queryID = queryID;
    //maybe make this more descriptive later
    this.status = "failed for x reason";
  }

  public String getQueryID() {
    return queryID;
  }
  public String getStatus() { return status; }


  @Override
  public String getJsonType() {
    return jsonType;
  }
}
