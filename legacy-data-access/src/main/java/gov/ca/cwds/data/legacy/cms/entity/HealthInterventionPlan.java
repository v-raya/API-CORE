package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.HealthPlanTerminationType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@NamedQuery(
  name = HealthInterventionPlan.FIND_ACTIVE_PLANS_BY_CLIENT_ID,
  query =
      "SELECT plan FROM gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan plan where plan.childClient.id =:"
          + HealthInterventionPlan.PARAM_CLIENT_ID
          + " and plan.endDate is null"
)
@Table(name = "HLTH_PLT")
public class HealthInterventionPlan extends CmsPersistentObject {

  private static final long serialVersionUID = 1619289937653893727L;

  public static final String FIND_ACTIVE_PLANS_BY_CLIENT_ID =
      "HealthInterventionPlan.findActivePlansByClientId";
  public static final String PARAM_CLIENT_ID = "reclientId";

  @EmbeddedId private Id id = new Id();

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
    name = "FKCHLD_CLT",
    referencedColumnName = "FKCLIENT_T",
    insertable = false,
    updatable = false
  )
  private ChildClient childClient;

  @NotNull
  @Column(
    name = "THIRD_ID",
    nullable = false,
    length = CMS_ID_LEN,
    insertable = false,
    updatable = false
  )
  private String thirdId;

  @NotNull
  @Column(name = "HL_INTV_CD", nullable = false, length = 1)
  @Convert(converter = InterventionPlanType.InterventionPlanTypeConverter.class)
  private InterventionPlanType interventionPlanType;

  @NotNull
  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "COMNT_TXT", referencedColumnName = "IDENTIFIER")
  private LongText commentText;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "HL_TERMC", referencedColumnName = "SYS_ID")
  private HealthPlanTerminationType healthPlanTerminationType;

  @Column(name = "END_DT")
  private LocalDate endDate;

  /**
   * MEDICAL_NECESSITY_DATE - The date the Childs mental health needs were determined to be a Medical Necessity.
   */
  @Column(name = "MEDNCST_DT")
  private LocalDate medicalNecessityDate;

  /**
   * MEDICAL_NECESSITY_CODE - Indicates whether a Child’s Mental Health needs meet the definition of medical necessity.
   * Yes (Y) or No (N) or Unknown (U).
   */
  @Column(name = "MEDNCST_CD", length = 1, nullable = false)
  private String medicalNecessityCode;

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public void setChildClient(ChildClient client) {
    this.childClient = client;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public InterventionPlanType getInterventionPlanType() {
    return interventionPlanType;
  }

  public void setInterventionPlanCode(InterventionPlanType interventionPlanType) {
    this.interventionPlanType = interventionPlanType;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public LongText getCommentText() {
    return commentText;
  }

  public void setCommentText(LongText commentText) {
    this.commentText = commentText;
  }

  public HealthPlanTerminationType getHealthPlanTerminationType() {
    return healthPlanTerminationType;
  }

  public void setHealthPlanTerminationType(HealthPlanTerminationType healthPlanTerminationType) {
    this.healthPlanTerminationType = healthPlanTerminationType;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public LocalDate getMedicalNecessityDate() {
    return medicalNecessityDate;
  }

  public void setMedicalNecessityDate(LocalDate medicalNecessityDate) {
    this.medicalNecessityDate = medicalNecessityDate;
  }

  public String getMedicalNecessityCode() {
    return medicalNecessityCode;
  }

  public void setMedicalNecessityCode(String medicalNecessityCode) {
    this.medicalNecessityCode = medicalNecessityCode;
  }

  @Embeddable
  public static class Id implements Serializable {

    private static final long serialVersionUID = -7450788469587012105L;

    @NotNull
    @Column(name = "FKCHLD_CLT", nullable = false, length = CMS_ID_LEN)
    private String childClientId;

    @NotNull
    @Column(name = "THIRD_ID", nullable = false, length = CMS_ID_LEN)
    private String thirdId;

    public Id() {}

    public Id(String childClientId, String thirdId) {
      this.childClientId = childClientId;
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
      Id that = (Id) o;
      return Objects.equals(childClientId, that.childClientId)
          && Objects.equals(thirdId, that.thirdId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(childClientId, thirdId);
    }
  }
}
