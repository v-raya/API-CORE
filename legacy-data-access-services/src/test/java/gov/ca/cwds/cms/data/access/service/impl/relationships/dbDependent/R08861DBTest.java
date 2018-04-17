package gov.ca.cwds.cms.data.access.service.impl.relationships.dbDependent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientRelationshipCoreService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
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

  @Before
  public void before() throws Exception {
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

    cleanAllAndInsert("/dbunit/R08861.xml");
  }

  @Test
  public void testNeedDeleteSubTribals() throws Exception {

    final List<TribalMembershipVerification> shouldBeDeleted =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID);

    updateClientRelationship(RELATIONSHIP_ID);

    final List<TribalMembershipVerification> afterDelete =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID);

    assertNotEquals(shouldBeDeleted, afterDelete);
    assertNotNull(afterDelete);
    assertEquals(1, afterDelete.size());
  }

  @Test
  public void testDoNotNeedDeleteSubTribals() throws Exception {

    final List<TribalMembershipVerification> shouldBeDeleted =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID_2);

    updateClientRelationship(RELATIONSHIP_ID_2);

    final List<TribalMembershipVerification> afterDelete =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID_2);

    assertNotNull(afterDelete);
    assertNotNull(shouldBeDeleted);
    assertTrue(CollectionUtils.isEmpty(shouldBeDeleted));
    assertTrue(CollectionUtils.isEmpty(afterDelete));

    final List<TribalMembershipVerification> shouldBeDeleted2 =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID_2);

    updateClientRelationship(RELATIONSHIP_ID_2);

    final List<TribalMembershipVerification> afterDelete2 =
        getTrbalMembershipVerificationThatHaveSubTribals(CLIENT_ID, PARENT_CLIENT_ID_2);

    assertNotNull(afterDelete);
    assertNotNull(shouldBeDeleted);
    assertTrue(CollectionUtils.isEmpty(shouldBeDeleted));
    assertTrue(CollectionUtils.isEmpty(afterDelete));
  }

  private List<TribalMembershipVerification> getTrbalMembershipVerificationThatHaveSubTribals(String clientId, String parentId) {
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
            ClientRelationshipType type = new ClientRelationshipType();
            type.setSystemId((short) 291);
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
