package rest.number_records;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecordSerializer<T> extends StdSerializer<T> {


  protected final String NUMBER_LABEL = "Number";
  protected final String LATA_LABEL = "LATA";
  protected final String OCN_LABEL = "OCN";
  protected final String AOCN_LABEL = "AOCN";
  protected final String RATE_CENTER_LABEL = "Rate Center";
  protected final String STATE_LABEL = "State";
  protected final String OCN_OVERALL_LABEL = "OCN Overall";
  protected final String EFFECTIVE_DATE_LABEL = "Effective Date";
  protected final String DEFAULT_VALUE = "N/A";

  public RecordSerializer(Class<T> record)
  {
    super(record);
  }

  @Override
  public void serialize(T record, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
  {
    return;
  }

  protected String convertDateToString(LocalDateTime date)
  {
    if(date == null) return null;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    return date.format(formatter);
  }
}
