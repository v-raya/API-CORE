package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
public class PlacementHomeInformationPK implements Serializable {

  private static final long serialVersionUID = -7640982561710880631L;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  @Id
  @Column(name = "FKSB_PVDRT", nullable = false, length = 10)
  private String fksbPvdrt;

  @Id
  @Column(name = "FKPLC_HM_T", nullable = false, length = 10)
  private String fkplcHmT;

  public PlacementHomeInformationPK() {
  }

  public PlacementHomeInformationPK(String thirdId, String fksbPvdrt, String fkplcHmT) {
    this.thirdId = thirdId;
    this.fksbPvdrt = fksbPvdrt;
    this.fkplcHmT = fkplcHmT;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public String getFksbPvdrt() {
    return fksbPvdrt;
  }

  public void setFksbPvdrt(String fksbPvdrt) {
    this.fksbPvdrt = fksbPvdrt;
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
