package gov.ca.cwds.data.es;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.ApiSysCodeAware;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.CmsSystemCode;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiAddressAwareWritable;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.data.std.ApiPhoneAwareWritable;
import gov.ca.cwds.inject.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Generic "person" class for ElasticSearch results with a String id and optional nested person
 * specialty document.
 * 
 * @author CWDS API Team
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticSearchPerson implements ApiTypedIdentifier<String> {

  /**
   * ElasticSearch field names for document type people.person.
   * 
   * @author CWDS API Team
   * @deprecated retrieves ES document objects for the obsolete Person Search.
   */
  @Deprecated
  public enum ESColumn {
    /**
     * ElasticSearch identifier
     */
    ID("id", String.class, ""),

    /**
     * first name
     */
    FIRST_NAME("first_name", String.class, ""),

    /**
     * middle name
     */
    MIDDLE_NAME("middle_name", String.class, ""),

    /**
     * last name
     */
    LAST_NAME("last_name", String.class, ""),

    /**
     * gender code (M,F,U)
     */
    GENDER("gender", String.class, "U"),

    /**
     * birth date
     */
    BIRTH_DATE("date_of_birth", String.class, null),

    /**
     * Social Security Number
     */
    SSN("ssn", String.class, null),

    /**
     * CWDS API class type, usually a domain class.
     */
    TYPE("type", String.class, null),

    /**
     * Nested JSON from {@link #TYPE} class.
     */
    SOURCE("source", String.class, null);

    /**
     * ElasticSearch column name.
     */
    final String col;

    /**
     * Value's data type as a Java Class.
     */
    final Class<? extends Serializable> klazz;

    /**
     * Default value, if no value is provided.
     */
    final Object defaultVal;

    /**
     * Constructor populates immutable members.
     * 
     * @param col ES column name
     * @param klazz Java Class of value
     * @param defaultVal default value. Must be assignable from {@link #klazz}.
     */
    ESColumn(String col, Class<? extends Serializable> klazz, Object defaultVal) {
      this.col = col;
      this.klazz = klazz;
      this.defaultVal = defaultVal;
    }

    /**
     * @return ElasticSearch column name
     */
    public final String getCol() {
      return col;
    }

    /**
     * @return target data type. Must be assignable from the ElasticSearch result data type.
     */
    public final Class<? extends Serializable> getKlazz() {
      return klazz;
    }

    /**
     * @return default value for target field that lacking a corresponding value in the
     *         ElasticSearch result Map
     */
    public final Object getDefaultVal() {
      return defaultVal;
    }
  }


  /**
   * Enum of optional collections under ES Person, including relationships, referrals, screenings,
   * and cases.
   */
  public enum ESOptionalCollection {
    NONE, REFERRAL, SCREENING, CASE, RELATIONSHIP, AKA
  }

  /**
   * Legacy descriptor.
   * 
   * @author CWDS API Team
   */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public static class ElasticSearchLegacyDescriptor extends ApiObjectIdentity {

    private static final long serialVersionUID = 2802094735397568904L;

    @JsonProperty("legacy_id")
    private String legacyId;

    @JsonProperty("legacy_ui_id")
    private String legacyUiId;

    @JsonProperty("legacy_last_updated")
    private String legacyLastUpdated;

    @JsonProperty("legacy_table_name")
    private String legacyTableName;

    @JsonProperty("legacy_table_description")
    private String legacyTableDescription;

    /**
     * Default no-arg constructor
     */
    public ElasticSearchLegacyDescriptor() {}

    /**
     * Create with fields
     * 
     * @param legacyId Legacy ID
     * @param legacyUiId Legacy UI ID
     * @param legacyLastUpdated Legacy last updated time stamp
     * @param legacyTableName Legacy table name
     * @param legacyTableDescription Legacy table description
     */
    public ElasticSearchLegacyDescriptor(String legacyId, String legacyUiId,
        String legacyLastUpdated, String legacyTableName, String legacyTableDescription) {
      super();
      this.legacyId = legacyId;
      this.legacyUiId = legacyUiId;
      this.legacyLastUpdated = legacyLastUpdated;
      this.legacyTableName = legacyTableName;
      this.legacyTableDescription = legacyTableDescription;
    }

    public String getLegacyId() {
      return legacyId;
    }

    public void setLegacyId(String legacyId) {
      this.legacyId = legacyId;
    }

    public String getLegacyUiId() {
      return legacyUiId;
    }

    public void setLegacyUiId(String legacyUiId) {
      this.legacyUiId = legacyUiId;
    }

    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }

    public void setLegacyLastUpdated(String legacyLastUpdated) {
      this.legacyLastUpdated = legacyLastUpdated;
    }

    public String getLegacyTableName() {
      return legacyTableName;
    }

    public void setLegacyTableName(String legacyTableName) {
      this.legacyTableName = legacyTableName;
    }

    public String getLegacyTableDescription() {
      return legacyTableDescription;
    }

    public void setLegacyTableDescription(String legacyTableDescription) {
      this.legacyTableDescription = legacyTableDescription;
    }
  }

  /**
   * Simplistic, all String representation of ES person.address.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonAddress extends ApiObjectIdentity
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

      if (systemCodes != null) {
        if (address.getStateCd() != null && address.getStateCd().intValue() != 0) {
          final CmsSystemCode sysCode = systemCodes.lookup(address.getStateCd().intValue());
          this.stateName = sysCode.getLongDsc();
          this.stateCode = sysCode.getLgcId();
        }

        if (address.getApiAdrAddressType() != null
            && address.getApiAdrAddressType().intValue() != 0) {
          this.type = systemCodes.lookup(address.getApiAdrAddressType().intValue()).getLongDsc();
        }

        if (address.getApiAdrUnitType() != null && address.getApiAdrUnitType().intValue() != 0) {
          this.unitType = systemCodes.lookup(address.getApiAdrUnitType().intValue()).getLongDsc();
        }
      } else {
        LOGGER.error("SYSCODE TRANSLATOR NOT SET!!!");
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

    // @JsonIgnore
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
    public String getStateName() {
      return stateName;
    }

    public void setStateName(String stateName) {
      this.stateName = stateName;
    }

    @JsonIgnore
    public String getZip4() {
      return zip4;
    }

    public void setZip4(String zip4) {
      this.zip4 = zip4;
    }

    @JsonIgnore
    public String getUnitType() {
      return unitType;
    }

    public void setUnitType(String unitType) {
      this.unitType = unitType;
    }

    @JsonIgnore
    public String getUnitNumber() {
      return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
      this.unitNumber = unitNumber;
    }

    public void setStreetNumber(String streetNumber) {
      this.streetNumber = streetNumber;
    }

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

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Screening allegation.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonAllegation extends ApiObjectIdentity
      implements ApiTypedIdentifier<String> {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("legacy_allegation_id")
    @Deprecated
    private String legacyId;

    @JsonProperty("allegation_description")
    private String allegationDescription;

    @JsonProperty("disposition_description")
    private String dispositionDescription;

    @JsonProperty("disposition_id")
    private String dispositionId;

    @JsonProperty("perpetrator_id")
    @Deprecated
    private String perpetratorId;

    @JsonProperty("perpetrator_first_name")
    @Deprecated
    private String perpetratorFirstName;

    @JsonProperty("perpetrator_last_name")
    @Deprecated
    private String perpetratorLastName;

    @JsonProperty("perpetrator_legacy_client_id")
    @Deprecated
    private String perpetratorLegacyClientId;

    @JsonProperty("victim_id")
    @Deprecated
    private String victimId;

    @JsonProperty("victim_first_name")
    @Deprecated
    private String victimFirstName;

    @JsonProperty("victim_last_name")
    @Deprecated
    private String victimLastName;

    @JsonProperty("victim_legacy_client_id")
    @Deprecated
    private String victimLegacyClientId;

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @JsonProperty("victim")
    private ElasticSearchPersonNestedPerson victim = new ElasticSearchPersonNestedPerson();

    @JsonProperty("perpetrator")
    private ElasticSearchPersonNestedPerson perpetrator = new ElasticSearchPersonNestedPerson();

    public ElasticSearchPersonNestedPerson getVictim() {
      return victim;
    }

    public void setVictim(ElasticSearchPersonNestedPerson victim) {
      this.victim = victim;
    }

    public ElasticSearchPersonNestedPerson getPerpetrator() {
      return perpetrator;
    }

    public void setPerpetrator(ElasticSearchPersonNestedPerson perpetrator) {
      this.perpetrator = perpetrator;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    @Deprecated
    public String getLegacyId() {
      return legacyId;
    }

    @Deprecated
    public void setLegacyId(String legacyId) {
      this.legacyId = legacyId;
    }

    public String getAllegationDescription() {
      return allegationDescription;
    }

    public void setAllegationDescription(String allegationDescription) {
      this.allegationDescription = allegationDescription;
    }

    public String getDispositionDescription() {
      return dispositionDescription;
    }

    public void setDispositionDescription(String dispositionDescription) {
      this.dispositionDescription = dispositionDescription;
    }

    public String getDispositionId() {
      return dispositionId;
    }

    public void setDispositionId(String dispositionId) {
      this.dispositionId = dispositionId;
    }

    @Deprecated
    public String getPerpetratorId() {
      return perpetratorId;
    }

    @Deprecated
    public void setPerpetratorId(String perpetratorId) {
      this.perpetratorId = perpetratorId;
    }

    @Deprecated
    public String getPerpetratorFirstName() {
      return perpetratorFirstName;
    }

    @Deprecated
    public void setPerpetratorFirstName(String perpetratorFirstName) {
      this.perpetratorFirstName = perpetratorFirstName;
    }

    @Deprecated
    public String getPerpetratorLastName() {
      return perpetratorLastName;
    }

    @Deprecated
    public void setPerpetratorLastName(String perpetratorLastName) {
      this.perpetratorLastName = perpetratorLastName;
    }

    @Deprecated
    public String getPerpetratorLegacyClientId() {
      return perpetratorLegacyClientId;
    }

    @Deprecated
    public void setPerpetratorLegacyClientId(String perpetratorLegacyClientId) {
      this.perpetratorLegacyClientId = perpetratorLegacyClientId;
    }

    @Deprecated
    public String getVictimId() {
      return victimId;
    }

    @Deprecated
    public void setVictimId(String victimId) {
      this.victimId = victimId;
    }

    @Deprecated
    public String getVictimFirstName() {
      return victimFirstName;
    }

    @Deprecated
    public void setVictimFirstName(String victimFirstName) {
      this.victimFirstName = victimFirstName;
    }

    @Deprecated
    public String getVictimLastName() {
      return victimLastName;
    }

    @Deprecated
    public void setVictimLastName(String victimLastName) {
      this.victimLastName = victimLastName;
    }

    @Deprecated
    public String getVictimLegacyClientId() {
      return victimLegacyClientId;
    }

    @Deprecated
    public void setVictimLegacyClientId(String victimLegacyClientId) {
      this.victimLegacyClientId = victimLegacyClientId;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Relationship.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonRelationship extends ApiObjectIdentity {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("related_person_id")
    private String relatedPersonId;

    @JsonProperty("related_person_first_name")
    private String relatedPersonFirstName;

    @JsonProperty("related_person_last_name")
    private String relatedPersonLastName;

    @JsonProperty("indexed_person_relationship")
    private String indexedPersonRelationship;

    @JsonProperty("related_person_legacy_id")
    @Deprecated
    private String relatedPersonLegacyId;

    @JsonProperty("related_person_legacy_source_table")
    @Deprecated
    private String relatedPersonLegacySourceTable;

    @JsonProperty("related_person_relationship")
    private String relatedPersonRelationship;

    @JsonProperty("relationship_context")
    private String relationshipContext;

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @JsonIgnore
    public String getRelatedPersonId() {
      return relatedPersonId;
    }

    public void setRelatedPersonId(String relatedPersonId) {
      this.relatedPersonId = relatedPersonId;
    }

    @JsonIgnore
    public String getRelatedPersonFirstName() {
      return relatedPersonFirstName;
    }

    public void setRelatedPersonFirstName(String relatedPersonFirstName) {
      this.relatedPersonFirstName = relatedPersonFirstName;
    }

    @JsonIgnore
    public String getRelatedPersonLastName() {
      return relatedPersonLastName;
    }

    public void setRelatedPersonLastName(String relatedPersonLastName) {
      this.relatedPersonLastName = relatedPersonLastName;
    }

    @JsonIgnore
    public String getIndexedPersonRelationship() {
      return indexedPersonRelationship;
    }

    public void setIndexedPersonRelationship(String indexedPersonRelationship) {
      this.indexedPersonRelationship = indexedPersonRelationship;
    }

    @Deprecated
    public String getRelatedPersonLegacyId() {
      return relatedPersonLegacyId;
    }

    @Deprecated
    public void setRelatedPersonLegacyId(String relatedPersonLegacyId) {
      this.relatedPersonLegacyId = relatedPersonLegacyId;
    }

    @Deprecated
    public String getRelatedPersonLegacySourceTable() {
      return relatedPersonLegacySourceTable;
    }

    @Deprecated
    public void setRelatedPersonLegacySourceTable(String relatedPersonLegacySourceTable) {
      this.relatedPersonLegacySourceTable = relatedPersonLegacySourceTable;
    }

    public String getRelatedPersonRelationship() {
      return relatedPersonRelationship;
    }

    public void setRelatedPersonRelationship(String relatedPersonRelationship) {
      this.relatedPersonRelationship = relatedPersonRelationship;
    }

    public String getRelationshipContext() {
      return relationshipContext;
    }

    public void setRelationshipContext(String relationshipContext) {
      this.relationshipContext = relationshipContext;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Screening nested person, such as reporter or assigned social worker.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonNestedPerson extends ApiObjectIdentity
      implements ApiTypedIdentifier<String> {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @JsonIgnore
    @Deprecated
    protected String legacyPersonId;

    @JsonIgnore
    @Deprecated
    protected String legacyLastUpdated;

    @JsonIgnore
    @Deprecated
    private String legacySourceTable;

    /**
     * Getter for legacy person id. Child classes override the JSON field name, per the document
     * mapping.
     * 
     * @return legacy person id
     */
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    @Deprecated
    public void setLegacyClientId(String legacyClientId) {
      this.legacyPersonId = legacyClientId;
    }

    @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }

    @Deprecated
    public void setLegacyLastUpdated(String legacyLastUpdated) {
      this.legacyLastUpdated = legacyLastUpdated;
    }

    @Deprecated
    public String getLegacySourceTable() {
      return legacySourceTable;
    }

    @Deprecated
    public void setLegacySourceTable(String legacySourceTable) {
      this.legacySourceTable = legacySourceTable;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Screening person for "all_people" element.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonAny extends ElasticSearchPersonNestedPerson {

    @JsonProperty("legacy_source_table")
    @Deprecated
    private String legacySourceTable;

    private List<String> roles = new ArrayList<>();

    @Override
    @JsonProperty("legacy_id")
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }

    @Override
    @Deprecated
    public String getLegacySourceTable() {
      return legacySourceTable;
    }

    @Override
    @Deprecated
    public void setLegacySourceTable(String legacySourceTable) {
      this.legacySourceTable = legacySourceTable;
    }

    public List<String> getRoles() {
      return roles;
    }

    public void setRoles(List<String> roles) {
      this.roles = roles;
    }

  }

  /**
   * Screening reporter.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonReporter extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_reporter_id")
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }
  }

  /**
   * Screening assigned social worker.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonSocialWorker extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_assigned_social_worker_id")
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }

    @Override
    @JsonProperty("legacy_assigned_social_worker_last_updated")
    @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }
  }

  /**
   * Screening staff.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonStaff extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_staff_id")
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }
  }

  /**
   * Aka for "other client name".
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonAka implements Serializable {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("last_name")
    private String lastLast;

    @JsonProperty("middle_name")
    private String middleMiddle;

    @JsonProperty("name_type")
    private String nameName;

    @JsonProperty("prefix")
    private String prefix;

    @JsonProperty("suffix")
    private String suffix;

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @JsonIgnore
    public String getLastLast() {
      return lastLast;
    }

    public void setLastLast(String lastLast) {
      this.lastLast = lastLast;
    }

    @JsonIgnore
    public String getMiddleMiddle() {
      return middleMiddle;
    }

    public void setMiddleMiddle(String middleMiddle) {
      this.middleMiddle = middleMiddle;
    }

    @JsonIgnore
    public String getNameName() {
      return nameName;
    }

    public void setNameName(String nameName) {
      this.nameName = nameName;
    }

    @JsonIgnore
    public String getPrefix() {
      return prefix;
    }

    public void setPrefix(String prefix) {
      this.prefix = prefix;
    }

    @JsonIgnore
    public String getSuffix() {
      return suffix;
    }

    public void setSuffix(String suffix) {
      this.suffix = suffix;
    }

    @JsonIgnore
    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }

  }

  /**
   * Screening.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonScreening extends ApiObjectIdentity
      implements ApiTypedIdentifier<String> {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    private String id;

    @JsonProperty("referral_id")
    private String referralId;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("county_name")
    private String countyName;

    @JsonProperty("decision")
    private String decision;

    @JsonProperty("response_time")
    private String responseTime;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("reporter")
    private ElasticSearchPersonReporter reporter = new ElasticSearchPersonReporter();

    @JsonProperty("assigned_social_worker")
    private ElasticSearchPersonSocialWorker assignedSocialWorker =
        new ElasticSearchPersonSocialWorker();

    @JsonProperty("staff_name")
    private ElasticSearchPersonStaff staffName = new ElasticSearchPersonStaff();

    @JsonProperty("allegations")
    private List<ElasticSearchPersonAllegation> allegations = new ArrayList<>();

    @JsonProperty("all_people")
    private List<ElasticSearchPersonAny> allPeople = new ArrayList<>();

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    @JsonIgnore
    public Date getStartDate() {
      return DomainChef.uncookDateString(startDate);
    }

    public void setStartDate(Date startDate) {
      this.startDate = DomainChef.cookDate(startDate);
    }

    @JsonIgnore
    public Date getEndDate() {
      return DomainChef.uncookDateString(endDate);
    }

    public void setEndDate(Date endDate) {
      this.endDate = DomainChef.cookDate(endDate);
    }

    @JsonIgnore
    public String getCountyName() {
      return countyName;
    }

    public void setCountyName(String countyName) {
      this.countyName = countyName;
    }

    @JsonIgnore
    public String getDecision() {
      return decision;
    }

    public void setDecision(String decision) {
      this.decision = decision;
    }

    @JsonIgnore
    public String getResponseTime() {
      return responseTime;
    }

    public void setResponseTime(String responseTime) {
      this.responseTime = responseTime;
    }

    @JsonIgnore
    public String getServiceName() {
      return serviceName;
    }

    public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
    }

    @JsonIgnore
    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    @JsonIgnore
    public ElasticSearchPersonReporter getReporter() {
      return reporter;
    }

    public void setReporter(ElasticSearchPersonReporter reporter) {
      this.reporter = reporter;
    }

    @JsonIgnore
    public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
      return assignedSocialWorker;
    }

    public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
      this.assignedSocialWorker = assignedSocialWorker;
    }

    @JsonIgnore
    public ElasticSearchPersonStaff getStaffName() {
      return staffName;
    }

    public void setStaffName(ElasticSearchPersonStaff staffName) {
      this.staffName = staffName;
    }

    @JsonIgnore
    public List<ElasticSearchPersonAllegation> getAllegations() {
      return allegations;
    }

    public void setAllegations(List<ElasticSearchPersonAllegation> allegations) {
      this.allegations = allegations;
    }

    @JsonIgnore
    public List<ElasticSearchPersonAny> getAllPeople() {
      return allPeople;
    }

    public void setAllPeople(List<ElasticSearchPersonAny> allPeople) {
      this.allPeople = allPeople;
    }

    public String getReferralId() {
      return referralId;
    }

    public void setReferralId(String referralId) {
      this.referralId = referralId;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Referral.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonReferral extends ApiObjectIdentity
      implements ApiTypedIdentifier<String> {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("legacy_referral_id")
    @Deprecated
    private String legacyId;

    @JsonProperty("legacy_last_updated")
    @Deprecated
    private String legacyLastUpdated;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("county_name")
    private String countyName;

    @JsonProperty("county_id")
    private String countyId;

    @JsonProperty("response_time")
    private String responseTime;

    @JsonProperty("response_time_id")
    private String responseTimeId;

    @JsonProperty("reporter")
    private ElasticSearchPersonReporter reporter = new ElasticSearchPersonReporter();

    @JsonProperty("assigned_social_worker")
    private ElasticSearchPersonSocialWorker assignedSocialWorker =
        new ElasticSearchPersonSocialWorker();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("allegations")
    private List<ElasticSearchPersonAllegation> allegations = new ArrayList<>();

    @JsonProperty("access_limitation")
    private ElasticSearchAccessLimitation accessLimitation = new ElasticSearchAccessLimitation();

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    @JsonIgnore
    @Deprecated
    public String getLegacyId() {
      return legacyId;
    }

    @Deprecated
    public void setLegacyId(String legacyId) {
      this.legacyId = legacyId;
    }

    @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }

    @Deprecated
    public void setLegacyLastUpdated(String legacyLastUpdated) {
      this.legacyLastUpdated = legacyLastUpdated;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public String getCountyName() {
      return countyName;
    }

    public void setCountyName(String countyName) {
      this.countyName = countyName;
    }

    public String getResponseTime() {
      return responseTime;
    }

    public void setResponseTime(String responseTime) {
      this.responseTime = responseTime;
    }

    public ElasticSearchPersonReporter getReporter() {
      return reporter;
    }

    public void setReporter(ElasticSearchPersonReporter reporter) {
      this.reporter = reporter;
    }

    public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
      return assignedSocialWorker;
    }

    public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
      this.assignedSocialWorker = assignedSocialWorker;
    }

    public List<ElasticSearchPersonAllegation> getAllegations() {
      return allegations;
    }

    public void setAllegations(List<ElasticSearchPersonAllegation> allegations) {
      this.allegations = allegations;
    }

    public ElasticSearchAccessLimitation getAccessLimitation() {
      return accessLimitation;
    }

    public void setAccessLimitation(ElasticSearchAccessLimitation accessLimitation) {
      this.accessLimitation = accessLimitation;
    }

    public String getCountyId() {
      return countyId;
    }

    public void setCountyId(String countyId) {
      this.countyId = countyId;
    }

    public String getResponseTimeId() {
      return responseTimeId;
    }

    public void setResponseTimeId(String responseTimeId) {
      this.responseTimeId = responseTimeId;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Case.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonCase extends ApiObjectIdentity
      implements ApiTypedIdentifier<String> {

    /**
     * Default serialization version.
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("legacy_case_id")
    @Deprecated
    private String legacyId;

    @JsonProperty("legacy_last_updated")
    @Deprecated
    private String legacyLastUpdated;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("county_name")
    private String countyName;

    @JsonProperty("county_id")
    private String countyId;

    @JsonProperty("service_component")
    private String serviceComponent;

    @JsonProperty("service_component_id")
    private String serviceComponentId;

    @JsonProperty("focus_child")
    private ElasticSearchPersonChild focusChild;

    @JsonProperty("assigned_social_worker")
    private ElasticSearchPersonSocialWorker assignedSocialWorker =
        new ElasticSearchPersonSocialWorker();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("parents")
    private List<ElasticSearchPersonParent> parents = new ArrayList<>();

    @JsonProperty("access_limitation")
    private ElasticSearchAccessLimitation accessLimitation = new ElasticSearchAccessLimitation();

    @JsonProperty("legacy_descriptor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    @Deprecated
    public String getLegacyId() {
      return legacyId;
    }

    @Deprecated
    public void setLegacyId(String legacyId) {
      this.legacyId = legacyId;
    }

    @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }

    @Deprecated
    public void setLegacyLastUpdated(String legacyLastUpdated) {
      this.legacyLastUpdated = legacyLastUpdated;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public String getCountyName() {
      return countyName;
    }

    public void setCountyName(String countyName) {
      this.countyName = countyName;
    }

    public String getServiceComponent() {
      return serviceComponent;
    }

    public void setServiceComponent(String serviceComponent) {
      this.serviceComponent = serviceComponent;
    }

    public ElasticSearchPersonChild getFocusChild() {
      return focusChild;
    }

    public void setFocusChild(ElasticSearchPersonChild focusChild) {
      this.focusChild = focusChild;
    }

    public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
      return assignedSocialWorker;
    }

    public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
      this.assignedSocialWorker = assignedSocialWorker;
    }

    public List<ElasticSearchPersonParent> getParents() {
      return parents;
    }

    public void setParents(List<ElasticSearchPersonParent> parents) {
      this.parents = parents;
    }

    public ElasticSearchAccessLimitation getAccessLimitation() {
      return accessLimitation;
    }

    public void setAccessLimitation(ElasticSearchAccessLimitation accessLimitation) {
      this.accessLimitation = accessLimitation;
    }

    public String getCountyId() {
      return countyId;
    }

    public void setCountyId(String countyId) {
      this.countyId = countyId;
    }

    public String getServiceComponentId() {
      return serviceComponentId;
    }

    public void setServiceComponentId(String serviceComponentId) {
      this.serviceComponentId = serviceComponentId;
    }

    public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
      return legacyDescriptor;
    }

    public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
      this.legacyDescriptor = legacyDescriptor;
    }
  }

  /**
   * Child.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonChild extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_client_id")
    @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }

    @Override
    @JsonProperty("legacy_last_updated")
    @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }
  }

  /**
   * Parent.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonParent extends ElasticSearchPersonNestedPerson {

    @JsonProperty("relationship")
    private String relationship;

    @Override
    @JsonProperty("legacy_id")
    @Deprecated
    public String getLegacyClientId() {
      return super.legacyPersonId;
    }

    @Override
    @JsonProperty("legacy_last_updated")
    @Deprecated
    public String getLegacyLastUpdated() {
      return super.legacyLastUpdated;
    }

    @Override
    @JsonProperty("legacy_source_table")
    @Deprecated
    public String getLegacySourceTable() {
      return super.legacySourceTable;
    }

    public String getRelationship() {
      return relationship;
    }

    public void setRelationship(String relationship) {
      this.relationship = relationship;
    }
  }

  /**
   * Access Limitation (sealed, sensitive, restricted).
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  @JsonInclude(value = Include.NON_EMPTY)
  public static class ElasticSearchAccessLimitation extends ApiObjectIdentity {

    @JsonProperty("limited_access_code")
    private String limitedAccessCode;

    @JsonProperty("limited_access_date")
    private String limitedAccessDate;

    @JsonProperty("limited_access_description")
    private String limitedAccessDescription;

    @JsonProperty("limited_access_government_entity_id")
    private String limitedAccessGovernmentEntityId;

    @JsonProperty("limited_access_government_entity_name")
    private String limitedAccessGovernmentEntityName;

    public String getLimitedAccessCode() {
      return limitedAccessCode;
    }

    public void setLimitedAccessCode(String limitedAccessCode) {
      this.limitedAccessCode = limitedAccessCode;
    }

    public String getLimitedAccessDate() {
      return limitedAccessDate;
    }

    public void setLimitedAccessDate(String limitedAccessDate) {
      this.limitedAccessDate = limitedAccessDate;
    }

    public String getLimitedAccessDescription() {
      return limitedAccessDescription;
    }

    public void setLimitedAccessDescription(String limitedAccessDescription) {
      this.limitedAccessDescription = limitedAccessDescription;
    }

    public String getLimitedAccessGovernmentEntityId() {
      return limitedAccessGovernmentEntityId;
    }

    public void setLimitedAccessGovernmentEntityId(String limitedAccessGovernmentEntityId) {
      this.limitedAccessGovernmentEntityId = limitedAccessGovernmentEntityId;
    }

    public String getLimitedAccessGovernmentEntityName() {
      return limitedAccessGovernmentEntityName;
    }

    public void setLimitedAccessGovernmentEntityName(String limitedAccessGovernmentEntityName) {
      this.limitedAccessGovernmentEntityName = limitedAccessGovernmentEntityName;
    }
  }

  /**
   * Simplistic, all String representation of ES person.phone_numbers.
   * 
   * @author CWDS API Team
   */
  public static class ElasticSearchPersonPhone extends ApiObjectIdentity
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

  // =========================
  // PRIVATE STATIC:
  // =========================

  /**
   * Name suffix contract for Intake API and Elasticsearch person documents. Translates related
   * legacy values to appropriate Intake values.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum ElasticSearchPersonNameSuffix {

    ESQUIRE("esq", new String[] {"esq", "eq", "esqu"}),

    SECOND("ii", new String[] {"ii", "2", "2nd", "second", "02"}),

    THIRD("iii", new String[] {"iii", "3", "3rd", "third", "03"}),

    FOURTH("iv", new String[] {"iv", "iiii", "4", "4th", "fourth", "04"}),

    JR("jr", new String[] {"jr", "junior", "jnr"}),

    SR("sr", new String[] {"sr", "senior", "snr"}),

    MD("md", new String[] {"md", "dr", "doc", "doctor"}),

    PHD("phd", new String[] {"phd", "professor", "prof"}),

    JD("jd", new String[] {"jd"});

    /**
     * Acceptable/contacted value for Intake.
     */
    public final String intake;

    /**
     * Potential matching source values from legacy.
     */
    @JsonIgnore
    private final String[] legacy;

    // Key = legacy free-form value.
    @JsonIgnore
    private static final Map<String, ElasticSearchPersonNameSuffix> mapLegacy = new HashMap<>();

    // Key = Intake value.
    @JsonIgnore
    private static final Map<String, ElasticSearchPersonNameSuffix> mapIntake = new HashMap<>();

    private ElasticSearchPersonNameSuffix(String intake, String[] legacy) {
      this.intake = intake;
      this.legacy = legacy;
    }

    @JsonValue
    public String getIntake() {
      return intake;
    }

    @JsonIgnore
    public String[] getLegacy() {
      return legacy;
    }

    public ElasticSearchPersonNameSuffix lookupLegacy(String val) {
      return ElasticSearchPersonNameSuffix.findByLegacy(val);
    }

    public static ElasticSearchPersonNameSuffix findByLegacy(String legacy) {
      return mapLegacy.get(legacy);
    }

    public ElasticSearchPersonNameSuffix lookupIntake(String val) {
      return ElasticSearchPersonNameSuffix.findByIntake(val);
    }

    public static ElasticSearchPersonNameSuffix findByIntake(String legacy) {
      return mapIntake.get(legacy);
    }

    public static ElasticSearchPersonNameSuffix translateNameSuffix(String nameSuffix) {
      return ElasticSearchPerson.ElasticSearchPersonNameSuffix
          .findByLegacy(nameSuffix.trim().toLowerCase().replaceAll("[^a-zA-Z0-9]", ""));
    }

    static {
      for (ElasticSearchPersonNameSuffix e : ElasticSearchPersonNameSuffix.values()) {
        mapIntake.put(e.intake, e);
        for (String leg : e.getLegacy()) {
          mapLegacy.put(leg, e);
        }
      }
    }

  }

  /**
   * Languages.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum ElasticSearchPersonLanguage implements ApiSysCodeAware {

    ENGLISH(1253, "English", 7),

    SPANISH(1274, "Spanish", 1),

    AMERICAN_SIGN_LANGUAGE(1248, "American Sign Language", 13),

    ARABIC(1249, "Arabic", 14),

    ARMENIAN(1250, "Armenian", 15),

    CAMBODIAN(1251, "Cambodian", 19),

    CANTONESE(1252, "Cantonese", 74),

    FARSI(1254, "Farsi", 41),

    FILIPINO(3198, "Filipino", 49),

    FRENCH(1255, "French", 28),

    GERMAN(1267, "German", 29),

    HAWAIIAN(1268, "Hawaiian", 99),

    HEBREW(1256, "Hebrew", 33),

    HMONG(1257, "Hmong", 35),

    ILACANO(1258, "Ilacano", 77),

    INDOCHINESE(3199, "Indochinese", 99),

    ITALIAN(1259, "Italian", 42),

    JAPANESE(1260, "Japanese", 3),

    KOREAN(1261, "Korean", 4),

    LAO(1262, "Lao", 43),

    MANDARIN(1263, "Mandarin", 75),

    MIEN(1264, "Mien", 76),

    OTHER_CHINESE(1265, "Other Chinese", 2),

    OTHER_NON_ENGLISH(1266, "Other Non-English", 99),

    POLISH(1269, "Polish", 50),

    PORTUGUESE(1270, "Portuguese", 51),

    ROMANIAN(3200, "Romanian", 99),

    RUSSIAN(1271, "Russian", 54),

    SAMOAN(1272, "Samoan", 55),

    SIGN_LANGUAGE_NOT_ASL(1273, "Sign Language (Not ASL)", 78),

    TAGALOG(1275, "Tagalog", 5),

    THAI(1276, "Thai", 65),

    TURKISH(1277, "Turkish", 67),

    VIETNAMESE(1278, "Vietnamese", 69);

    private final int sysId;
    private final String description;
    private final int displayOrder;

    private static final Map<Integer, ElasticSearchPersonLanguage> mapBySysId = new HashMap<>();

    private ElasticSearchPersonLanguage(int sysId, String description, int displayOrder) {
      this.sysId = sysId;
      this.description = description;
      this.displayOrder = displayOrder;
    }

    /**
     * Getter for SYS_ID in CMS table SYS_CD_C.
     * 
     * @return SYS_ID
     */
    @Override
    public int getSysId() {
      return sysId;
    }

    @Override
    @JsonValue
    public String getDescription() {
      return description;
    }

    public int getDisplayOrder() {
      return displayOrder;
    }

    @Override
    public ApiSysCodeAware lookupBySysId(int sysId) {
      return ElasticSearchPersonLanguage.findBySysId(sysId);
    }

    public static ElasticSearchPersonLanguage findBySysId(int sysId) {
      return mapBySysId.get(sysId);
    }

    static {
      for (ElasticSearchPersonLanguage e : ElasticSearchPersonLanguage.values()) {
        mapBySysId.put(e.sysId, e);
      }
    }

  }

  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchPerson.class);

  private static ApiSystemCodeCache systemCodes;

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * {@link ObjectMapper}, used to deserialize JSON Strings from member {@link #sourceJson} into
   * instances of {@link #sourceType}.
   * 
   * <p>
   * This mapper is thread-safe and reusable across multiple threads, yet any configuration made to
   * it, such as ignoring unknown JSON properties, applies to ALL target class types.
   * </p>
   */
  public static final ObjectMapper MAPPER;

  // =========================
  // STATIC INITIALIZATION:
  // =========================

  /**
   * Relax strict constraints regarding unknown JSON properties, since API classes may change over
   * time, and not all classes emit version information in JSON.
   * 
   * <p>
   * Bug #140710983: Bug: Person Search converts dates to GMT. Set default time zone to JVM default,
   * which must match the database and Elasticsearch server.
   * </p>
   */
  static {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    // The mainframe DB2 database runs in PST, and so we must too.
    final TimeZone tz = TimeZone.getTimeZone("PST");

    final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    fmt.setTimeZone(tz);
    mapper.setDateFormat(fmt);
    mapper.getSerializationConfig().with(fmt);
    mapper.setTimeZone(tz);
    mapper.getSerializationConfig().with(tz);
    MAPPER = mapper;
  }

  // ================
  // MEMBERS:
  // ================

  @JsonIgnore
  private transient boolean upsert = false;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("middle_name")
  private String middleName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("name_suffix")
  private String nameSuffix;

  @JsonProperty("date_of_birth")
  private String dateOfBirth;

  @JsonProperty("gender")
  private String gender;

  @JsonProperty("ssn")
  private String ssn;

  @JsonProperty("type")
  private transient String type;

  @JsonProperty("sensitivity_indicator")
  private transient String sensitivityIndicator;

  @JsonProperty("soc158_sealed_client_indicator")
  private transient String soc158SealedClientIndicator;

  @JsonProperty("source")
  private transient String source;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @Deprecated
  @JsonProperty("legacy_source_table")
  private String legacySourceTable;

  @Deprecated
  @JsonProperty("legacy_id")
  private String legacyId;

  @JsonProperty("addresses")
  private List<ElasticSearchPersonAddress> addresses = new ArrayList<>();

  @JsonProperty("phone_numbers")
  private List<ElasticSearchPersonPhone> phones = new ArrayList<>();

  @JsonProperty("languages")
  private List<String> languages = new ArrayList<>();

  @JsonProperty("screenings")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonScreening> screenings = new ArrayList<>();

  @JsonProperty("referrals")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonReferral> referrals = new ArrayList<>();

  @JsonProperty("relationships")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonRelationship> relationships = new ArrayList<>();

  @JsonProperty("cases")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonCase> cases = new ArrayList<>();

  @JsonProperty("akas")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonAka> akas = new ArrayList<>();

  @Transient
  private transient Map<String, String> highlights = new LinkedHashMap<>();

  /**
   * The identifier is String in legacy (CMS, mainframe DB2) but Long in new style (NS, PostGreSQL).
   * Therefore, the generic id here is String to accomodate all possibilities without resorting to
   * generics, untyped Object, or collections with heterogenous types. For now,
   * 
   * <p>
   * Java lacks a "union" construct (mutually exclusive child structures), polymorphic return types
   * (though, technically, the JVM can...), and true typed templates.
   * </p>
   */
  @JsonProperty("id")
  private String id;

  /**
   * Original, fully-qualified, persistence-level source class, such
   * "gov.ca.cwds.rest.api.persistence.cms.OtherClientName".
   */
  @JsonIgnore
  private transient String sourceType;

  /**
   * Raw, nested, child document JSON of an API class that implements the CWDS API interface,
   * IPersonAware.
   * 
   * <p>
   * Note that JSON serialization intentionally ignores this member, since it represents the JSON to
   * create a child Object and not the Object itself.
   * </p>
   */
  @JsonIgnore
  private transient String sourceJson;

  /**
   * highlight JSON returned from Elasticsearch with fragments flattened out
   */
  @JsonProperty("highlight")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private transient String highlightFields;

  /**
   * Nested document Object, constructed by deserializing {@link #sourceJson} into an instance of
   * Class type {@link #sourceType}.
   */
  private transient Object sourceObj;

  /**
   * default constructor for Jackson
   */
  public ElasticSearchPerson() {
    // Default, no-op.
  }

  /**
   * Overload constructor, used to accommodate nested document members {@link #sourceType} and
   * {@link #sourceJson}.
   * 
   * @param id identifier
   * @param firstName first name
   * @param lastName last name
   * @param middleName middle name
   * @param nameSuffix name suffix, such as jr or sr
   * @param gender gender code
   * @param birthDate birth date
   * @param ssn SSN without dashes
   * @param sourceType fully-qualified, persistence-level source class
   * @param sourceJson raw, nested child document as JSON
   * @param highlight highlightFields from Elasticsearch
   * @param addresses addresses
   * @param phones phones
   * @param languages languages
   * @param screenings screenings
   */
  public ElasticSearchPerson(String id, String firstName, String lastName, String middleName,
      String nameSuffix, String gender, String birthDate, String ssn, String sourceType,
      String sourceJson, String highlight, List<ElasticSearchPersonAddress> addresses,
      List<ElasticSearchPersonPhone> phones, List<String> languages,
      List<ElasticSearchPersonScreening> screenings) {

    // CMS/legacy String id:
    this.id = id;

    // Incorporate Person fields:
    this.firstName = trim(firstName);
    this.lastName = trim(lastName);
    this.middleName = trim(middleName);

    if (StringUtils.isNotBlank(nameSuffix)) {
      final ElasticSearchPersonNameSuffix maybe =
          ElasticSearchPersonNameSuffix.translateNameSuffix(nameSuffix);
      this.nameSuffix = maybe != null ? maybe.intake : null;
    }

    if (StringUtils.isNotBlank(gender)) {
      final String comp = gender.trim().toLowerCase();
      if ("m".equals(comp)) {
        this.gender = "male";
      } else if ("f".equals(comp)) {
        this.gender = "female";
      }
    }

    this.dateOfBirth = trim(birthDate);
    this.ssn = trim(ssn);

    if (addresses != null && !addresses.isEmpty()) {
      this.addresses = addresses;
    }

    if (phones != null && !phones.isEmpty()) {
      this.phones = phones;
    }

    if (languages != null && !languages.isEmpty()) {
      this.languages = languages;
    }

    if (screenings != null && !screenings.isEmpty()) {
      this.screenings = screenings;
    }

    // Nested document
    this.source = trim(sourceJson);

    // class name
    this.type = trim(sourceType);

    // Nested document:
    this.sourceType = sourceType;
    this.sourceJson = sourceJson;

    // Elasticsearch HighlightFields
    this.highlightFields = trim(highlight);
  }

  /**
   * Convenience method streams variable argument enum entries into a EnumSet.
   * 
   * @param enumClass any enum class
   * @param elem vararg enum entries
   * @return Set of enums
   * @param <V> enum class class
   */
  @SafeVarargs
  public static <V extends Enum<V>> Set<V> setOf(Class<V> enumClass, V... elem) {
    return Arrays.stream(elem).collect(Collectors.toCollection(() -> EnumSet.noneOf(enumClass)));
  }

  /**
   * Clear out optional collections (set to null) so that they are not overwritten by "last run"
   * jobs.
   * 
   * @param keep collections to keep
   */
  public void clearOptionalCollections(ESOptionalCollection... keep) {
    final Set<ESOptionalCollection> keepers = setOf(ESOptionalCollection.class, keep);

    if (!keepers.contains(ESOptionalCollection.REFERRAL)) {
      LOGGER.trace("clear REFERAL");
      this.referrals = null;
    }
    if (!keepers.contains(ESOptionalCollection.SCREENING)) {
      LOGGER.trace("clear SCREENING");
      this.screenings = null;
    }
    if (!keepers.contains(ESOptionalCollection.RELATIONSHIP)) {
      LOGGER.trace("clear SCREENING");
      this.relationships = null;
    }
    if (!keepers.contains(ESOptionalCollection.CASE)) {
      LOGGER.trace("clear CASE");
      this.cases = null;
    }
    if (!keepers.contains(ESOptionalCollection.AKA)) {
      LOGGER.trace("clear AKA");
      this.akas = null;
    }

    this.highlights = null;
  }

  /**
   * Clear out optional collections so that they are not overwritten by "last run" jobs. This
   * overload clears <strong>all</strong> optional collections.
   */
  public void clearOptionalCollections() {
    clearOptionalCollections(ESOptionalCollection.NONE);
  }

  // =========================
  // PROTECTED STATIC:
  // =========================

  /**
   * Extract field's value from an ElasticSearch result {@link Map} (key: field name), using the
   * field's ES column name and data type.
   * 
   * @param <T> expected data type
   * @param m ES result map
   * @param f field to extract
   * @return field value as specified type T
   */
  @SuppressWarnings("unchecked")
  protected static <T extends Serializable> T pullCol(final Map<String, Object> m, ESColumn f) {
    return (T) f.klazz.cast(m.getOrDefault(f.col, f.defaultVal));
  }

  /**
   * Trim excess whitespace from ElasticSearch results. Non-null input of all whitespace returns
   * empty String, null input returns null.
   * 
   * @param s String to trim
   * @return String trimmed of outer whitespace or null if input is null
   */
  protected static String trim(String s) {
    return s != null ? s.trim() : null;
  }

  // =========================
  // PUBLIC STATIC:
  // =========================

  /**
   * Getter for CMS system code cache.
   * 
   * @return reference to CMS system code cache
   */
  public static ApiSystemCodeCache getSystemCodes() {
    return ElasticSearchPerson.systemCodes;
  }

  /**
   * Store a reference to the singleton CMS system code cache for quick convenient access.
   * 
   * @param systemCodes CMS system code cache
   */
  @Inject
  public static void setSystemCodes(@SystemCodeCache ApiSystemCodeCache systemCodes) {
    ElasticSearchPerson.systemCodes = systemCodes;
  }

  /**
   * Produce an ElasticSearchPerson domain instance from native ElasticSearch {@link SearchHit}.
   * Parse JSON results and populate associated fields. ElasticSearch Java API returns an overly
   * broad type of {@code Map<String,Object>}. The enum "knows" how to extract columns of a given
   * type.
   * 
   * <p>
   * <strong>Classloader Note:</strong> When running in an application server, the root classloader
   * may not know of our domain/persistence class, and so we look it up using the current thread's
   * classloader, like so:
   * </p>
   * 
   * <blockquote>
   * {@code Class.forName("some.nested.class", false, Thread.currentThread().getContextClassLoader())}
   * </blockquote>
   * 
   * @param hit search result
   * @return populated domain-level ES object
   * @deprecated retrieves ES document objects for the obsolete Person Search.
   * @see #pullCol(Map, ESColumn)
   */
  @Deprecated
  public static ElasticSearchPerson makeESPerson(final SearchHit hit) {
    final Map<String, Object> m = hit.getSource();

    ElasticSearchPerson ret =
        new ElasticSearchPerson(ElasticSearchPerson.<String>pullCol(m, ESColumn.ID),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.LAST_NAME), null, null,
            ElasticSearchPerson.<String>pullCol(m, ESColumn.GENDER),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.BIRTH_DATE),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.SSN),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.TYPE),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.SOURCE), "", null, null, null, null);

    if (!StringUtils.isBlank(ret.getSourceType()) && !StringUtils.isBlank(ret.getSourceJson())) {
      try {
        // DELIVERED: STORY #137216799:
        // Tech debt: reverse compatibility with existing ElasticSearch documents.
        if (ret.getSourceType().startsWith("gov.ca.cwds.rest.api.")) {
          LOGGER.warn("LEGACY CLASS IN ELASTICSEARCH! class={}, id={}", ret.getSourceType(),
              ret.getId());
        }

        if (!StringUtils.isBlank(ret.getSourceJson())) {
          // Remove excess whitespace.
          // No job should store excess whitespace in ElasticSearch!
          final String json = ret.getSourceJson().replaceAll("\\s+\",", "\",");

          // Dynamically instantiate the domain class specified by "type" and load from JSON.
          // Note: When running in an application server, the app server's root classloader may not
          // know of our domain/persistence class, but the current thread's classloader should.
          final Object obj = MAPPER.readValue(json, Class.forName(ret.getSourceType(), false,
              Thread.currentThread().getContextClassLoader()));

          ret.sourceObj = obj;
        }

      } catch (ClassNotFoundException ce) {
        throw new ServiceException("ElasticSearch Person error: Failed to instantiate class "
            + ret.getSourceType() + ", ES person id=" + ret.getId(), ce);
      } catch (Exception e) {
        throw new ServiceException(
            "ElasticSearch Person error: " + e.getMessage() + ", ES person id=" + ret.getId(), e);
      }
    }

    // ElasticSearch Java API returns map of highlighted fields
    final Map<String, HighlightField> h = hit.getHighlightFields();
    final Map<String, String> highlightValues = new LinkedHashMap<>();

    // Go through the HighlightFields returned from ES deal with fragments and create map.
    for (final Map.Entry<String, HighlightField> entry : h.entrySet()) {
      String highlightValue;
      final HighlightField highlightField = entry.getValue();
      final Text[] fragments = highlightField.fragments();
      if (fragments != null && fragments.length != 0) {
        final String[] texts = new String[fragments.length];
        for (int i = 0; i < fragments.length; i++) {
          texts[i] = fragments[i].string().trim();
        }
        highlightValue = StringUtils.join(texts, "...");
        highlightValues.put(DomainChef.camelCaseToLowerUnderscore(highlightField.getName()),
            highlightValue);
      }
    }

    // Update this ElasticSearchPerson property with the highlighted text.
    String highLights = "";
    try {
      highLights = MAPPER.writeValueAsString(highlightValues);
    } catch (JsonProcessingException e) {
      throw new ServiceException("ElasticSearch Person error: Failed serialize map to JSON "
          + ret.getSourceType() + ", ES person id=" + ret.getId(), e);
    }
    ret.setHighlightFields(highLights);
    ret.setHighlights(highlightValues);
    return ret;
  }

  // =========================
  // ACCESSORS:
  // =========================

  /**
   * See comments on {@link #id}.
   * 
   * @return the id
   */
  @Override
  @JsonProperty
  public String getId() {
    return id;
  }

  /**
   * See comments on {@link #sourceType}.
   * 
   * @return the fully-qualified source persistence class
   */
  @JsonIgnore
  public String getSourceType() {
    return sourceType;
  }

  /**
   * See comments on {@link #sourceJson}. JSON streaming intentionally ignores this field.
   * 
   * @return the raw JSON of nested person document, if any
   * @see #sourceType
   */
  @JsonIgnore
  public String getSourceJson() {
    return sourceJson;
  }

  /**
   * See comments on {@link #sourceObj}.
   * 
   * @return an instance of the Object represented by {@link #sourceType} and {@link #sourceJson}.
   * @see #sourceType
   */
  @JsonIgnore
  public Object getSourceObj() {
    return sourceObj;
  }

  /**
   * Getter for first name.
   * 
   * @return first name
   */
  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  /**
   * Setter for first name
   * 
   * @param firstName first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Getter for last name.
   * 
   * @return last name
   */
  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }

  /**
   * Setter for last name.
   * 
   * @param lastName last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Getter for gender.
   * 
   * @return gender
   */
  @JsonProperty("gender")
  public String getGender() {
    return gender;
  }

  /**
   * Setter for gender.
   * 
   * @param gender gender code (not SYS_ID)
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Getter for date of birth.
   * 
   * @return date of birth
   */
  @JsonProperty("date_of_birth")
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Setter for dateOfBirth.
   * 
   * @param dateOfBirth birth date
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Getter for SSN.
   * 
   * @return SSN
   */
  @JsonProperty("ssn")
  public String getSsn() {
    return ssn;
  }

  /**
   * Setter for SSN
   * 
   * @param ssn the SSN
   */
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  /**
   * Getter for nested document type.
   * 
   * @return nested document type as an API class
   */
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  /**
   * Setter for nested document type.
   * 
   * @param type nested document type as an API class
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get sensitivity code (indicator)
   * 
   * @return The sensitivity code (indicator)
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  /**
   * Set the sensitivity code (indicator)
   * 
   * @param sensitivityIndicator The sensitivity code (indicator)
   */
  public void setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
  }

  /**
   * Get SOC158 sealed code (indicator)
   * 
   * @return SOC158 sealed client code (indicator)
   */
  public String getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  /**
   * Set SOC158 sealed code (indicator)
   * 
   * @param soc158SealedClientIndicator SOC158 sealed client code (indicator)
   */
  public void setSoc158SealedClientIndicator(String soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
  }

  /**
   * Getter for source.
   * 
   * @return source object JSON
   */
  @JsonProperty("source")
  public String getSource() {
    return source;
  }

  /**
   * Setter for source.
   * 
   * @param source JSON source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Setter for id.
   * 
   * @param id legacy String identifier
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Setter for API class type of nested JSON.
   * 
   * @param sourceType fully-qualified API class of nested JSON
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  /**
   * Setter for source JSON.
   * 
   * @param sourceJson JSON of the nested API class
   */
  public void setSourceJson(String sourceJson) {
    this.sourceJson = sourceJson;
  }

  /**
   * Setter for source object.
   * 
   * @param sourceObj live CWDS API class instance
   */
  public void setSourceObj(Object sourceObj) {
    this.sourceObj = sourceObj;
  }

  /**
   * Getter for highlighFields
   * 
   * @return JSON for highlightFields returned from Elasticsearch
   */
  @JsonProperty("highlight")
  public String getHighlightFields() {
    return highlightFields;
  }

  /**
   * Setter for highlightFields
   * 
   * @param highlightFields JSON of Elasticsearch highlightFields with flattened fragments
   */
  public void setHighlightFields(String highlightFields) {
    this.highlightFields = highlightFields;
  }

  @Override
  public String toString() {
    String ret = "";

    try {
      ret = ElasticSearchPerson.MAPPER
          // .writerWithDefaultPrettyPrinter()
          .writeValueAsString(this);
    } catch (JsonProcessingException e) {
      final String msg = MessageFormat.format("UNABLE TO SERIALIZE JSON!! {}", e.getMessage());
      LOGGER.error(msg, e);
      throw new ApiElasticSearchException(msg, e);
    }

    return ret;
  }

  /**
   * Omit transient fields, such as source object and source JSON. Only compare meaningful fields
   * like "id" or "first name."
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * Omit transient fields, such as source object and source JSON. Only compare meaningful fields
   * like "id" or "first name." Even "source type" should not affect the comparison, only "real"
   * fields should. Therefore, if all non-transient fields match, then the objects match, despite
   * their source.
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @JsonIgnore
  public Map<String, String> getHighlights() {
    return highlights;
  }

  public void setHighlights(Map<String, String> highlights) {
    this.highlights = highlights;
  }

  @JsonIgnore
  public List<ElasticSearchPersonAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<ElasticSearchPersonAddress> addresses) {
    this.addresses = addresses;
  }

  @JsonIgnore
  public List<ElasticSearchPersonPhone> getPhones() {
    return phones;
  }

  public void setPhones(List<ElasticSearchPersonPhone> phones) {
    this.phones = phones;
  }

  @JsonIgnore
  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  @JsonIgnore
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @JsonIgnore
  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  @JsonIgnore
  public List<ElasticSearchPersonScreening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<ElasticSearchPersonScreening> screenings) {
    this.screenings = screenings;
  }

  @JsonIgnore
  public boolean isUpsert() {
    return upsert;
  }

  public void setUpsert(boolean upsert) {
    this.upsert = upsert;
  }

  public List<ElasticSearchPersonReferral> getReferrals() {
    return referrals;
  }

  public void setReferrals(List<ElasticSearchPersonReferral> referrals) {
    this.referrals = referrals;
  }

  public List<ElasticSearchPersonRelationship> getRelationships() {
    return relationships;
  }

  public void setRelationships(List<ElasticSearchPersonRelationship> relationships) {
    this.relationships = relationships;
  }

  public List<ElasticSearchPersonCase> getCases() {
    return cases;
  }

  public void setCases(List<ElasticSearchPersonCase> cases) {
    this.cases = cases;
  }

  @Deprecated
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  @Deprecated
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  @Deprecated
  public String getLegacyId() {
    return legacyId;
  }

  @Deprecated
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * Get legacy descriptor
   * 
   * @return The legacy descriptor
   */
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * Set legacy descriptor
   * 
   * @param legacyDescriptor The legacy descriptor
   */
  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }
}
