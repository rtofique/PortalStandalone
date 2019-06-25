package number_lookup.number_records;

import com.bandwidth.engineering.correlator.enums.cache.IconectivStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Used to add annotations to the RateCenter class which cannot be tampered with
 */

public abstract class RateCenterMixin {


  @JsonIgnore
  abstract IconectivStatus getRecordStatus();

}
