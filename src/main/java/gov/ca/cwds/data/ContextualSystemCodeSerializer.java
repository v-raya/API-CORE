package gov.ca.cwds.data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

public class ContextualSystemCodeSerializer extends JsonSerializer<Short>
    implements ContextualSerializer {

  protected final boolean useShortDescription;
  protected final boolean useOtherCd;

  public ContextualSystemCodeSerializer() {
    this.useShortDescription = true;
    this.useOtherCd = false;
  }

  public ContextualSystemCodeSerializer(boolean useShortDescription, boolean useOtherCd) {
    this.useShortDescription = useShortDescription;
    this.useOtherCd = useOtherCd;
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {
    // First find annotation used for getter or field:
    SystemCodeSerializer ann = property.getAnnotation(SystemCodeSerializer.class);
    if (ann == null) { // but if missing, default one from class
      ann = property.getContextAnnotation(SystemCodeSerializer.class);
    }
    // If no customization found, just return base instance (this); no need to construct new
    // serializer.
    if (ann == null || !(ann.description() && ann.other())) {
      return this;
    }
    return new ContextualSystemCodeSerializer();
  }

  @Override
  public void serialize(Short s, JsonGenerator jgen, SerializerProvider sp)
      throws IOException, JsonGenerationException {
    jgen.writeStartObject();
    jgen.writeNumberField("id", s);
    jgen.writeStringField("description", "fred");
    jgen.writeEndObject();
  }

}
