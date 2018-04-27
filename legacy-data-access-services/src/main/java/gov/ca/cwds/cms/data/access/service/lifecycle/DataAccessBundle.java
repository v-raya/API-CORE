package gov.ca.cwds.cms.data.access.service.lifecycle;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import java.util.HashMap;
import java.util.Map;

public class DataAccessBundle<P extends BaseEntityAwareDTO> {
  private P awareDto;
  private final Map<String, Object> daos = new HashMap();
  private BusinessValidationService businessValidationService;

  public DataAccessBundle(P awareDto) {
    this.awareDto = awareDto;
  }

  public P getAwareDto() {
    return awareDto;
  }

  public void setAwareDto(P awareDto) {
    this.awareDto = awareDto;
  }

  public Map<String, Object> getDaos() {
    return daos;
  }

  public BusinessValidationService getBusinessValidationService() {
    return businessValidationService;
  }

  public void setBusinessValidationService(
      BusinessValidationService businessValidationService) {
    this.businessValidationService = businessValidationService;
  }
}
