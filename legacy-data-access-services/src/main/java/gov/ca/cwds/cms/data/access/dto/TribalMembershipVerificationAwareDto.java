package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for Tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 **/
public class TribalMembershipVerificationAwareDto
    extends BaseEntityAwareDTO<TribalMembershipVerification> {

  private List<TribalMembershipVerification> childTribals = new ArrayList<>();
  private List<TribalMembershipVerification> parentTribals = new ArrayList<>();
  private TribalMembershipVerification childTribalForDuplicate;

  private final String parentId;
  private final String childId;

  private Client childClient;
  private Client parentClient;

  public TribalMembershipVerificationAwareDto(String parentId,
    String childId) {
    this.parentId = parentId;
    this.childId = childId;
  }

  public Client getChildClient() {
    return childClient;
  }

  public void setChildClient(Client childClient) {
    this.childClient = childClient;
  }

  public Client getParentClient() {
    return parentClient;
  }

  public void setParentClient(Client parentClient) {
    this.parentClient = parentClient;
  }

  public String getParentId() {
    return parentId;
  }

  public String getChildId() {
    return childId;
  }

  public List<TribalMembershipVerification> getChildTribals() {
    return childTribals;
  }

  public TribalMembershipVerification getChildTribalForDuplicate() {
    return childTribalForDuplicate;
  }

  public void setChildTribalForDuplicate(
    TribalMembershipVerification childTribalForDuplicate) {
    this.childTribalForDuplicate = childTribalForDuplicate;
  }

  public List<TribalMembershipVerification> getParentTribals() {
    return parentTribals;
  }
}
