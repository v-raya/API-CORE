package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiAddressAwareWritable;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;

/**
 * Simplistic, all String representation of ES person.address.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ElasticSearchPersonAddress extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, ApiAddressAwareWritable {

  private String id;

  @JsonIgnore
  private String streetAddress;

  @JsonProperty("city")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String city;

  @JsonIgnore
  private String state;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonProperty("state_code")
  private String stateCode;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonProperty("state_name")
  private String stateName;

  // Bug #141508231: county not in Intake API swagger.yml. Intake JSON parsing error.
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String county;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonProperty("zip")
  private String zip;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("zip_4")
  private String zip4;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  private String type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("unit_type")
  private String unitType;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("unit_number")
  private String unitNumber;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("street_number")
  private String streetNumber;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("street_name")
  private String streetName;

  @JsonProperty("effective_start_date")
  private String effectiveStartDate;

  @JsonProperty("effective_end_date")
  private String effectiveEndDate;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  /**
   * Default constructor.
   */
  public ElasticSearchPersonAddress() {
    // Default
  }

  /**
   * Construct from all fields.
   * 
   * @param id pk/identifier
   * @param streetAddress concatenated street address
   * @param city city
   * @param state two-letter state code
   * @param zip 5-digit zip
   * @param type address type, if any
   */
  public ElasticSearchPersonAddress(String id, String streetAddress, String city, String state,
      String zip, String type) {
    this.id = id;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
  }

  /**
   * Construct from persistence address.
   * 
   * @param address address persistence bean
   */
  public ElasticSearchPersonAddress(ApiAddressAware address) {
    this.id = address.getAddressId();
    this.city = address.getCity();
    this.county = address.getCounty();
    this.state = address.getState();

    this.zip = address.getZip();
    this.zip4 = address.getApiAdrZip4();
    this.streetName = address.getStreetName();
    this.streetNumber = address.getStreetNumber();
    this.unitNumber = address.getApiAdrUnitNumber();

    this.effectiveStartDate = DomainChef.cookDate(address.getClientAddressEffectiveStartDate());
    this.effectiveEndDate = DomainChef.cookDate(address.getClientAddressEffectiveEndDate());

    if (ElasticSearchPerson.systemCodes != null) {
      if (address.getStateCd() != null && address.getStateCd().intValue() != 0) {
        final SystemCode sysCode =
            ElasticSearchPerson.systemCodes.getSystemCode(address.getStateCd());
        this.stateName = sysCode.getShortDescription();
        this.stateCode = sysCode.getLogicalId();
      }

      if (address.getApiAdrAddressType() != null
          && address.getApiAdrAddressType().intValue() != 0) {
        this.type = ElasticSearchPerson.systemCodes.getSystemCode(address.getApiAdrAddressType())
            .getShortDescription();
      }

      if (address.getApiAdrUnitType() != null && address.getApiAdrUnitType().intValue() != 0) {
        this.unitType = ElasticSearchPerson.systemCodes.getSystemCode(address.getApiAdrUnitType())
            .getShortDescription();
      }
    } else {
      ElasticSearchPerson.LOGGER.error("SYSCODE TRANSLATOR NOT SET!!!");
    }
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @JsonIgnore
  @Override
  public String getStreetAddress() {
    return streetAddress;
  }

  @Override
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String city) {
    this.city = city;
  }

  @JsonIgnore
  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @JsonIgnore
  @Override
  public String getCounty() {
    return county;
  }

  @Override
  public void setCounty(String county) {
    this.county = county;
  }

  @JsonIgnore
  @Override
  public String getZip() {
    return zip;
  }

  @Override
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * Getter for optional address type.
   * 
   * @return address type, per Intake contract, if provided
   */
  @JsonIgnore
  public String getType() {
    return type;
  }

  /**
   * Setter for optional address type
   * 
   * @param type address type, per Intake contract
   */
  public void setType(String type) {
    this.type = type;
  }

  @JsonIgnore
  @Override
  public String getAddressId() {
    return this.id;
  }

  @JsonIgnore
  @Override
  public Short getStateCd() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getStreetName() {
    return this.streetName;
  }

  @JsonIgnore
  @Override
  public String getStreetNumber() {
    return this.streetNumber;
  }

  @JsonIgnore
  @Override
  public String getApiAdrZip4() {
    return this.zip4;
  }

  @JsonIgnore
  @Override
  public String getApiAdrUnitNumber() {
    return this.unitNumber;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getStateName() {
    return stateName;
  }

  @SuppressWarnings("javadoc")
  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getZip4() {
    return zip4;
  }

  @SuppressWarnings("javadoc")
  public void setZip4(String zip4) {
    this.zip4 = zip4;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getUnitType() {
    return unitType;
  }

  @SuppressWarnings("javadoc")
  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getUnitNumber() {
    return unitNumber;
  }

  @SuppressWarnings("javadoc")
  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  @SuppressWarnings("javadoc")
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  @SuppressWarnings("javadoc")
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @JsonIgnore
  @Override
  public Short getApiAdrAddressType() {
    return null;
  }

  @JsonIgnore
  @Override
  public Short getApiAdrUnitType() {
    return null;
  }

  @SuppressWarnings("javadoc")
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  @SuppressWarnings("javadoc")
  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public String getEffectiveStartDate() {
    return effectiveStartDate;
  }

  public void setEffectiveStartDate(String effectiveStartDate) {
    this.effectiveStartDate = effectiveStartDate;
  }

  public String getEffectiveEndDate() {
    return effectiveEndDate;
  }

  public void setEffectiveEndDate(String effectiveEndDate) {
    this.effectiveEndDate = effectiveEndDate;
  }
}
