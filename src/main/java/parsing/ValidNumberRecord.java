package parsing;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.ArrayList;

/**
 * Class to be used for JSON output
 * This will return its own JSON string?
 */

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

  public boolean isRecordValid()
  {
    return this.isResolvable;
  }

}
