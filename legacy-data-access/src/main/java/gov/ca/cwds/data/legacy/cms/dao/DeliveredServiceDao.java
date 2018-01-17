package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.inject.CmsSessionFactory;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author CWDS TPT-3 Team */
public class DeliveredServiceDao extends BaseDaoImpl<DeliveredService> {

  protected static final Logger LOG = LoggerFactory.getLogger(DeliveredServiceDao.class);

  @Inject
  public DeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public DeliveredService findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<DeliveredService> query =
        session.createNamedQuery(DeliveredService.FIND_BY_CLIENT, DeliveredService.class);
    query.setParameter("clientId", clientId);
    DeliveredService service = null;
    try {
      service = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.warn("There is no result for clientId = {}", clientId);
      LOG.debug(e.getMessage(), e);
    }
    return service;
  }
}
