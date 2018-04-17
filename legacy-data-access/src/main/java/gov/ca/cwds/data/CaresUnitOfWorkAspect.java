package gov.ca.cwds.data;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;

public class CaresUnitOfWorkAspect extends UnitOfWorkAspect {

  public CaresUnitOfWorkAspect(Map<String, SessionFactory> sessionFactories) {
    super(sessionFactories);
  }

  @Override
  public void beforeStart(UnitOfWork unitOfWork) {
    if (unitOfWork == null) {
      return;
    }
    this.unitOfWork = unitOfWork;

    sessionFactory = sessionFactories.get(unitOfWork.value());
    if (sessionFactory == null) {
      // If the user didn't specify the name of a session factory,
      // and we have only one registered, we can assume that it's the right one.
      if (unitOfWork.value().equals(HibernateBundle.DEFAULT_NAME) && sessionFactories.size() == 1) {
        sessionFactory = sessionFactories.values().iterator().next();
      } else {
        throw new IllegalArgumentException(
            "Unregistered Hibernate bundle: '" + unitOfWork.value() + "'");
      }
    }
    session = sessionFactory.openSession();
    try {
      configureSession();
      ManagedSessionContext.bind(session);
      beginTransaction();
    } catch (Throwable th) {
      session.close();
      session = null;
      ManagedSessionContext.unbind(sessionFactory);
      throw th;
    }
  }

}
