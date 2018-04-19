package gov.ca.cwds.data.persistence;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.Reference;

import com.atomikos.jdbc.AtomikosDataSourceBean;

import io.dropwizard.db.ManagedDataSource;

/**
 * @author CWDS CALS API Team
 */
public class AtomikosPooledManagedDataSource extends AtomikosDataSourceBean
    implements ManagedDataSource {

  private static final long serialVersionUID = -1670043209229276391L;

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException("Doesn't use java.util.logging");
  }

  @Override
  public void start() throws Exception {
    init();
  }

  @Override
  public void stop() throws Exception {
    close();
  }

  @Override
  public Reference getReference() throws NamingException {
    // Do nothing
    return null;
  }

}
