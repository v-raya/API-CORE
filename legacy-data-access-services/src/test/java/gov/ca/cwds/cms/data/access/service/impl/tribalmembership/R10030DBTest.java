package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianTribeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDto;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
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
  private static final String PARENT_CLIENT_ID = "K2DISKkabf";
  private static final String ORGANIZATION = "VyTDQ2lg25";
  private static final String PARENT_CLIENT_ID_2 = "3Lb0X5Cabf";
  private static final String CHILD_CLIENT_ID = "7u1zM98abf";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientCoreService clientCoreService;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;
  private BusinessValidationService businessValidationService;
  private CreateLifeCycle createLifeCycle;

  @Before
  public void before() throws Exception {
    initUserAccount(USER_ID);
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientCoreService = new ClientCoreService(clientDao);
    createLifeCycle =
        new CreateLifeCycle(
            tribalMembershipVerificationDao, clientCoreService, businessValidationService);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(
            tribalMembershipVerificationDao, createLifeCycle);

    cleanAllAndInsert("/dbunit/R10030.xml");
  }

  @Test
  public void testNoChildTribalExist() throws Exception {
    createTribal(CHILD_CLIENT_ID, PARENT_CLIENT_ID, ORGANIZATION, (short) 2100);
    testTribals(1, 1, CHILD_CLIENT_ID, PARENT_CLIENT_ID, ORGANIZATION);
  }

  @Test
  public void testOneChildTribalExist() throws Exception {
    createTribal(CHILD_CLIENT_ID, PARENT_CLIENT_ID_2, ORGANIZATION, (short) 2100);
    testTribals(2, 1, CHILD_CLIENT_ID, PARENT_CLIENT_ID, ORGANIZATION);
  }

  private void testTribals(
      int childTribalsCount,
      int parentTribalsCount,
      String primaryClientId,
      String secondaryClientId,
      String organization) {
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
          childTribalsBeforeUpdate.forEach(
              e -> {
                assertEquals(primaryClientId, e.getClientId());
                assertEquals(organization, e.getFkSentToTribalOrganization());
                assertNotNull(e.getFkFromTribalMembershipVerification());
              });

          List<TribalMembershipVerification> parentTribalsBeforeUpdate =
              tribalMembershipVerificationDao.findByClientIds(secondaryClientId);
          assertNotNull(parentTribalsBeforeUpdate);
          assertEquals(parentTribalsCount, parentTribalsBeforeUpdate.size());
          parentTribalsBeforeUpdate.forEach(
              e -> {
                assertEquals(secondaryClientId, e.getClientId());
                assertEquals(organization, e.getFkSentToTribalOrganization());
                assertEquals(null, e.getFkFromTribalMembershipVerification());
              });
        });
  }

  private void createTribal(String child, String parent, String organization, Short indianType) {
    final TribalMembershipVerificationAwareDto awareDTO =
        new TribalMembershipVerificationAwareDto(parent, child);

    enrichNewRelationship(awareDTO, organization, indianType, parent);
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          try {
            tribalMembershipVerificationCoreService.create(awareDTO);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });
  }

  private void enrichNewRelationship(
      TribalMembershipVerificationAwareDto awareDTO,
      String organization,
      Short indianType,
      String client) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory1) -> {
          TribalMembershipVerification tribalMembershipVerification =
              new TribalMembershipVerification();
          tribalMembershipVerification.setDoc318id("123123");
          tribalMembershipVerification.setEnrollmentNumber("123123");
          tribalMembershipVerification.setFkSentToTribalOrganization(organization);
          IndianTribeType tribeType = new IndianTribeType();
          tribeType.setSystemId(indianType);
          tribalMembershipVerification.setIndianTribeType(tribeType);
          tribalMembershipVerification.setStatusDate(LocalDate.now());
          tribalMembershipVerification.setThirdId("0001110005");
          tribalMembershipVerification.setClientId(client);
          tribalMembershipVerification.setLastUpdateTime(LocalDateTime.now());
          tribalMembershipVerification.setLastUpdateId("0X5");

          awareDTO.setEntity(tribalMembershipVerification);
        });
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
