package gov.ca.cwds.data.stream;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * @author CWDS TPT-2
 */
@FunctionalInterface
public interface QueryCreator<T> {

  Query<T> createQuery(Session session, Class<T> entityClass);
}
