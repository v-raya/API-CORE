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
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
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

  private final ISystemCodeCache cache;
  private final boolean showShortDescription;
  private final boolean showLogicalId;
  private final boolean showMetaCategory;

  /**
   * Initial constructor, called by Guice, which provides all dependencies.
   * 
   * @param cache syscode cache implementation.
   */
  @Inject
  public CmsSystemCodeSerializer(ISystemCodeCache cache) {
    this.cache = cache;
    this.showShortDescription = true;
    this.showLogicalId = false;
    this.showMetaCategory = false;
  }

  /**
   * Factory map for this contextual serializer. Saves thread-safe serializer instances by
   * combination of settings.
   * 
   * @see #buildSerializer(ISystemCodeCache, boolean, boolean, boolean)
   */
  protected static Map<BitSet, CmsSystemCodeSerializer> serializerStyles =
      new ConcurrentHashMap<>();

  /**
   * Build a {@link BitSet} from variable array of boolean flags (as arguments as
   * CmsSystemCodeSerializer constructor). BitSet is used by our serializer factory to produce
   * unique settings combinations per serializer as needed.
   * 
   * @param flags variable array of boolean flags (as arguments as CmsSystemCodeSerializer
   *        constructor)
   * @return a BitSet that uniquely identifies serializer settings
   * 
   * @see #buildSerializer(ISystemCodeCache, boolean, boolean, boolean)
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

  /**
   * Reduce object churn by recording serializer instances by settings. Thread-safe by means of
   * {@link ConcurrentHashMap}.
   * 
   * @param cache syscode cache
   * @param showShortDescription show short description flag
   * @param showLogicalId show logical id flag
   * @param showMetaCategory show meta/category flag
   * @return syscode serializer with the given settings
   */
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

  /**
   * <p>
   * Guice finds and injects the dependencies for us. Register this Jackson serializer with the
   * ObjectMapper.
   * </p>
   * 
   * @param om the common ObjectMapper registered with this application
   */
  @Inject
  public void init(final ObjectMapper om) {
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "cms_sys_code", "alpha", ""));
    module.addSerializer(Short.class, this);
    om.registerModule(module);
  }

  /**
   * Construct from all fields. Subsequent constructor, called to create to instances of the
   * serializer per settings.
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

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {

    // Find the marker annotation. It can hide.
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
    if (s == null) {
      jgen.writeNull();
    } else if (s.intValue() == 0 || (cache == null || !(showLogicalId && showShortDescription))) {
      // Zero means no translatable value but store zero for consistency.
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

  /**
   * Getter for show short description.
   * 
   * @return whether to show field, "short description"
   */
  public boolean isShowShortDescription() {
    return showShortDescription;
  }

  /**
   * Getter for show logical id.
   * 
   * @return whether to show field, "logical id"
   */
  public boolean isShowLogicalId() {
    return showLogicalId;
  }

  /**
   * Getter for show meta/category.
   * 
   * @return whether to show field, "meta/category"
   */
  public boolean isShowMetaCategory() {
    return showMetaCategory;
  }

}
