package rest;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This is a utility class that formats the outputs returned by the ignite database
 *
 */

public class QueryOutputFormatter {

  private final String NUMBER_LABEL = "NUMBER";
  private final String LATA_LABEL = "LATA";
  private final String OCN_LABEL = "OCN";
  private final String AOCN_LABEL = "AOCN";
  private final String RATE_CENTER_LABEL = "Rate Center";
  private final String STATE_LABEL = "State";
  private final String OCN_OVERALL_LABEL = "OCN Overall";
  private final String EFFECTIVE_DATE_LABEL = "Effective Date";

  /**
   *
   * @param query original formatted query string
   * @param rateCenters all the rate centers returned
   * @return should ideally return a json output of all the results but this will do for now
   */
  public String formatRateCenterOutput(String query, Iterable<RateCenter> rateCenters)
  {
    return getPlainStringOutput(query, rateCenters);
  }

  private String getPlainStringOutput(String query, Iterable<RateCenter> rateCenters)
  {
    StringBuilder builder = new StringBuilder();

    builder.append(formatRow(NUMBER_LABEL, query));

    for(final RateCenter rc : rateCenters)
    {
      builder.append(formatRow(LATA_LABEL, rc.getLata()) +
          formatRow(OCN_LABEL, rc.getOcn()) +
          formatRow(AOCN_LABEL, rc.getAocn()) +
          formatRow(RATE_CENTER_LABEL, rc.getRateCenter()) +
          formatRow(STATE_LABEL, rc.getState()) +
          formatRow(OCN_OVERALL_LABEL, rc.getOcnOverall()) +
          formatRow(EFFECTIVE_DATE_LABEL, convertDateToString(rc.getEffectiveDate())));
      builder.append("\n");
    }

    return builder.toString();
  }

  private String convertDateToString(LocalDateTime date)
  {
    if(date == null) return null;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    return date.format(formatter);
  }

  private String formatRow(String label, String value)
  {
    if(value == null) value = "N/A";
    return label + ": " + value + ", \r\n";
  }


}
