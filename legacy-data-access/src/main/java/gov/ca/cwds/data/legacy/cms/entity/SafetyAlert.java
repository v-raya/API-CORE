package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "SAF_ALRT")
@IdClass(SafetyAlertPK.class)
@NamedQueries(
    @NamedQuery(
        name = SafetyAlert.NQ_FIND_BY_CLIENT_ID,
        query = "from gov.ca.cwds.data.legacy.cms.entity.SafetyAlert a where a.fkClientId = :" + SafetyAlert.NQ_PARAM_CLIENT_ID
    )
)
public class SafetyAlert extends CmsPersistentObject {

  private static final long serialVersionUID = 4957223131673425323L;

  public static final String NQ_FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.SafetyAlert.findByClientId";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

  @Id
  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  private String fkClientId;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ACTV_GEC", referencedColumnName = "SYS_ID")
  private County activationGovernmentEntityType;

  @Column(name = "ACTV_DT", nullable = false)
  private LocalDate activationDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ACTV_TXT", referencedColumnName = "IDENTIFIER")
  private LongText activationExplanationText;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ACTV_RNC", referencedColumnName = "SYS_ID")
  private SafetyAlertActivationReasonType activationReasonType;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "DACT_GEC", referencedColumnName = "SYS_ID")
  private County deactivationGovernmentEntityType;

  @Column(name = "DACT_DT")
  private LocalDate deactivationDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "DACT_TXT", referencedColumnName = "IDENTIFIER")
  private LongText deactivationExplanationText;

  @Override
  public Serializable getPrimaryKey() {
    return new SafetyAlertPK(fkClientId, thirdId);
  }

  public String getFkClientId() {
    return fkClientId;
  }

  public void setFkClientId(String fkClientId) {
    this.fkClientId = fkClientId;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public County getActivationGovernmentEntityType() {
    return activationGovernmentEntityType;
  }

  public void setActivationGovernmentEntityType(
      County activationGovernmentEntityType) {
    this.activationGovernmentEntityType = activationGovernmentEntityType;
  }

  public LocalDate getActivationDate() {
    return activationDate;
  }

  public void setActivationDate(LocalDate activationDate) {
    this.activationDate = activationDate;
  }

  public LongText getActivationExplanationText() {
    return activationExplanationText;
  }

  public void setActivationExplanationText(
      LongText activationExplanationText) {
    this.activationExplanationText = activationExplanationText;
  }

  public SafetyAlertActivationReasonType getActivationReasonType() {
    return activationReasonType;
  }

  public void setActivationReasonType(
      SafetyAlertActivationReasonType activationReasonType) {
    this.activationReasonType = activationReasonType;
  }

  public County getDeactivationGovernmentEntityType() {
    return deactivationGovernmentEntityType;
  }

  public void setDeactivationGovernmentEntityType(
      County deactivationGovernmentEntityType) {
    this.deactivationGovernmentEntityType = deactivationGovernmentEntityType;
  }

  public LocalDate getDeactivationDate() {
    return deactivationDate;
  }

  public void setDeactivationDate(LocalDate deactivationDate) {
    this.deactivationDate = deactivationDate;
  }

  public LongText getDeactivationExplanationText() {
    return deactivationExplanationText;
  }

  public void setDeactivationExplanationText(
      LongText deactivationExplanationText) {
    this.deactivationExplanationText = deactivationExplanationText;
  }
}
