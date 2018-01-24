package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ChildClientCoreService;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import java.util.List;

/** @author CWDS TPT-3 Team */
public class ChildClientCoreServiceImpl extends ClientCoreServiceBase<ChildClientEntityAwareDTO> implements ChildClientCoreService {

  protected void enrichClientEntityAwareDto(ChildClientEntityAwareDTO clientEntityAwareDTO) {
    ChildClient childClient = (ChildClient) clientEntityAwareDTO.getEntity();
    if (childClient.getAfdcFcEligibilityIndicatorVar()) {
      List<FCEligibility> fcEligibilities =
          getFcEligibilityDao().findByChildClientId(childClient.getIdentifier());
      clientEntityAwareDTO.setFcEligibilities(fcEligibilities);
    }
  }
}
