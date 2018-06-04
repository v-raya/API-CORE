package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDto;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.TribalMembershipVerificationCoreService;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.utils.RelationshipUtil;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Life Cycle for create relationships.
 *
 * @author CWDS TPT-3 Team
 */
class CreateLifeCycle extends CreateUpdateBaseLifeCycle {

  private final TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;

  @Inject
  CreateLifeCycle(
      ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService<ClientRelationship, ClientRelationshipAwareDTO>
          businessValidationService,
      ClientDao clientDao,
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      PaternityDetailDao paternityDetailDao,
      SearchClientRelationshipService searchClientRelationshipService,
      TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService) {
    super(
        clientRelationshipDao,
        businessValidationService,
        clientDao,
        tribalMembershipVerificationDao,
        paternityDetailDao,
        searchClientRelationshipService);

    this.tribalMembershipVerificationCoreService = tribalMembershipVerificationCoreService;
  }

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    createChildDuplicateTribalIfNeeded(bundle);
  }

  @Override
  public void afterBusinessValidation(DataAccessBundle bundle) {
    super.afterBusinessValidation(bundle);
    deleteTribals(bundle);
    createTribals(bundle);
  }

  // RULE - 10030
  private void createChildDuplicateTribalIfNeeded(DataAccessBundle bundle)
      throws DataAccessServicesException {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    Client parent = RelationshipUtil.getParent(awareDto.getEntity());
    Client child = RelationshipUtil.getChild(awareDto.getEntity());

    // if parent has a tribal and child does not
    List<TribalMembershipVerification> parentRelations =
        tribalMembershipVerificationDao.findByClientId(parent.getIdentifier());
    if (CollectionUtils.isEmpty(parentRelations)) {
      return;
    }

    List<TribalMembershipVerification> tribalsWithSubTribals =
        tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
            child.getIdentifier(), parent.getIdentifier());

    if (CollectionUtils.isNotEmpty(tribalsWithSubTribals)) {
      return;
    }

    TribalMembershipVerificationAwareDto tribalMembershipVerificationAwareDto =
        new TribalMembershipVerificationAwareDto(parent.getIdentifier(), child.getIdentifier());
    TribalMembershipVerification tribalEntity = parentRelations.get(0);
    tribalMembershipVerificationAwareDto.setEntity(tribalEntity);
    tribalMembershipVerificationCoreService.update(tribalMembershipVerificationAwareDto);
  }
}
