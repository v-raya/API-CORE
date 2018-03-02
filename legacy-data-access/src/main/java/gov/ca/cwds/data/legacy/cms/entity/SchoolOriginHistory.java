package gov.ca.cwds.data.legacy.cms.entity;

import com.google.common.base.Objects;
import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.YesNoUnknownConverter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "SCH_HIST")
@IdClass(SchoolOriginHistoryPK.class)
@NamedQuery(
    name = SchoolOriginHistory.NQ_FIND_BY_CLIENT_ID,
    query = "from SchoolOriginHistory where fkchldClt = :"
        + SchoolOriginHistory.NQ_PARAM_CLIENT_ID
)
public class SchoolOriginHistory extends CmsPersistentObject {

  private static final long serialVersionUID = -1668851209270175254L;

  public static final String NQ_FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory.findByClientId";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

  @Id
  @Column(name = "FKCHLD_CLT", nullable = false, length = 10)
  private String fkchldClt;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  @Column(name = "SC_DCSN_CD", nullable = false, length = 1)
  @Convert(converter = YesNoUnknownConverter.class)
  private YesNoUnknown schoolDecision;

  @Column(name = "SC_DCSN_DT", nullable = false)
  private LocalDate schoolDecisionDate;

  @Column(name = "FKED_PVDRT", length = 10)
  private String fkedPvdrt;

  @Override
  public Serializable getPrimaryKey() {
    return new SchoolOriginHistoryPK(this.fkchldClt, this.thirdId);
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

  public YesNoUnknown getSchoolDecision() {
    return schoolDecision;
  }

  public void setSchoolDecision(
      YesNoUnknown schoolDecisionCode) {
    this.schoolDecision = schoolDecisionCode;
  }

  public LocalDate getSchoolDecisionDate() {
    return schoolDecisionDate;
  }

  public void setSchoolDecisionDate(LocalDate scDcsnDt) {
    this.schoolDecisionDate = scDcsnDt;
  }

  public String getFkedPvdrt() {
    return fkedPvdrt;
  }

  public void setFkedPvdrt(String fkedPvdrt) {
    this.fkedPvdrt = fkedPvdrt;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchoolOriginHistory schoolOriginHistory = (SchoolOriginHistory) o;
    return Objects.equal(fkchldClt, schoolOriginHistory.fkchldClt) &&
        Objects.equal(thirdId, schoolOriginHistory.thirdId) &&
        Objects.equal(schoolDecision, schoolOriginHistory.schoolDecision) &&
        Objects.equal(schoolDecisionDate, schoolOriginHistory.schoolDecisionDate) &&
        Objects.equal(fkedPvdrt, schoolOriginHistory.fkedPvdrt);
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(fkchldClt, thirdId, schoolDecision, schoolDecisionDate, fkedPvdrt);
  }
}
