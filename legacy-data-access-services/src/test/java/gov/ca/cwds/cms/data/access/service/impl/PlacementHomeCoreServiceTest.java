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

import gov.ca.cwds.cms.data.access.dao.NonXaDaoProvider;
import gov.ca.cwds.cms.data.access.dao.XaDaoProvider;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.data.legacy.cms.dao.BackgroundCheckDao;
import gov.ca.cwds.data.legacy.cms.dao.CountyOwnershipDao;
import gov.ca.cwds.data.legacy.cms.dao.EmergencyContactDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.ExternalInterfaceDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherAdultsInPlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherChildrenInPlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherPeopleScpRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.OutOfStateCheckDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementFacilityTypeHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeProfileDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeUcDao;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;
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
public class PlacementHomeCoreServiceTest
// extends LegacyBarneyTest<Client>
{

  public static final String USER_ID = "aaa";
  public static final String PLACEMENT_ID = "2212121212";

  @Mock
  protected XaDaoProvider xaDaoProvider;

  @Mock
  protected NonXaDaoProvider nonXaDaoProvider;

  @Mock
  private PlacementHomeDao placementHomeDao;

  @Mock protected PlacementHomeUcDao placementHomeUcDao;
  @Mock protected CountyOwnershipDao countyOwnershipDao;
  @Mock protected ExternalInterfaceDao externalInterfaceDao;
  @Mock protected EmergencyContactDetailDao emergencyContactDetailDao;
  @Mock protected PlacementHomeProfileDao placementHomeProfileDao;
  @Mock protected PlacementFacilityTypeHistoryDao placementFacilityTypeHistoryDao;
  @Mock protected OtherChildrenInPlacementHomeDao otherChildrenInPlacementHomeDao;
  @Mock protected OtherPeopleScpRelationshipDao otherPeopleScpRelationshipDao;
  @Mock protected OtherAdultsInPlacementHomeDao otherAdultsInPlacementHomeDao;
  @Mock protected OutOfStateCheckDao outOfStateCheckDao;
  @Mock protected BackgroundCheckDao backgroundCheckDao;

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
    when(xaDaoProvider.getDao(any())).thenReturn(placementHomeDao);
    when(nonXaDaoProvider.getDao(any())).thenReturn(placementHomeDao);
    when(placementHomeDao.create(any(PlacementHome.class)))
        .thenReturn(function.apply(PLACEMENT_ID));
    when(xaDaoProvider.getDao(PlacementHomeUcDao.class)).thenReturn(placementHomeUcDao);
    when(xaDaoProvider.getDao(CountyOwnershipDao.class)).thenReturn(countyOwnershipDao);
    when(xaDaoProvider.getDao(ExternalInterfaceDao.class)).thenReturn(externalInterfaceDao);
    when(xaDaoProvider.getDao(EmergencyContactDetailDao.class)).thenReturn(emergencyContactDetailDao);
    when(xaDaoProvider.getDao(EmergencyContactDetailDao.class)).thenReturn(emergencyContactDetailDao);
    when(xaDaoProvider.getDao(PlacementHomeProfileDao.class)).thenReturn(placementHomeProfileDao);
    when(xaDaoProvider.getDao(PlacementFacilityTypeHistoryDao.class)).thenReturn(placementFacilityTypeHistoryDao);
    when(xaDaoProvider.getDao(OtherChildrenInPlacementHomeDao.class)).thenReturn(otherChildrenInPlacementHomeDao);
    when(xaDaoProvider.getDao(OtherPeopleScpRelationshipDao.class)).thenReturn(otherPeopleScpRelationshipDao);
    when(xaDaoProvider.getDao(OtherAdultsInPlacementHomeDao.class)).thenReturn(otherAdultsInPlacementHomeDao);
    when(xaDaoProvider.getDao(OutOfStateCheckDao.class)).thenReturn(outOfStateCheckDao);
    when(xaDaoProvider.getDao(BackgroundCheckDao.class)).thenReturn(backgroundCheckDao);
    when(xaDaoProvider.getSsaName3Dao()).thenReturn(null);
    target = PowerMockito.spy(new PlacementHomeCoreService(xaDaoProvider,
      nonXaDaoProvider));
    when(target.getCreateLifeCycle()).thenReturn(null);
  }

  @Test
  public void create() throws Exception {
    final PlacementHome placementHome =
      target.create(placementFunction.apply(function.apply(PLACEMENT_ID)), true);
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
    final Serializable primaryKey = "1234567abc";
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
