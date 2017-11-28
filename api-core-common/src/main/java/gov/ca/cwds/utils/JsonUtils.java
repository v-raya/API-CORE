package gov.ca.cwds.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;

/**
 * @author CWDS CALS API Team
 */

public final class JsonUtils {

  private static final ObjectMapper objectMapper = Jackson.newObjectMapper();

  private JsonUtils() {}

  public static String to(Object o) throws JsonProcessingException {
    return objectMapper.writeValueAsString(o);
  }

  public static Object from(String json, Class clazz) throws IOException {
    return objectMapper.readValue(json, clazz);
  }
}
