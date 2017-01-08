package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;

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
public final class AutoCompletePersonRequest implements Serializable, Request {

  /**
   * Base version. Increment by version.
   */
  private static final long serialVersionUID = 1L;

  private String searchTerm;

  /**
   * Constructor takes solitary search term.
   * 
   * @param searchTerm String search term.
   */
  public AutoCompletePersonRequest(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  /**
   * Getter for auto-complete search term.
   * 
   * @return search term
   */
  public String getSearchTerm() {
    return searchTerm;
  }

  /**
   * Setter for auto-complete search term.
   * 
   * @param searchTerm search term
   */
  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

}
