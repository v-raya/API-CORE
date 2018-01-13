package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS TPT-3 Team
 */
public class StaffPersonCaseLoadPK implements Serializable {

  private static final long serialVersionUID = 8090175218721828513L;

  private String thirdId;
  private String fkcaseLdt;
  private String fkstfperst;

  public StaffPersonCaseLoadPK() {
  }

  public StaffPersonCaseLoadPK(String thirdId, String fkcaseLdt, String fkstfperst) {
    this.thirdId = thirdId;
    this.fkcaseLdt = fkcaseLdt;
    this.fkstfperst = fkstfperst;
  }

  @Column(name = "THIRD_ID", nullable = false, length = 10)
  @Id
  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Column(name = "FKCASE_LDT", nullable = false, length = 10)
  @Id
  public String getFkcaseLdt() {
    return fkcaseLdt;
  }

  public void setFkcaseLdt(String fkcaseLdt) {
    this.fkcaseLdt = fkcaseLdt;
  }

  @Column(name = "FKSTFPERST", nullable = false, length = 3)
  @Id
  public String getFkstfperst() {
    return fkstfperst;
  }

  public void setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StaffPersonCaseLoadPK that = (StaffPersonCaseLoadPK) o;

    return new EqualsBuilder()
        .append(thirdId, that.thirdId)
        .append(fkcaseLdt, that.fkcaseLdt)
        .append(fkstfperst, that.fkstfperst)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(thirdId)
        .append(fkcaseLdt)
        .append(fkstfperst)
        .toHashCode();
  }
}
