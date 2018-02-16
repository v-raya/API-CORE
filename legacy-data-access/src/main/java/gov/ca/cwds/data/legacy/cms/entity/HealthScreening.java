package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.ScreeningResult;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.HealthScreenedByType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.HealthScreeningType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@Table(name = "HLTH_SCT")
public class HealthScreening extends CmsPersistentObject {

  private static final long serialVersionUID = 8308841481569645507L;

  @Id
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "THIRD_ID")
  private String id;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "HL_SCRNC",
      referencedColumnName = "SYS_ID",
      insertable = false,
      updatable = false)
  private HealthScreeningType healthScreeningType;

  @NotNull
  @Column(name = "HL_SCR_DT")
  private LocalDate screeningDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "HL_SCRDC",
      referencedColumnName = "SYS_ID",
      insertable = false,
      updatable = false)
  private HealthScreenedByType healthScreenedByType;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "COMNT_TXT", referencedColumnName = "IDENTIFIER")
  private LongText commentText;

  @Column(name = "SCR_RSL_CD")
  @Convert(converter = ScreeningResult.ScreeningResultConverter.class)
  private ScreeningResult screeningResult;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public void setChildClient(ChildClient childClient) {
    this.childClient = childClient;
  }

  public String getId() {
    return id;
  }

  public void setId(String thirdId) {
    this.id = thirdId;
  }

  public HealthScreeningType getHealthScreeningType() {
    return healthScreeningType;
  }

  public void setHealthScreeningType(HealthScreeningType healthScreeningType) {
    this.healthScreeningType = healthScreeningType;
  }

  public LocalDate getScreeningDate() {
    return screeningDate;
  }

  public void setScreeningDate(LocalDate screeningDate) {
    this.screeningDate = screeningDate;
  }

  public HealthScreenedByType getHealthScreenedByType() {
    return healthScreenedByType;
  }

  public void setHealthScreenedByType(HealthScreenedByType healthScreenedBy) {
    this.healthScreenedByType = healthScreenedBy;
  }

  public LongText getCommentText() {
    return commentText;
  }

  public void setCommentText(LongText commentText) {
    this.commentText = commentText;
  }

  public ScreeningResult getScreeningResult() {
    return screeningResult;
  }

  public void setScreeningResult(ScreeningResult screeningResult) {
    this.screeningResult = screeningResult;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HealthScreening)) {
      return false;
    }
    HealthScreening that = (HealthScreening) o;
    return Objects.equals(getChildClient().getIdentifier(), that.getChildClient().getIdentifier()) &&
        Objects.equals(getHealthScreeningType().getSystemId(),
            that.getHealthScreeningType().getSystemId()) &&
        Objects.equals(getScreeningDate(), that.getScreeningDate()) &&
        Objects.equals(getHealthScreenedByType().getSystemId(),
            that.getHealthScreenedByType().getSystemId());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getChildClient().getIdentifier(), getHealthScreeningType().getSystemId(),
            getScreeningDate(), getHealthScreenedByType().getSystemId());
  }
}
