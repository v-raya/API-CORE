package gov.ca.cwds.cms.data.access.service.impl.client;

import static org.mockito.Mockito.mock;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ClientCoreService;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesClientImplementationTest extends BaseDocToolRulesClientTest<Client, ClientEntityAwareDTO> {

  @Override
  protected ClientCoreService getClientCoreService() {
    return new ClientCoreService(mock(ClientDao.class));
  }

  @Override
  protected ClientEntityAwareDTO getAwareDTO() {
    return new ClientEntityAwareDTO();
  }
}
