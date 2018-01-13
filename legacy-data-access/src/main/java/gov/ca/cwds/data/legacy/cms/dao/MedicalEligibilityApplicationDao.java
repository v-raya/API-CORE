package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

import java.util.List;

public class MedicalEligibilityApplicationDao extends BaseDaoImpl<MedicalEligibilityApplication> {
    /**
     * Constructor
     *
     * @param sessionFactory The session factory
     */
    @Inject
    public MedicalEligibilityApplicationDao(@CmsSessionFactory SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<MedicalEligibilityApplication> findByChildClientId(String childClientId) {
        List<MedicalEligibilityApplication> medicalEligibilityApplications = this.getSessionFactory().getCurrentSession()
                .createNamedQuery(MedicalEligibilityApplication.NQ_FIND_BY_CHILD_CLIENT_ID, MedicalEligibilityApplication.class)
                .setParameter(MedicalEligibilityApplication.NQ_PARAM_CHILD_CLIENT_ID, childClientId)
                .list();

        return ImmutableList.<MedicalEligibilityApplication>builder()
                .addAll(medicalEligibilityApplications)
                .build();

    }

}
