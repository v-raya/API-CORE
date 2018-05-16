package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.drools.DroolsService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R08861DBTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CLIENT_ID = "AJ4piWo0WL";
  public static final String PARENT_CLIENT_ID = "AJ4piWo0WP";
  public static final String RELATIONSHIP_ID = "Aaqj06L01h";
  public static final String PARENT_CLIENT_ID_2 = "AJ4piWo0qq";
  public static final String RELATIONSHIP_ID_2 = "Aaqj06L0ww";

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
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    clientDao = new ClientDao(sessionFactory);
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

    cleanAllAndInsert("/dbunit/R08861.xml");
  }

  @Test
  public void testNeedDeleteSubTribals() throws Exception {

    final List<TribalMembershipVerification> shouldBeDeleted =
        getTrbalMembershipVerificationThatHaveSubTribals("8888888888", "9999999999");

    updateClientRelationship("8888888888");

    final List<TribalMembershipVerification> afterDelete =
        getTrbalMembershipVerificationThatHaveSubTribals("8888888888", "9999999999");

    assertNotNull(afterDelete);
    assertEquals(0, afterDelete.size());
  }

  private List<TribalMembershipVerification> getTrbalMembershipVerificationThatHaveSubTribals(
      String clientId, String parentId) {
    final List<TribalMembershipVerification> shouldBeDeleted = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          shouldBeDeleted.addAll(
              tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
                  clientId, parentId));
        });
    return shouldBeDeleted;
  }

  private void updateClientRelationship(String relationshipId) {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          try {
            ClientRelationship relationship = clientRelationshipCoreService.find(relationshipId);
            sessionFactory.getCurrentSession().detach(relationship);
            ClientRelationshipType type = new ClientRelationshipType();
            type.setSystemId((short) 248);
            relationship.setType(type);

            ClientRelationshipAwareDTO awareDTO = new ClientRelationshipAwareDTO();
            awareDTO.setEntity(relationship);

            clientRelationshipCoreService.update(awareDTO);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });
  }
}
