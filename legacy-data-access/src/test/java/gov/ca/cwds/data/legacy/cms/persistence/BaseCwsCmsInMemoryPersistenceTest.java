package gov.ca.cwds.data.legacy.cms.persistence;

public abstract class BaseCwsCmsInMemoryPersistenceTest extends BasePersistenceTest {

  private final String SCHEMA = "CWSCMS";
  private final String URL =
      "jdbc:h2:mem:cwscms;INIT=create schema if not exists " + SCHEMA +
          "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";
  private final String USER = "";
  private final String PASSWORD = "";
  private final String DRIVER_CLASS_NAME = "org.h2.Driver";
  private final String LIQUIBASE_SCRIPT_PATH = "liquibase/cwscms_database_base_with_lookups.xml";
  private final String HIBERNATE_CONFIG_PATH = "hibernate.cfg.xml";

  @Override
  protected String getUrl() {
    return URL;
  }

  @Override
  protected String getUser() {
    return USER;
  }

  @Override
  protected String getPassword() {
    return PASSWORD;
  }

  @Override
  protected String getSchema() {
    return SCHEMA;
  }

  @Override
  protected String getDriverClassName() {
    return DRIVER_CLASS_NAME;
  }

  @Override
  protected String getLiquibaseScriptPath() {
    return LIQUIBASE_SCRIPT_PATH;
  }

  @Override
  protected String getHibernateConfigPath() {
    return HIBERNATE_CONFIG_PATH;
  }
}
