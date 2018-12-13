package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.CountyLicenseCaseDao;
import gov.ca.cwds.cms.data.access.dto.CLCEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;

/** @author CWDS TPT-3 Team */
public class CountyLicenseCaseService
    extends DataAccessServiceBase<CountyLicenseCaseDao, CountyLicenseCase, CLCEntityAwareDTO> {

  @Inject
  public CountyLicenseCaseService(CountyLicenseCaseDao crudDao) {
    super(crudDao);
  }

  @Override
  public DataAccessServiceLifecycle getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  public DataAccessServiceLifecycle getCreateLifeCycle() {
    return new CreateLifecycle();
  }

  @Override
  public DataAccessServiceLifecycle getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  protected static class CreateLifecycle extends DefaultDataAccessLifeCycle<CLCEntityAwareDTO> {
    @Override
    public void beforeDataProcessing(DataAccessBundle bundle) {
      CLCEntityAwareDTO awareDTO = (CLCEntityAwareDTO) bundle.getAwareDto();
      CountyLicenseCase countyLicenseCase = awareDTO.getEntity();
      countyLicenseCase.setIdentifier(IdGenerator.generateId());
      awareDTO.setEntity(countyLicenseCase);
      bundle.setAwareDto(awareDTO);
    }

  }

}
