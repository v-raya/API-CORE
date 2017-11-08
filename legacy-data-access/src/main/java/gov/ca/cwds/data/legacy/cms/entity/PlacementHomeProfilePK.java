package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
public class PlacementHomeProfilePK implements Serializable {

  private static final long serialVersionUID = -1338578310179366105L;

  @Column(name = "THIRD_ID", nullable = false, length = 10)
  @Id
  private String thirdId;

  @Column(name = "FKPLC_HM_T", nullable = false, length = 10)
  @Id
  private String fkplcHmT;

  public PlacementHomeProfilePK() {
  }

  public PlacementHomeProfilePK(String thirdId, String fkplcHmT) {
    this.thirdId = thirdId;
    this.fkplcHmT = fkplcHmT;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public String getFkplcHmT() {
    return fkplcHmT;
  }

  public void setFkplcHmT(String fkplcHmT) {
    this.fkplcHmT = fkplcHmT;
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
