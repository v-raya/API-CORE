package gov.ca.cwds.cms.data.access.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.ExternalInterfaceMapper;
import gov.ca.cwds.cms.data.access.service.PlacementHomeService;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.service.impl.PlacementHomeServiceImpl;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderServiceImpl;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public abstract class AbstractDataAccessServicesModule extends AbstractModule {

  @Override
  protected final void configure() {
    configureMappers();
    configureDataAccessServices();
  }

  @Provides
  @DataAccessServicesSessionFactory
  @Inject
  protected SessionFactory dataAccessServicesSessionFactory(Injector injector) {
    return getDataAccessSercvicesSessionFactory(injector);
  }

  protected abstract SessionFactory getDataAccessSercvicesSessionFactory(Injector injector);

  private void configureDataAccessServices() {
    bind(PlacementHomeService.class).to(PlacementHomeServiceImpl.class);
    bind(SubstituteCareProviderService.class).to(SubstituteCareProviderServiceImpl.class);
  }

  private void configureMappers() {
    bind(CountyOwnershipMapper.class).to(CountyOwnershipMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ExternalInterfaceMapper.class).to(ExternalInterfaceMapper.INSTANCE.getClass())
        .asEagerSingleton();
  }

}
