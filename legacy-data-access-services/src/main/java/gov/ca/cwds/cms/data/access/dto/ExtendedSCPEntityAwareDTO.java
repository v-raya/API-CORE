package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProviderUc;
import java.util.List;

/**
 * @author CWDS TPT-2 Team
 */
public class ExtendedSCPEntityAwareDTO extends SCPEntityAwareDTO {

  private PlacementHomeInformation placementHomeInformation;
  private ClientScpEthnicity clientScpEthnicity;
  private List<OutOfStateCheck> outOfStateChecks;

  public ExtendedSCPEntityAwareDTO(SCPEntityAwareDTO scpEntityAwareDTO) {
    this.setPhoneNumbers(scpEntityAwareDTO.getPhoneNumbers());
    this.setPlacementHomeId(scpEntityAwareDTO.getPlacementHomeId());
    this.setEthnicity(scpEntityAwareDTO.getEthnicity());
    this.setOtherStatesOfLiving(scpEntityAwareDTO.getOtherStatesOfLiving());
    this.setPrimaryApplicant(scpEntityAwareDTO.isPrimaryApplicant());
    this.setEntity(scpEntityAwareDTO.getEntity());
  }

  private SubstituteCareProviderUc substituteCareProviderUc;

  public void setSubstituteCareProviderUc(SubstituteCareProviderUc substituteCareProviderUc) {
    this.substituteCareProviderUc = substituteCareProviderUc;
  }

  public SubstituteCareProviderUc getSubstituteCareProviderUc() {
    return substituteCareProviderUc;
  }

  public void setPlacementHomeInformation(PlacementHomeInformation placementHomeInformation) {
    this.placementHomeInformation = placementHomeInformation;
  }

  public PlacementHomeInformation getPlacementHomeInformation() {
    return placementHomeInformation;
  }

  public void setClientScpEthnicity(ClientScpEthnicity clientScpEthnicity) {
    this.clientScpEthnicity = clientScpEthnicity;
  }

  public ClientScpEthnicity getClientScpEthnicity() {
    return clientScpEthnicity;
  }

  public void setOutOfStateChecks(List<OutOfStateCheck> outOfStateChecks) {
    this.outOfStateChecks = outOfStateChecks;
  }

  public List<OutOfStateCheck> getOutOfStateChecks() {
    return outOfStateChecks;
  }
}
