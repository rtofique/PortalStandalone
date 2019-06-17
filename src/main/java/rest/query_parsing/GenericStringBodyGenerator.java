package rest.query_parsing;

import rest.number_records.NumberRecord;
import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.util.List;

public class GenericStringBodyGenerator implements ResponseBodyGenerator {



  @Override
  public String generateResponseBody(List<? extends NumberRecord> records) {
    return "";
  }

  /**
   *
   *
   * @param query User query
   * @param rateCenters Results from ignite database
   * @return  Returns the results as a single plain string for output
   */
  private String getPlainStringOutput(String query, Iterable<RateCenter> rateCenters)
  {/*
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

    return builder.toString();*/
    return "";
  }

  private String formatRow(String label, String value)
  {
    if(value == null) value = "N/A";
    return label + ": " + value + ", \r\n";
  }
}
