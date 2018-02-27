package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.OtherEthnicity;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class OtherEthnicityDao extends BaseDaoImpl<OtherEthnicity> {

    /**
     * Constructor
     *
     * @param sessionFactory The session factory
     */
    @Inject
    public OtherEthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
