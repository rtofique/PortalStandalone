package number_lookup.query_parsing;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.IOException;
import number_lookup.number_records.NumberRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import number_lookup.number_records.RateCenterMixin;

/**
Translates the database result into a JSON response
 */

public class JSONResponseBodyGenerator implements ResponseBodyGenerator {

  final private ObjectMapper mapper;

  public JSONResponseBodyGenerator()
  {
    this.mapper = new ObjectMapper();
  }


  @Override
  public String generateResponseBody(List<? extends NumberRecord> records) {

      mapper.setSerializationInclusion(Include.NON_NULL);

      String recordsJson;
      try
      {
        mapper.addMixIn(RateCenter.class, RateCenterMixin.class);
        recordsJson = mapper.writeValueAsString(records);
      }
      catch(IOException e)
      {
        e.printStackTrace();
        recordsJson = "";
      }

      return recordsJson;
  }

}
