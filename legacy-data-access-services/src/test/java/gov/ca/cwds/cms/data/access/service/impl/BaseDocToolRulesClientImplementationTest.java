package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesClientImplementationTest extends BaseDocToolRulesClientTest<Client, ClientEntityAwareDTO> {

  @Override
  protected ClientEntityAwareDTO getAwareDTO() {
    return new ClientEntityAwareDTO();
  }
}
