package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeParameterObject extends BaseParameterObject {

  private Set<? extends CWSIdentifier> homeLanguages;

  public Set<? extends CWSIdentifier> getHomeLanguages() {
    return homeLanguages;
  }

  public void setHomeLanguages(Set<? extends CWSIdentifier> homeLanguages) {
    this.homeLanguages = homeLanguages;
  }

}
