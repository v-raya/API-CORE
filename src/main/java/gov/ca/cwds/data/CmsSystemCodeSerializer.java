package gov.ca.cwds.data;

import java.io.IOException;

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

public class CmsSystemCodeSerializer extends JsonSerializer<Short> implements ContextualSerializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeSerializer.class);

  private final ISystemCodeCache cache;
  protected final boolean useShortDescription;
  protected final boolean useOtherCd;

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

  // OPTION: write a factory for contextual serializer.

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {

    // Find the marker annotation. It can hide in a couple places.
    SystemCodeSerializer ann = property.getAnnotation(SystemCodeSerializer.class);
    if (ann == null) {
      ann = property.getContextAnnotation(SystemCodeSerializer.class);
    }

    if (ann == null) {
      // prov.find
      return new CmsSystemCodeSerializer(this.cache, false, false);
    }

    return new CmsSystemCodeSerializer(this.cache, ann.description(), ann.other());
  }

  @Override
  public void serialize(Short s, JsonGenerator jgen, SerializerProvider sp)
      throws IOException, JsonGenerationException {
    LOGGER.warn("thread id={}", Thread.currentThread().getId());

    if (!useOtherCd && !useShortDescription) {
      jgen.writeNumber(s);
    } else {
      jgen.writeStartObject();
      jgen.writeNumberField("id", s);

      if (cache != null && s != null && s.intValue() != 0) {
        final CmsSystemCode code = cache.lookup(s.intValue());
        if (this.useShortDescription) {
          jgen.writeStringField("description", code.getShortDsc());
        }
        if (this.useOtherCd) {
          jgen.writeStringField("other", code.getOtherCd());
        }
      } else {
        // Default.
        jgen.writeStringField("description", "");
      }

      jgen.writeEndObject();
    }
  }

}
