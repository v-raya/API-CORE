package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.legacy.cms.dao.ClientUcDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherClientNameDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.entity.OtherClientName;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PrincipalUtils.class)
public class OtherClientNameServiceTest extends BaseUnitTest {

  public static final String USER_ID = "aaa";
  public static final String CLIENT_ID = "11aass2233";

  private OtherClientNameService otherClientNameService;

  @Mock private OtherClientNameDao otherClientNameDao;
  @Mock private SsaName3Dao ssaName3Dao;
  @Mock private ClientUcDao clientUcDao;

  private static final Function<String, List<OtherClientName>> function =
      (a) -> {
        OtherClientName otherClientName = new OtherClientName();
        otherClientName.setClientId(a);
        return Arrays.asList(otherClientName);
      };

  @Before
  public void init() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(otherClientNameDao.findByClientId(any(String.class)))
        .thenReturn(function.apply(CLIENT_ID));

    otherClientNameService =
        new OtherClientNameService(otherClientNameDao, ssaName3Dao, clientUcDao);
  }

  @Test
  public void findByClientId() throws Exception {
    List<OtherClientName> otherClientNames = otherClientNameService.findByClientId(CLIENT_ID);
    assertNotNull(otherClientNames);
    assertEquals(1, otherClientNames.size());
    assertEquals(CLIENT_ID, otherClientNames.get(0).getClientId());
  }
}
