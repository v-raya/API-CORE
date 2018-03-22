package gov.ca.cwds.data.legacy.cms.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
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
import org.junit.ClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCwsCmsInMemoryPersistenceTest  {

  private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
  private final static DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  static protected SessionFactory sessionFactory = null;
  static protected IDatabaseConnection dbUnitConnection = null;
  static protected JdbcDatabaseTester dbUnitTester = null;
  static protected DefaultColumnFilter columnFilter;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest.class);

  @ClassRule
  public static final InMemoryTestResources inMemoryTestResources = InMemoryTestResources.getInstance();

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

  protected IDataSet readXmlDataSet(String dataSetFilePath) {
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
   * Cleans all tables mentioned in XML dataset
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

  private void cleanAllAndInsert(IDataSet dataSet) throws Exception {
    DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
  }

  private void cleanAll(IDataSet dataSet) throws Exception {
    DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, dataSet);
  }
}
