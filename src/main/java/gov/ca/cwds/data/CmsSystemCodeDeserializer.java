package gov.ca.cwds.data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class CmsSystemCodeDeserializer extends StdDeserializer<Short> {

  public CmsSystemCodeDeserializer() {
    this(null);
  }

  public CmsSystemCodeDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Short deserialize(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    final JsonNode node = jp.getCodec().readTree(jp);
    final IntNode sn = (IntNode) node.get("sys_id");
    Short sysId = null;

    if (sn != null) {
      sysId = sn.numberValue().shortValue();
    }

    return sysId;
  }

}
