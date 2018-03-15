package gov.ca.cwds.data.legacy.cms.persistence;

//import static org.powermock.api.mockito.PowerMockito.when;

//import gov.ca.cwds.security.utils.PrincipalUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({PrincipalUtils.class})
public abstract class BaseCwsCmsInMemoryPersistenceTest  {

  private static final String LIQUIBASE_SCRIPT_PATH = "liquibase/cwscms_database_base_with_lookups.xml";
  private static final String SCHEMA = "CWSCMS";
  private static final String URL =
      "jdbc:h2:mem:cwscms;INIT=create schema if not exists " + SCHEMA +
          "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";
  private static final String USER = "";
  private static final String PASSWORD = "";
  private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
  private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
  private final static DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  protected static SessionFactory sessionFactory = null;
  protected static IDatabaseConnection dbUnitConnection = null;
  private static JdbcDatabaseTester dbUnitTester = null;
  private static DefaultColumnFilter columnFilter;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest.class);


  @BeforeClass
  public static void superBeforeClass() throws Exception {
    createDatabase();
    sessionFactory = createHibrtnateSessionFactory();
    dbUnitTester = new JdbcDatabaseTester(DRIVER_CLASS_NAME, URL, USER, PASSWORD, SCHEMA);
    dbUnitConnection = dbUnitTester.getConnection();

    columnFilter = new DefaultColumnFilter();
    columnFilter.excludeColumn("LST_UPD_TS");
  }

  @Before
  public void superBefore() throws Exception {
//    PowerMockito.mockStatic(PrincipalUtils.class);
//    when(PrincipalUtils.getStaffPersonId()).thenReturn("0X5");
  }

  @After
  public void superAfter() throws Exception {
  }

  @AfterClass
  public static void superAfterClass() throws Exception {
    if (dbUnitConnection != null) {
      dbUnitConnection.close();
    }
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  protected static void createDatabase() throws Exception {
    Class.forName(DRIVER_CLASS_NAME);
    runLiquibaseScript(LIQUIBASE_SCRIPT_PATH);
  }

  protected static SessionFactory createHibrtnateSessionFactory() {
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("CWSCMS-TEST");
    SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    return sessionFactory;
  }

  protected void executeInTransaction(SessionFactory sessionFactory,
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
      if (session.isOpen()) {
        session.close();
      }
    }
  }

  protected IDataSet readXmlDataSet(String dataSetFilePath) throws Exception {
    DataFileLoader loader = new FlatXmlDataFileLoader();
    IDataSet dataSet = loader.load(dataSetFilePath);
    ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
    replacementDataSet.addReplacementObject("[NULL]", null);
    return replacementDataSet;
  }

  /**
   * Cleans and populates tables mentioned in XML dataset
   */
  protected void cleanAllAndInsert(String dataSetFilePath) throws Exception {
    IDataSet dataSet = readXmlDataSet(dataSetFilePath);
    cleanAllAndInsert(dataSet);
  }

  /**
   * //Cleans all tables mentioned in XML dataset
   */
  protected void cleanAll(String dataSetFilePath) throws Exception {
    IDataSet dataSet = readXmlDataSet(dataSetFilePath);
    cleanAll(dataSet);
  }

  protected LocalDate toDate(String dateStr) {
    return LocalDate.parse(dateStr, DATE_FORMATTER);
  }

  protected LocalDateTime toDateTime(String dateStr) {
    return LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
  }

  protected DefaultColumnFilter getColumnFilter() {
    return columnFilter;
  }

  protected void assertTableEquals(ITable expectedTable, ITable actualTable, String... ignoreCols)
      throws Exception {
    ITable filteredTable = columnFilter.includedColumnsTable(actualTable,
        expectedTable.getTableMetaData().getColumns());
    Assertion.assertEqualsIgnoreCols(expectedTable, filteredTable, ignoreCols);
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

  private void cleanAllAndInsert(IDataSet dataSet) throws Exception {
    DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
  }

  private void cleanAll(IDataSet dataSet) throws Exception {
    DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, dataSet);
  }
}
