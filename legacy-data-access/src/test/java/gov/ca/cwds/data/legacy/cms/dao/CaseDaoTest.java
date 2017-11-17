package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.Case;
import gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess;
import gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class CaseDaoTest {

  public static final String URL = "jdbc:h2:mem:cwscms;INIT=create schema if not exists CWSCMS\\;set schema CWSCMS;DB_CLOSE_DELAY=-1";
  public static final String USER = "sa";
  public static final String PASSWORD = "";
  public static final String SCHEMA = "CWSCMS";
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");

  protected static SessionFactory sessionFactory = null;
  protected static JdbcDatabaseTester dbUnitTester;
  protected static IDatabaseConnection dbUnitConnection;

  private CaseDao caseDao = null;

  @BeforeClass
  public static void beforeClass() throws Exception {
    setUpCms();
    sessionFactory = createHibrtnateSessionFactory();
    dbUnitTester = new JdbcDatabaseTester("org.h2.Driver", URL, USER, PASSWORD, SCHEMA);
    dbUnitConnection = dbUnitTester.getConnection();
  }

  @AfterClass
  public static void afterClass() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
    if (dbUnitConnection != null) {
      dbUnitConnection.close();
    }
  }

  @Before
  public void before() throws Exception {
    caseDao = new CaseDao(sessionFactory);
  }

  @Test
  public void testFind() throws Exception {
    cleanAllAndInsert("/dbunit/Case.xml");

    executeInTransaction(sessionFactory,
        (sessionFactory) -> {
          Case c = caseDao.find("AadfKnG07n");
          assertNotNull(c);
          assertEquals("AadfKnG07n", c.getIdentifier());//IDENTIFIER
          assertNull(c.getAlertText());//ALERT_TXT
          assertEquals("R8TQDh807n", c.getApprovalNumber());//APRVL_NO
          assertEquals("Approved", c.getApprovalStatusType().getShortDsc());//APV_STC
          assertEquals("Child Runaway", c.getCaseClosureReasonType().getShortDsc());//CLS_RSNC
          assertFalse(c.getCaseplanChildrenDetailIndVar());//CSPL_DET_B
          assertEquals("Very Long text",
              c.getClosureStatementText().getTextDescription());//CL_STM_TXT
          assertEquals("United States", c.getCountry().getShortDsc());//CNTRY_C
          assertEquals("57", c.getCountySpecificCode());//CNTY_SPFCD
          assertNull(c.getDrmsNotesDoc());//NOTES_DOC
          assertNull(c.getEmancipationDate());//EMANCPN_DT
          assertEquals(toDate("2002-11-08"), c.getEndDate());//END_DT
          //c.getChildClient()//FKCHLD_CLT
          assertNull(c.getReferralId());//FKREFERL_T
          //c.getStaffPerson()//FKSTFPERST
          assertEquals("Yolo", c.getCounty().getShortDsc());//GVR_ENTC
          assertFalse(c.getIcpcOutgngPlcmtStatusIndVar());//ICPCSTAT_B
          assertTrue(c.getIcpcOutgoingRequestIndVar());//ICPC_RQT_B
          assertEquals(LimitedAccess.NO_RESTRICTION, c.getLimitedAccess());//LMT_ACSSCD
          assertEquals(toDate("2014-06-17"), c.getLimitedAccessDate());//LMT_ACS_DT
          assertEquals(
              "County determined by primary assignment as of Release 7.1 implementation date of May 2014",
              c.getLimitedAccessDesc());//LMT_ACSDSC
          assertEquals("Lake", c.getLimitedAccessCounty().getShortDsc());//L_GVR_ENTC
          assertEquals("pts 20414", c.getCaseName());//CASE_NM
          assertNull(c.getNextTilpDueDate());//NXT_TILPDT
          assertNull(c.getProjectedEndDate());//PRJ_END_DT
          assertEquals(ResponsibleAgency.COUNTY_WELFARE_DEPARTMENT,
              c.getResponsibleAgency());//RSP_AGY_CD
          assertFalse(c.getSpecialProjectCaseIndVar());//SPRJ_CST_B
          assertEquals(toDate("2002-11-08"), c.getStartDate());//START_DT
          assertEquals("California", c.getState().getShortDsc());//STATE_C
          assertEquals("Emergency Response",
              c.getActiveServiceComponentType().getShortDsc());//SRV_CMPC
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

    executeInTransaction(sessionFactory,
        (sessionFactory) -> {
          Case c = new Case();
          //TODO: populate Case
          caseDao.create(c);
        });

    IDataSet expectedDataSet = readXmlDataSet("/dbunit/Case.xml");
    ITable expectedTable = expectedDataSet.getTable("CASE_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet();
    ITable actualTable = actualDataSet.getTable("CASE_T");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  private static void setUpCms() throws Exception {
    runLiquibaseScript("liquibase/cwscms_database_master.xml");
  }

  private static void runLiquibaseScript(String script) throws LiquibaseException {
    try {
      Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private static Database getDatabase() throws SQLException, DatabaseException {
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    return DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(connection));
  }

  private static SessionFactory createHibrtnateSessionFactory() {
    Configuration configuration = new Configuration();
    configuration.configure();
    configuration.setProperty("hibernate.connection.url", URL);
    configuration.setProperty("hibernate.connection.username", USER);
    configuration.setProperty("hibernate.connection.password", PASSWORD);
    configuration.setProperty("hibernate.default_schema", SCHEMA);
    return configuration.buildSessionFactory();
  }

  private void executeInTransaction(SessionFactory sessionFactory,
      Consumer<SessionFactory> consumer) {

    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();

    try {
      consumer.accept(sessionFactory);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      throw e;
    } finally {
      session.close();
    }
  }

  private IDataSet readXmlDataSet(String dataSetFilePath) throws Exception {
    DataFileLoader loader = new FlatXmlDataFileLoader();
    IDataSet dataSet = loader.load(dataSetFilePath);
    ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
    replacementDataSet.addReplacementObject("[NULL]", null);
    return replacementDataSet;
  }

  private void cleanAllAndInsert(IDataSet dataSet) throws Exception {
    DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
  }

  //Cleans and populates tables mentioned in XML dataset
  private void cleanAllAndInsert(String dataSetFilePath) throws Exception {
    IDataSet dataSet = readXmlDataSet(dataSetFilePath);
    cleanAllAndInsert(dataSet);
  }

  private void cleanAll(IDataSet dataSet) throws Exception {
    DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, dataSet);
  }

  //Cleans tables mentioned in XML dataset
  private void cleanAll(String dataSetFilePath) throws Exception {
    IDataSet dataSet = readXmlDataSet(dataSetFilePath);
    cleanAll(dataSet);
  }

  private static LocalDate toDate(String dateStr) {
    return LocalDate.parse(dateStr, DATE_FORMATTER);
  }

  private static LocalDateTime toDateTime(String dateStr) {
    return LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
  }
}
