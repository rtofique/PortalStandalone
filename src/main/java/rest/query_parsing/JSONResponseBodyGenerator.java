package rest.query_parsing;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.IOException;
import rest.number_records.NumberRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JSONResponseBodyGenerator implements ResponseBodyGenerator {

  final private ObjectMapper mapper;

  public JSONResponseBodyGenerator()
  {
    this.mapper = new ObjectMapper();
  }


  @Override
  public String generateResponseBody(List<? extends NumberRecord> records) {

      mapper.setSerializationInclusion(Include.NON_NULL);

      //we get a single serialized string here of the body
      String recordsJson;
      try
      {
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
