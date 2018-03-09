package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDao;
import gov.ca.cwds.data.legacy.cms.dao.NameTypeDao;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDao;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Hibernate;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;

/** @author CWDS TPT-3 Team */
public abstract class ClientCoreServiceBase<T extends ClientEntityAwareDTO>
    implements ClientCoreService {

  @Inject private ClientDao clientDao;
  @Inject private DeliveredServiceDao deliveredServiceDao;
  @Inject private NameTypeDao nameTypeDao;
  @Inject private SafetyAlertDao safetyAlertDao;
  @Inject private DasHistoryDao dasHistoryDao;
  @Inject private NearFatalityDao nearFatalityDao;
  @Inject private BusinessValidationService businessValidationService;

  @Override
  @Authorize(CLIENT_READ_CLIENT)
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
    businessValidationService.runBusinessValidation(
        enrichValidationData(clientEntityAwareDTO),
        PrincipalUtils.getPrincipal(),
        ClientDroolsConfiguration.INSTANCE
    );
  }

  protected abstract void enrichClientEntityAwareDto(T clientEntityAwareDTO);

  private ClientEntityAwareDTO enrichValidationData(ClientEntityAwareDTO clientEntityAwareDTO) {
    Client client = clientEntityAwareDTO.getEntity();
    String clientId = client.getIdentifier();

    Hibernate.initialize(client.getOtherEthnicities());
    clientEntityAwareDTO.getOtherEthnicities().addAll(client.getOtherEthnicities());

    List<DeliveredService> deliveredServices = deliveredServiceDao.findByClientId(clientId);
    clientEntityAwareDTO.setDeliveredService(deliveredServices);

    List<NearFatality> nearFatalities = nearFatalityDao.findNearFatalitiesByClientId(clientId);
    clientEntityAwareDTO.getNearFatalities().addAll(nearFatalities);

    Client persistentClientState = clientDao.find(clientId);
    clientEntityAwareDTO.setPersistentClientState(persistentClientState);

    Short nameTypeId = clientEntityAwareDTO.getEntity().getNameType().getSystemId();
    clientEntityAwareDTO.getEntity().setNameType(nameTypeDao.find(nameTypeId));

    final Collection<SafetyAlert> safetyAlerts = safetyAlertDao.findByClientId(clientId);
    clientEntityAwareDTO.getSafetyAlerts().addAll(safetyAlerts);

    final Collection<DasHistory> dasHistories = dasHistoryDao.findByClientId(clientId);
    clientEntityAwareDTO.getDasHistories().addAll(dasHistories);

    enrichClientEntityAwareDto((T) clientEntityAwareDTO);

    return clientEntityAwareDTO;
  }

  private void updateClient(ClientEntityAwareDTO clientEntityAwareDTO) {
    final Client client = clientEntityAwareDTO.getEntity();
    clientDao.update(client);
  }

  public BusinessValidationService getBusinessValidationService() {
    return businessValidationService;
  }
}
