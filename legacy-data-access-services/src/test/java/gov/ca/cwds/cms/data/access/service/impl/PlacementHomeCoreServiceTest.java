package gov.ca.cwds.cms.data.access.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.common.OscarTheGrouch;
import gov.ca.cwds.data.legacy.cms.dao.LegacyBarneyTest;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.utils.PrincipalUtils;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({PrincipalUtils.class, PlacementHomeCoreService.class})
public class PlacementHomeCoreServiceTest extends LegacyBarneyTest<Client> {

  public static final String USER_ID = "aaa";
  public static final String PLACEMENT_ID = "2212121212";

  @Mock
  private PlacementHomeDao placementHomeDao;

  private PlacementHomeCoreService target;

  private static final Function<String, PlacementHome> function = (a) -> {
    final PlacementHome otherClientName = new PlacementHome();
    otherClientName.setIdentifier(a);
    return otherClientName;
  };

  public static final Function<PlacementHome, PlacementHomeEntityAwareDTO> placementFunction =
      a -> {
        final PlacementHomeEntityAwareDTO awareDTO = new PlacementHomeEntityAwareDTO();
        awareDTO.setEntity(a);
        return awareDTO;
      };

  @Before
  public void init() throws DroolsException {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(placementHomeDao.create(any(PlacementHome.class)))
        .thenReturn(function.apply(PLACEMENT_ID));
    target = PowerMockito.spy(new PlacementHomeCoreService(placementHomeDao));
    when(target.getCreateLifeCycle()).thenReturn(null);
  }

  @Test
  public void create() throws Exception {
    final PlacementHome placementHome =
        target.create(placementFunction.apply(function.apply(PLACEMENT_ID)));
    assertNotNull(placementHome);
    assertEquals(PLACEMENT_ID, placementHome.getIdentifier());
  }

  @Test
  public void type() throws Exception {
    assertThat(PlacementHomeCoreService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getUpdateLifeCycle_A$() throws Exception {
    DataAccessServiceLifecycle actual = target.getUpdateLifeCycle();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getCreateLifeCycle_A$() throws Exception {
    DataAccessServiceLifecycle actual = target.getCreateLifeCycle();
    DataAccessServiceLifecycle expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeleteLifeCycle_A$() throws Exception {
    DataAccessServiceLifecycle actual = target.getDeleteLifeCycle();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void find_A$Serializable() throws Exception {
    Serializable primaryKey = DEFAULT_CLIENT_ID;
    PlacementHome actual = target.find(primaryKey);
    PlacementHome expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_A$PlacementHomeEntityAwareDTO() throws Exception {
    PlacementHomeEntityAwareDTO entityAwareDto = mock(PlacementHomeEntityAwareDTO.class);
    PlacementHome actual = target.create(entityAwareDto);
    PlacementHome expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
