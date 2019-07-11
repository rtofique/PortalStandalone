package number_lookup.number_records;

/**
 * Response to invalid numbers entered by the user
 */

public class InvalidNumberRecord extends NumberRecord {

  private final String status;
  private final String jsonType = "$INVALID";

  public InvalidNumberRecord(String query, String queryID, String status)
  {
    super(queryID, query);
    //maybe make this more descriptive later
    this.status = status;
  }

  public String getStatus() { return status; }


  @Override
  public String getJsonType() {
    return jsonType;
  }
}
