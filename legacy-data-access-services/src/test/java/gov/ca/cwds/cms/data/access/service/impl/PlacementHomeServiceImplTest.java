package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.parameter.PlacementHomeParameterObject;
import gov.ca.cwds.cms.data.access.parameter.SCPParameterObject;
import gov.ca.cwds.cms.data.access.service.PlacementHomeService;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeServiceImplTest {

  @Test
  public void checkParametersValidationForCrerate() {
    try {
      PlacementHomeService placementHomeService = new PlacementHomeServiceImpl();
      PlacementHome placementHome = new PlacementHome();
      placementHome.setIdentifier("1");
      PlacementHomeParameterObject parameterObject = new PlacementHomeParameterObject("1");
      parameterObject.setEntity(placementHome);
      placementHomeService.create(parameterObject);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(String.format(ParametersValidator.INSTANCE_MUST_NOT_BE_PERSISTED,
          PlacementHome.class.getSimpleName()), e.getMessage());
    }
  }

  @Test
  public void createScpNotPersisted() throws Exception {
    try {
      PlacementHomeService service = new PlacementHomeServiceImpl();
      PlacementHome placementHome = new PlacementHome();
      SCPParameterObject scpParameterObject = new SCPParameterObject("1");
      SubstituteCareProvider scp = new SubstituteCareProvider();
      scpParameterObject.setEntity(scp);
      PlacementHomeParameterObject parameterObject = new PlacementHomeParameterObject("1");
      scp.setIdentifier("1");
      List<SCPParameterObject> scpList = new ArrayList<>();
      scpList.add(scpParameterObject);
      parameterObject.setScpParameterObjects(scpList);
      parameterObject.setEntity(placementHome);
      service.create(parameterObject);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(String.format(ParametersValidator.INSTANCE_MUST_NOT_BE_PERSISTED,
          SubstituteCareProvider.class.getSimpleName()), e.getMessage());
    }
  }


}