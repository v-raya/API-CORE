package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import gov.ca.cwds.data.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

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
@ApiModel
@JsonSnakeCase
public class AutoCompletePersonResponse implements Serializable, Response {

  /**
   * Base version. Increment by class version.
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

  private AutoCompletePerson[] persons;

  /**
   * Disallow calls to default constructor.
   */
  @SuppressWarnings("unused")
  private AutoCompletePersonResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from person array.
   * 
   * @param persons array of {@link AutoCompletePerson}
   */
  public AutoCompletePersonResponse(AutoCompletePerson[] persons) {
    this.persons = persons;
  }

  public AutoCompletePerson[] getPersons() {
    return persons;
  }

  public void setPersons(AutoCompletePerson[] persons) {
    this.persons = persons;
  }


}
