package gov.ca.cwds.authorizer.util;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author CWDS TPT-3 Team
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PerrySubject.class)
public class ClientConditionUtilsTest {

  @Test
  public void toClientCondition_noConditions_whenClientSensitivityIsNull() {
    initUserAccount(null);
    assertEquals(ClientCondition.NO_CONDITIONS, toClientCondition(null, null));
  }

  @Test
  public void toClientCondition_noConditions_whenClientSensitivityIsNotApplicable() {
    initUserAccount(null);
    assertEquals(ClientCondition.NO_CONDITIONS, toClientCondition(Sensitivity.NOT_APPLICABLE, null));
  }

  @Test
  public void toClientCondition_noCountySensitive_whenSensitiveClientWithoutCounty() {
    initUserAccount(null);
    assertEquals(ClientCondition.NO_COUNTY_SENSITIVE, toClientCondition(Sensitivity.SENSITIVE, null));
  }

  @Test
  public void toClientCondition_sameCountySensitive_whenSensitiveClientFromSameCounty() {
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    initUserAccount("1101");
    assertEquals(ClientCondition.SAME_COUNTY_SENSITIVE, toClientCondition(Sensitivity.SENSITIVE, clientCountyCodes));
  }

  @Test
  public void toClientCondition_differentCountySensitive_whenSensitiveClientFromSameCounty() {
    initUserAccount(null);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    assertEquals(ClientCondition.DIFFERENT_COUNTY_SENSITIVE, toClientCondition(Sensitivity.SENSITIVE, clientCountyCodes));
  }

  @Test
  public void toClientCondition_noCountySealed_whenSealedClientWithoutCounty() {
    initUserAccount(null);
    assertEquals(ClientCondition.NO_COUNTY_SEALED, toClientCondition(Sensitivity.SEALED, null));
  }

  @Test
  public void toClientCondition_sameCountySealed_whenSealedClientFromSameCounty() {
    initUserAccount("1101");
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    assertEquals(ClientCondition.SAME_COUNTY_SEALED, toClientCondition(Sensitivity.SEALED, clientCountyCodes));
  }

  @Test
  public void toClientCondition_differentCountySealed_whenSealedClientFromSameCounty() {
    initUserAccount(null);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    assertEquals(ClientCondition.DIFFERENT_COUNTY_SEALED, toClientCondition(Sensitivity.SEALED, clientCountyCodes));
  }

  private void initUserAccount(String countyCwsCode) {
    PerryAccount perryAccount = new PerryAccount();
    perryAccount.setCountyCwsCode(countyCwsCode);
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
  }
}