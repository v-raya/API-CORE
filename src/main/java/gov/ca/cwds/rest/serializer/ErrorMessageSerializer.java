package gov.ca.cwds.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.inject.spi.Message;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

import java.io.IOException;
import java.util.Set;

//public class ErrorMessageSerializer extends JsonSerializer<Set<ErrorMessage>> {
public class ErrorMessageSerializer extends JsonSerializer<ErrorMessage> {

//    public void serialize(Set<ErrorMessage> errorMessages, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        public void serialize(ErrorMessage errorMessages, JsonGenerator generator, SerializerProvider serializers) throws IOException {
          generator.writeString(errorMessages.getMessage());
//        generator.writeStartObject();
//        generator.writeFieldName("messages");
//        generator.writeStartArray();
//         if (errorMessages != null && ! errorMessages.isEmpty()){
//            for (ErrorMessage message : errorMessages) {
//                generator.writeString(message.getMessage());
//            }
//         }
//        generator.writeEndArray();
//        generator.writeEndObject();
    }
}
