package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
public class CountyOwnershipPK implements Serializable {

  private static final long serialVersionUID = 2925386558395818255L;

  @Id
  @Column(name = "ENTITY_ID", nullable = false, length = 10)
  private String entityId;

  @Id
  @Column(name = "ENTITY_CD", nullable = false, length = 2)
  private String entityCd;

  public CountyOwnershipPK() {
  }

  public CountyOwnershipPK(String entityId, String entityCd) {
    this.entityId = entityId;
    this.entityCd = entityCd;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getEntityCd() {
    return entityCd;
  }

  public void setEntityCd(String entityCd) {
    this.entityCd = entityCd;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
