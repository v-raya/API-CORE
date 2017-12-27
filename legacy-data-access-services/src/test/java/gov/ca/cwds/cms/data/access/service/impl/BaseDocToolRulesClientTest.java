package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import org.junit.Before;

public abstract class BaseDocToolRulesClientTest extends BaseDocToolRulesTest {

  protected ClientCoreServiceImpl clientCoreService;

  @Before
  public void setUp() {
    clientCoreService = new ClientCoreServiceImpl();
    clientCoreService.setDroolsService(droolsService);
  }

  @Override
  public String getPrivilege() {
    return "";
  }

  public static ClientEntityAwareDTO clientEntityAwareDTO(Client client) {
    ClientEntityAwareDTO clientEntityAwareDTO = new ClientEntityAwareDTO();
    clientEntityAwareDTO.setEntity(client);
    return clientEntityAwareDTO;
  }
}
