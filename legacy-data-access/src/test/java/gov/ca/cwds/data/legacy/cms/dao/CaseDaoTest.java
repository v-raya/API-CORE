package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.Case;
import gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess;
import gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientByStaff;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import java.util.Collection;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("squid:S1607")
public class CaseDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private CaseDao caseDao = null;

  @Before
  public void before() throws Exception {
    caseDao = new CaseDao(sessionFactory);
  }

  @Test
  public void testFind() throws Exception {
    cleanAllAndInsert("/dbunit/Case.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Case c = caseDao.find("AadfKnG07n");
          assertNotNull(c);
          assertEquals("AadfKnG07n", c.getIdentifier());//IDENTIFIER
          assertNull(c.getAlertText());//ALERT_TXT
          assertEquals("R8TQDh807n", c.getApprovalNumber());//APRVL_NO
          assertEquals("Approved", c.getApprovalStatusType().getShortDescription());//APV_STC
          assertEquals(310, c.getCaseClosureReasonTypeCode());//CLS_RSNC, Child Runaway
          assertFalse(c.getCaseplanChildrenDetailIndVar());//CSPL_DET_B
          assertEquals("Very Long text",
              c.getClosureStatementText().getTextDescription());//CL_STM_TXT
          assertEquals("United States", c.getCountry().getShortDescription());//CNTRY_C
          assertEquals("57", c.getCountySpecificCode());//CNTY_SPFCD
          assertNull(c.getDrmsNotesDoc());//NOTES_DOC
          assertNull(c.getEmancipationDate());//EMANCPN_DT
          assertEquals(toDate("2002-11-08"), c.getEndDate());//END_DT
          assertNull(c.getReferralId());//FKREFERL_T
          assertEquals("Yolo", c.getCounty().getShortDescription());//GVR_ENTC
          assertFalse(c.getIcpcOutgngPlcmtStatusIndVar());//ICPCSTAT_B
          assertTrue(c.getIcpcOutgoingRequestIndVar());//ICPC_RQT_B
          assertEquals(LimitedAccess.NO_RESTRICTION, c.getLimitedAccess());//LMT_ACSSCD
          assertEquals(toDate("2014-06-17"), c.getLimitedAccessDate());//LMT_ACS_DT
          assertEquals(
              "County determined by primary assignment as of Release 7.1 implementation date of May 2014",
              c.getLimitedAccessDesc());//LMT_ACSDSC
          assertEquals("Lake", c.getLimitedAccessCounty().getShortDescription());//L_GVR_ENTC
          assertEquals("pts 20414", c.getCaseName());//CASE_NM
          assertNull(c.getNextTilpDueDate());//NXT_TILPDT
          assertNull(c.getProjectedEndDate());//PRJ_END_DT
          assertEquals(ResponsibleAgency.COUNTY_WELFARE_DEPARTMENT,
              c.getResponsibleAgency());//RSP_AGY_CD
          assertFalse(c.getSpecialProjectCaseIndVar());//SPRJ_CST_B
          assertEquals(toDate("2002-11-08"), c.getStartDate());//START_DT
          assertEquals(1828, c.getStateCode());//STATE_C, California
          assertEquals("Emergency Response",
              c.getActiveServiceComponentType().getShortDescription());//SRV_CMPC
          assertEquals(toDate("2002-11-08"), c.getActiveSvcComponentStartDate());//SRV_CMPDT
          assertFalse(c.getTickleIndVar());//TICKLE_T_B
          assertEquals("07n", c.getLastUpdateId());//LST_UPD_ID
          assertEquals(toDateTime("2002-11-08 09:02:20"), c.getLastUpdateTime());//LST_UPD_TS
        });
  }

  @Ignore
  @Test
  public void testCreate() throws Exception {

    cleanAll("/dbunit/Case.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Case c = new Case();
          // TODO: populate Case
          caseDao.create(c);
        });

    IDataSet expectedDataSet = readXmlDataSet("/dbunit/Case.xml");
    ITable expectedTable = expectedDataSet.getTable("CASE_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[] {"CASE_T"});
    ITable actualTable = actualDataSet.getTable("CASE_T");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void shouldFindActiveCaseByClient() throws Exception {
    cleanAllAndInsert("/dbunit/CaseActiveCanceled.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          List<Case> cases = caseDao.findActiveByClient("AapJGAU04Z");
          assertEquals(1, cases.size());
        });
  }

  @Test
  public void shouldFindClosedCasesByClient() throws Exception {
    cleanAllAndInsert("/dbunit/CaseActiveCanceled.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          List<Case> cases = caseDao.findClosedByClient("AaiU7IW999");
          assertEquals(2, cases.size());
        });
  }

  @Test
  public void shouldFindClientsByStaffId() throws Exception {
    cleanAllAndInsert("/dbunit/ClientsByStaffPerson.xml");

    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        Collection<ClientByStaff> clients = caseDao.findClientsByStaffIdAndActiveDate("0Ki", LocalDate.now());
        assertEquals(109, clients.size());
        clients.stream().filter(client -> "Ju2u3Pk0Ki".equals(client.getIdentifier()))
          .forEach(clientByStaff -> {
            assertEquals("Test", clientByStaff.getFirstName());
            assertEquals("", clientByStaff.getMiddleName());
            assertEquals("Gendricke", clientByStaff.getLastName());
            assertEquals("", clientByStaff.getNameSuffix());
            assertEquals(LocalDate.parse("1999-08-09"), clientByStaff.getBirthDate());
            assertEquals("N", clientByStaff.getSensitivityType());
            assertEquals(LocalDate.parse("2005-02-09"), clientByStaff.getCasePlanReviewDueDate());
        });
      });
  }
}
