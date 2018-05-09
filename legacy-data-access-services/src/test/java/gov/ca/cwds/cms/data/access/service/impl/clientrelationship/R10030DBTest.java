package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10030DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String USER_ID = "0X5";
  private static final String TRIBAL_PARENT_ID = "64rAJbe0WL";
  private static final String TRIBAL_CHILD_ID = "64rAJbe0W3";
  private static final String PRIMARY_CLIENT_ID = "AJ4piWo0WL";
  private static final String SECONDARY_CLIENT_ID = "AJ4piWo0WP";
  private static final String PRIMARY_CLIENT_ID2 = "2222222222";
  private static final String SECONDARY_CLIENT_ID2 = "3333333333";
  private static final String PRIMARY_CLIENT_ID3 = "4444444444";
  private static final String SECONDARY_CLIENT_ID3 = "5555555555";
  private static final String PRIMARY_CLIENT_ID4 = "6666666666";
  private static final String SECONDARY_CLIENT_ID4 = "7777777777";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;
  private PaternityDetailDao paternityDetailDao;
  private UpdateLifecycle updateLifecycle;
  private SearchClientRelationshipService searchClientRelationshipService;

  @Before
  public void before() throws Exception {
    initUserAccount(USER_ID);
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    searchClientRelationshipService = new SearchClientRelationshipService(clientRelationshipDao);
    updateLifecycle =
        new UpdateLifecycle(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao, updateLifecycle, searchClientRelationshipService);

    cleanAllAndInsert("/dbunit/R10030.xml");
  }

  @Test
  public void testOneChildTwoParent() throws Exception {
    testTribals(1, 2, PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    updateRelationship(PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    testTribals(2, 3, PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
  }

  @Test
  public void testOneChildOneParent() throws Exception {
    testTribals(1, 1, PRIMARY_CLIENT_ID2, SECONDARY_CLIENT_ID2);
    updateRelationship(PRIMARY_CLIENT_ID2, SECONDARY_CLIENT_ID2);
    testTribals(2, 1, PRIMARY_CLIENT_ID2, SECONDARY_CLIENT_ID2);
  }

  @Test
  public void testOneChildNoParent() {
    testTribals(1, 0, PRIMARY_CLIENT_ID3, SECONDARY_CLIENT_ID3);
    updateRelationship(PRIMARY_CLIENT_ID3, SECONDARY_CLIENT_ID3);
    testTribals(1, 0, PRIMARY_CLIENT_ID3, SECONDARY_CLIENT_ID3);
  }

  @Test
  public void testNoChildOneParent() {
    testTribals(0, 1, PRIMARY_CLIENT_ID4, SECONDARY_CLIENT_ID4);
    updateRelationship(PRIMARY_CLIENT_ID4, SECONDARY_CLIENT_ID4);
    testTribals(0, 2, PRIMARY_CLIENT_ID4, SECONDARY_CLIENT_ID4);
  }

  private void testTribals(
      int childTribalsCount,
      int parentTribalsCount,
      String primaryClientId,
      String secondaryClientId) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          List<TribalMembershipVerification> allTribalsBeforeUpdate =
              tribalMembershipVerificationDao.findByClientIds(primaryClientId, secondaryClientId);
          assertNotNull(allTribalsBeforeUpdate);
          assertEquals(childTribalsCount + parentTribalsCount, allTribalsBeforeUpdate.size());

          List<TribalMembershipVerification> childTribalsBeforeUpdate =
              tribalMembershipVerificationDao.findByClientIds(primaryClientId);
          assertNotNull(childTribalsBeforeUpdate);
          assertEquals(childTribalsCount, childTribalsBeforeUpdate.size());

          List<TribalMembershipVerification> parentTribalsBeforeUpdate =
              tribalMembershipVerificationDao.findByClientIds(secondaryClientId);
          assertNotNull(parentTribalsBeforeUpdate);
          assertEquals(parentTribalsCount, parentTribalsBeforeUpdate.size());
        });
  }

  private void updateRelationship(String primaryClientId, String secondaryClientId) {
    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
    enrichNewRelationship(awareDTO, primaryClientId, secondaryClientId);
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          try {
            clientRelationshipCoreService.update(awareDTO);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });
  }

  private void enrichNewRelationship(
      ClientRelationshipAwareDTO awareDTO, String primaryClient, String secondaryClient) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory1) -> {
          Client child = clientDao.find(primaryClient);
          Client secondary = clientDao.find(secondaryClient);

          ClientRelationship relationship = new ClientRelationship();
          relationship.setIdentifier("1011001101");
          relationship.setPrimaryClient(child);
          relationship.setSecondaryClient(secondary);
          relationship.setStartDate(LocalDate.of(2000, 1, 1));
          relationship.setEndDate(LocalDate.of(2019, 1, 1));

          ClientRelationshipType type = new ClientRelationshipType();
          type.setSystemId((short) 190);
          relationship.setType(type);
          relationship.setLastUpdateId("0X5");
          relationship.setLastUpdateTime(LocalDateTime.now());
          relationship.setSameHomeStatus(YesNoUnknown.YES);

          awareDTO.setEntity(relationship);
        });
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
