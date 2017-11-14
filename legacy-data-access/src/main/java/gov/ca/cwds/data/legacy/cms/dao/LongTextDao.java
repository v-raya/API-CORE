package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.LongText;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * DAO for {@link LongText}.
 * 
 * @author CWDS API Team
 */
public class LongTextDao extends CrudsDaoImpl<LongText> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public LongTextDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
