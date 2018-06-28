package gov.ca.cwds.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.dao.cms.SensitivityDeterminationDao;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import io.dropwizard.testing.junit.DAOTestRule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
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
public class ClientSensitivityDeterminationServiceTest {

  private static final String LIQUIBASE_CWSRS1_PATH =
    "liquibase/api-core-abac/api-core-abac-database-master.xml";
  private static final String SCHEMA = "CWSRS1";
  private static final String USERNAME = "";
  private static final String URL = "jdbc:h2:mem:" + SCHEMA;
  private static final String LIQUIBASE_URL = URL + ";INIT=create schema if not exists " + SCHEMA
    + "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";

  private ClientSensitivityDeterminationService testSubject;

  @Rule
  public DAOTestRule daoTestRule =
    DAOTestRule.newBuilder().setDriver(org.h2.Driver.class).setUrl(URL).setUsername(USERNAME)
      .setProperty("hibernate.default_schema", SCHEMA).setShowSql(true).build();

  @Before
  public void setUp() throws LiquibaseException {
    runLiquibaseScript(LIQUIBASE_CWSRS1_PATH);

    final SensitivityDeterminationDao sensitivityDeterminationDao =
      new SensitivityDeterminationDao(daoTestRule.getSessionFactory());
    testSubject = new ClientSensitivityDeterminationService(sensitivityDeterminationDao);
  }

  @Test
  public void getClientSensitivity_success_whenFound() {
    Sensitivity sensitivity = testSubject.getClientSensitivityById("AaiU7IW0Rt");
    assertTrue(Sensitivity.NOT_APPLICABLE == sensitivity);
  }

  @Test
  public void getClientSensitivity_success_whenNotFound() {
    Sensitivity sensitivity = testSubject.getClientSensitivityById("ZZZZZZZZZZ");
    assertNull(sensitivity);
  }

  @Test
  public void getClientSensitivityMapByIds_success_whenFound() {
    Map<String, Sensitivity> sensitivityMap = testSubject
      .getClientSensitivityMapByIds(Arrays.asList("AaiU7IW0Rt"));
    assertTrue(sensitivityMap != null && !sensitivityMap.isEmpty());
    assertEquals(Sensitivity.NOT_APPLICABLE, sensitivityMap.get("AaiU7IW0Rt"));
  }

  @Test
  public void getClientSensitivityMapByIds_success_whenNotFound() {
    Map<String, Sensitivity> sensitivityMap = testSubject
      .getClientSensitivityMapByIds(Arrays.asList("ZZZZZZZZZZ"));
    assertTrue(sensitivityMap != null && sensitivityMap.isEmpty());
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
