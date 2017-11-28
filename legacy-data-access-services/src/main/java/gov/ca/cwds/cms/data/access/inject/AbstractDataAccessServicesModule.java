package gov.ca.cwds.cms.data.access.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import gov.ca.cwds.cms.data.access.dao.CountyOwnershipForPlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.service.PlacementHomeService;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public abstract class AbstractDataAccessServicesModule extends AbstractModule {

  @Override
  protected final void configure() {
    configureMappers();
    configureDAOs();
    configureDataAccessServices();
  }

  @Provides
  @PlacementHomeSessionFactory
  @Inject
  protected SessionFactory placementHomeSessionFactory(Injector injector) {
    return getPlacementHomeSessionFactory(injector);
  }

  protected abstract SessionFactory getPlacementHomeSessionFactory(Injector injector);

  private void configureDataAccessServices() {
    bind(PlacementHomeService.class);
  }

  private void configureDAOs() {
    bind(CountyOwnershipForPlacementHomeDao.class);
    bind(PlacementHomeDao.class);
    bind(CountyOwnershipForPlacementHomeDao.class);
  }

  private void configureMappers() {
    bind(CountyOwnershipMapper.class).to(CountyOwnershipMapper.INSTANCE.getClass())
        .asEagerSingleton();
  }

}
