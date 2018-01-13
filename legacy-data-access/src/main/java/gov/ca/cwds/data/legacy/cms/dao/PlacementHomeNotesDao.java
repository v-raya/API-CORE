package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeNotes;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class PlacementHomeNotesDao extends BaseDaoImpl<PlacementHomeNotes> {
   @Inject
   public PlacementHomeNotesDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
