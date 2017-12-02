package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeParameterObject extends BaseParameterObject<PlacementHome> {

  private List<SCPParameterObject> scpParameterObjects = new ArrayList<>();

  private Set<? extends CWSIdentifier> homeLanguages = new HashSet<>();

  public Set<? extends CWSIdentifier> getHomeLanguages() {
    return homeLanguages;
  }

  public void setHomeLanguages(Set<? extends CWSIdentifier> homeLanguages) {
    this.homeLanguages = homeLanguages;
  }

  public List<SCPParameterObject> getScpParameterObjects() {
    return scpParameterObjects;
  }

  public void setScpParameterObjects(
      List<SCPParameterObject> scpParameterObjects) {
    this.scpParameterObjects = scpParameterObjects;
  }

  public void addSCPParameterObject(SCPParameterObject scpParameterObject) {
    scpParameterObjects.add(scpParameterObject);
  }

}
