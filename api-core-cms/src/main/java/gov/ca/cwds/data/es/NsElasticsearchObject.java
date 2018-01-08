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

  private String updatedAt;
  private String createdAt;

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
    this.updatedAt = updatedAt;
    this.createdAt = DomainChef.cookTimestamp(new Date());
  }

  /**
   * @return the updated_at
   */
  public String getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @return the created_at
   */
  public String getCreatedAt() {
    return createdAt;
  }

}
