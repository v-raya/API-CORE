package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

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
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** @author CWDS TPT-3 Team */
public class R08840DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String PARENT_CLIENT_1 = "RM1Mq5GABC";
  public static final String CHILD_CLIENT_1 = "HkKiO2wABC";
  public static final String PARENT_CLIENT_2 = "RM1Mq5GAB2";
  public static final String CHILD_CLIENT_2 = "HkKiO2wAB2";
  public static final String PARENT_CLIENT_3 = "3333333333";
  public static final String CHILD_CLIENT_3 = "3444444444";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;
  private PaternityDetailDao paternityDetailDao;
  private UpdateLifeCycle updateLifeCycle;
  private CreateLifeCycle createLifeCycle;
  private SearchClientRelationshipService searchClientRelationshipService;

  @Before
  public void before() throws Exception {
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    searchClientRelationshipService = new SearchClientRelationshipService(clientRelationshipDao);
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
            searchClientRelationshipService);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao,
            updateLifeCycle,
            searchClientRelationshipService,
            createLifeCycle);

    cleanAllAndInsert("/dbunit/R08840.xml");

  }

  @Test
  public void oneParentExist() throws Exception {
    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
    List<TribalMembershipVerification> tribalsBeforeCreateRelationship = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          parentTribalsBeforeUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_1));
          tribalsBeforeCreateRelationship.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_1));
        });

    assertNotNull(tribalsBeforeCreateRelationship);
    assertEquals(0, tribalsBeforeCreateRelationship.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());

    createRelationship(awareDTO, CHILD_CLIENT_1, PARENT_CLIENT_1);

    List<TribalMembershipVerification> childTribalsAfterCreate = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          childTribalsAfterCreate.addAll(
              tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_1));
          parentTribalsAfterUpdate.addAll(
              tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_1));
        });

    assertNotNull(parentTribalsAfterUpdate);
    assertEquals(1, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterCreate);
    assertEquals(1, childTribalsAfterCreate.size());
    assertEquals(
        parentTribalsAfterUpdate.get(0).getThirdId(),
        childTribalsAfterCreate.get(0).getFkFromTribalMembershipVerification());
  }

  @Test
  public void parentDoesnotExist() throws Exception {

    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
    List<TribalMembershipVerification> tribalsBeforeCreateRelationship = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        parentTribalsBeforeUpdate.addAll(
          tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_2));
        tribalsBeforeCreateRelationship.addAll(
          tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_2));
      });

    assertNotNull(tribalsBeforeCreateRelationship);
    assertEquals(0, tribalsBeforeCreateRelationship.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(0, parentTribalsBeforeUpdate.size());

    createRelationship(awareDTO, CHILD_CLIENT_2, PARENT_CLIENT_2);

    List<TribalMembershipVerification> childTribalsAfterCreate = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        childTribalsAfterCreate.addAll(
          tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_2));
        parentTribalsAfterUpdate.addAll(
          tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_2));
      });

    assertNotNull(parentTribalsAfterUpdate);
    assertEquals(0, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterCreate);
    assertEquals(0, childTribalsAfterCreate.size());
  }

  @Test
  public void parentExistAndChildExist() {
    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
    List<TribalMembershipVerification> tribalsBeforeCreateRelationship = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsBeforeUpdate = new ArrayList<>();
    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        parentTribalsBeforeUpdate.addAll(
          tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_3));
        tribalsBeforeCreateRelationship.addAll(
          tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_3));
      });

    assertNotNull(tribalsBeforeCreateRelationship);
    assertEquals(1, tribalsBeforeCreateRelationship.size());
    assertNotNull(parentTribalsBeforeUpdate);
    assertEquals(1, parentTribalsBeforeUpdate.size());

    createRelationship(awareDTO, CHILD_CLIENT_3, PARENT_CLIENT_3);

    List<TribalMembershipVerification> childTribalsAfterCreate = new ArrayList<>();
    List<TribalMembershipVerification> parentTribalsAfterUpdate = new ArrayList<>();
    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        childTribalsAfterCreate.addAll(
          tribalMembershipVerificationDao.findByClientId(CHILD_CLIENT_3));
        parentTribalsAfterUpdate.addAll(
          tribalMembershipVerificationDao.findByClientId(PARENT_CLIENT_3));
      });

    assertNotNull(parentTribalsAfterUpdate);
    assertEquals(1, parentTribalsAfterUpdate.size());
    assertNotNull(childTribalsAfterCreate);
    assertEquals(1, childTribalsAfterCreate.size());

    assertEquals(
      parentTribalsAfterUpdate.get(0).getThirdId(),
      childTribalsAfterCreate.get(0).getFkFromTribalMembershipVerification());
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
          type.setSystemId((short) 190);
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
