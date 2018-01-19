package gov.ca.cwds.data.legacy.cms.dao;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class ClientRelationshipDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ClientRelationshipDao dao = null;

  @Before
  public void before() {
    dao = new ClientRelationshipDao(sessionFactory);
  }

  @Test
  public void testFindRelationshipsByClientId() throws Exception {
    cleanAllAndInsert("/dbunit/ClientsRelationships.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {

          final LocalDate CURRENT_DATE = LocalDate.of(2018, 1, 18);

          List<ClientRelationship> rels0 = dao
              .findRelationshipsFromLeftSide("AaiU7IW0Rt", CURRENT_DATE);
          assertEquals(2, rels0.size());

          Map<String, ClientRelationship> relMap0 = rels0.stream().collect(Collectors
              .toMap(clientRelationship -> clientRelationship.getRightSide().getIdentifier(),
                  clientRelationship -> clientRelationship));

          assertTrue(relMap0.containsKey("AasRx3r0Ha"));
          ClientRelationship rel00 = relMap0.get("AasRx3r0Ha");
          assertEquals("AaiU7IW0Rt", rel00.getLeftSide().getIdentifier());
          assertEquals("AasRx3r0Ha", rel00.getRightSide().getIdentifier());
          assertFalse(rel00.getAbsentParentIndicator());
          assertEquals(SameHomeStatus.YES, rel00.getSameHomeStatus());
          ClientRelationshipType relType0 = rel00.getRelationshipType();
          assertEquals((short) 276, relType0.getPrimaryKey());
          assertEquals("Sister/Brother", relType0.getShortDescription());

          assertTrue(relMap0.containsKey("AazXkWY06s"));
          ClientRelationship rel01 = relMap0.get("AazXkWY06s");
          assertEquals("AaiU7IW0Rt", rel01.getLeftSide().getIdentifier());
          assertEquals("AazXkWY06s", rel01.getRightSide().getIdentifier());
          assertTrue(rel01.getAbsentParentIndicator());
          assertEquals(SameHomeStatus.NO, rel01.getSameHomeStatus());
          ClientRelationshipType relType01 = rel01.getRelationshipType();
          assertEquals((short) 190, relType01.getPrimaryKey());
          assertEquals("Daughter/Father (Birth)", relType01.getShortDescription());

          List<ClientRelationship> rels1 = dao
              .findRelationshipsFromRightSide("AasRx3r0Ha", CURRENT_DATE);
          assertEquals(1, rels1.size());
          ClientRelationship rel10 = rels1.get(0);
          assertEquals(rel00, rel10);//the same relation is taken from the right side

          //no relations when taken from left side by right side id
          List<ClientRelationship> rels2 = dao
              .findRelationshipsFromLeftSide("AasRx3r0Ha", CURRENT_DATE);
          assertEquals(0, rels2.size());
        });
  }

}
