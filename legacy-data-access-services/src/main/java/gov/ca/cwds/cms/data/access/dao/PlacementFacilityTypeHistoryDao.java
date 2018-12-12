package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementFacilityTypeHistory;
import org.hibernate.SessionFactory;

public class PlacementFacilityTypeHistoryDao extends BaseDaoImpl<PlacementFacilityTypeHistory> {
    @Inject
    public PlacementFacilityTypeHistoryDao(@XaDasSessionFactory SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
