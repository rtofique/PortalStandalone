package rest.number_records;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class InvalidRecordSerializer extends StdSerializer<InvalidNumberRecord> {

  public InvalidRecordSerializer()
  {
    this(null);
  }

  public InvalidRecordSerializer(Class<InvalidNumberRecord> record)
  {
    super(record);
  }

  @Override
  public void serialize(InvalidNumberRecord record, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
  {
    jgen.writeStartObject();
    jgen.writeStringField("phone_number", record.getQuery());
    jgen.writeStringField("phone_number_line", record.getQueryID());
    jgen.writeEndObject();
  }

}
