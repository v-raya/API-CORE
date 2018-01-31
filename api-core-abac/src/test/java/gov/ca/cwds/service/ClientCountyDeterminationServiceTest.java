package gov.ca.cwds.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import io.dropwizard.testing.junit.DAOTestRule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationServiceTest {

  private static final String LIQUIBASE_CWSRS1_PATH = "liquibase/cwsrs1-database-master.xml";
  private static final String SCHEMA = "CWSRS1";
  private static final String USERNAME = "";
  private static final String URL = "jdbc:h2:mem:"+ SCHEMA;
  private static final String LIQUIBASE_URL = URL + ";INIT=create schema if not exists " + SCHEMA
      + "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";

  private ClientCountyDeterminationService testSubject;

  @Rule
  public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
      .setDriver(org.h2.Driver.class)
      .setUrl(URL)
      .setUsername(USERNAME)
      .setProperty("hibernate.default_schema", SCHEMA)
      .setShowSql(true)
      .build();

  @Before
  public void setUp() throws LiquibaseException {
    runLiquibaseScript(LIQUIBASE_CWSRS1_PATH);

    final CountyDeterminationDao countyDeterminationDao
        = new CountyDeterminationDao(daoTestRule.getSessionFactory());
    testSubject = new ClientCountyDeterminationService(countyDeterminationDao);
  }

  @Test
  public void getClientCounties_success_whenFound() {
    assertThat(
        testSubject.getClientCountyById("00jnURO00h"),
        is((short) 1077)
    );
  }

  @Test
  public void getClientCounties_success_whenCountyIdIsZero() {
    assertThat(
        testSubject.getClientCountyById("0044Q7k0Rt"),
        is(nullValue())
    );
  }

  @Test
  public void getClientCounties_success_whenNotFound() {
    assertThat(
        testSubject.getClientCountyById("unknownId"),
        is(nullValue())
    );
  }

  private static void runLiquibaseScript(String script) throws LiquibaseException {
    try {
      final Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private static Database getDatabase() throws SQLException, DatabaseException {
    final Connection connection = DriverManager.getConnection(LIQUIBASE_URL, USERNAME, "");
    return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
  }

}