package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * <p>
 * The Intake Auto-complete for Person takes a single search term, which is used to query
 * Elasticsearch Person documents by ALL relevant fields. For example, search strings consisting of
 * only digits could be phone numbers, social security numbers, or street address numbers. Search
 * strings consisting of only letters could be last name, first name, city, state, language, and so
 * forth.
 * </p>
 * 
 * @author CWDS API Team
 */
public class AutoCompletePersonResponse implements Serializable, Response {

  /**
   * Base version. Increment by version.
   */
  private static final long serialVersionUID = 1L;

  // [{
  // "id": 1,
  // "date_of_birth": "1964-01-14",
  // "first_name": "John",
  // "gender": null,
  // "last_name": "Smith",
  // "middle_name": null,
  // "ssn": "858584561",
  // "name_suffix": null,
  // "addresses": [
  // {
  // "city": "city",
  // "id": 6,
  // "state": "IN",
  // "street_address": "876 home",
  // "zip": "66666",
  // "type": "Placement"
  // }
  // ],
  // "phone_numbers": [],
  // "languages": []
  // }]


  // Address:
  // type: object
  // properties:
  // street_address:
  // type: string
  // city:
  // type: string
  // state:
  // type: string
  // zip:
  // type: string
  // type:
  // type: string
  // enum:

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum AutoCompletePersonAddressType {
    Home, School, Work, Placement, Homeless, Other
  }

  public static final class AutoCompletePersonAddress implements Serializable {

    /**
     * Base version. Increment by version.
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private AutoCompletePersonAddressType type;

  }

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum AutoCompletePersonPhoneType {
    Cell, Work, Home, Other
  }

  public static final class AutoCompletePersonPhone implements Serializable {

    /**
     * Base version. Increment by version.
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private AutoCompletePersonPhoneType type;
    private String number;


  }


  @JsonProperty("id")
  private long id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("middle_name")
  private String middleName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("name_suffix")
  private String nameSuffix;

  @JsonProperty("type")
  private String gender;

  @JsonProperty("type")
  private String ssn;

  @JsonProperty("date_of_birth")
  private String dateOfBirth;

  @JsonProperty("addresses")
  private List<AutoCompletePersonAddress> addresses;

  @JsonProperty("phone_numbers")
  private List<AutoCompletePersonPhone> phoneNumbers;

  // name_suffix:
  // enum:
  // - esq
  // - ii
  // - iii
  // - iv
  // - jr
  // - sr
  // - md
  // - phd
  // - jd
  //
  // gender:
  // type: string
  // enum:
  // - male
  // - female

  // languages:
  // type: array
  // items:
  // type: string
  // enum:
  // - American Sign Language
  // - Arabic
  // - Armenian
  // - Cambodian
  // - Cantonese
  // - English
  // - Farsi
  // - Filipino
  // - French
  // - German
  // - Hawaiian
  // - Hebrew
  // - Hmong
  // - Ilacano
  // - Indochinese
  // - Italian
  // - Japanese
  // - Korean
  // - Lao
  // - Mandarin
  // - Mien
  // - Other Chinese
  // - Other Non-English
  // - Polish
  // - Portuguese
  // - Romanian
  // - Russian
  // - Samoan
  // - Sign Language (Not ASL)
  // - Spanish
  // - Tagalog
  // - Thai
  // - Turkish
  // - Vietnamese

}
