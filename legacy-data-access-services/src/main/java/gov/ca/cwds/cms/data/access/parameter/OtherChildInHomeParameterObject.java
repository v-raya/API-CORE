package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class OtherChildInHomeParameterObject extends
    BaseParameterObject<OtherChildrenInPlacementHome> {

  private List<OtherPeopleScpRelationship> relationships = new ArrayList<>();

  public OtherChildInHomeParameterObject(String staffPersonId) {
    super(staffPersonId);
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
