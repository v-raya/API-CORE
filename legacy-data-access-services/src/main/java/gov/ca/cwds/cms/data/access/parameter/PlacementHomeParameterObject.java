package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
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
  private List<OtherChildInHomeParameterObject> otherChildrenInHomeParameterObjects = new ArrayList<>();
  private List<OtherAdultInHomeParameterObject> otherAdultInHomeParameterObjects = new ArrayList<>();
  private EmergencyContactDetail emergencyContactDetail;

  private Set<? extends CWSIdentifier> homeLanguages = new HashSet<>();

  public PlacementHomeParameterObject(String staffPersonId) {
    super(staffPersonId);
  }

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
      List<SCPParameterObject> parameterObjects) {
    this.scpParameterObjects = parameterObjects;
  }

  public void addSCPParameterObject(SCPParameterObject parameterObject) {
    scpParameterObjects.add(parameterObject);
  }

  public List<OtherChildInHomeParameterObject> getOtherChildrenInHomeParameterObjects() {
    return otherChildrenInHomeParameterObjects;
  }

  public void setOtherChildrenInHomeParameterObjects(
      List<OtherChildInHomeParameterObject> parameterObjects) {
    this.otherChildrenInHomeParameterObjects = parameterObjects;
  }

  public void addOtherChildrenInHomeParameterObject(OtherChildInHomeParameterObject parameterObject) {
    otherChildrenInHomeParameterObjects.add(parameterObject);
  }

  public List<OtherAdultInHomeParameterObject> getOtherAdultInHomeParameterObjects() {
    return otherAdultInHomeParameterObjects;
  }

  public void setOtherAdultInHomeParameterObjects(
      List<OtherAdultInHomeParameterObject> parameterObjects) {
    this.otherAdultInHomeParameterObjects = parameterObjects;
  }

  public void addOtherAdultInHomeParameterObject(OtherAdultInHomeParameterObject parameterObject) {
    this.otherAdultInHomeParameterObjects.add(parameterObject);
  }

  public void addOtherChildInHomeParameterObject(OtherChildInHomeParameterObject parameterObject) {
    this.otherChildrenInHomeParameterObjects.add(parameterObject);
  }

  public EmergencyContactDetail getEmergencyContactDetail() {
    return emergencyContactDetail;
  }

  public void setEmergencyContactDetail(
      EmergencyContactDetail emergencyContactDetail) {
    this.emergencyContactDetail = emergencyContactDetail;
  }

}
