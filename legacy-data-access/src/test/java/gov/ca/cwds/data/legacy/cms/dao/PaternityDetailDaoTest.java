package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.ClientPaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.CollateralInividualPaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class PaternityDetailDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private PaternityDetailDao dao = null;

  @Before
  public void before() {
    dao = new PaternityDetailDao(sessionFactory);
  }

  @Test
  public void testFindParentalRightsTerminationsByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/PaternityDetails.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final String CHILD_CLIENT_ID = "AapJGAU04Z";

          List<PaternityDetail> paternityDetails = dao.findByChildClientId(CHILD_CLIENT_ID);

          assertEquals(2, paternityDetails.size());

          Map<String, PaternityDetail> resultMap =
              paternityDetails.stream()
                  .collect(Collectors.toMap(PaternityDetail::getId,
                      paternityDetail -> paternityDetail));

          assertEquals(2, resultMap.size());

          PaternityDetail pt0 = resultMap.get("KncJHIT0If");
          assertEquals(pt0.getChildClient().getIdentifier(), CHILD_CLIENT_ID);
          assertTrue(pt0 instanceof ClientPaternityDetail);

          PaternityDetail pt1 = resultMap.get("Tq5uZMo75C");
          assertEquals(pt1.getChildClient().getIdentifier(), CHILD_CLIENT_ID);
          assertTrue(pt1 instanceof CollateralInividualPaternityDetail);

          List<PaternityDetail> paternityDetails2 = dao
              .findByChildClientId("unknownone");
          assertEquals(0, paternityDetails2.size());
        });
  }
}
