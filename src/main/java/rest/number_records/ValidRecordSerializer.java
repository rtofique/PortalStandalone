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
      jgen.writeStringField(LATA_LABEL, rc.getLata());
      jgen.writeStringField(OCN_LABEL, rc.getOcn());
      jgen.writeStringField(AOCN_LABEL, rc.getAocn());
      jgen.writeStringField(RATE_CENTER_LABEL, rc.getRateCenter());
      jgen.writeStringField(STATE_LABEL, rc.getState());
      jgen.writeStringField(OCN_OVERALL_LABEL, rc.getOcnOverall());
      jgen.writeStringField(EFFECTIVE_DATE_LABEL, convertDateToString(rc.getEffectiveDate()));
    }
    jgen.writeEndArray();
    jgen.writeEndObject();
  }

}
