package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

public class YamlShiroConfiguration extends ShiroConfiguration {
  @JsonProperty("ini")
  private Map<String, Map<String, String>> ini;

  public Map<String, Map<String, String>> getIni() {
    return ini;
  }

  public void setIni(Map<String, Map<String, String>> ini) {
    this.ini = ini;
  }
}
