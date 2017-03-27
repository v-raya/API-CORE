package gov.ca.cwds.data.es;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.data.std.ApiAddressAwareWritable;
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
public class ElasticSearchPerson implements Serializable, ApiTypedIdentifier<String> {

  /**
   * ElasticSearch field names for document type people.person.
   * 
   * @author CWDS API Team
   */
  public enum ESColumn {
    /**
     * ElasticSearch identifier
     */
    ID("id", String.class, ""),

    /**
     * first name
     */
    FIRST_NAME("firstName", String.class, ""),

    /**
     * last name
     */
    LAST_NAME("lastName", String.class, ""),

    /**
     * gender code (M,F,U)
     */
    GENDER("gender", String.class, "U"),

    /**
     * birth date
     */
    BIRTH_DATE("dateOfBirth", String.class, null),

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
   * Simplistic, all String representation of ES person.address.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static final class ElasticSearchPersonAddress
      implements Serializable, ApiTypedIdentifier<String>, ApiAddressAwareWritable {

    private String id;

    @JsonProperty("street_address")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String streetAddress;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String city;

    @JsonProperty("state")
    private String state;

    // Bug #141508231: county not in Intake API swagger.yml. Intake JSON parsing error.
    @JsonIgnore
    private String county;

    // Getter displays JSON.
    @JsonIgnore
    private String zip;

    @JsonProperty("type")
    private String type;

    /**
     * Default ctor.
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

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

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

    @Override
    public String getState() {
      return state;
    }

    @Override
    public void setState(String state) {
      this.state = state;
    }

    @Override
    public String getCounty() {
      return county;
    }

    @Override
    public void setCounty(String county) {
      this.county = county;
    }

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

    @Override
    public String getAddressId() {
      return this.id;
    }

  }

  /**
   * Simplistic, all String representation of ES person.phone_numbers.
   * 
   * @author CWDS API Team
   */
  public static final class ElasticSearchPersonPhone
      implements Serializable, ApiTypedIdentifier<String>, ApiPhoneAwareWritable {

    /**
     * Default
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

  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchPerson.class);

  private static ApiSystemCodeCache systemCodes;

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * {@link ObjectMapper}, used to unmarshall JSON Strings from member {@link #sourceJson} into
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
   * Produce an ESPerson domain from native ElasticSearch {@link SearchHit}. Parse JSON results and
   * populate associated fields. ElasticSearch Java API returns an overly broad type of
   * {@code Map<String,Object>}. The enum "knows" how to extract columns of a given type.
   * 
   * <p>
   * <strong>Classloader Note:</strong> When running in an application server, the root classloader
   * may not know of our domain/persistence class, and so we look it up using the current thread's
   * classloader, like so:
   * </p>
   * 
   * <blockquote>
   * {@code Class.forName("some.nested.class", false, Thread.currentThread().getContextClassLoader())}</blockquote>
   * 
   * @param hit search result
   * @return populated domain-level ES object
   * @see #pullCol(Map, ESColumn)
   */
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
            ElasticSearchPerson.<String>pullCol(m, ESColumn.SOURCE), "", null, null, null);

    if (!StringUtils.isBlank(ret.getSourceType()) && !StringUtils.isBlank(ret.getSourceJson())) {
      try {
        // TODO: STORY #137216799:
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

          // DELIVERED: STORY #137216799: again.
          final Object obj = MAPPER.readValue(json, Class.forName(ret.getSourceType()
          // .replaceAll("gov\\.ca\\.cwds\\.rest\\.api\\.", "gov\\.ca\\.cwds\\.data\\.")
              , false, Thread.currentThread().getContextClassLoader()));

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
      String highlightValue = "";
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

  // ================
  // MEMBERS:
  // ================

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
  private String type;

  @JsonProperty("source")
  private String source;

  private List<ElasticSearchPersonAddress> addresses = new ArrayList<>();

  @JsonProperty("phone_numbers")
  private List<ElasticSearchPersonPhone> phones = new ArrayList<>();

  private List<String> languages = new ArrayList<>();

  @Transient
  private Map<String, String> highlights = new LinkedHashMap<>();

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
  private String sourceType;

  /**
   * Raw, nested, child document JSON of an API class that implements the CWDS API interface,
   * IPersonAware.
   * 
   * <p>
   * Note that JSON marshalling intentionally ignores this member, since it represents the JSON to
   * create a child Object and not the Object itself.
   * </p>
   */
  @JsonIgnore
  private String sourceJson;

  /**
   * highlight JSON returned from Elasticsearch with fragments flattened out
   */
  @JsonProperty("highlight")
  private String highlightFields;

  /**
   * Nested document Object, constructed by unmarshalling {@link #sourceJson} into an instance of
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
   */
  public ElasticSearchPerson(String id, String firstName, String lastName, String middleName,
      String nameSuffix, String gender, String birthDate, String ssn, String sourceType,
      String sourceJson, String highlight, List<ElasticSearchPersonAddress> addresses,
      List<ElasticSearchPersonPhone> phones, List<String> languages) {

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

    this.gender = trim(gender);
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
   * Setter for ssn
   * 
   * @param ssn the ssn
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
   * 
   */
  public void setHighlightFields(String highlightFields) {
    this.highlightFields = highlightFields;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public Map<String, String> getHighlights() {
    return highlights;
  }

  public void setHighlights(Map<String, String> highlights) {
    this.highlights = highlights;
  }

  public List<ElasticSearchPersonAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<ElasticSearchPersonAddress> addresses) {
    this.addresses = addresses;
  }

  public List<ElasticSearchPersonPhone> getPhones() {
    return phones;
  }

  public void setPhones(List<ElasticSearchPersonPhone> phones) {
    this.phones = phones;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

}
