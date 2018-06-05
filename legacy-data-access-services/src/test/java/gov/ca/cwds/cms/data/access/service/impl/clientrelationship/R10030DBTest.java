package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.TribalMembershipVerificationCoreService;
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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10030DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String USER_ID = "0X5";
  private static final String CHILD_CLIENT_ID = "1222222222";
  private static final String PARENT_CLIENT_ID = "1111111111";
  private static final String ORGANIZATION = "2222222222";
  private static final String EXISTING_TRIBAL_ID = "2333333333";
  private static final Short TYPE = 2100;
  private static final String PARENT_CLIENT_ID_2 = "9999999921";
  private static final String CHILD_CLIENT_ID_2 = "8888888821";
  private static final String EXISTING_TRIBAL_ID_2 = "9999999921";
  private static final String EXISTING_TRIBAL_ID_3 = "8888888821";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientCoreService clientCoreService;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;
  private BusinessValidationService businessValidationService;
  private CreateLifeCycle createLifeCycle;
  private UpdateLifeCycle updateLifeCycle;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private PaternityDetailDao paternityDetailDao;
  private SearchClientRelationshipService searchClientRelationshipService;

  @Before
  public void before() throws Exception {
    initUserAccount(USER_ID);
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    clientCoreService = new ClientCoreService(clientDao);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    searchClientRelationshipService = new SearchClientRelationshipService(clientRelationshipDao);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);

    gov.ca.cwds.cms.data.access.service.impl.tribalmembership.UpdateLifeCycle updateLifeCycle1 =
        new gov.ca.cwds.cms.data.access.service.impl.tribalmembership.UpdateLifeCycle(
            tribalMembershipVerificationDao, clientCoreService, businessValidationService);
    gov.ca.cwds.cms.data.access.service.impl.tribalmembership.CreateLifeCycle createLifeCycle1 =
        new gov.ca.cwds.cms.data.access.service.impl.tribalmembership.CreateLifeCycle(
            tribalMembershipVerificationDao, clientCoreService, businessValidationService);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(
            tribalMembershipVerificationDao, createLifeCycle1, updateLifeCycle1);

    updateLifeCycle =
        new UpdateLifeCycle(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService);
    createLifeCycle =
        new CreateLifeCycle(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService,
            tribalMembershipVerificationCoreService);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao,
            updateLifeCycle,
            searchClientRelationshipService,
            createLifeCycle);
  }

  @Test
  public void testNoChildTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R10030_2.xml");

    List<TribalMembershipVerification> childTribals = getTribals(CHILD_CLIENT_ID);
    List<TribalMembershipVerification> parentTribals = getTribals(PARENT_CLIENT_ID);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(0, childTribals.size());
    assertEquals(1, parentTribals.size());

    createRelationship(new ClientRelationshipAwareDTO(), PARENT_CLIENT_ID, CHILD_CLIENT_ID);

    childTribals = getTribals(CHILD_CLIENT_ID);
    parentTribals = getTribals(PARENT_CLIENT_ID);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(1, childTribals.size());
    assertEquals(1, parentTribals.size());
    assertNotNull(parentTribals.get(0));
    assertEquals(null, parentTribals.get(0).getFkFromTribalMembershipVerification());
    assertEquals(EXISTING_TRIBAL_ID, parentTribals.get(0).getThirdId());
    assertEquals(PARENT_CLIENT_ID, parentTribals.get(0).getClientId());
    assertEquals(ORGANIZATION, parentTribals.get(0).getFkSentToTribalOrganization());
    assertEquals(TYPE, parentTribals.get(0).getIndianTribeType().getSystemId());

    assertEquals(TYPE, childTribals.get(0).getIndianTribeType().getSystemId());
    assertEquals(CHILD_CLIENT_ID, childTribals.get(0).getClientId());
    assertEquals(ORGANIZATION, childTribals.get(0).getFkSentToTribalOrganization());
  }

  @Test
  public void testChildTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R10030_2.xml");

    List<TribalMembershipVerification> childTribals = getTribals(CHILD_CLIENT_ID_2);
    List<TribalMembershipVerification> parentTribals = getTribals(PARENT_CLIENT_ID_2);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(1, childTribals.size());
    assertEquals(1, parentTribals.size());
    assertEquals(EXISTING_TRIBAL_ID_2, parentTribals.get(0).getThirdId());
    assertEquals(EXISTING_TRIBAL_ID_3, childTribals.get(0).getThirdId());

    createRelationship(new ClientRelationshipAwareDTO(), PARENT_CLIENT_ID_2, CHILD_CLIENT_ID_2);

    childTribals = getTribals(CHILD_CLIENT_ID_2);
    parentTribals = getTribals(PARENT_CLIENT_ID_2);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(1, childTribals.size());
    assertEquals(1, parentTribals.size());
    assertEquals(EXISTING_TRIBAL_ID_2, parentTribals.get(0).getThirdId());
    assertEquals(EXISTING_TRIBAL_ID_3, childTribals.get(0).getThirdId());
  }

  private List<TribalMembershipVerification> getTribals(String clientId) {
    List<TribalMembershipVerification> allTribalsBeforeUpdate = new ArrayList<>();
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          allTribalsBeforeUpdate.addAll(tribalMembershipVerificationDao.findByClientId(clientId));
        });
    return allTribalsBeforeUpdate;
  }

  private void createRelationship(
      ClientRelationshipAwareDTO awareDTO, String primaryClient, String secondaryClient) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory1) -> {
          Client child = clientDao.find(primaryClient);
          Client secondary = clientDao.find(secondaryClient);

          ClientRelationship relationship = new ClientRelationship();
          relationship.setIdentifier("0011001100");
          relationship.setPrimaryClient(child);
          relationship.setSecondaryClient(secondary);
          relationship.setStartDate(LocalDate.of(2000, 1, 1));
          relationship.setEndDate(LocalDate.of(2019, 1, 1));

          ClientRelationshipType type = new ClientRelationshipType();
          type.setSystemId((short) 207);
          relationship.setType(type);
          relationship.setLastUpdateId("0X5");
          relationship.setLastUpdateTime(LocalDateTime.now());
          relationship.setSameHomeStatus(YesNoUnknown.YES);

          awareDTO.setEntity(relationship);
          try {
            clientRelationshipCoreService.create(awareDTO);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
