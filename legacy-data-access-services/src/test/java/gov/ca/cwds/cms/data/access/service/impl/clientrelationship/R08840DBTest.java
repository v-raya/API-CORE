package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
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
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/** @author CWDS TPT-3 Team */
public class R08840DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String PARENT_CLIENT_1 = "1111111111";
  public static final String CHILD_CLIENT_1 = "2222222222";
  public static final String TRIBAL_THIRD_ID_FOR_DELETE = "MObcDoVabf";
  public static final String TRIBAL_THIRD_ID_PARENT_1 = "1111111111";

  public static final String PARENT_CLIENT_2 = "3333333333";
  public static final String CHILD_CLIENT_2 = "4444444444";
  public static final String TRIBAL_THIRD_ID_PARENT_2 = "1222222222";
  public static final String TRIBAL_THIRD_ID_CHILD_2 = "1333333333";

  public static final String PARENT_CLIENT_3 = "5555555555";
  public static final String CHILD_CLIENT_3 = "6666666666";
  public static final String TRIBAL_THIRD_ID_PARENT_3 = "1555555555";

  public static final String PARENT_CLIENT_4 = "7777777777";
  public static final String CHILD_CLIENT_4 = "8888888888";
  public static final String CHILD_CLIENT_4_1 = "9999999999";
  public static final String CHILD_CLIENT_4_2 = "0000000000";
  public static final String TRIBAL_THIRD_ID_PARENT_4 = "1999999999";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;
  private PaternityDetailDao paternityDetailDao;
  private UpdateLifeCycle updateLifeCycle;
  private CreateLifeCycle createLifeCycle;
  private SearchClientRelationshipService searchClientRelationshipService;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;

  @Before
  public void before() {
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    searchClientRelationshipService = new SearchClientRelationshipService(clientRelationshipDao);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(tribalMembershipVerificationDao, null, null);
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
  public void test_noRelations_oneTribalForChildExist_withDateBefore2005() throws Exception {
    cleanAllAndInsert("/dbunit/R08840.xml");

    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsBeforeUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_1));
          childTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_1));
          allTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_1, CHILD_CLIENT_1));
        });

    assertNotNull(allTribalsBeforeUpdate);
    assertEquals(2, allTribalsBeforeUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(1, childTribalsBeforeUpdate.size());
    assertNotNull(childTribalsBeforeUpdate.get(0));
    assertEquals(TRIBAL_THIRD_ID_FOR_DELETE, childTribalsBeforeUpdate.get(0).getThirdId());
    assertEquals(null, childTribalsBeforeUpdate.get(0).getFkFromTribalMembershipVerification());
    assertEquals(CHILD_CLIENT_1, childTribalsBeforeUpdate.get(0).getClientId());

    createRelationship(awareDTO, PARENT_CLIENT_1, CHILD_CLIENT_1);

    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsAfterUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_1));
          childTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_1));
          allTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_1, CHILD_CLIENT_1));
        });

    assertNotNull(allTribalsAfterUpdate);
    assertEquals(2, allTribalsAfterUpdate.size());
    assertNotNull(parentTribalsAfterUpdate);
    assertEquals(1, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterUpdate);
    assertEquals(1, childTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterUpdate.get(0));
    assertNotEquals(TRIBAL_THIRD_ID_FOR_DELETE, childTribalsAfterUpdate.get(0).getThirdId());
    assertEquals(
        TRIBAL_THIRD_ID_PARENT_1,
        childTribalsAfterUpdate.get(0).getFkFromTribalMembershipVerification());
    assertEquals(CHILD_CLIENT_1, childTribalsAfterUpdate.get(0).getClientId());
  }

  @Test
  public void test_noRelations_oneTribalForChildExist_oneParentTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R08840.xml");

    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsBeforeUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_2));
          childTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_2));
          allTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_2, CHILD_CLIENT_2));
        });

    assertNotNull(allTribalsBeforeUpdate);
    assertEquals(2, allTribalsBeforeUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(1, childTribalsBeforeUpdate.size());
    assertNotNull(childTribalsBeforeUpdate.get(0));
    assertEquals(
        TRIBAL_THIRD_ID_PARENT_2,
        childTribalsBeforeUpdate.get(0).getFkFromTribalMembershipVerification());
    assertEquals(TRIBAL_THIRD_ID_PARENT_2, parentTribalsBeforeUpdate.get(0).getThirdId());
    assertEquals(TRIBAL_THIRD_ID_CHILD_2, childTribalsBeforeUpdate.get(0).getThirdId());

    createRelationship(awareDTO, PARENT_CLIENT_2, CHILD_CLIENT_2);

    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsAfterUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_2));
          childTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_2));
          allTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_2, CHILD_CLIENT_2));
        });

    assertNotNull(allTribalsAfterUpdate);
    assertEquals(2, allTribalsAfterUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(1, childTribalsAfterUpdate.size());
    assertNotNull(childTribalsBeforeUpdate.get(0));
    assertEquals(
        TRIBAL_THIRD_ID_PARENT_2,
        childTribalsAfterUpdate.get(0).getFkFromTribalMembershipVerification());
    assertEquals(TRIBAL_THIRD_ID_PARENT_2, parentTribalsAfterUpdate.get(0).getThirdId());
    assertEquals(TRIBAL_THIRD_ID_CHILD_2, childTribalsAfterUpdate.get(0).getThirdId());
  }

  @Test
  public void test_noRelations_oneParentTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R08840.xml");

    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsBeforeUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_3));
          childTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_3));
          allTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_3, CHILD_CLIENT_3));
        });

    assertNotNull(allTribalsBeforeUpdate);
    assertEquals(1, allTribalsBeforeUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(0, childTribalsBeforeUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate.get(0));
    assertEquals(TRIBAL_THIRD_ID_PARENT_3, parentTribalsBeforeUpdate.get(0).getThirdId());

    createRelationship(awareDTO, PARENT_CLIENT_3, CHILD_CLIENT_3);

    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsAfterUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_3));
          childTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_3));
          allTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(PARENT_CLIENT_3, CHILD_CLIENT_3));
        });

    assertNotNull(allTribalsAfterUpdate);
    assertEquals(2, allTribalsAfterUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(1, childTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterUpdate.get(0));
    assertEquals(
        TRIBAL_THIRD_ID_PARENT_3,
        childTribalsAfterUpdate.get(0).getFkFromTribalMembershipVerification());
    assertEquals(TRIBAL_THIRD_ID_PARENT_3, parentTribalsAfterUpdate.get(0).getThirdId());
  }

  @Test
  public void test_noRelation_oneParent_2Childs() throws Exception {
    cleanAllAndInsert("/dbunit/R08840.xml");

    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsBeforeUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsBeforeUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_4));
          childTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_4));
          allTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(
                  PARENT_CLIENT_4, CHILD_CLIENT_4, CHILD_CLIENT_4_1, CHILD_CLIENT_4_2));
        });

    assertNotNull(allTribalsBeforeUpdate);
    assertEquals(1, allTribalsBeforeUpdate.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());
    assertEquals(PARENT_CLIENT_4, parentTribalsBeforeUpdate.get(0).getClientId());
    assertNotNull(childTribalsBeforeUpdate);
    assertEquals(0, childTribalsBeforeUpdate.size());

    createRelationship(awareDTO, PARENT_CLIENT_4, CHILD_CLIENT_4);

    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> childTribalsAfterUpdate = new ArrayList<>();
    List<TribalMembershipVerification> allTribalsAfterUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_4));
          childTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(
                  CHILD_CLIENT_4, CHILD_CLIENT_4_1, CHILD_CLIENT_4_2));
          allTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientIds(
                  PARENT_CLIENT_4, CHILD_CLIENT_4, CHILD_CLIENT_4_1, CHILD_CLIENT_4_2));
        });

    assertNotNull(allTribalsBeforeUpdate);
    assertEquals(4, allTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterUpdate);
    assertEquals(3, childTribalsAfterUpdate.size());
    childTribalsAfterUpdate.forEach(
        e -> assertEquals(TRIBAL_THIRD_ID_PARENT_4, e.getFkFromTribalMembershipVerification()));
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
          type.setSystemId((short) 204);
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
}
