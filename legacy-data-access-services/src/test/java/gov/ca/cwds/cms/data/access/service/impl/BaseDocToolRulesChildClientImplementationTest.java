package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesChildClientImplementationTest extends BaseDocToolRulesClientTest<ChildClient, ChildClientEntityAwareDTO>{

  @Override
  protected ClientCoreServiceBase<ChildClientEntityAwareDTO> getClientCoreService() {
    return new ChildClientCoreServiceImpl();
  }

  @Override
  protected ChildClientEntityAwareDTO getAwareDTO() {
    return new ChildClientEntityAwareDTO();
  }


}
