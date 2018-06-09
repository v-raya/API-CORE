package gov.ca.cwds.cms.data.access.inject;

import gov.ca.cwds.cms.data.access.mapper.ClientMapper;
import gov.ca.cwds.cms.data.access.service.impl.CountyLicenseCaseService;
import gov.ca.cwds.cms.data.access.service.impl.StaffPersonService;
import gov.ca.cwds.cms.data.access.service.impl.clientrelationship.ClientRelationshipCoreService;
import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.TribalMembershipVerificationCoreService;
import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;

import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.ExternalInterfaceMapper;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.ChildClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.PlacementHomeCoreService;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderCoreService;

/** @author CWDS CALS API Team */
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
    bind(PlacementHomeCoreService.class);
    bind(SubstituteCareProviderCoreService.class);
    bind(CountyLicenseCaseService.class);
    bind(StaffPersonService.class);
    bind(ClientCoreService.class);
    bind(ChildClientCoreService.class);
    bind(BusinessValidationService.class);
    bind(TribalMembershipVerificationCoreService.class);
    bind(gov.ca.cwds.cms.data.access.service.impl.tribalmembership.CreateLifeCycle.class);
    bind(gov.ca.cwds.cms.data.access.service.ClientRelationshipService.class)
        .to(ClientRelationshipCoreService.class);
  }

  private void configureMappers() {
    bind(CountyOwnershipMapper.class)
        .to(CountyOwnershipMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ExternalInterfaceMapper.class)
        .to(ExternalInterfaceMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ClientMapper.class).to(ClientMapper.INSTANCE.getClass()).asEagerSingleton();
  }
}
