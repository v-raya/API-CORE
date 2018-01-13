package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Language;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS CALS API Team */
public class LanguageDao extends BaseDaoImpl<Language> {

  @Inject
  public LanguageDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<Language> findAll() {
    return queryImmutableList(Language.NQ_ALL);
  }
}
