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
public class ClientIdsByStaff implements ApiMarker {

  public static final String NATIVE_FIND_CLIENT_IDS_BY_STAFF_IDS =
      "ClientIdsByStaff.findClientIdsByStaffIds";
  public static final String MAPPING = "ClientIdsByStaff.mapping";
  private static final long serialVersionUID = 5524305005314767318L;

  private String staffIdentifier;
  private String clientIdentifier;

  /**
   * All args constructor is required by {@link SqlResultSetMapping}.
   *
   * @param staffIdentifier - staff person identifier
   * @param clientIdentifier - identifier for a client to whom staff person is assigned via referral
   * or case
   */
  public ClientIdsByStaff(String staffIdentifier, String clientIdentifier) {
    this.staffIdentifier = staffIdentifier;
    this.clientIdentifier = clientIdentifier;
  }

  public String getStaffIdentifier() {
    return staffIdentifier;
  }

  public void setStaffIdentifier(String staffIdentifier) {
    this.staffIdentifier = staffIdentifier;
  }

  public String getClientIdentifier() {
    return clientIdentifier;
  }

  public void setClientIdentifier(String clientIdentifier) {
    this.clientIdentifier = clientIdentifier;
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
      .append("staffIdentifier", staffIdentifier)
      .append("clientIdentifier", clientIdentifier)
      .toString();
  }
}
