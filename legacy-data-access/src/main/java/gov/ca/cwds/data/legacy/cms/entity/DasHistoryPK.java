package gov.ca.cwds.data.legacy.cms.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author CWDS TPT-3 Team
 */
public class DasHistoryPK implements Serializable {

  private static final long serialVersionUID = -7321082877124692110L;

  @Id
  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  private String fkclientT;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  public DasHistoryPK() {
  }

  public DasHistoryPK(String fkclientT, String thirdId) {
    this.fkclientT = fkclientT;
    this.thirdId = thirdId;
  }

  public String getFkclientT() {
    return fkclientT;
  }

  public void setFkclientT(String fkclientT) {
    this.fkclientT = fkclientT;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DasHistoryPK)) {
      return false;
    }
    DasHistoryPK that = (DasHistoryPK) o;
    return Objects.equal(fkclientT, that.fkclientT) &&
        Objects.equal(thirdId, that.thirdId);
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(fkclientT, thirdId);
  }
}
