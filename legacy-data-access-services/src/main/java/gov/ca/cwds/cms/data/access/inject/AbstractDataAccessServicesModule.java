package gov.ca.cwds.cms.data.access.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import gov.ca.cwds.cms.data.access.dao.placementhome.BackgroundCheckDao;
import gov.ca.cwds.cms.data.access.dao.placementhome.CountyOwnershipDao;
import gov.ca.cwds.cms.data.access.dao.placementhome.EmergencyContactDetailDao;
import gov.ca.cwds.cms.data.access.dao.placementhome.ExternalInterfaceDao;
import gov.ca.cwds.cms.data.access.dao.placementhome.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.placementhome.PlacementHomeProfileDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.OutOfStateCheckDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.PlacementHomeInformationDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.ScpSsaName3Dao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.SubstituteCareProviderDao;
import gov.ca.cwds.cms.data.access.mapper.BackgroundCheckMapper;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.EmergencyContactDetailMapper;
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
    configureDAOs();
    configureDataAccessServices();
  }

  @Provides
  @PlacementHomeSessionFactory
  @Inject
  protected SessionFactory placementHomeSessionFactory(Injector injector) {
    return getPlacementHomeSessionFactory(injector);
  }

  @Provides
  @SubstituteCareProviderSessionFactory
  @Inject
  protected SessionFactory substituteCareProviderSessionFactory(Injector injector) {
    return getSubstituteCareProviderSessionFactory(injector);
  }

  protected abstract SessionFactory getPlacementHomeSessionFactory(Injector injector);
  protected abstract SessionFactory getSubstituteCareProviderSessionFactory(Injector injector);

  private void configureDataAccessServices() {
    bind(PlacementHomeService.class).to(PlacementHomeServiceImpl.class);
    bind(SubstituteCareProviderService.class).to(SubstituteCareProviderServiceImpl.class);
  }

  private void configureDAOs() {
    bind(CountyOwnershipDao.class);
    bind(PlacementHomeDao.class);
    bind(CountyOwnershipDao.class);
    bind(ExternalInterfaceDao.class);
    bind(BackgroundCheckDao.class);
    bind(EmergencyContactDetailDao.class);
    bind(PlacementHomeProfileDao.class);
    bind(SubstituteCareProviderDao.class);
    bind(PlacementHomeInformationDao.class);
    bind(ScpSsaName3Dao.class);
    bind(OutOfStateCheckDao.class);
  }

  private void configureMappers() {
    bind(CountyOwnershipMapper.class).to(CountyOwnershipMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ExternalInterfaceMapper.class).to(ExternalInterfaceMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(EmergencyContactDetailMapper.class).to(EmergencyContactDetailMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(BackgroundCheckMapper.class).to(BackgroundCheckMapper.INSTANCE.getClass())
        .asEagerSingleton();
  }

}
