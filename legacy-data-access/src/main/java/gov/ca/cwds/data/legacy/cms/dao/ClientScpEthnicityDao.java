package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author CWDS CALS API Team
 */
public class ClientScpEthnicityDao extends BaseDaoImpl<ClientScpEthnicity> {

    /**
     * Constructor
     *
     * @param sessionFactory The session factory
     */
    @Inject
    public ClientScpEthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<ClientScpEthnicity> findEthnicitiesByClient(String clientId) {
        List<ClientScpEthnicity> clientScpEthnicities = this.getSessionFactory().getCurrentSession()
                .createNamedQuery(ClientScpEthnicity.NQ_FIND_BY_TYPE_AND_ESTABLISHED_FOR_ID, ClientScpEthnicity.class)
                .setParameter(ClientScpEthnicity.NQ_PARAM_ESTABLISHED_FOR_CODE, ClientScpEthnicity.ESTABLISHED_FOR_CLIENT_CODE)
                .setParameter(ClientScpEthnicity.NQ_PARAM_ESTABLISHED_FOR_ID, clientId)
                .list();

        return ImmutableList.<ClientScpEthnicity>builder()
                .addAll(clientScpEthnicities)
                .build();

    }
}
