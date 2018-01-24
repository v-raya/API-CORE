package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class SafetyAlertDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SafetyAlertDao testSubject = null;

  @Before
  public void before() throws Exception {
    testSubject = new SafetyAlertDao(sessionFactory);
    cleanAllAndInsert("/dbunit/SafetyAlert.xml");
  }

  @Test
  public void findByClientId_success_whenMultipleRecords() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<SafetyAlert> actualResults = testSubject.findByClientId("R06FKZ20X5");
      assertThat(actualResults.size(), is(2));
    });
  }

  @Test
  @Ignore // TODO(dd): Fix it. Works as single test, doesn't work in the full suite
  public void findByClientId_success_whenSingleRecord() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      // when
      final Collection<SafetyAlert> actualResults = testSubject.findByClientId("9HNHgeH00E");

      // then
      assertThat(actualResults.size(), is(1));
      final SafetyAlert safetyAlert = actualResults.iterator().next();
      assertThat(safetyAlert.getFkClientId(), is("9HNHgeH00E"));
      assertThat(safetyAlert.getActivationGovernmentEntityType().getPrimaryKey(), is((short) 1126));
      assertThat(safetyAlert.getActivationDate(), is(LocalDate.of(2013, 1, 28)));
      assertThat(safetyAlert.getActivationExplanationText().getIdentifier(), is("7xvG9WT00E"));
      assertThat(safetyAlert.getActivationReasonType().getSystemId(), is((short) 6402));
      assertThat(safetyAlert.getDeactivationGovernmentEntityType(), is(nullValue()));
      assertThat(safetyAlert.getDeactivationDate(), is(nullValue()));
      assertThat(safetyAlert.getDeactivationExplanationText(), is(nullValue()));
    });
  }

  @Test
  public void findByClientId_empty_whenNoRecordsFound() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<SafetyAlert> actualResults = testSubject.findByClientId("nonExistent");
      assertThat(actualResults.size(), is(0));
    });
  }

}
