package gov.ca.cwds.cms.data.access.service.impl.tribalmembershipverification.dbDependent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.TribalMembershipVerificationCoreService;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianTribeType;

import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R01128Test extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String CLIENT_ID = "00TMBVR000";
  private static final Short TRYBE_TYPE_ID = 2106;
  private static final String TRIBE_ORGANIZATION_ID = "0901012033";
  private static final String TRIBAL_THIRD_ID = "0982340011";
  private static final String STAFF_PERSON_ID = "00x";

  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientDao clientDao;
  private BusinessValidationService businessValidationService;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;

  @Before
  public void init() throws Exception {
    businessValidationService = new BusinessValidationService(new DroolsService());
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(
            tribalMembershipVerificationDao, clientDao, businessValidationService);

    cleanAllAndInsert("/dbunit/R01128.xml");
  }

  @Test
  public void testCreateTribalMembershipVerificationClientExist() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Client clientBeforeUpdate = clientDao.find(CLIENT_ID);
          assertNotNull(clientBeforeUpdate);
          assertEquals(Boolean.FALSE, clientBeforeUpdate.getTribalMembershipVerifcationIndicator());
          TribalMembershipVerificationAwareDTO awareDTO = new TribalMembershipVerificationAwareDTO();
          awareDTO.setEntity(tribalMembershipVerificationSupplier.get());
          try {
            tribalMembershipVerificationCoreService.create(awareDTO);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Client clientBeforeUpdate = clientDao.find(CLIENT_ID);
          assertNotNull(clientBeforeUpdate);
          assertEquals(Boolean.TRUE, clientBeforeUpdate.getTribalMembershipVerifcationIndicator());
        });
  }

  private Supplier<TribalMembershipVerification> tribalMembershipVerificationSupplier =
      () -> {
        TribalMembershipVerification verification = new TribalMembershipVerification();
        verification.setFkSentTotribalOrganization(TRIBE_ORGANIZATION_ID);

        IndianTribeType tribeType = new IndianTribeType();
        tribeType.setSystemId(TRYBE_TYPE_ID);

        verification.setIndianTribeType(tribeType);
        verification.setStatusDate(LocalDate.now());
        verification.setThirdId(TRIBAL_THIRD_ID);
        verification.setClientId(CLIENT_ID);
        verification.setLastUpdateTime(LocalDateTime.now());
        verification.setLastUpdateId(STAFF_PERSON_ID);

        return verification;
      };
}
