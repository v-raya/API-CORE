package gov.ca.cwds.cms.data.access.service.impl.relationships;

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
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
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
public class R08861DBTest extends BaseDocToolInMemoryPersistenceTest {

  public static final String CLIENT_ID = "AJ4piWo0WL";
  public static final String PARENT_CLIENT_ID = "AJ4piWo0WP";
  public static final String RELATIONSHIP_ID = "Aaqj06L00h";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;

  @Before
  public void before() {
    businessValidationService = new BusinessValidationService(new DroolsService());
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    clientDao = new ClientDao(sessionFactory);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao);
  }

  @Test
  public void testNeedDeleteSubTribals() throws Exception {
    cleanAllAndInsert("/dbunit/R08861.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          try {
            ClientRelationship relationship = clientRelationshipCoreService.find(RELATIONSHIP_ID);
            ClientRelationshipType type = new ClientRelationshipType();
            type.setSystemId((short) 300);
            relationship.setType(type);

            List<TribalMembershipVerification> shouldBeDeleted =
                tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
                    CLIENT_ID, PARENT_CLIENT_ID);

            ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
            awareDTO.setEntity(relationship);

            clientRelationshipCoreService.update(awareDTO);

            List<TribalMembershipVerification> isDeleted =
                tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
                    CLIENT_ID, PARENT_CLIENT_ID);

            System.out.println(isDeleted);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });
  }

  private void initUserAccount(String countyCwsCode) {
    PerryAccount perryAccount = new PerryAccount();
    perryAccount.setCountyCwsCode(countyCwsCode);
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn("ox5");
  }
}
