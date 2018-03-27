package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.PARAM_CHILD_CLIENT_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianEnrolmentStatus;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianTribeType;
import gov.ca.cwds.data.persistence.CompositeKey;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author CWDS TPT-3 Team
 *     <p>The inquiry to and response given by an individual TRIBAL_ORGANIZATION, referred to as
 *     Tribe in CWS/CMS, in regards to the potential membership or eligibility for membership of a
 *     CLIENT into that tribe.
 */
@Entity
@Table(name = "TR_MBVRT")
@NamedQuery(
  name =
      TribalMembershipVerification
          .FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID_NO_TRIBAL_ELIG_FROM,
  query =
      "SELECT t FROM gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification t "
          + "where t.clientId =:"
          + TribalMembershipVerification.PARAM_CLIENT_ID
          + " and t.fkFromTribalMembershipVerification is null"
)
public class TribalMembershipVerification extends CmsPersistentObject {

  public static final String FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID_NO_TRIBAL_ELIG_FROM =
      "gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification";

  public static final String PARAM_CLIENT_ID = "clientId";

  @Id
  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "THIRD_ID")
  private String thirdId;

  @Id
  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "FKCLIENT_T")
  private String clientId;

  @Column(name = "SOC318_DOC")
  private String doc318id;

  @Column(name = "ENROLL_NO")
  private String enrollmentNumber;

  @Column(name = "FKTR_MBVRT")
  private String fkFromTribalMembershipVerification;

  @NotNull
  @Column(name = "FKTRB_ORGT")
  private String fkSentTotribalOrganization;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "INDN_STC", referencedColumnName = "SYS_ID")
  private IndianEnrolmentStatus indianEnrollmentStatus;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "IDN_TRBC", referencedColumnName = "SYS_ID")
  private IndianTribeType indianTribeType;

  @Column(name = "STATUS_DT")
  private LocalDate statusDate;

  @Override
  public Serializable getPrimaryKey() {
    return new CompositeKey(clientId, thirdId);
  }

  public String getDoc318id() {
    return doc318id;
  }

  public void setDoc318id(String doc318id) {
    this.doc318id = doc318id;
  }

  public String getEnrollmentNumber() {
    return enrollmentNumber;
  }

  public void setEnrollmentNumber(String enrollmentNumber) {
    this.enrollmentNumber = enrollmentNumber;
  }

  public String getFkFromTribalMembershipVerification() {
    return fkFromTribalMembershipVerification;
  }

  public void setFkFromTribalMembershipVerification(String fkFromTribalMembershipVerification) {
    this.fkFromTribalMembershipVerification = fkFromTribalMembershipVerification;
  }

  public String getFkSentTotribalOrganization() {
    return fkSentTotribalOrganization;
  }

  public void setFkSentTotribalOrganization(String fkSentTotribalOrganization) {
    this.fkSentTotribalOrganization = fkSentTotribalOrganization;
  }

  public IndianEnrolmentStatus getIndianEnrollmentStatus() {
    return indianEnrollmentStatus;
  }

  public void setIndianEnrollmentStatus(IndianEnrolmentStatus indianEnrollmentStatus) {
    this.indianEnrollmentStatus = indianEnrollmentStatus;
  }

  public IndianTribeType getIndianTribeType() {
    return indianTribeType;
  }

  public void setIndianTribeType(IndianTribeType indianTribeType) {
    this.indianTribeType = indianTribeType;
  }

  public LocalDate getStatusDate() {
    return statusDate;
  }

  public void setStatusDate(LocalDate statusDate) {
    this.statusDate = statusDate;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
