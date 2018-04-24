package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import java.util.ArrayList;
import java.util.List;

/** @author CWDS TPT-3 Team */
public class ClientRelationshipAwareDTO extends BaseEntityAwareDTO<ClientRelationship> {

  private final List<ClientRelationship> clientRelationshipList = new ArrayList<>();
  private final List<TribalMembershipVerification> tribalsThatHaveSubTribals = new ArrayList<>();
  private List<TribalMembershipVerification> tribalMembershipVerificationsForDelete =
      new ArrayList<>();
  private final List<PaternityDetail> paternityDetails = new ArrayList<>();
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

  public List<TribalMembershipVerification> getTribalsThatHaveSubTribals() {
    return tribalsThatHaveSubTribals;
  }

  public List<TribalMembershipVerification> getTribalMembershipVerificationsForDelete() {
    return tribalMembershipVerificationsForDelete;
  }

  public void setTribalMembershipVerificationsForDelete(
      List<TribalMembershipVerification> tribalMembershipVerificationsForDelete) {
    this.tribalMembershipVerificationsForDelete = tribalMembershipVerificationsForDelete;
  }

  public List<PaternityDetail> getPaternityDetails() {
    return paternityDetails;
  }
}
