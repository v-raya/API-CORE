package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.CmsPersistentObject.CMS_ID_LEN;

import com.google.common.base.Objects;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnTransformer;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "DAS_HIST")
@IdClass(DualAgencyServicesHistoryPK.class)
@NamedQueries(
    @NamedQuery(
        name = DualAgencyServicesHistory.NQ_FIND_BY_CLIENT_ID,
        query = "from DualAgencyServicesHistory where fkclientT = :"
            + DualAgencyServicesHistory.NQ_PARAM_CLIENT_ID
    )
)
public class DualAgencyServicesHistory implements PersistentObject {

  private static final long serialVersionUID = 6572734754959171762L;

  public static final String NQ_FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.DualAgencyServicesHistory.findByClientId";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

  @Id
  @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
  private String fkclientT;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Column(name = "PROVD_BYCD", nullable = false, length = 1)
  private String providedByCode;

  @Column(name = "OTHER_DSC", length = 50)
  @ColumnTransformer(read = "trim(OTHER_DSC)")
  private String otherDescription;

  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
  private String lstUpdId;

  @Column(name = "LST_UPD_TS", nullable = false)
  private Timestamp lstUpdTs;

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return new DualAgencyServicesHistoryPK(this.fkclientT, this.thirdId);
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public String getProvidedByCode() {
    return providedByCode;
  }

  public void setProvidedByCode(String provdBycd) {
    this.providedByCode = provdBycd;
  }

  public String getOtherDescription() {
    return otherDescription;
  }

  public void setOtherDescription(String otherDsc) {
    this.otherDescription = otherDsc;
  }

  public String getLstUpdId() {
    return lstUpdId;
  }

  public void setLstUpdId(String lstUpdId) {
    this.lstUpdId = lstUpdId;
  }

  public Timestamp getLstUpdTs() {
    return lstUpdTs;
  }

  public void setLstUpdTs(Timestamp lstUpdTs) {
    this.lstUpdTs = lstUpdTs;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DualAgencyServicesHistory)) {
      return false;
    }
    DualAgencyServicesHistory that = (DualAgencyServicesHistory) o;
    return Objects.equal(fkclientT, that.fkclientT) &&
        Objects.equal(thirdId, that.thirdId) &&
        Objects.equal(startDate, that.startDate) &&
        Objects.equal(endDate, that.endDate) &&
        Objects.equal(providedByCode, that.providedByCode) &&
        Objects.equal(otherDescription, that.otherDescription);
  }

  @Override
  public final int hashCode() {
    return Objects
        .hashCode(fkclientT, thirdId, startDate, endDate, providedByCode, otherDescription);
  }
}
