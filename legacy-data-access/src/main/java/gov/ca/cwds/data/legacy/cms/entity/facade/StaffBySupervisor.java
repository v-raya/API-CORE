package gov.ca.cwds.data.legacy.cms.entity.facade;

import gov.ca.cwds.data.std.ApiMarker;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * The POJO is intended to contain data from different tables and to be filled by {@link
 * SqlResultSetMapping} after {@link NamedNativeQuery} is invoked.
 *
 * <p>The reason to use {@link NamedNativeQuery} and to create the 'facade' class is a performance
 * issue with lots of joins when using {@link Entity} classes.
 *
 * <p>N.B. {@link NamedNativeQuery} and {@link SqlResultSetMapping} are placed in {@link
 * gov.ca.cwds.data.legacy.cms.entity.StaffPerson} {@link Entity} class as they can be found by
 * hibernate in {@link Entity} classes only.
 *
 * @author denys.davydov
 */
public class StaffBySupervisor implements ApiMarker {

  public static final String NATIVE_FIND_STAFF_BY_SUPERVISOR_ID =
      "StaffBySupervisor.nativeFindStaffBySupervisorId";
  public static final String MAPPING_STAFF_BY_SUPERVISOR = "StaffBySupervisor.mapping";
  private static final long serialVersionUID = 1L;
  private String identifier;
  private String racfId;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;

  /**
   * All args constructor is required by {@link SqlResultSetMapping}.
   *
   * @param identifier unique identifier
   * @param racfId RACF ID
   * @param firstName first name
   * @param lastName last name
   * @param phoneNumber phone number
   * @param email email
   */
  public StaffBySupervisor(
      String identifier,
      String racfId,
      String firstName,
      String lastName,
      String phoneNumber,
      String email) {
    this.identifier = identifier;
    this.racfId = racfId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getRacfId() {
    return racfId;
  }

  public void setRacfId(String racfId) {
    this.racfId = racfId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object other) {
    return EqualsBuilder.reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("identifier", identifier)
        .append("racfId", racfId)
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("phoneNumber", phoneNumber)
        .append("email", email)
        .toString();
  }
}
