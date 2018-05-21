package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author CWDS TPT-3 Team */
public class ClientRelationshipAwareDTO extends BaseEntityAwareDTO<ClientRelationship> {

  private final List<ClientRelationship> clientRelationshipList = new ArrayList<>();
  private final List<TribalMembershipVerification> tribalsThatHaveSubTribals = new ArrayList<>();
  private final List<PaternityDetail> primaryClientPaternityDetails = new ArrayList<>();
  private final List<PaternityDetail> secondaryClientPaternityDetails = new ArrayList<>();
  private final Set<TribalMembershipVerification> tribalMembershipVerificationsForDelete =
      new HashSet<>();
  private final Set<TribalMembershipVerification> tribalMembershipVerificationsForCreate =
    new HashSet<>();
  private boolean isNeedMembershipVerification;
  private ClientRelationship relationshipThatHasToBeChanged;
  private Client parent;
  private Client child;

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

  public Set<TribalMembershipVerification> getTribalMembershipVerificationsForDelete() {
    return tribalMembershipVerificationsForDelete;
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

  public void setRelationshipThatHasToBeChanged(ClientRelationship relationshipThatHasToBeChanged) {
    this.relationshipThatHasToBeChanged = relationshipThatHasToBeChanged;
  }

  public Set<TribalMembershipVerification> getTribalMembershipVerificationsForCreate() {
    return tribalMembershipVerificationsForCreate;
  }

  public Client getParent() {
    return parent;
  }

  public void setParent(Client parent) {
    this.parent = parent;
  }

  public Client getChild() {
    return child;
  }

  public void setChild(Client child) {
    this.child = child;
  }
}
