package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS TPT-3 Team
 */
public class SafetyAlertPK implements Serializable {

  private static final long serialVersionUID = 4118321105344595836L;

  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  @Id
  private String fkClientId;

  @Column(name = "THIRD_ID", nullable = false, length = 10)
  @Id
  private String thirdId;

  public SafetyAlertPK() {
  }

  public SafetyAlertPK(String fkClientId, String thirdId) {
    this.fkClientId = fkClientId;
    this.thirdId = thirdId;
  }

  public String getFkClientId() {
    return fkClientId;
  }

  public void setFkClientId(String fkclientT) {
    this.fkClientId = fkclientT;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SafetyAlertPK safetyAlertPK = (SafetyAlertPK) o;

    return new EqualsBuilder()
        .append(fkClientId, safetyAlertPK.fkClientId)
        .append(thirdId, safetyAlertPK.thirdId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(fkClientId)
        .append(thirdId)
        .toHashCode();
  }
}
