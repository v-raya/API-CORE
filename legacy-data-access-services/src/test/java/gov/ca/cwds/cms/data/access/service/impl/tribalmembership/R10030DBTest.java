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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10030DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String USER_ID = "0X5";
  private static final String CHILD_CLIENT_ID = "1111111111";
  private static final String PARENT_CLIENT_ID = "1222222222";
  private static final String ORGANIZATION = "1111111111";
  private static final Short TYPE = 2100;

  private static final String CHILD_CLIENT_ID_2 = "2222222222";
  private static final String ORGANIZATION_2 = "2222222222";
  private static final String PARENT_CLIENT_ID_2 = "2333333333";
  private static final Short INDIAN_STATUS_TYPE_2 = 1211;

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientCoreService clientCoreService;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;
  private BusinessValidationService businessValidationService;
  private CreateLifeCycle createLifeCycle;
  private UpdateLifeCycle updateLifeCycle;

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
    updateLifeCycle =
        new UpdateLifeCycle(
            tribalMembershipVerificationDao, clientCoreService, businessValidationService);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(
            tribalMembershipVerificationDao, createLifeCycle, updateLifeCycle);
  }

  @Test
  public void testNoChildTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R10030.xml");
    createTribal(CHILD_CLIENT_ID, PARENT_CLIENT_ID, ORGANIZATION, TYPE);
    List<TribalMembershipVerification> childTribals;
    List<TribalMembershipVerification> parentTribals;
    childTribals = getTribals(CHILD_CLIENT_ID);
    parentTribals = getTribals(PARENT_CLIENT_ID);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(1, childTribals.size());
    assertEquals(1, parentTribals.size());
    assertNotNull(childTribals.get(0).getFkFromTribalMembershipVerification());
    assertEquals(null, parentTribals.get(0).getFkFromTribalMembershipVerification());
    assertEquals(
        parentTribals.get(0).getThirdId(),
        childTribals.get(0).getFkFromTribalMembershipVerification());
    assertEquals(CHILD_CLIENT_ID, childTribals.get(0).getClientId());
    assertEquals(PARENT_CLIENT_ID, parentTribals.get(0).getClientId());
    assertEquals(ORGANIZATION, parentTribals.get(0).getFkSentToTribalOrganization());
    assertEquals(ORGANIZATION, childTribals.get(0).getFkSentToTribalOrganization());
    assertEquals(TYPE, childTribals.get(0).getIndianTribeType().getSystemId());
    assertEquals(TYPE, parentTribals.get(0).getIndianTribeType().getSystemId());
  }

  @Test
  public void testOneChildTribalExist() throws Exception {
    cleanAllAndInsert("/dbunit/R10030.xml");

    createTribal(CHILD_CLIENT_ID_2, PARENT_CLIENT_ID_2, ORGANIZATION_2, TYPE);
    List<TribalMembershipVerification> childTribals;
    List<TribalMembershipVerification> parentTribals;
    childTribals = getTribals(CHILD_CLIENT_ID_2);
    parentTribals = getTribals(PARENT_CLIENT_ID_2);
    LocalDate localDate = LocalDate.of(2001, 7, 30);

    assertNotNull(childTribals);
    assertNotNull(parentTribals);
    assertEquals(2, childTribals.size());
    assertEquals(1, parentTribals.size());
    assertEquals(PARENT_CLIENT_ID_2, parentTribals.get(0).getClientId());
    assertEquals(TYPE, parentTribals.get(0).getIndianTribeType().getSystemId());
    assertEquals(ORGANIZATION_2, parentTribals.get(0).getFkSentToTribalOrganization());

    TribalMembershipVerification newChildRow =
        childTribals
            .stream()
            .filter(
                e ->
                    e.getFkFromTribalMembershipVerification()
                        .equals(parentTribals.get(0).getThirdId()))
            .findFirst()
            .orElse(null);

    assertNotNull(newChildRow);
    assertEquals(
        parentTribals.get(0).getThirdId(), newChildRow.getFkFromTribalMembershipVerification());
    assertEquals(CHILD_CLIENT_ID_2, newChildRow.getClientId());
    assertEquals(ORGANIZATION_2, newChildRow.getFkSentToTribalOrganization());
    assertEquals(TYPE, newChildRow.getIndianTribeType().getSystemId());
    assertEquals(localDate, newChildRow.getStatusDate());
    assertNotNull(newChildRow.getIndianEnrollmentStatus());
    assertEquals(INDIAN_STATUS_TYPE_2, newChildRow.getIndianEnrollmentStatus().getSystemId());
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
