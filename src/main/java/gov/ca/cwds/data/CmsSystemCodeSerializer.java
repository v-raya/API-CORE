package gov.ca.cwds.data;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import gov.ca.cwds.data.persistence.cms.VarargTuple;

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
  protected final boolean useShortDescription;
  protected final boolean useOtherCd;

  // OPTION: write a factory for contextual serializer. Thread safe?
  protected static Map<VarargTuple<Boolean>, CmsSystemCodeSerializer> serializerStyle =
      new ConcurrentHashMap<>();

  @Inject
  public CmsSystemCodeSerializer(ISystemCodeCache cache) {
    this.cache = cache;
    this.useShortDescription = true;
    this.useOtherCd = false;
  }

  public CmsSystemCodeSerializer(ISystemCodeCache cache, boolean useShortDescription,
      boolean useOtherCd) {
    this.cache = cache;
    this.useShortDescription = useShortDescription;
    this.useOtherCd = useOtherCd;
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
      return new CmsSystemCodeSerializer(this.cache, false, false);
    }

    return new CmsSystemCodeSerializer(this.cache, ann.description(), ann.other());
  }

  @Override
  public void serialize(Short s, JsonGenerator jgen, SerializerProvider sp)
      throws IOException, JsonGenerationException {
    LOGGER.debug("thread id={}", Thread.currentThread().getId());

    if (!useOtherCd && !useShortDescription) {
      // Write ordinary Short.
      if (s != null) {
        jgen.writeNumber(s);
      } else {
        jgen.writeNull();
      }
    } else {
      // Write translated system code.
      jgen.writeStartObject();
      if (s != null) {
        jgen.writeNumberField("sys_id", s);

        // Zero means no translatable value.
        if (cache != null && s.intValue() != 0) {
          final CmsSystemCode code = cache.lookup(s.intValue());
          if (this.useShortDescription) {
            jgen.writeStringField("description", code.getShortDsc());
          }
          if (this.useOtherCd) {
            jgen.writeStringField("logical_id", code.getLgcId());
          }
        } else {
          // Default.
          jgen.writeStringField("description", "");
          jgen.writeStringField("logical_id", "");
        }
      } else {
        jgen.writeNull();
      }

      jgen.writeEndObject();
    }
  }

}
