package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ClientRelationshipDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ClientRelationshipDao dao = null;

  @Before
  public void before() {
    dao = new ClientRelationshipDao(sessionFactory);
  }

  @Test
  public void testFind() throws Exception {
    cleanAllAndInsert("/dbunit/ClientsRelationships.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {

          List<ClientRelationship> rels = dao.findCurrentRelationshipsFromLeftSide("AaiU7IW0Rt");
          assertEquals(1, rels.size());
          ClientRelationship rel0 = rels.get(0);
          assertEquals("AaiU7IW0Rt", rel0.getLeftSide().getIdentifier());
          assertEquals("AasRx3r0Ha", rel0.getRightSide().getIdentifier());
          assertFalse(rel0.getAbsentParentIndicator());
          assertEquals(SameHomeStatus.YES, rel0.getSameHomeStatus());

          ClientRelationshipType relType0 = rel0.getRelationshipType();
          assertEquals((short)276, relType0.getPrimaryKey());
          assertEquals("Sister/Brother", relType0.getShortDescription());

        });
  }

}
