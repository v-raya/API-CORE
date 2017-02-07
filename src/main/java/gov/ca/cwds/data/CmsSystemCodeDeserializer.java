package gov.ca.cwds.data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

/**
 * Custom JSON deserializer for CMS system code translation.
 * 
 * <h3>Usage on persistence class:</h3>
 * 
 * <h4>Class declaration:</h4>
 * 
 * <pre>
 * &#64;Entity &#64;Table(name = "ATTRNY_T") &#64;JsonPropertyOrder(alphabetic =
 * true) &#64;JsonIgnoreProperties(ignoreUnknown = true) public class Attorney extends
 * CmsPersistentObject implements IPersonAware, IMultiplePhonesAware {
 * </pre>
 * 
 * <h4>Field declaration:</h4>
 * 
 * <pre>
 * &#64;SystemCodeSerializer(logical = true, description = true)
 * &#64;JsonDeserialize(using = CmsSystemCodeDeserializer.class)
 * &#64;Type(type = "short")
 * &#64;Column(name = "GVR_ENTC")
 * private Short governmentEntityType;
 * </pre>
 * 
 * @author CWDS API Team
 * @see CmsSystemCodeSerializer
 */
public class CmsSystemCodeDeserializer extends StdDeserializer<Short> {

  /**
   * Base serialization version. Increment by class change.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor. Required for StdDeserializer.
   */
  public CmsSystemCodeDeserializer() {
    this(null);
  }

  /**
   * Construct for target class type. Required for StdDeserializer.
   * 
   * @param vc target class to monitor. Should be Short.
   */
  public CmsSystemCodeDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Short deserialize(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    Short sysId = null;
    final JsonNode node = jp.getCodec().readTree(jp);

    // Full style (sys_id, logical_id, description).
    // EXAMPLE:
    // "primaryLanguageType": {
    // "sys_id": 1274,
    // "short_description": "Spanish",
    // "logical_id": "01"
    // },

    final IntNode sn = (IntNode) node.get("sys_id");
    if (sn != null) {
      sysId = sn.numberValue().shortValue();
    } else if (node instanceof IntNode) {
      // Try original style.
      // EXAMPLE:
      // "primaryLanguageType": 1274,
      sysId = node.numberValue().shortValue();
    }

    return sysId;
  }

}
