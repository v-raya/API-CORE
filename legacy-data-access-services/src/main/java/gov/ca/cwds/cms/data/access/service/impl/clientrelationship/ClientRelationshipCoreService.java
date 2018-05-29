package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.cms.data.access.mapper.ClientMapper;
import gov.ca.cwds.cms.data.access.service.ClientRelationshipService;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import java.time.LocalDateTime;
import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.utils.PrincipalUtils;

/**
 * Service for create/update/find ClientRelationship with business validation and data processing.
 *
 * @author CWDS TPT-3 Team
 */
public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO>
    implements ClientRelationshipService {

  @Inject private ClientMapper clientMapper;
  private final UpdateLifeCycle updateLifeCycle;
  private final CreateLifeCycle createLifeCycle;
  private final SearchClientRelationshipService searchClientRelationshipService;

  /**
   * Constructor with injected services.
   *
   * @param clientRelationshipDao client relationship dao
   * @param updateLifeCycle lifecycle for update
   * @param searchClientRelationshipService service for search relationships
   * @param createLifeCycle common create lifecycle
   */
  @Inject
  public ClientRelationshipCoreService(
      final ClientRelationshipDao clientRelationshipDao,
      final UpdateLifeCycle updateLifeCycle,
      final SearchClientRelationshipService searchClientRelationshipService,
      final CreateLifeCycle createLifeCycle) {
    super(clientRelationshipDao);
    this.updateLifeCycle = updateLifeCycle;
    this.searchClientRelationshipService = searchClientRelationshipService;
    this.createLifeCycle = createLifeCycle;
  }

  @Override
  public ClientRelationship create(ClientRelationshipAwareDTO entityAwareDto)
      throws DataAccessServicesException {
    String staffPerson = PrincipalUtils.getStaffPersonId();
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(staffPerson);
    entityAwareDto.getEntity().setIdentifier(CmsKeyIdGenerator.getNextValue(staffPerson));
    return super.create(entityAwareDto);
  }

  @Override
  public ClientRelationship update(ClientRelationshipAwareDTO entityAwareDto)
      throws DataAccessServicesException {
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(PrincipalUtils.getStaffPersonId());
    return super.update(entityAwareDto);
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getUpdateLifeCycle() {
    return updateLifeCycle;
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getCreateLifeCycle() {
    return createLifeCycle;
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  public List<ClientRelationship> findRelationshipsBySecondaryClient(final Client client) {
    return searchClientRelationshipService.findRelationshipsBySecondaryClient(client);
  }

  public List<ClientRelationship> findRelationshipsByPrimaryClient(final Client client) {
    return searchClientRelationshipService.findRelationshipsByPrimaryClient(client);
  }

  public List<ClientRelationship> findRelationshipsBySecondaryClientId(final String clientId) {
    return searchClientRelationshipService.findRelationshipsBySecondaryClientId(clientId);
  }

  public List<ClientRelationship> findRelationshipsByPrimaryClientId(final String clientId) {
    return searchClientRelationshipService.findRelationshipsByPrimaryClientId(clientId);
  }

  @Override
  public void createRelationship(ClientRelationshipDTO clientRelationshipDto)
      throws DataAccessServicesException {
    create(form(clientRelationshipDto));
  }

  private ClientRelationshipAwareDTO form(ClientRelationshipDTO clientRelationshipDto) {
    ClientRelationshipAwareDTO awareDto = new ClientRelationshipAwareDTO();
    ClientRelationship clientRelationship = new ClientRelationship();
    clientRelationship.setIdentifier(clientRelationshipDto.getIdentifier());
    clientRelationship.setAbsentParentIndicator(clientRelationshipDto.isAbsentParentIndicator());
    ClientRelationshipType clientRelationshipType = new ClientRelationshipType();
    clientRelationshipType.setSystemId(clientRelationshipDto.getType());
    clientRelationship.setType(clientRelationshipType);
    clientRelationship.setEndDate(clientRelationshipDto.getEndDate());
    clientRelationship.setSameHomeStatus(clientRelationshipDto.getSameHomeStatus());
    clientRelationship.setStartDate(clientRelationshipDto.getStartDate());

    crudDao.getSessionFactory().getCurrentSession().flush();
    Client legacyPrimaryClient =
        clientMapper.toLegacyClient(clientRelationshipDto.getSecondaryClient());
    Client legacySecondaryClient =
        clientMapper.toLegacyClient(clientRelationshipDto.getPrimaryClient());

    clientRelationship.setSecondaryClient(
        crudDao
            .getSessionFactory()
            .getCurrentSession()
            .load(Client.class, legacySecondaryClient.getIdentifier()));
    clientRelationship.setPrimaryClient(
        crudDao
            .getSessionFactory()
            .getCurrentSession()
            .load(Client.class, legacyPrimaryClient.getIdentifier()));

    clientRelationship.setLastUpdateTime(clientRelationship.getLastUpdateTime());
    clientRelationship.setLastUpdateId(clientRelationship.getLastUpdateId());

    awareDto.setEntity(clientRelationship);
    return awareDto;
  }

  public void setClientMapper(ClientMapper clientMapper) {
    this.clientMapper = clientMapper;
  }
}
