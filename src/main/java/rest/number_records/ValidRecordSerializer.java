package rest.number_records;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class ValidRecordSerializer extends RecordSerializer<ValidNumberRecord> {

  public ValidRecordSerializer()
  {
    this(null);
  }

  public ValidRecordSerializer(Class<ValidNumberRecord> record)
  {
    super(record);
  }

  @Override
  public void serialize(ValidNumberRecord record, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
  {
    jgen.writeStartObject();
    jgen.writeStringField(NUMBER_LABEL, record.getQuery());
    jgen.writeFieldName("Rate Centers");
    jgen.writeStartArray();
    for(final RateCenter rc : record.getRateCenter())
    {
      jgen.writeStartObject();
      jgen.writeStringField(LATA_LABEL, getOrDefault(rc.getLata(), DEFAULT_VALUE));
      jgen.writeStringField(OCN_LABEL, getOrDefault(rc.getOcn(), DEFAULT_VALUE));
      jgen.writeStringField(AOCN_LABEL, getOrDefault(rc.getAocn(), DEFAULT_VALUE));
      jgen.writeStringField(RATE_CENTER_LABEL, getOrDefault(rc.getRateCenter(), DEFAULT_VALUE));
      jgen.writeStringField(STATE_LABEL, getOrDefault(rc.getState(), DEFAULT_VALUE));
      jgen.writeStringField(OCN_OVERALL_LABEL, getOrDefault(rc.getOcnOverall(), DEFAULT_VALUE));
      jgen.writeStringField(EFFECTIVE_DATE_LABEL, getOrDefault(convertDateToString(rc.getEffectiveDate()), DEFAULT_VALUE));
      jgen.writeEndObject();
    }
    jgen.writeEndArray();
    jgen.writeStringField("Record Found", record.isResolvable() + "");
    jgen.writeEndObject();
  }

  private String getOrDefault(String field, String defaultValue)
  {
    return (field != null) ? field : defaultValue;
  }

}
