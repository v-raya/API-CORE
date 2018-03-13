package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDao;
import gov.ca.cwds.data.legacy.cms.dao.NameTypeDao;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDao;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Hibernate;

/** @author CWDS TPT-3 Team */
public class ClientCoreService
    extends DataAccessServiceBase<ClientDao, Client, ClientEntityAwareDTO> {

  @Inject private ClientDao clientDao;
  @Inject private DeliveredServiceDao deliveredServiceDao;
  @Inject private NameTypeDao nameTypeDao;
  @Inject private SafetyAlertDao safetyAlertDao;
  @Inject private DasHistoryDao dasHistoryDao;
  @Inject private NearFatalityDao nearFatalityDao;
  @Inject private PlacementEpisodeDao placementEpisodeDao;
  @Inject private BusinessValidationService businessValidationService;

  @Override
  public Client create(ClientEntityAwareDTO entityAwareDTO) throws DataAccessServicesException {
    return super.create(entityAwareDTO);
  }

  @Inject
  public ClientCoreService(ClientDao crudDao) {
    super(crudDao);
  }

  @Override
  @Authorize(CLIENT_READ_CLIENT)
  public Client find(Serializable primaryKey) {
    return super.find(primaryKey);
  }

  @Override
  protected DataAccessServiceLifecycle getUpdateLifeCycle() {
    return new UpdateLifecycle();
  }

  @Override
  protected DataAccessServiceLifecycle getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  protected DataAccessServiceLifecycle getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  protected class UpdateLifecycle extends DefaultDataAccessLifeCycle<ClientEntityAwareDTO> {

    @Override
    public void beforeBusinessValidation(DataAccessBundle bundle) {
      ClientEntityAwareDTO clientEntityAwareDTO = (ClientEntityAwareDTO) bundle.getAwareDto();

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

      final Collection<PlacementEpisode> placementEpisodes = placementEpisodeDao.findByClientId(clientId);
      clientEntityAwareDTO.getPlacementEpisodes().addAll(placementEpisodes);
    }

    @Override
    public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount)
        throws DroolsException {
      businessValidationService.runBusinessValidation(
          bundle.getAwareDto(), PrincipalUtils.getPrincipal(), ClientDroolsConfiguration.INSTANCE);
    }

  }
}
