package number_lookup.number_records;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import com.bandwidth.engineering.correlator.enums.cache.IconectivStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Record in response to a valid number query. Serialized into JSON to pass to the end user
*/

public class ValidNumberRecord extends NumberRecord {

  private final RateCenter rateCenter;
  private final boolean recordFound;
  private final String jsonType = "$VALID";


  public ValidNumberRecord(String id, String query, Iterable<RateCenter> rateCenters)
  {
    super(id, query);
    this.rateCenter = getFirstRateCenter(rateCenters);
    this.recordFound = true;
  }

  public ValidNumberRecord(String id, String query)
  {
    super(id, query);
    this.rateCenter = createNullRateCenter();
    this.recordFound = false;
  }

  @Override
  public String getJsonType() {
    return jsonType;
  }


  public boolean isRecordFound() {
    return recordFound;
  }

  /*public Iterable<RateCenter> getRateCenters() {
    return rateCenters;
  }*/

  public RateCenter getRateCenter()
  {
    return this.rateCenter;
  }

  private RateCenter createNullRateCenter()
  {
    String na = "N/A";
    final IconectivStatus status = IconectivStatus.from(null);
    RateCenter rc = new RateCenter(na, na, na, na, na, na, status,
        LocalDateTime.of(0000, 1, 1, 0, 0));

    return rc;
  }

  private RateCenter getFirstRateCenter(Iterable<RateCenter> rateCenters)
  {
    for(RateCenter r  : rateCenters)
    {
      return r;
    }
    return null;
  }

}
