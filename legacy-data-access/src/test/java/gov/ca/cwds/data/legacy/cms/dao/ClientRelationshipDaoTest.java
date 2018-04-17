package gov.ca.cwds.data.legacy.cms.dao;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
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

          List<ClientRelationship> rghtRels = dao
              .findRelationshipsByPrimaryClientId("AaiU7Irere", CURRENT_DATE);
          assertEquals(3, rghtRels.size());

          Map<String, ClientRelationship> relMap = rghtRels.stream().collect(Collectors
              .toMap(clientRelationship -> clientRelationship.getSecondaryClient().getIdentifier(),
                  clientRelationship -> clientRelationship));

          assertTrue(relMap.containsKey("AasRx3rere"));
          assertTrue(relMap.containsKey("AazXkWrere"));
          assertTrue(relMap.containsKey("AfGN4urtrt"));

          //both dates are NULL
          ClientRelationship rel0 = relMap.get("AasRx3rere");
          assertEquals("AaiU7Irere", rel0.getPrimaryClient().getIdentifier());
          assertEquals("AasRx3rere", rel0.getSecondaryClient().getIdentifier());
          assertFalse(rel0.getAbsentParentIndicator());
          assertEquals(YesNoUnknown.YES, rel0.getSameHomeStatus());
          ClientRelationshipType relType0 = rel0.getType();
          assertEquals((short) 276, relType0.getPrimaryKey());
          assertEquals("Sister/Brother", relType0.getShortDescription());

          //both dates are not null
          ClientRelationship rel1 = relMap.get("AazXkWrere");
          assertEquals("AaiU7Irere", rel1.getPrimaryClient().getIdentifier());
          assertEquals("AazXkWrere", rel1.getSecondaryClient().getIdentifier());
          assertTrue(rel1.getAbsentParentIndicator());
          assertEquals(YesNoUnknown.NO, rel1.getSameHomeStatus());
          ClientRelationshipType relType1 = rel1.getType();
          assertEquals((short) 190, relType1.getPrimaryKey());
          assertEquals("Daughter/Father (Birth)", relType1.getShortDescription());

          //start date is null, end date greater than current date
          ClientRelationship rel2 = relMap.get("AfGN4urtrt");
          assertEquals("AaiU7Irere", rel2.getPrimaryClient().getIdentifier());
          assertEquals("AfGN4urtrt", rel2.getSecondaryClient().getIdentifier());
          assertFalse(rel2.getAbsentParentIndicator());
          assertEquals(YesNoUnknown.NO, rel2.getSameHomeStatus());
          ClientRelationshipType relType2 = rel2.getType();
          assertEquals((short) 301, relType2.getPrimaryKey());
          assertEquals("Ward/Guardian", relType2.getShortDescription());

          //the same relation is taken from the right side
          List<ClientRelationship> leftRels = dao
              .findRelationshipsBySecondaryClientId("AasRx3rere", CURRENT_DATE);
          assertEquals(1, leftRels.size());
          ClientRelationship relR0 = leftRels.get(0);
          assertEquals(rel0, relR0);

          //no relations when take them from left side by right side id
          List<ClientRelationship> rightRels2 = dao
              .findRelationshipsByPrimaryClientId("AasRx3rere", CURRENT_DATE);
          assertEquals(0, rightRels2.size());

          //relation is not taken into account if its type is inactive
          List<ClientRelationship> rightRels3 = dao
              .findRelationshipsByPrimaryClientId("AapJGArdrd", CURRENT_DATE);
          assertEquals(0, rightRels3.size());
        });
  }
}
