package parsing;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//use final objects as much as possible
//object creation is expensive
//

/**
 * This is a utility class that formats the outputs returned by the ignite database
 *
 */

@Configuration
public class QueryOutputFormatter {

  private final String NUMBER_LABEL = "NUMBER";
  private final String LATA_LABEL = "LATA";
  private final String OCN_LABEL = "OCN";
  private final String AOCN_LABEL = "AOCN";
  private final String RATE_CENTER_LABEL = "Rate Center";
  private final String STATE_LABEL = "State";
  private final String OCN_OVERALL_LABEL = "OCN Overall";
  private final String EFFECTIVE_DATE_LABEL = "Effective Date";


  final private ObjectMapper mapper;

  public QueryOutputFormatter()
  {

    this.mapper = new ObjectMapper();

  }

  /**
   *
   * @param query original formatted query string
   * @param rateCenters all the rate centers returned
   * @return should ideally return a json output of all the results but this will do for now
   */
  public String formatRateCenterOutput(String query, Iterable<RateCenter> rateCenters)
  {
    //return getJsonOutput(query, rateCenters);
  }

  /**
   *
   * Give back a complete JSON output of all the numbers queried
   *
   * @param query
   * @param rateCenters
   * @return
   */
  private String getJsonOutput(ValidNumberRecord validNumberRecord)
  {
    //mapper should be created only once
    //final String json = mapper.writeValueAsString(rateCenters);
    //use logger instead of systemout
    //System.out.println(json);
    //return json;
  }

  /**
   *
   *
   * @param query User query
   * @param rateCenters Results from ignite database
   * @return  Returns the results as a single plain string for output
   */
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


  //needs to make a JSON string out of all the number records, incomplete queries, and invalid queries it gets

  @Bean
  public QueryOutputFormatter getOutputFormatter()
  {
    return this;
  }


}
