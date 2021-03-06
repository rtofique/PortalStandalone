package rest.query_parsing;

import rest.number_records.InvalidNumberRecord;
import rest.number_records.NumberRecord;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

//use final objects as much as possible
//object creation is expensive
//

/**
 * This is a utility class that formats the outputs returned by the ignite database
 *
 */

@Configuration
public class QueryOutputFormatter {

  ResponseBodyGenerator responseBodyGenerator;

  public QueryOutputFormatter()
  {
      this.responseBodyGenerator = new JSONResponseBodyGenerator();
  }


  /**
   * Consolidates all the results into a QueryResultWrapper by generating the JSON response and figuring out the appropriate HTTP response code
   * @param records
   * @return
   */
  public QueryResultWrapper generateResultResponse(List<? extends NumberRecord> records)
  {
    //calculate json response here
    String jsonRepresentation = responseBodyGenerator.generateResponseBody(records);

    //if(records.isEmpty()) return new QueryResultWrapper(jsonRepresentation, HttpStatus.NO_CONTENT);
    //ask if this works
    if(!records.isEmpty() && records.get(0) instanceof InvalidNumberRecord) return new QueryResultWrapper(jsonRepresentation, HttpStatus.BAD_REQUEST);
    /*for(NumberRecord record : records)
    {
        ValidNumberRecord validRecord = (ValidNumberRecord) record;
        if(!validRecord.isRecordValid()) return new QueryResultWrapper(jsonRepresentation, Httpsta);
    }*/

    return new QueryResultWrapper(jsonRepresentation, HttpStatus.OK);

  }

  @Bean
  public QueryOutputFormatter getOutputFormatter()
  {
    return this;
  }


}
