package number_lookup.query_parsing;

import number_lookup.number_records.NumberRecord;
import java.util.List;

/**
 * Allows for the response to be crafted in different formats including JSON
 */

public interface ResponseBodyGenerator {



  /**
   * Must be implemented by any subclass to convert the NumberRecord objects into the desired output format
   *
   * @param records the resolved records from QueryProcessor
   * @return the transformed output which must be
   */
  public String generateResponseBody(List<? extends NumberRecord> records);

}
