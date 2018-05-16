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
  private final List<PaternityDetail> primaryClientPaternityDetails = new ArrayList<>();
  private final List<PaternityDetail> secondaryClientPaternityDetails = new ArrayList<>();
  private List<TribalMembershipVerification> tribalMembershipVerificationsForDelete =
      new ArrayList<>();
  private boolean isNeedMembershipVerification;
  private ClientRelationship relationshipThatHasToBeChanged;

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
    if (tribalMembershipVerificationsForDelete == null) {
      return;
    }
    this.tribalMembershipVerificationsForDelete = tribalMembershipVerificationsForDelete;
  }

  public List<PaternityDetail> getPrimaryClientPaternityDetails() {
    return primaryClientPaternityDetails;
  }

  public List<PaternityDetail> getSecondaryClientPaternityDetails() {
    return secondaryClientPaternityDetails;
  }

  public ClientRelationship getRelationshipThatHasToBeChanged() {
    return relationshipThatHasToBeChanged;
  }

  public void setRelationshipThatHasToBeChanged(
    ClientRelationship relationshipThatHasToBeChanged) {
    this.relationshipThatHasToBeChanged = relationshipThatHasToBeChanged;
  }
}
