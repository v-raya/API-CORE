package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlertPK;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/** @author CWDS TPT-3 Team */
public class SafetyAlertDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SafetyAlertDao testSubject = null;

  @Before
  public void before() throws Exception {
    testSubject = new SafetyAlertDao(sessionFactory);
    cleanAllAndInsert("/dbunit/SafetyAlert.xml");
  }

  @Test
  public void findByClientId_success_whenMultipleRecords() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<SafetyAlert> actualResults = testSubject.findByClientId("R06FKZ20X5");
          assertThat(actualResults.size(), is(2));
        });
  }

  @Test
  public void findByClientId_success_whenSingleRecord() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          // when
          final Collection<SafetyAlert> actualResults = testSubject.findByClientId("9HNHgeH00E");

          // then
          assertThat(actualResults.size(), is(1));
          final SafetyAlert safetyAlert = actualResults.iterator().next();
          assertThat(safetyAlert.getFkClientId(), is("9HNHgeH00E"));
          assertThat(
              safetyAlert.getActivationGovernmentEntityType().getPrimaryKey(), is((short) 1126));
          assertThat(safetyAlert.getActivationDate(), is(LocalDate.of(2013, 1, 28)));
          assertThat(safetyAlert.getActivationExplanationText().getIdentifier(), is("7xvG9WT00E"));
          assertThat(safetyAlert.getActivationReasonType().getSystemId(), is((short) 6402));
          assertThat(safetyAlert.getDeactivationGovernmentEntityType(), is(nullValue()));
          assertThat(safetyAlert.getDeactivationDate(), is(nullValue()));
          assertThat(safetyAlert.getDeactivationExplanationText(), is(nullValue()));
        });
  }

  @Test
  public void shouldRemoveOne() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Collection<SafetyAlert> safetyAlerts = testSubject.findByClientId("9HNHgeH00E");
          assertThat(safetyAlerts.size(), is(1));
          SafetyAlert toDelete = safetyAlerts.iterator().next();
          testSubject.delete(toDelete.getPrimaryKey());
          safetyAlerts = testSubject.findByClientId("9HNHgeH00E");
          assertThat(safetyAlerts.size(), is(0));
        });
  }

  @Test
  public void shouldFindByPrimaryKey() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          SafetyAlert result = testSubject.find(new SafetyAlertPK("9HNHgeH00E", "7MPwBxx00E"));
          assertNotNull(result);
        });
  }

  @Test
  public void findByClientId_empty_whenNoRecordsFound() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<SafetyAlert> actualResults = testSubject.findByClientId("nonExistent");
          assertThat(actualResults.size(), is(0));
        });
  }
}
