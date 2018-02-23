package gov.ca.cwds.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiPhoneAware;

/**
 * Simple, concrete implementation of interface {@link ApiPhoneAware}.
 * 
 * @author CWDS API Team
 */
public class ReadablePhone implements ApiPhoneAware, Serializable {

  private static final long serialVersionUID = 1L;

  private final String phoneId;
  private final String phoneNumber;
  private final String phoneNumberExtension;
  private final PhoneType phoneType;

  /**
   * Construct a readable phone from all required values.
   * 
   * @param phoneId phone identifier (primary key), if any
   * @param phoneNumber concatenated phone number. Not atomic fields.
   * @param phoneNumberExtension phone extension
   * @param phoneType phone type
   */
  @JsonCreator
  public ReadablePhone(@JsonProperty("phoneId") String phoneId,
      @JsonProperty("phoneNumber") String phoneNumber,
      @JsonProperty("phoneNumberExtension") String phoneNumberExtension,
      @JsonProperty("phoneType") PhoneType phoneType) {
    this.phoneId = phoneId;
    this.phoneNumber = phoneNumber;
    this.phoneNumberExtension = phoneNumberExtension;
    this.phoneType = phoneType;
  }

  @Override
  public String getPhoneId() {
    return phoneId;
  }

  @Override
  public PhoneType getPhoneType() {
    return this.phoneType;
  }

  @Override
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  @Override
  public String getPhoneNumberExtension() {
    return this.phoneNumberExtension;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
