package gov.ca.cwds.test.support;

import gov.ca.cwds.DataSourceName;
import io.dropwizard.db.DataSourceFactory;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author CWDS CALS API Team
 */
public class DatabaseHelper {

  private Database database;
  private String url;
  private String user;
  private String password;

  public DatabaseHelper(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public void runScript(String script) throws LiquibaseException {
    try {
      Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  public void runScript(String script, Map<String, Object> parameters, String schema) throws LiquibaseException {
    try {
      String defaultSchema = getDatabase().getDefaultSchemaName();
      getDatabase().setDefaultSchemaName(schema);
      Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      parameters.forEach(liquibase::setChangeLogParameter);
      liquibase.update((String) null);
      getDatabase().setDefaultSchemaName(defaultSchema);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  public void runScript(String script, String schema) throws LiquibaseException {
    try {
      String defaultSchema = getDatabase().getDefaultSchemaName();
      getDatabase().setDefaultSchemaName(schema);
      runScript(script);
      getDatabase().setDefaultSchemaName(defaultSchema);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private Database getDatabase() throws SQLException, DatabaseException {
    if (database == null) {
      Connection connection = DriverManager.getConnection(url, user, password);
      database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
    }

    return database;
  }
  public static void setUpDatabase(DataSourceFactory dataSourceFactory,
                            DataSourceName dataSourceName ) throws LiquibaseException {
    DatabaseHelper databaseHelper = new DatabaseHelper(
            dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
    switch (dataSourceName) {
      case CMS: databaseHelper.runScript("liquibase/cwscms_database_master.xml");
                break;
      case LIS: databaseHelper.runScript("liquibase/lis_database_master.xml");
                break;
      case FAS: databaseHelper.runScript("liquibase/fas_database_master.xml");
                break;
      case NS:  databaseHelper.runScript("liquibase/calsns_database_master_for_tests.xml");
                break;
      case CMSRS: databaseHelper.runScript("liquibase/cwscmsrs_database_master.xml");
    };
  }

}
