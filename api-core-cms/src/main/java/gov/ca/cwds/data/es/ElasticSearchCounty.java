package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Client county
 * 
 * @author CWDS API Team
 */
public class ElasticSearchCounty extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 4396490810924002909L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  /**
   * Default no-arg constructor
   */
  public ElasticSearchCounty() {}

  /**
   * Get county id
   * 
   * @return The county id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Set county id
   * 
   * @param id The county id
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get county name
   * 
   * @return The county name
   */
  public String getName() {
    return name;
  }

  /**
   * Set county name
   * 
   * @param name The county name
   */
  public void setName(String name) {
    this.name = name;
  }
}
