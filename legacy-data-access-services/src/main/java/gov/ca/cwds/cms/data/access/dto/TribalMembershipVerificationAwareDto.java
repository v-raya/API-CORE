package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;

/**
 * DTO for Tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 **/
public class TribalMembershipVerificationAwareDto
    extends BaseEntityAwareDTO<TribalMembershipVerification> {

  private Client client;

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
