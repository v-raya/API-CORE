package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;

/**
 * Life Cycle for update relationships.
 *
 * @author CWDS TPT-3 Team
 * */
class UpdateLifeCycle extends CreateUpdateBaseLifeCycle {

  @Inject
  UpdateLifeCycle(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService<ClientRelationship, ClientRelationshipAwareDTO>
          businessValidationService,
      ClientDao clientDao,
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      PaternityDetailDao paternityDetailDao,
      SearchClientRelationshipService searchClientRelationshipService) {
    super(
        clientRelationshipDao,
        businessValidationService,
        clientDao,
        tribalMembershipVerificationDao,
        paternityDetailDao,
        searchClientRelationshipService);
  }
}