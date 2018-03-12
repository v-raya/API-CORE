package gov.ca.cwds.cms.data.access.service.impl.client;

import static org.mockito.Mockito.mock;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ChildClientCoreService;
import gov.ca.cwds.data.legacy.cms.dao.ChildClientDao;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesChildClientImplementationTest extends BaseDocToolRulesClientTest<ChildClient, ChildClientEntityAwareDTO>{

  @Override
  protected ChildClientCoreService getClientCoreService() {
    return new ChildClientCoreService(mock(ChildClientDao.class));
  }

  @Override
  protected ChildClientEntityAwareDTO getAwareDTO() {
    return new ChildClientEntityAwareDTO();
  }


}
