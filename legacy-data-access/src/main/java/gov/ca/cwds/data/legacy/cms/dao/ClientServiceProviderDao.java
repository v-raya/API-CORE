package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider.FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider.PARAM_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class ClientServiceProviderDao extends BaseDaoImpl<ClientServiceProvider> {

  @Inject
  public ClientServiceProviderDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<ClientServiceProvider> findByClientId(String clientId) {

    Require.requireNotNullAndNotEmpty(clientId);

    final List<ClientServiceProvider> clientServiceProviders = currentSession()
        .createNamedQuery(FIND_BY_CLIENT_ID, ClientServiceProvider.class)
        .setParameter(PARAM_CLIENT_ID, clientId)
        .list();

    return ImmutableList.copyOf(clientServiceProviders);
  }
}
