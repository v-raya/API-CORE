package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.Set;

public class ClientCoreServiceImpl implements ClientCoreService {

  @Inject
  private DroolsService droolsService;

  @Inject
  private ClientDao clientDao;

  @Override
  public Client create(ClientEntityAwareDTO entityAwareDTO) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Client update(ClientEntityAwareDTO clientEntityAwareDTO)
      throws DataAccessServicesException {
    try {
      runBusinessValidation(clientEntityAwareDTO);
      updateClient(clientEntityAwareDTO);
      return clientEntityAwareDTO.getEntity();
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  @Override
  public void runBusinessValidation(ClientEntityAwareDTO clientEntityAwareDTO)
      throws DroolsException {
    Set<IssueDetails> detailsList =
        droolsService.performBusinessRules(
            clientEntityAwareDTO, ClientDroolsConfiguration.INSTANCE);
    if (!detailsList.isEmpty()) {
      throw new BusinessValidationException("Can't create Client", detailsList);
    }
  }

  private void updateClient(ClientEntityAwareDTO clientEntityAwareDTO) {
    final Client client = clientEntityAwareDTO.getEntity();
    clientDao.update(client);
  }

  public void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

  public void setClientDao(ClientDao clientDao) {
    this.clientDao = clientDao;
  }
}
