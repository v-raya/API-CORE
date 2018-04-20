package gov.ca.cwds.data.legacy.cms.entity.facade;

import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The POJO is intended to contain data from different tables and to be filled
 * by @SqlResultSetMapping after @NamedNativeQuery is invoked.
 *
 * <p>
 * The reason to use @NamedNativeQuery and to create the 'facade' class is a
 * performance issue with lots of joins when using @Entity classes.
 *
 * <p>
 * N.B. @NamedNativeQuery and @SqlResultSetMapping are placed in
 * {@link gov.ca.cwds.data.legacy.cms.entity.Referral} @Entity class as they
 * can be found by hibernate in @Entity classes
 *
 * <p>
 * @author CWDS TPT-3 Team
 */
public class ReferralByStaff {

  public static final String NATIVE_FIND_REFERRALS_BY_STAFF_ID = "ReferralByStaff.nativeFindActiveByStaffId";
  public static final String MAPPING_CASE_BY_STAFF = "ReferralByStaff.mapping";

  private String identifier;
  private String referralName;
  private LocalDate receivedDate;
  private LocalTime receivedTime;
  private String referralResponseType;
  private String assignmentIdentifier;
  private AssignmentType assignmentType;

  public ReferralByStaff(
      String identifier,
      String referralName,
      LocalDate receivedDate,
      LocalTime receivedTime,
      String referralResponseType,
      String assignmentIdentifier,
      String assignmentTypeCode) {
    this.identifier = identifier;
    this.referralName = referralName;
    this.receivedDate = receivedDate;
    this.receivedTime = receivedTime;
    this.referralResponseType = referralResponseType;
    this.assignmentIdentifier = assignmentIdentifier;
    this.assignmentType = AssignmentType.fromCode(assignmentTypeCode);
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getReferralName() {
    return referralName;
  }

  public void setReferralName(String referralName) {
    this.referralName = referralName;
  }

  public LocalDate getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(LocalDate receivedDate) {
    this.receivedDate = receivedDate;
  }

  public LocalTime getReceivedTime() {
    return receivedTime;
  }

  public void setReceivedTime(LocalTime receivedTime) {
    this.receivedTime = receivedTime;
  }

  public String getReferralResponseType() {
    return referralResponseType;
  }

  public void setReferralResponseType(String responseType) {
    this.referralResponseType = responseType;
  }

  public String getAssignmentIdentifier() {
    return assignmentIdentifier;
  }

  public void setAssignmentIdentifier(String assignmentIdentifier) {
    this.assignmentIdentifier = assignmentIdentifier;
  }

  public AssignmentType getAssignmentType() {
    return assignmentType;
  }

  public void setAssignmentType(AssignmentType assignmentType) {
    this.assignmentType = assignmentType;
  }
}
