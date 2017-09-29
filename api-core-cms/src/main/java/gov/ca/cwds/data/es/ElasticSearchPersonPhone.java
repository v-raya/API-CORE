package gov.ca.cwds.data.es;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.data.std.ApiPhoneAwareWritable;

/**
 * Simplistic, all String representation of ES person.phone_numbers.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonPhone extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, ApiPhoneAwareWritable {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  private String id;

  @JsonProperty("number")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String phoneNumber;

  /**
   * Not specified by Intake Person Auto-complete YAML.
   */
  @JsonIgnore
  private String phoneNumberExtension;

  @JsonProperty("type")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ApiPhoneAware.PhoneType phoneType;

  /**
   * Default constructor.
   */
  public ElasticSearchPersonPhone() {
    // Default, no-op.
  }

  /**
   * Construct a phone from a phone-aware object.
   * 
   * @param other another phone object
   */
  public ElasticSearchPersonPhone(ApiPhoneAware other) {
    if (other != null) {
      if (StringUtils.isNotBlank(other.getPhoneId())) {
        setId(other.getPhoneId());
      }
      setPhoneNumber(other.getPhoneNumber());
      setPhoneType(other.getPhoneType());
    }
  }

  @JsonIgnore
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  @JsonIgnore
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  @Override
  @JsonIgnore
  public String getPhoneNumberExtension() {
    return this.phoneNumberExtension;
  }

  @Override
  @JsonIgnore
  public PhoneType getPhoneType() {
    return this.phoneType;
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @JsonIgnore
  @Override
  public void getPhoneNumberExtension(String phoneNumberExtension) {
    this.phoneNumberExtension = phoneNumberExtension;
  }

  @Override
  public void setPhoneType(PhoneType phoneType) {
    this.phoneType = phoneType;
  }

  @JsonIgnore
  @Override
  public String getPhoneId() {
    return this.id;
  }

  public void setPhoneNumberExtension(String phoneNumberExtension) {
    this.phoneNumberExtension = phoneNumberExtension;
  }
}
