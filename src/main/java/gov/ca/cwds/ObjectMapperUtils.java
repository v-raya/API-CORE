package gov.ca.cwds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Static functions for ObjectMapper creation and configuration.
 * 
 * @author CWDS API Team
 */
public class ObjectMapperUtils {

  /**
   * Create a new ObjectMapper
   * 
   * @return ObjectMapper
   */
  public static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    configureObjectMapper(objectMapper);
    return objectMapper;
  }

  /**
   * Configure given ObjectMapper
   * 
   * @param objectMapper ObjectMapper to configure
   */
  public static void configureObjectMapper(ObjectMapper objectMapper) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    // The mainframe DB2 database runs in PST, and so we must too.
    final TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

    final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    fmt.setTimeZone(tz);
    objectMapper.setDateFormat(fmt);
    objectMapper.getSerializationConfig().with(fmt);
    objectMapper.setTimeZone(tz);
    objectMapper.getSerializationConfig().with(tz);
  }
}
