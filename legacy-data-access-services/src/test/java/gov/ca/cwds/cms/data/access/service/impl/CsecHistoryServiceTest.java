package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
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
public class CsecHistoryServiceTest extends BaseUnitTest {

  public static final String USER_ID = "aaa";
  public static final String CLIENT_ID = "112233112233";

  private CsecHistoryService csecHistoryService;
  @Mock private CsecHistoryDao csecHistoryDao;

  private static final Function<String, List<CsecHistory>> stringListFunction =
      (a) -> {
        CsecHistory csecHistory = new CsecHistory();
        csecHistory.setChildClient(a);
        return Arrays.asList(csecHistory);
      };

  @Before
  public void init() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);

    when(csecHistoryDao.findByClientId(any(String.class)))
        .thenReturn(stringListFunction.apply(CLIENT_ID));
    csecHistoryService = new CsecHistoryService(csecHistoryDao);
  }

  @Test
  public void findByClientId() throws Exception {
    List<CsecHistory> csecHistories = csecHistoryDao.findByClientId(CLIENT_ID);
    assertNotNull(csecHistories);
    assertEquals(1, csecHistories.size());
    assertEquals(CLIENT_ID, csecHistories.get(0).getChildClient());
  }
}
