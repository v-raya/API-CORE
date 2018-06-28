package gov.ca.cwds.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import io.dropwizard.testing.junit.DAOTestRule;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationServiceTest {

  private static final String LIQUIBASE_CWSRS1_PATH =
      "liquibase/api-core-abac/api-core-abac-database-master.xml";
  private static final String SCHEMA = "CWSRS1";
  private static final String USERNAME = "";
  private static final String URL = "jdbc:h2:mem:" + SCHEMA;
  private static final String LIQUIBASE_URL = URL + ";INIT=create schema if not exists " + SCHEMA
      + "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";

  private ClientCountyDeterminationService testSubject;

  @Rule
  public DAOTestRule daoTestRule =
      DAOTestRule.newBuilder().setDriver(org.h2.Driver.class).setUrl(URL).setUsername(USERNAME)
          .setProperty("hibernate.default_schema", SCHEMA).setShowSql(true).build();

  @Before
  public void setUp() throws LiquibaseException {
    runLiquibaseScript(LIQUIBASE_CWSRS1_PATH);

    final CountyDeterminationDao countyDeterminationDao =
        new CountyDeterminationDao(daoTestRule.getSessionFactory());
    testSubject = new ClientCountyDeterminationService(countyDeterminationDao);
  }

  @Test
  public void getClientCounties_success_whenFound() {
    List<Short> clientCounties = testSubject.getClientCountiesById("00jnURO00h");
    Assert.assertArrayEquals(new Short[] {1077}, clientCounties.toArray());
  }

  @Test
  public void getClientCounties_success_whenCountyIdIsZero() {
    List<Short> clientCounties = testSubject.getClientCountiesById("0044Q7k0Rt");
    Assert.assertArrayEquals(new Short[] {0}, clientCounties.toArray());
  }

  @Test
  public void getClientCounties_success_whenNotFound() {
    Assert.assertTrue(testSubject.getClientCountiesById("unknownId").isEmpty());
  }

  @Test
  public void getClientCountiesMapByIds_success_whenFound() {
    Map<String, List<Short>> clientCountiesMap = testSubject.getClientCountiesMapByIds(Arrays.asList("00jnURO00h"));
    Assert.assertArrayEquals(new Short[] {1077}, clientCountiesMap.get("00jnURO00h").toArray());
  }

  private static void runLiquibaseScript(String script) throws LiquibaseException {
    try {
      final Liquibase liquibase =
          new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private static Database getDatabase() throws SQLException, DatabaseException {
    final Connection connection = DriverManager.getConnection(LIQUIBASE_URL, USERNAME, "");
    return DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(connection));
  }

}
