package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for create/update/find ClientRelationship with business validation and data processing.
 *
 * @author CWDS TPT-3 Team
 */
public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO> {

  private final UpdateLifeCycle updateLifeCycle;
  private final CreateLifeCycle createLifeCycle;
  private final SearchClientRelationshipService searchClientRelationshipService;

  /**
   * Constructor with injected services.
   *
   * @param clientRelationshipDao client relationship dao
   * @param updateLifeCycle lifecycle for update
   * @param searchClientRelationshipService service for search relationships
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

  public List<ClientRelationship> findRelationshipsBySecondaryClientId(final String clientId) {
    return searchClientRelationshipService.findRelationshipsBySecondaryClientId(clientId);
  }

  public List<ClientRelationship> findRelationshipsByPrimaryClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return searchClientRelationshipService.findRelationshipsByPrimaryClientId(clientId);
  }
}
