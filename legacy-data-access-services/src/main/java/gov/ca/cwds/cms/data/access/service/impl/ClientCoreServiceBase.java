package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientScpEthnicityDao;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDao;
import gov.ca.cwds.data.legacy.cms.dao.FCEligibilityDao;
import gov.ca.cwds.data.legacy.cms.dao.MedicalEligibilityApplicationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/** @author CWDS TPT-3 Team */
public abstract class ClientCoreServiceBase<T extends ClientEntityAwareDTO>
    implements ClientCoreService {

  @Inject private DroolsService droolsService;

  @Inject private ClientDao clientDao;

  @Inject private ClientScpEthnicityDao clientScpEthnicityDao;

  @Inject private FCEligibilityDao fcEligibilityDao;

  @Inject private MedicalEligibilityApplicationDao medicalEligibilityApplicationDao;

  @Inject private DeliveredServiceDao deliveredServiceDao;

  @Override
  public Client find(Serializable primaryKey) {
    return clientDao.find(primaryKey);
  }

  @Override
  public Client update(ClientEntityAwareDTO clientEntityAwareDTO)
      throws DataAccessServicesException, DroolsException {
    validate(clientEntityAwareDTO);
    try {
      updateClient(clientEntityAwareDTO);
      return clientEntityAwareDTO.getEntity();
    } catch (Exception e) {
      throw new DataAccessServicesException(e);
    }
  }

  public void validate(ClientEntityAwareDTO clientEntityAwareDTO) throws DroolsException {
    runBusinessValidation(enrichValidationData(clientEntityAwareDTO), PrincipalUtils.getPrincipal());
  }

  protected abstract void enrichClientEntityAwareDto(T clientEntityAwareDTO);

  private ClientEntityAwareDTO enrichValidationData(ClientEntityAwareDTO clientEntityAwareDTO) {
    String clientId = clientEntityAwareDTO.getEntity().getIdentifier();
    List<ClientScpEthnicity> clientScpEthnicityList =
        getClientScpEthnicityDao().findEthnicitiesByClient(clientId);
    clientEntityAwareDTO.getClientScpEthnicities().addAll(clientScpEthnicityList);

    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        medicalEligibilityApplicationDao.findByChildClientId(clientId);
    clientEntityAwareDTO.setMedicalEligibilityApplications(medicalEligibilityApplications);

    List<DeliveredService> deliveredServices = deliveredServiceDao.findByClientId(clientId);
    clientEntityAwareDTO.setDeliveredService(deliveredServices);

    enrichClientEntityAwareDto((T) clientEntityAwareDTO);

    return clientEntityAwareDTO;
  }

  @Override
  public void runBusinessValidation(
      ClientEntityAwareDTO clientEntityAwareDTO, PerryAccount principal) throws DroolsException {
    Set<IssueDetails> detailsList =
        droolsService.performBusinessRules(
            ClientDroolsConfiguration.INSTANCE, clientEntityAwareDTO, principal);
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

  public FCEligibilityDao getFcEligibilityDao() {
    return fcEligibilityDao;
  }

  public void setFcEligibilityDao(FCEligibilityDao fcEligibilityDao) {
    this.fcEligibilityDao = fcEligibilityDao;
  }

  public DroolsService getDroolsService() {
    return droolsService;
  }

  public ClientDao getClientDao() {
    return clientDao;
  }

  public ClientScpEthnicityDao getClientScpEthnicityDao() {
    return clientScpEthnicityDao;
  }

}
