package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination.FIND_TERMINATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination.PARAM_CHILD_CLIENT_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.CompetencyType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ParentalRightTerminationType;
import java.io.Serializable;
import java.sql.Date;
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
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@NamedQuery(
    name = FIND_TERMINATION_BY_CHILD_CLIENT_ID,
    query = "SELECT t FROM ParentalRightsTermination t where t.child.id =:" + PARAM_CHILD_CLIENT_ID
)
@Table(name = "PRG_TRMT")
public class ParentalRightsTermination extends CmsPersistentObject {

  private static final long serialVersionUID = -1268395950553736492L;

  public static final String FIND_TERMINATION_BY_CHILD_CLIENT_ID =
      "ParentalRightsTermination.findTerminationByChildClientId";

  public static final String PARAM_CHILD_CLIENT_ID = "childClientId";

  @EmbeddedId
  private Id id = new Id();

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "FKCHLD_CLT",
      referencedColumnName = "FKCLIENT_T",
      insertable = false,
      updatable = false
  )
  private ChildClient child;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "FKCLIENT_T",
      referencedColumnName = "IDENTIFIER",
      insertable = false,
      updatable = false
  )
  private Client parent;

  @NotNull
  @Size(max = 100)
  @Column(name = "COMNT_DSC")
  private String commentDescription;

  @NotNull
  @Column(name = "COMP_CD")
  @Convert(converter = CompetencyType.CompetencyTypeConverter.class)
  private CompetencyType competencyType;

  @Column(name = "COMP_EX_DT")
  private Date competencyExaminationDate;

  @NotNull
  @Size(max = 30)
  @Column(name = "COMP_PRONM")
  private String competencyProfessionalName;

  @Type(type = "yes_no")
  @Column(name = "CON_IND")
  private boolean conservatorshipAllowsRelIndicator;

  @NotNull
  @Size(max = 10)
  @Column(name = "COURTCS_NO")
  private String courtCaseNumber;

  @NotNull
  @Column(name = "TERMN_DT")
  private LocalDate date;

  //TODO: map to Court when such entity will be created
  @Size(max = CMS_ID_LEN)
  @Column(name = "FKCOURT_T")
  private String courtId;

  @NotNull
  @Size(max = 50)
  @Column(name = "ENDLOC_DSC")
  private String locationDescription;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "PRTERM_C", referencedColumnName = "SYS_ID")
  private ParentalRightTerminationType parentalRightTerminationType;

  @Type(type = "yes_no")
  @Column(name = "APPEAL_IND")
  private boolean underAppealedIndicator;

  @Type(type = "yes_no")
  @Column(name = "RLNQSH_IND")
  private boolean voluntaryRelinquishmentIndicator;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public boolean getUnderAppealedIndicator() {
    return underAppealedIndicator;
  }

  public void setUnderAppealedIndicator(boolean underAppealedIndicator) {
    this.underAppealedIndicator = underAppealedIndicator;
  }

  public boolean getVoluntaryRelinquishmentIndicator() {
    return voluntaryRelinquishmentIndicator;
  }

  public void setVoluntaryRelinquishmentIndicator(boolean voluntaryRelinquishmentIndicator) {
    this.voluntaryRelinquishmentIndicator = voluntaryRelinquishmentIndicator;
  }

  public ChildClient getChild() {
    return child;
  }

  public void setChild(ChildClient child) {
    this.child = child;
  }


  public Client getParent() {
    return parent;
  }

  public void setParent(Client parent) {
    this.parent = parent;
  }


  public String getCourtId() {
    return courtId;
  }

  public void setCourtId(String fkcourtT) {
    this.courtId = fkcourtT;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String comntDsc) {
    this.commentDescription = comntDsc;
  }


  public String getLocationDescription() {
    return locationDescription;
  }

  public void setLocationDescription(String endlocDsc) {
    this.locationDescription = endlocDsc;
  }


  public CompetencyType getCompetencyType() {
    return competencyType;
  }

  public void setCompetencyType(CompetencyType competencyType) {
    this.competencyType = competencyType;
  }


  public Date getCompetencyExaminationDate() {
    return competencyExaminationDate;
  }

  public void setCompetencyExaminationDate(Date compExDt) {
    this.competencyExaminationDate = compExDt;
  }


  public String getCompetencyProfessionalName() {
    return competencyProfessionalName;
  }

  public void setCompetencyProfessionalName(String compPronm) {
    this.competencyProfessionalName = compPronm;
  }


  public boolean getConservatorshipAllowsRelIndicator() {
    return conservatorshipAllowsRelIndicator;
  }

  public void setConservatorshipAllowsRelIndicator(boolean conservatorshipAllowsRelIndicator) {
    this.conservatorshipAllowsRelIndicator = conservatorshipAllowsRelIndicator;
  }

  public String getCourtCaseNumber() {
    return courtCaseNumber;
  }

  public void setCourtCaseNumber(String courtcsNo) {
    this.courtCaseNumber = courtcsNo;
  }

  public ParentalRightTerminationType getParentalRightTerminationType() {
    return parentalRightTerminationType;
  }

  public void setParentalRightTerminationType(
      ParentalRightTerminationType parentalRightTerminationType) {
    this.parentalRightTerminationType = parentalRightTerminationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ParentalRightsTermination that = (ParentalRightsTermination) o;
    return Objects.equals(child.getIdentifier(), that.child.getIdentifier()) &&
        Objects.equals(parent.getIdentifier(), that.parent.getIdentifier()) &&
        Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), child.getIdentifier(), parent.getIdentifier(), date);
  }

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

  @Embeddable
  public static class Id implements Serializable {

    private static final long serialVersionUID = -2925716141162949744L;

    @NotNull
    @Size(max = CMS_ID_LEN)
    @Column(name = "FKCHLD_CLT")
    private String childId;

    @NotNull
    @Size(max = CMS_ID_LEN)
    @Column(name = "FKCLIENT_T")
    private String parentId;

    public Id() {
    }

    public Id(String childId, String parentId) {
      this.childId = childId;
      this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ParentalRightsTermination.Id that = (ParentalRightsTermination.Id) o;
      return Objects.equals(childId, that.childId)
          && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(childId, parentId);
    }
  }
}
