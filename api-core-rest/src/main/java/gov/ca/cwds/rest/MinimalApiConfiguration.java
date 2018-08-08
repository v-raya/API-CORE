package gov.ca.cwds.rest;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.knowm.dropwizard.sundial.SundialConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.security.configuration.SecurityConfiguration;
import io.dropwizard.Configuration;

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
  private WebSecurityConfiguration webSecurityConfiguration;

  @Nullable
  private YamlShiroConfiguration shiroConfiguration;

  @Nullable
  private SecurityConfiguration securityConfiguration;

  @Valid
  @NotNull
  public SundialConfiguration sundialConfiguration = new SundialConfiguration();

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
  public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
    this.securityConfiguration = securityConfiguration;
  }

  @JsonProperty("sundial")
  public SundialConfiguration getSundialConfiguration() {
    return sundialConfiguration;
  }

  @JsonProperty
  public void setSundialConfiguration(SundialConfiguration sundialConfiguration) {
    this.sundialConfiguration = sundialConfiguration;
  }
}
