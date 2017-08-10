package gov.ca.cwds.rest.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

public class ErrorMessageSerializer extends JsonSerializer<ErrorMessage> {

  @Override
  public void serialize(ErrorMessage errorMessages, JsonGenerator generator,
      SerializerProvider serializers) throws IOException {
    generator.writeString(errorMessages.getMessage());
  }

}
