package gov.ca.cwds.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.hibernate.validator.constraints.NotEmpty;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class BaseApiConfiguration extends Configuration {

  /**
   * The application name
   */
  @NotEmpty
  private String applicationName;

  /**
   * The version
   */
  @NotEmpty
  private String version;

  @Nullable
  private SwaggerConfiguration swaggerConfiguration;

  @Nullable
  private SmartyStreetsConfiguration smartyStreetsConfiguration;

  @Nullable
  private TriggerTablesConfiguration triggerTablesConfiguration;

  @Nullable
  private WebSecurityConfiguration webSecurityConfiguration;

  @Nullable
  private ShiroConfiguration shiroConfiguration;

  @JsonProperty
  public String getApplicationName() {
    return applicationName;
  }

  @JsonProperty
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * @return the version
   */
  @JsonProperty
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  @JsonProperty
  public void setVersion(String version) {
    this.version = version;
  }

  @JsonProperty(value = "swagger")
  public SwaggerConfiguration getSwaggerConfiguration() {
    return swaggerConfiguration;
  }

  @JsonProperty
  public void setSwaggerConfiguration(SwaggerConfiguration swaggerConfiguration) {
    this.swaggerConfiguration = swaggerConfiguration;
  }

  @JsonProperty(value = "smartystreets")
  public SmartyStreetsConfiguration getSmartyStreetsConfiguration() {
    return smartyStreetsConfiguration;
  }

  @JsonProperty(value = "triggertables")
  public TriggerTablesConfiguration getTriggerTablesConfiguration() {
    return triggerTablesConfiguration;
  }

  @JsonProperty
  public void setSmartystreetsConfiguration(SmartyStreetsConfiguration smartyStreetsConfiguration) {
    this.smartyStreetsConfiguration = smartyStreetsConfiguration;
  }

  @JsonProperty(value = "shiro")
  public ShiroConfiguration getShiroConfiguration() {
    return shiroConfiguration;
  }

  @JsonProperty
  public void setShiroConfiguration(ShiroConfiguration shiroConfiguration) {
    this.shiroConfiguration = shiroConfiguration;
  }

  @JsonProperty(value = "webSecurity")
  public WebSecurityConfiguration getWebSecurityConfiguration() {
    return webSecurityConfiguration;
  }

  @JsonProperty
  public void setWebSecurityConfiguration(WebSecurityConfiguration webSecurityConfiguration) {
    this.webSecurityConfiguration = webSecurityConfiguration;
  }
}
