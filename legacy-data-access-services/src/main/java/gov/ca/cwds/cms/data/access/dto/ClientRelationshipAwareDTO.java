package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.util.ArrayList;
import java.util.List;

/** @author CWDS TPT-3 Team */
public class ClientRelationshipAwareDTO extends BaseEntityAwareDTO<ClientRelationship> {
  private final List<ClientRelationship> clientRelationshipList = new ArrayList<>();
  private boolean isNeedMembershipVerification;

  public List<ClientRelationship> getClientRelationshipList() {
    return clientRelationshipList;
  }

  public boolean isNeedMembershipVerification() {
    return isNeedMembershipVerification;
  }

  public void setNeedMembershipVerification(boolean needMembershipVerification) {
    isNeedMembershipVerification = needMembershipVerification;
  }
}
