package rest.number_records;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Invalid numbers entered by the user that contained mista
 */

@JsonSerialize(using = InvalidRecordSerializer.class)
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
