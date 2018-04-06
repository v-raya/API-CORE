package gov.ca.cwds.cms.data.access.service.impl.relationships;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientRelationshipCoreService;
import gov.ca.cwds.cms.data.access.service.impl.persistance.BaseDocToolInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PrincipalUtils.class)
@PowerMockIgnore({"javax.*", "org.xml.*", "org.w3c.*", "com.sun.*"})
public class R08840DBTest extends BaseDocToolInMemoryPersistenceTest {

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;

  @Before
  public void before() {
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao);
  }

  private void initUserAccount(String countyCwsCode) {
    PerryAccount perryAccount = new PerryAccount();
    perryAccount.setCountyCwsCode(countyCwsCode);
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn("ox5");
  }

  @Test
  public void testPrimaryTribalAdded() throws Exception {
    cleanAllAndInsert("/dbunit/R08840_1.xml");
    initUserAccount(null);

    final List<TribalMembershipVerification> primaryTribals = new ArrayList<>();
    final List<TribalMembershipVerification> secondaryTribals = new ArrayList<>();
    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          primaryTribals.addAll(tribalMembershipVerificationDao.findByClientId("RM1Mq5GABC"));
        });
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          secondaryTribals.addAll(tribalMembershipVerificationDao.findByClientId("HkKiO2wABC"));
        });

    assertEquals(primaryTribals.size(), 2);
    assertEquals(secondaryTribals.size(), 2);

    createRelationship(awareDTO, "RM1Mq5GABC", "HkKiO2wABC");

    List<TribalMembershipVerification> primaryTribalsAfterRUle = new ArrayList<>();
    List<TribalMembershipVerification> secondaryTribalsAfterRUle = new ArrayList<>();

    persistRelationship(
        awareDTO, primaryTribalsAfterRUle, secondaryTribalsAfterRUle, "RM1Mq5GABC", "HkKiO2wABC");

    assertEquals(primaryTribalsAfterRUle.size(), 3);
    assertEquals(secondaryTribals.size(), 2);
  }

  @Test
  public void testPrimaryTribalNotAdded() throws Exception {
    cleanAllAndInsert("/dbunit/R08840_2.xml");
    initUserAccount(null);

    final List<TribalMembershipVerification> primaryTribals = new ArrayList<>();
    final List<TribalMembershipVerification> secondaryTribals = new ArrayList<>();
    final ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          primaryTribals.addAll(tribalMembershipVerificationDao.findByClientId("RM1Mq5GAB1"));
        });
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          secondaryTribals.addAll(tribalMembershipVerificationDao.findByClientId("HkKiO2wAB1"));
        });

    assertEquals(primaryTribals.size(), 2);
    assertEquals(secondaryTribals.size(), 2);

    createRelationship(awareDTO, "RM1Mq5GAB1", "HkKiO2wAB1");

    List<TribalMembershipVerification> primaryTribalsAfterRule = new ArrayList<>();
    List<TribalMembershipVerification> secondaryTribalsAfterRule = new ArrayList<>();

    persistRelationship(
        awareDTO, primaryTribalsAfterRule, secondaryTribalsAfterRule, "RM1Mq5GAB1", "HkKiO2wAB1");

    assertEquals(primaryTribalsAfterRule.size(), 2);
    assertEquals(secondaryTribals.size(), 2);
  }

  private void persistRelationship(
      ClientRelationshipAwareDTO awareDTO,
      List<TribalMembershipVerification> primaryTribalsAfterRule,
      List<TribalMembershipVerification> secondaryTribalsAfterRule,
      String primaryClientId,
      String secondaryClientId) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          try {
            clientRelationshipCoreService.update(awareDTO);
            primaryTribalsAfterRule.addAll(
                tribalMembershipVerificationDao.findByClientId(primaryClientId));

            secondaryTribalsAfterRule.addAll(
                tribalMembershipVerificationDao.findByClientId(secondaryClientId));
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });
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
