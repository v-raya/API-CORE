package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class OtherAdultInHomeParameterObject extends BaseParameterObject<OtherAdultsInPlacementHome> {

  private List<OtherPeopleScpRelationship> relationships = new ArrayList<>();
  private List<OutOfStateCheck> outOfStateChecks = new ArrayList<>();

  public OtherAdultInHomeParameterObject(String staffPersonId) {
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

  public List<OutOfStateCheck> getOutOfStateChecks() {
    return outOfStateChecks;
  }

  public void setOutOfStateChecks(
      List<OutOfStateCheck> outOfStateChecks) {
    this.outOfStateChecks = outOfStateChecks;
  }

  public void addOutOfStateCheck(OutOfStateCheck outOfStateCheck) {
    outOfStateChecks.add(outOfStateCheck);
  }
}


