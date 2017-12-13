package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class OtherChildInHomeEntityAwareDTO extends
    BaseEntityAwareDTO<OtherChildrenInPlacementHome> {

  private List<OtherPeopleScpRelationship> relationships = new ArrayList<>();

  public OtherChildInHomeEntityAwareDTO(PerryAccount perryAccount) {
    super(perryAccount);
  }

  public List<OtherPeopleScpRelationship> getRelationships() {
    return relationships;
  }

  public void setRelationships(
      List<OtherPeopleScpRelationship> relationships) {
    this.relationships = relationships;
  }

  public void addRelationship(OtherPeopleScpRelationship relationship) {
    relationships.add(relationship);
  }
}
