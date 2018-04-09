package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.security.configuration.SecurityConfiguration;
import io.dropwizard.Configuration;
import javax.annotation.Nullable;
import org.hibernate.validator.constraints.NotEmpty;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

public class MinimalApiConfiguration extends Configuration {

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
  private WebSecurityConfiguration webSecurityConfiguration;

  @Nullable
  private YamlShiroConfiguration shiroConfiguration;

  @Nullable
  private SecurityConfiguration securityConfiguration;

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

  @JsonProperty
  public void setSmartystreetsConfiguration(SmartyStreetsConfiguration smartyStreetsConfiguration) {
    this.smartyStreetsConfiguration = smartyStreetsConfiguration;
  }

  @JsonProperty(value = "shiro")
  public YamlShiroConfiguration getShiroConfiguration() {
    return shiroConfiguration;
  }

  @JsonProperty
  public void setShiroConfiguration(YamlShiroConfiguration shiroConfiguration) {
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

  @JsonProperty(value = "security")
  public SecurityConfiguration getSecurityConfiguration() {
    return securityConfiguration;
  }

  @JsonProperty
  public void setSecurityConfiguration(
      SecurityConfiguration securityConfiguration) {
    this.securityConfiguration = securityConfiguration;
  }
}
