package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
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
import org.apache.commons.lang3.StringUtils;

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

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    enrichWithRelationshipThatShouldBeChanged(bundle);
    enrichWithTribalsMembershipVerifications(bundle);
  }

  @Override
  public void afterBusinessValidation(DataAccessBundle bundle) {
    super.afterBusinessValidation(bundle);
    deleteTribals(bundle);
    createTribals(bundle);
  }

  private void enrichWithRelationshipThatShouldBeChanged(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    if (StringUtils.isEmpty(awareDto.getEntity().getIdentifier())) {
      return;
    }

    awareDto.setRelationshipThatHasToBeChanged(
      searchClientRelationshipService.getRelationshipById(awareDto.getEntity().getIdentifier()));
  }

  /**
   * Rule - 08861 "1) If a parent has one of the following relationships: Birth Mother, Alleged
   * Mother, Presumed Mother, Birth Father, Alleged Father or Presumed Father and the child in the
   * relationship has a membership status entered on their associated Tribal Membership Verification
   * row and if user tries to change relationship to something other than relationships listed
   * above, display error and reset to previous value. 2) If a parent has one of the following
   * relationships: Birth Mother, Alleged Mother, Presumed Mother, Birth Father, Alleged Father or
   * Presumed Father and the child in the relationship does not have a membership status entered on
   * their associated Tribal Membership Verification row, and if user tries to change relationship
   * to something other than relationships listed above, allow change in client relationship and
   * delete the associated child's Tribal Membership Verification rows created from this parent. 3)
   * If the relationship changes to one of the relationships listed above, then no action needs to
   * be taken."
   */
  private void enrichWithTribalsMembershipVerifications(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();

    ClientRelationship relationshipThatHasToBeChanged =
      awareDto.getRelationshipThatHasToBeChanged();
    if (relationshipThatHasToBeChanged == null) {
      return;
    }

    Client parent = RelationshipUtil.getParent(relationshipThatHasToBeChanged);
    Client child = RelationshipUtil.getChild(relationshipThatHasToBeChanged);

    List<TribalMembershipVerification> tribalsThatHasSubTribals =
      tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
        child.getIdentifier(), parent.getIdentifier());

    if (CollectionUtils.isNotEmpty(tribalsThatHasSubTribals)) {
      awareDto.getTribalsThatHaveSubTribals().addAll(tribalsThatHasSubTribals);
    }
  }
}
