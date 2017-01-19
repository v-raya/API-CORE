package gov.ca.cwds.data;

import java.io.IOException;
import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.CmsSystemCode;
import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;

/**
 * Jackson JSON serializer automatically translates CMS system codes on the fly.
 * 
 * <pre>
 * SimpleModule module =
 *     new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "cms_sys_code", "alpha", ""));
 * module.addSerializer(Short.class,
 *     new CmsSystemCodeSerializer(injector.getInstance(ISystemCodeCache.class)));
 * environment.getObjectMapper().registerModule(module);
 * Guice.createInjector().getInstance(ObjectMapper.class).registerModule(module);
 * 
 * </pre>
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeSerializer extends JsonSerializer<Short> implements ContextualSerializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeSerializer.class);

  protected final ISystemCodeCache cache;
  protected final boolean showShortDescription;
  protected final boolean showLogicalId;
  protected final boolean showMetaCategory;

  // Factory for contextual serializer.
  protected static Map<BitSet, CmsSystemCodeSerializer> serializerStyles =
      new ConcurrentHashMap<>();

  @Inject
  public CmsSystemCodeSerializer(ISystemCodeCache cache) {
    this.cache = cache;
    this.showShortDescription = true;
    this.showLogicalId = false;
    this.showMetaCategory = false;
  }

  /**
   * Construct from all final fields.
   * 
   * <p>
   * See the field list in class {@link CmsSystemCode}.
   * </p>
   * 
   * @param cache system code cache
   * @param showShortDescription short description
   * @param showLogicalId show logical id, such as "CA" for California
   * @param showMetaCategory show the "meta", the system code category
   */
  public CmsSystemCodeSerializer(ISystemCodeCache cache, boolean showShortDescription,
      boolean showLogicalId, boolean showMetaCategory) {
    this.cache = cache;
    this.showShortDescription = showShortDescription;
    this.showLogicalId = showLogicalId;
    this.showMetaCategory = showMetaCategory;
  }

  /**
   * Build a {@link BitSet} from variable array of boolean flags (as arguments as
   * CmsSystemCodeSerializer constructor). BitSet is used by our serializer factory to produce
   * unique settings combinations per serializer as needed.
   * 
   * @param flags variable array of boolean flags (as arguments as CmsSystemCodeSerializer
   *        constructor)
   * @return a BitSet that uniquely identifies serializer settings
   */
  protected static BitSet buildBits(boolean... flags) {
    BitSet bs = new BitSet();

    int counter = 0;
    for (boolean b : flags) {
      if (b) {
        bs.set(counter++);
      }
    }

    return bs;
  }

  protected static CmsSystemCodeSerializer buildSerializer(ISystemCodeCache cache,
      boolean showShortDescription, boolean showLogicalId, boolean showMetaCategory) {
    final BitSet bs =
        buildBits(cache != null, showShortDescription, showLogicalId, showMetaCategory);
    if (!serializerStyles.containsKey(bs)) {
      LOGGER.debug("new CmsSystemCodeSerializer: {}, {}, {}, {}", cache != null,
          showShortDescription, showLogicalId, showMetaCategory);
      serializerStyles.put(bs, new CmsSystemCodeSerializer(cache, showShortDescription,
          showLogicalId, showMetaCategory));
    }

    return serializerStyles.get(bs);
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {

    // Find the marker annotation. It can hide in a couple places.
    SystemCodeSerializer ann = property.getAnnotation(SystemCodeSerializer.class);
    if (ann == null) {
      ann = property.getContextAnnotation(SystemCodeSerializer.class);
    }

    if (ann == null) {
      // Default Short. No translation.
      return buildSerializer(this.cache, false, false, false);
    }

    return buildSerializer(this.cache, ann.description(), ann.logical(), ann.meta());
  }

  @Override
  public void serialize(Short s, JsonGenerator jgen, SerializerProvider sp)
      throws IOException, JsonGenerationException {
    if (s == null || s.intValue() == 0) {
      // Zero means no translatable value.
      jgen.writeNull();
    } else if (cache == null || !(showLogicalId && showShortDescription)) {
      // Cache disabled or no syscode fields to show. Write ordinary short.
      jgen.writeNumber(s);
    } else {
      // Translate system code.
      jgen.writeStartObject();
      jgen.writeNumberField("sys_id", s);

      final CmsSystemCode code = cache.lookup(s.intValue());
      if (code != null) {
        if (this.showShortDescription) {
          jgen.writeStringField("short_description", code.getShortDsc());
        }
        if (this.showLogicalId) {
          jgen.writeStringField("logical_id", code.getLgcId());
        }
      } else {
        LOGGER.error("UNKNOWN SYS_ID: {}! NOT TRANSLATED!", s.intValue());
        jgen.writeStringField("short_description", "NOT TRANSLATED");
      }

      jgen.writeEndObject();
    }
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
