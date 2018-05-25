package gov.ca.cwds.data.audit;

import java.sql.Connection;
import java.sql.SQLException;
import com.ibm.db2.jcc.DB2Connection;
import gov.ca.cwds.security.realm.PerrySubject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;

public class AuditingSessionFactory extends SessionFactoryDelegatingImpl implements Work {
  public AuditingSessionFactory(SessionFactoryImplementor delegate) {
    super(delegate);
  }

  @Override
  public Session openSession() throws HibernateException {
    Session session = super.openSession();
    session.doWork(this);
    return session;
  }

  @Override
  public void execute(Connection connection) throws SQLException {
    DB2Connection db2Connection = (DB2Connection) connection;
    String racfid = PerrySubject.getPerryAccount().getUser();
    //racfid will be available as CURRENT CLIENT_USERID
    //racfid will be availabe as "Client user ID" in Audit record layout for EXECUTE events
    //see: https://www.ibm.com/support/knowledgecenter/en/SSEPGG_9.5.0/com.ibm.db2.luw.admin.sec.doc/doc/r0051526.html
    db2Connection.setDB2ClientUser(racfid);
  }
}
