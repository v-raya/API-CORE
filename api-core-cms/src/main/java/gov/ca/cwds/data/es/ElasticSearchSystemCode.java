package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * System code
 * 
 * @author CWDS API Team
 */
public class ElasticSearchSystemCode extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 4396490810924002909L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("description")
  private String description;

  /**
   * Default no-arg constructor
   */
  public ElasticSearchSystemCode() {}

  /**
   * Get id
   * 
   * @return The id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Set id
   * 
   * @param id The id
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get description
   * 
   * @return The code description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set description
   * 
   * @param description The code description
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
