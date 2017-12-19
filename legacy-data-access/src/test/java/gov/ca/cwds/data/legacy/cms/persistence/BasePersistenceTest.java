package gov.ca.cwds.data.legacy.cms.persistence;

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
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

public abstract class BasePersistenceTest {

  protected SessionFactory sessionFactory = null;
  protected IDatabaseConnection dbUnitConnection = null;

  private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
  private final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private JdbcDatabaseTester dbUnitTester = null;

  private DefaultColumnFilter columnFilter;


  @Before
  public void superBefore() throws Exception {
    createDatabase();
    sessionFactory = createHibrtnateSessionFactory();
    dbUnitTester = new JdbcDatabaseTester(getDriverClassName(), getUrl(), getUser(), getPassword(),
        getSchema());
    dbUnitConnection = dbUnitTester.getConnection();
    columnFilter = new DefaultColumnFilter();
    columnFilter.excludeColumn("LST_UPD_TS");
  }

  @After
  public void superAfter() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
    if (dbUnitConnection != null) {
      dbUnitConnection.close();
    }
  }

  protected abstract String getUrl();

  protected abstract String getUser();

  protected abstract String getPassword();

  protected abstract String getSchema();

  protected abstract String getDriverClassName();

  protected abstract String getLiquibaseScriptPath();

  protected abstract String getHibernateConfigPath();

  protected DateTimeFormatter getDateFormatter(){
    return DATE_FORMATTER;
  }

  protected DateTimeFormatter getDateTimeFormatter(){
    return DATE_TIME_FORMATTER;
  }

  protected void createDatabase() throws Exception {
    runLiquibaseScript(getLiquibaseScriptPath());
  }

  protected SessionFactory createHibrtnateSessionFactory() {
    Configuration configuration = new Configuration();
    configuration.configure(getHibernateConfigPath());
    configuration.setProperty("hibernate.connection.url", getUrl());
    configuration.setProperty("hibernate.connection.username", getUser());
    configuration.setProperty("hibernate.connection.password", getPassword());
    configuration.setProperty("hibernate.default_schema", getSchema());
    return configuration.buildSessionFactory();
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
      session.close();
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
    return LocalDate.parse(dateStr, getDateFormatter());
  }

  protected LocalDateTime toDateTime(String dateStr) {
    return LocalDateTime.parse(dateStr, getDateTimeFormatter());
  }

  protected DefaultColumnFilter getColumnFilter() {
    return columnFilter;
  }

  protected void assertTableEquals(ITable expectedTable, ITable actualTable) throws Exception {
    ITable filteredTable = columnFilter.includedColumnsTable(actualTable,
        expectedTable.getTableMetaData().getColumns());
    Assertion.assertEquals(expectedTable, filteredTable);
  }

  private void runLiquibaseScript(String script) throws LiquibaseException {
    try {
      Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private Database getDatabase() throws SQLException, DatabaseException {
    Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPassword());
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
