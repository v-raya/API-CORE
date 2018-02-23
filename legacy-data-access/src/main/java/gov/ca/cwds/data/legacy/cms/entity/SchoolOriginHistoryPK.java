package gov.ca.cwds.data.legacy.cms.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author CWDS TPT-3 Team
 */
public class SchoolOriginHistoryPK implements Serializable {

  private static final long serialVersionUID = -324424842233185258L;

  @Id
  @Column(name = "FKCHLD_CLT", nullable = false, length = 10)
  private String fkchldClt;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  public SchoolOriginHistoryPK() {
  }

  public SchoolOriginHistoryPK(String fkchldClt, String thirdId) {
    this.fkchldClt = fkchldClt;
    this.thirdId = thirdId;
  }

  public String getFkchldClt() {
    return fkchldClt;
  }

  public void setFkchldClt(String fkchldClt) {
    this.fkchldClt = fkchldClt;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchoolOriginHistoryPK schoolOriginHistoryPK = (SchoolOriginHistoryPK) o;
    return Objects.equal(fkchldClt, schoolOriginHistoryPK.fkchldClt) &&
        Objects.equal(thirdId, schoolOriginHistoryPK.thirdId);
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(fkchldClt, thirdId);
  }
}
