package number_lookup.number_records.number_lookup.number_records;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.ArrayList;

/**
 * Record in response to a valid number query. Serialized into JSON to pass to the end user
*/

public class ValidNumberRecord extends NumberRecord {

  private final Iterable<RateCenter> rateCenters;
  private final boolean recordFound;
  private final String jsonType = "$VALID";


  public ValidNumberRecord(String query, Iterable<RateCenter> rateCenters)
  {
    super(query);
    this.rateCenters = rateCenters;
    this.recordFound = true;
  }

  public ValidNumberRecord(String query)
  {
    super(query);
    this.rateCenters = new ArrayList<>();
    this.recordFound = false;
  }

  @Override
  public String getJsonType() {
    return jsonType;
  }


  public boolean isRecordFound() {
    return recordFound;
  }

  public Iterable<RateCenter> getRateCenters() {
    return rateCenters;
  }

}
