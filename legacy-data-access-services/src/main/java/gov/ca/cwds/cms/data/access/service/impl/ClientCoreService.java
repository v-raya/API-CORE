package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.authorizer.ClientResultReadAuthorizer.CLIENT_RESULT_READ_OBJECT;
import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherClientNameDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientServiceProviderDao;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDao;
import gov.ca.cwds.data.legacy.cms.dao.NameTypeDao;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDao;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  @Inject private OtherClientNameService otherClientNameService;
  @Inject private ClientServiceProviderDao clientServiceProviderDao;
  @Inject private ClientRelationshipDao clientRelationshipDao;
  @Inject private BusinessValidationService businessValidationService;
  @Inject private SsaName3Dao ssaName3Dao;

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

  @Authorize(CLIENT_RESULT_READ_OBJECT)
  public Client getClientByLicNumAndChildId(String licenseNumber, String childId) {
    return crudDao.findByLicNumAndChildId(licenseNumber, childId);
  }

  @Authorize(CLIENT_RESULT_READ_OBJECT)
  public Client getClientByFacilityIdAndChildId(String facilityId, String childId) {
    return crudDao.findByFacilityIdAndChildId(facilityId, childId);
  }

  @Authorize(CLIENT_RESULT_READ_OBJECT)
  public List<Client> getClientsByLicenseNumber(String licenseNumber) {
    Stream<Client> clients = crudDao.streamByLicenseNumber(licenseNumber);
    return clients.collect(Collectors.toList());
  }

  @Authorize(CLIENT_RESULT_READ_OBJECT)
  public List<Client> getClientsByLicenseNumber(Integer licenseNumber) {
    Stream<Client> clients = crudDao.streamByLicenseNumber(licenseNumber);
    return clients.collect(Collectors.toList());
  }

  @Authorize(CLIENT_RESULT_READ_OBJECT)
  public List<Client> streamByFacilityId(String facilityId) {
    Stream<Client> clients = crudDao.streamByFacilityId(facilityId);
    return clients.collect(Collectors.toList());
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

      final Collection<PlacementEpisode> placementEpisodes =
          placementEpisodeDao.findByClientId(clientId);
      clientEntityAwareDTO.getPlacementEpisodes().addAll(placementEpisodes);

      final Collection<ClientServiceProvider> clientServiceProviders =
          clientServiceProviderDao.findByClientId(clientId);
      clientEntityAwareDTO.getClientServiceProviders().addAll(clientServiceProviders);

      LocalDate now = LocalDate.now();
      final Collection<ClientRelationship> relationshipsByPrimaryClientId =
          clientRelationshipDao.findRelationshipsByPrimaryClientId(clientId, now);
      final Collection<ClientRelationship> relationshipsBySecondaryClientId =
          clientRelationshipDao.findRelationshipsBySecondaryClientId(clientId, now);
      clientEntityAwareDTO.getClientRelationships().addAll(relationshipsByPrimaryClientId);
      clientEntityAwareDTO.getClientRelationships().addAll(relationshipsBySecondaryClientId);
    }

    @Override
    public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
      businessValidationService.runBusinessValidation(
          bundle.getAwareDto(), PrincipalUtils.getPrincipal(), ClientDroolsConfiguration.INSTANCE);
    }

    @Override
    public void afterStore(DataAccessBundle bundle) {
      ClientEntityAwareDTO clientEntityAwareDTO = (ClientEntityAwareDTO) bundle.getAwareDto();
      createOtherNameIfNeeded(clientEntityAwareDTO);
      updatePhoneticNameIfNeeded(clientEntityAwareDTO);
    }

    private void createOtherNameIfNeeded(ClientEntityAwareDTO clientEntityAwareDTO) {
      OtherClientNameDTO otherClientName = clientEntityAwareDTO.getOtherClientName();
      if (otherClientName != null) {
        otherClientNameService.createOtherClientName(otherClientName);
      }
    }

    private void updatePhoneticNameIfNeeded(ClientEntityAwareDTO clientEntityAwareDTO) {
      if (clientEntityAwareDTO.isUpdateClientPhoneticName()) {
        Client client = clientEntityAwareDTO.getEntity();
        SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
        parameterObject.setTableName(Constants.PhoneticSearchTables.CLT_PHNT);
        parameterObject.setCrudOper(
            Constants.SsaName3StoredProcedureCrudOperationCode.UPDATE_OPERATION_CODE);
        parameterObject.setNameCd(Constants.PhoneticPrimaryNameCode.CLIENT_NAME);
        parameterObject.setIdentifier(client.getIdentifier());
        parameterObject.setFirstName(client.getCommonFirstName());
        parameterObject.setLastName(client.getCommonLastName());
        parameterObject.setMiddleName(client.getCommonMiddleName());
        parameterObject.setUpdateTimeStamp(new Date());
        parameterObject.setUpdateId(client.getLastUpdateId());
        ssaName3Dao.callStoredProc(parameterObject);
      }
    }
  }
}
