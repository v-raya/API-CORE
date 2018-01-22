package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_PARAM_CURRENT_DATE;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_PARAM_LEFT_SIDE_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_PARAM_RIGHT_SIDE_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus;
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
    name = ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_LEFT_SIDE,
    query =
        "SELECT r FROM ClientRelationship r WHERE r.leftSide.identifier = :" + NQ_PARAM_LEFT_SIDE_ID
            + " AND ((r.startDate is null) OR (r.startDate <= :" + NQ_PARAM_CURRENT_DATE + "))"
            + " AND ((r.endDate is null) OR (r.endDate >= :" + NQ_PARAM_CURRENT_DATE + "))"
)
@NamedQuery(
    name = ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_RIGHT_SIDE,
    query =
        "SELECT r FROM ClientRelationship r WHERE r.rightSide.identifier = :" + NQ_PARAM_RIGHT_SIDE_ID
            + " AND ((r.startDate is null) OR (r.startDate <= :" + NQ_PARAM_CURRENT_DATE + "))"
            + " AND ((r.endDate is null) OR (r.endDate >= :" + NQ_PARAM_CURRENT_DATE +"))"
)
@SuppressWarnings("squid:S3437")
public class ClientRelationship extends CmsPersistentObject {

  public static final String NQ_FIND_CURRENT_RELATIONSHIPS_FROM_LEFT_SIDE =
      "gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.findCurrentRightRelationships";

  public static final String NQ_FIND_CURRENT_RELATIONSHIPS_FROM_RIGHT_SIDE =
      "gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.findCurrentLeftRelationships";

  public static final String NQ_PARAM_LEFT_SIDE_ID = "leftSideId";

  public static final String NQ_PARAM_RIGHT_SIDE_ID = "rightSideId";

  public static final String NQ_PARAM_CURRENT_DATE = "currentDate";

  private static final long serialVersionUID = -7091947672861995190L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String identifier;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
  private Client leftSide;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKCLIENT_0", referencedColumnName = "IDENTIFIER")
  private Client rightSide;

  @ManyToOne(fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "CLNTRELC", referencedColumnName = "SYS_ID")
  private ClientRelationshipType relationshipType;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Type(type = "yes_no")
  @Column(name = "ABSENT_CD")
  private boolean absentParentIndicator;

  @Column(name = "SAME_HM_CD")
  @Convert(converter = SameHomeStatus.SameHomeStatusConverter.class)
  private SameHomeStatus sameHomeStatus;

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

  public ClientRelationshipType getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(ClientRelationshipType relationshipType) {
    this.relationshipType = relationshipType;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public SameHomeStatus getSameHomeStatus() {
    return sameHomeStatus;
  }

  public void setSameHomeStatus(SameHomeStatus sameHomeStatus) {
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ClientRelationship that = (ClientRelationship) o;
    return Objects.equals(leftSide, that.leftSide) &&
        Objects.equals(rightSide, that.rightSide) &&
        Objects.equals(relationshipType, that.relationshipType) &&
        Objects.equals(startDate, that.startDate) &&
        Objects.equals(endDate, that.endDate);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(super.hashCode(), leftSide, rightSide, relationshipType, startDate, endDate);
  }
}
