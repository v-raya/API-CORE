package gov.ca.cwds.data.legacy.cms.entity.facade;

import gov.ca.cwds.data.std.ApiMarker;
import javax.persistence.SqlResultSetMapping;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The POJO is to hold staff person statistic like a number of clients assigned to the staff person
 * via cases or via referrals.
 *
 * @author denys.davydov
 */
public class ClientCountByStaff implements ApiMarker {

  public static final String NATIVE_COUNT_CLIENTS_BY_STAFF_IDS =
      "ClientCountByStaff.nativeCountClientsByStaffIds";
  public static final String MAPPING_CLIENT_COUNT_BY_STAFF = "ClientCountByStaff.mapping";
  private static final long serialVersionUID = 5524305005314767318L;

  private String identifier;
  private int caseClientsCount;
  private int referralClientsCount;

  /**
   * All args constructor is required by {@link SqlResultSetMapping}.
   *
   * @param identifier - staff person 3-symbols id
   * @param caseClientsCount - number of clients assigned to the staff person via cases
   * @param referralClientsCount - number of clients assigned to the staff person via referrals
   */
  public ClientCountByStaff(String identifier, int caseClientsCount, int referralClientsCount) {
    this.identifier = identifier;
    this.caseClientsCount = caseClientsCount;
    this.referralClientsCount = referralClientsCount;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public int getCaseClientsCount() {
    return caseClientsCount;
  }

  public void setCaseClientsCount(int caseClientsCount) {
    this.caseClientsCount = caseClientsCount;
  }

  public int getReferralClientsCount() {
    return referralClientsCount;
  }

  public void setReferralClientsCount(int referralClientsCount) {
    this.referralClientsCount = referralClientsCount;
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
        .append("caseClientsCount", caseClientsCount)
        .append("referralClientsCount", referralClientsCount)
        .toString();
  }
}
