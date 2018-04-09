package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.utils.PrincipalUtils;

import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({PrincipalUtils.class, PlacementHomeCoreService.class})
public class PlacementHomeCoreServiceTest extends BaseUnitTest {

  public static final String USER_ID = "aaa";
  public static final String PLACEMENT_ID = "2212121212";

  @Mock private PlacementHomeDao placementHomeDao;
  private PlacementHomeCoreService placementHomeCoreService;

  private static final Function<String, PlacementHome> function =
      (a) -> {
        PlacementHome otherClientName = new PlacementHome();
        otherClientName.setIdentifier(a);
        return otherClientName;
      };

  public static final Function<PlacementHome, PlacementHomeEntityAwareDTO> placementFunction =
      (a) -> {
        PlacementHomeEntityAwareDTO awareDTO = new PlacementHomeEntityAwareDTO();
        awareDTO.setEntity(a);
        return awareDTO;
      };

  @Before
  public void init() throws DroolsException {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(placementHomeDao.create(any(PlacementHome.class)))
        .thenReturn(function.apply(PLACEMENT_ID));
    placementHomeCoreService = PowerMockito.spy(new PlacementHomeCoreService(placementHomeDao));
    when(placementHomeCoreService.getCreateLifeCycle()).thenReturn(null);
  }

  @Test
  public void create() throws Exception {
    PlacementHome placementHome =
        placementHomeCoreService.create(placementFunction.apply(function.apply(PLACEMENT_ID)));
    assertNotNull(placementHome);
    assertEquals(PLACEMENT_ID, placementHome.getIdentifier());
  }
}
