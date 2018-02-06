package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParentalRightsTerminationDaoTest extends BaseCwsCmsInMemoryPersistenceTest {


  private ParentalRightsTerminationDao dao = null;

  @Before
  public void before() {
    dao = new ParentalRightsTerminationDao(sessionFactory);
  }

  @Test
  public void testFindParentalRightsTerminationsByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/ParentalRightsTerminations.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final String CHILD_CLIENT_ID = "AapJGAU04Z";

          List<ParentalRightsTermination> terminations =
              dao.findByChildClientId(CHILD_CLIENT_ID);
          assertEquals(2, terminations.size());

          ParentalRightsTermination term0 = terminations.get(0);
          assertEquals(term0.getChild().getIdentifier(), CHILD_CLIENT_ID);

          ParentalRightsTermination term1 = terminations.get(1);
          assertEquals(term1.getChild().getIdentifier(), CHILD_CLIENT_ID);

          List<ParentalRightsTermination> terminations2 = dao.findByChildClientId("unknownone");
          assertEquals(0, terminations2.size());
        });
  }
}
