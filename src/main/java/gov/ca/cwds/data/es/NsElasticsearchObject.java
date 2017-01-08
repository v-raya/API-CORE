package gov.ca.cwds.data.es;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * Base class of objects in the Elasticsearch layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class NsElasticsearchObject {

  private String updated_at;
  private String created_at;

  /**
   * Default constructor
   */
  protected NsElasticsearchObject() {
    // Default, no-op.
  }

  /**
   * Constructor
   * 
   * @param updatedAt The time of last update of this object
   */
  protected NsElasticsearchObject(String updatedAt) {
    this.updated_at = updatedAt;
    this.created_at = DomainChef.cookTimestamp(new Date()).toString();
  }

  /**
   * @return the updated_at
   */
  public String getUpdated_at() {
    return updated_at;
  }

  /**
   * @return the created_at
   */
  public String getCreated_at() {
    return created_at;
  }

}
