package rest.number_records;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;

/**
 * Class to be used for JSON output
 * This will return its own JSON string?
 */

@JsonSerialize(using = ValidRecordSerializer.class)
public class ValidNumberRecord extends NumberRecord {

  private final Iterable<RateCenter> rateCenter;
  private final boolean isResolvable;

  public ValidNumberRecord(String query, Iterable<RateCenter> rateCenter)
  {
    super(query);
    this.rateCenter = rateCenter;
    this.isResolvable = true;
  }

  public ValidNumberRecord(String query)
  {
    super(query);
    this.rateCenter = new ArrayList<>();
    this.isResolvable = false;
  }

  public boolean isResolvable() {
    return isResolvable;
  }

  public Iterable<RateCenter> getRateCenter() {
    return rateCenter;
  }

}
