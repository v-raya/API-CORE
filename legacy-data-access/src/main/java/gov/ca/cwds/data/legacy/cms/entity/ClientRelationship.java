package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.DATE_CONDITION;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.INACTIVE_IND_CONDITION;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.YesNoUnknownConverter;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "CLN_RELT")
@NamedQuery(
  name = ClientRelationship.NQ_FIND_RELATIONSHIPS_BY_LEFT_SIDE,
  query =
      "SELECT r FROM gov.ca.cwds.data.legacy.cms.entity.ClientRelationship r left join fetch r.type WHERE r.leftSide.identifier = :"
          + CLIENT_ID
          + " AND r.rightSide.identifier != :"
          + CLIENT_ID
          + INACTIVE_IND_CONDITION
          + DATE_CONDITION
)
@NamedQuery(
  name = ClientRelationship.NQ_FIND_RELATIONSHIPS_BY_RIGHT_SIDE,
  query =
      "SELECT r FROM gov.ca.cwds.data.legacy.cms.entity.ClientRelationship r left join fetch r.type WHERE r.rightSide.identifier = :"
          + CLIENT_ID
          + " AND r.leftSide.identifier != :"
          + CLIENT_ID
          + INACTIVE_IND_CONDITION
          + DATE_CONDITION
)
@SuppressWarnings("squid:S3437")
public class ClientRelationship extends CmsPersistentObject {

  public static final String NQ_FIND_RELATIONSHIPS_BY_LEFT_SIDE =
      "gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.findRelationshipsByLeftSide";

  public static final String NQ_FIND_RELATIONSHIPS_BY_RIGHT_SIDE =
      "gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.findRelationshipsByRigtSide";

  public static final String NQ_PARAM_CURRENT_DATE = "currentDate";

  public static final String INACTIVE_IND_CONDITION = " AND r.type.inactiveIndicator = FALSE";

  public static final String DATE_CONDITION =
      " AND ((r.startDate is null) OR (r.startDate <= :"
          + NQ_PARAM_CURRENT_DATE
          + "))"
          + " AND ((r.endDate is null) OR (r.endDate >= :"
          + NQ_PARAM_CURRENT_DATE
          + "))";

  public static final String CLIENT_ID = "clientId";

  private static final long serialVersionUID = -7091947672861995190L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String identifier;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
    name = "FKCLIENT_T",
    referencedColumnName = "IDENTIFIER",
    updatable = false,
    insertable = false
  )
  private Client leftSide;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
    name = "FKCLIENT_0",
    referencedColumnName = "IDENTIFIER",
    updatable = false,
    insertable = false
  )
  private Client rightSide;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CLNTRELC", referencedColumnName = "SYS_ID")
  private ClientRelationshipType type;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Type(type = "yes_no")
  @Column(name = "ABSENT_CD")
  private boolean absentParentIndicator;

  @Column(name = "SAME_HM_CD")
  @Convert(converter = YesNoUnknownConverter.class)
  private YesNoUnknown sameHomeStatus;

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public boolean getAbsentParentIndicator() {
    return absentParentIndicator;
  }

  public void setAbsentParentIndicator(boolean absentParentIndicator) {
    this.absentParentIndicator = absentParentIndicator;
  }

  public ClientRelationshipType getType() {
    return type;
  }

  public void setType(ClientRelationshipType relationshipType) {
    this.type = relationshipType;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public YesNoUnknown getSameHomeStatus() {
    return sameHomeStatus;
  }

  public void setSameHomeStatus(YesNoUnknown sameHomeStatus) {
    this.sameHomeStatus = sameHomeStatus;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public Client getLeftSide() {
    return leftSide;
  }

  public void setLeftSide(Client leftSide) {
    this.leftSide = leftSide;
  }

  public Client getRightSide() {
    return rightSide;
  }

  public void setRightSide(Client rightSide) {
    this.rightSide = rightSide;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClientRelationship)) {
      return false;
    }
    ClientRelationship that = (ClientRelationship) o;
    return Objects.equals(getLeftSide(), that.getLeftSide())
        && Objects.equals(getRightSide(), that.getRightSide())
        && Objects.equals(getType(), that.getType())
        && Objects.equals(getStartDate(), that.getStartDate())
        && Objects.equals(getEndDate(), that.getEndDate());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        super.hashCode(), getLeftSide(), getRightSide(), getType(), getStartDate(), getEndDate());
  }
}
