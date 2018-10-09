package gov.ca.cwds.data.legacy.cms.entity.facade;

import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType;
import gov.ca.cwds.data.std.ApiMarker;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * The POJO is intended to contain data from different tables and to be filled by
 * {@link SqlResultSetMapping} after {@link NamedNativeQuery} is invoked.
 *
 * <p>
 * The reason to use {@link NamedNativeQuery} and to create the 'facade' class is a performance
 * issue with lots of joins when using {@link Entity} classes.
 * </p>
 *
 * <p>
 * N.B. {@link NamedNativeQuery} and {@link SqlResultSetMapping} are placed in
 * {@link gov.ca.cwds.data.legacy.cms.entity.Case} {@link Entity} class as they can be found by
 * hibernate in {@link Entity} classes.
 * </p>
 *
 * @author CWDS TPT-2 Team
 */
public class ClientByStaff implements ApiMarker {

  private static final long serialVersionUID = 1L;

  public static final String NATIVE_FIND_CLIENTS_BY_STAFF_ID = "ClientByStaff.nativeFindClientsByStaffId";
  public static final String MAPPING_CLIENT_BY_STAFF = "ClientByStaff.mapping";

  private String clientIdentifier;
  private String clientFirstName;
  private String clientMiddleName;
  private String clientLastName;
  private String clientNameSuffix;
  private String clientSensitivityType;
  private LocalDate clientBirthDate;
  private LocalDate casePlanReviewDueDate;

  public ClientByStaff(String clientIdentifier, String clientFirstName,
    String clientMiddleName, String clientLastName, String clientNameSuffix,
    String clientSensitivityType, LocalDate clientBirthDate, LocalDate casePlanReviewDueDate) {
    this.clientIdentifier = clientIdentifier;
    this.clientFirstName = clientFirstName;
    this.clientMiddleName = clientMiddleName;
    this.clientLastName = clientLastName;
    this.clientNameSuffix = clientNameSuffix;
    this.clientSensitivityType = clientSensitivityType;
    this.clientBirthDate = clientBirthDate;
    this.casePlanReviewDueDate = casePlanReviewDueDate;
  }

  public String getClientIdentifier() {
    return clientIdentifier;
  }

  public void setClientIdentifier(String clientIdentifier) {
    this.clientIdentifier = clientIdentifier;
  }

  public String getClientFirstName() {
    return clientFirstName;
  }

  public void setClientFirstName(String clientFirstName) {
    this.clientFirstName = clientFirstName;
  }

  public String getClientMiddleName() {
    return clientMiddleName;
  }

  public void setClientMiddleName(String clientMiddleName) {
    this.clientMiddleName = clientMiddleName;
  }

  public String getClientLastName() {
    return clientLastName;
  }

  public void setClientLastName(String clientLastName) {
    this.clientLastName = clientLastName;
  }

  public String getClientNameSuffix() {
    return clientNameSuffix;
  }

  public void setClientNameSuffix(String clientNameSuffix) {
    this.clientNameSuffix = clientNameSuffix;
  }

  public String getClientSensitivityType() {
    return clientSensitivityType;
  }

  public void setClientSensitivityType(String clientSensitivityType) {
    this.clientSensitivityType = clientSensitivityType;
  }

  public LocalDate getClientBirthDate() {
    return clientBirthDate;
  }

  public void setClientBirthDate(LocalDate clientBirthDate) {
    this.clientBirthDate = clientBirthDate;
  }

  public LocalDate getCasePlanReviewDueDate() {
    return casePlanReviewDueDate;
  }

  public void setCasePlanReviewDueDate(LocalDate casePlanReviewDueDate) {
    this.casePlanReviewDueDate = casePlanReviewDueDate;
  }
}
