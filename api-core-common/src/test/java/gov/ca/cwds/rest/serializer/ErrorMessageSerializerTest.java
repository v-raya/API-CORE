package gov.ca.cwds.rest.serializer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;

public class ErrorMessageSerializerTest {

  Set<ErrorMessage> messages;
  ErrorMessage validationError;
  ErrorMessage businessError;
  JsonGenerator jsonGenerator;
  SerializerProvider serializerProvider;
  Writer jsonWriter;

  ErrorMessageSerializer writer;

  @Before
  public void setup() throws IOException {
    jsonWriter = new StringWriter();
    jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    serializerProvider = new ObjectMapper().getSerializerProvider();

    validationError =
        new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, "not valid", "backend validation");
    businessError =
        new ErrorMessage(ErrorMessage.ErrorType.BUSINESS, "Can't do that", "buisness rule");
    messages = new HashSet<ErrorMessage>();
    messages.add(validationError);
    messages.add(businessError);

    writer = new ErrorMessageSerializer();
  }

  // @Test
  // public void serializeMultipleErrorMessages() throws IOException {
  // writer.serialize(messages,jsonGenerator, serializerProvider);
  // jsonGenerator.flush();
  //
  // String expectedJson = "{\"messages\":[\"Can't do that\",\"not valid\"]}";
  // assertEquals("expected json to contain both error messages",expectedJson,
  // jsonWriter.toString());
  // }
  //
  // @Test
  // public void serializeSingleErrorMessage() throws IOException {
  // messages.remove(businessError);
  //
  // writer.serialize(messages,jsonGenerator, serializerProvider);
  // jsonGenerator.flush();
  //
  // String expectedJson = "{\"messages\":[\"not valid\"]}";
  // assertEquals("expected json to contain a single error messages",expectedJson,
  // jsonWriter.toString());
  // }
  //
  // @Test
  // public void serializeEmptyErrorMessage() throws IOException {
  // messages.clear();
  //
  // writer.serialize(messages,jsonGenerator, serializerProvider);
  // jsonGenerator.flush();
  //
  // String expectedJson = "{\"messages\":[]}";
  // assertEquals("expected json to contain an empty error messages",expectedJson,
  // jsonWriter.toString());
  // }
  //
  // @Test
  // public void serializeNullErrorMessage() throws IOException {
  // messages.clear();
  //
  // writer.serialize(null,jsonGenerator, serializerProvider);
  // jsonGenerator.flush();
  //
  // String expectedJson = "{\"messages\":[]}";
  // assertEquals("expected json to contain an empty messages",expectedJson, jsonWriter.toString());
  // }

}
