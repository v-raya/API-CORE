package gov.ca.cwds.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

import java.io.IOException;

public class ErrorMessageSerializer extends JsonSerializer<ErrorMessage> {
        public void serialize(ErrorMessage errorMessages, JsonGenerator generator, SerializerProvider serializers) throws IOException {
            generator.writeString(errorMessages.getMessage());
    }
}
