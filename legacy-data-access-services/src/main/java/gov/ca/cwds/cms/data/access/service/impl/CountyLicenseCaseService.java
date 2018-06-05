package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.authorizer.ClientResultReadAuthorizer.CLIENT_RESULT_READ_OBJECT;
import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.dto.CLCEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherClientNameDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.impl.OtherClientNameService;
import gov.ca.cwds.cms.data.access.service.impl.SafetyAlertService;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientOtherEthnicityDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientServiceProviderDao;
import gov.ca.cwds.data.legacy.cms.dao.CountyLicenseCaseDao;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDao;
import gov.ca.cwds.data.legacy.cms.dao.NameTypeDao;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** @author CWDS TPT-3 Team */
public class CountyLicenseCaseService
    extends DataAccessServiceBase<CountyLicenseCaseDao, CountyLicenseCase, CLCEntityAwareDTO> {

  @Override
  public CountyLicenseCase create(CLCEntityAwareDTO entityAwareDTO) throws DataAccessServicesException {
    return super.create(entityAwareDTO);
  }

  @Inject
  public CountyLicenseCaseService(CountyLicenseCaseDao crudDao) {
    super(crudDao);
  }

  @Override
  public CountyLicenseCase find(Serializable primaryKey) {
    return super.find(primaryKey);
  }

  @Override
  public DataAccessServiceLifecycle getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  public DataAccessServiceLifecycle getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  public DataAccessServiceLifecycle getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

}
