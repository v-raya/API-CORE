package gov.ca.cwds.authorizer.util;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientConditionUtilsTest {

  @Test
  public void toClientCondition_noConditions_whenClientSensitivityIsNull() {
    final ClientCondition actual = toClientCondition(new Client(), null, null);
    assertThat(
        actual,
        is(equalTo(ClientCondition.NO_CONDITIONS))
    );
  }

  @Test
  public void toClientCondition_noConditions_whenClientSensitivityIsNotApplicable() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.NOT_APPLICABLE);

    // when
    final ClientCondition actual = toClientCondition(client, null, null);

    // then
    assertThat(actual, is(equalTo(ClientCondition.NO_CONDITIONS)));
  }

  @Test
  public void toClientCondition_noCountySensitive_whenSensitiveClientWithoutCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SENSITIVE);

    // when
    final ClientCondition actual = toClientCondition(client, null, null);

    // then
    assertThat(actual, is(equalTo(ClientCondition.NO_COUNTY_SENSITIVE)));
  }

  @Test
  public void toClientCondition_sameCountySensitive_whenSensitiveClientFromSameCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SENSITIVE);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    final Short staffPersonCountyCode = 1101;

    // when
    final ClientCondition actual = toClientCondition(client, clientCountyCodes, staffPersonCountyCode);

    // then
    assertThat(actual, is(equalTo(ClientCondition.SAME_COUNTY_SENSITIVE)));
  }

  @Test
  public void toClientCondition_differentCountySensitive_whenSensitiveClientFromSameCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SENSITIVE);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    final Short staffPersonCountyCode = 1;

    // when
    final ClientCondition actual = toClientCondition(client, clientCountyCodes, staffPersonCountyCode);

    // then
    assertThat(actual, is(equalTo(ClientCondition.DIFFERENT_COUNTY_SENSITIVE)));
  }

  @Test
  public void toClientCondition_noCountySealed_whenSealedClientWithoutCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SEALED);

    // when
    final ClientCondition actual = toClientCondition(client, null, null);

    // then
    assertThat(actual, is(equalTo(ClientCondition.NO_COUNTY_SEALED)));
  }

  @Test
  public void toClientCondition_sameCountySealed_whenSealedClientFromSameCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SEALED);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    final Short staffPersonCountyCode = 1101;

    // when
    final ClientCondition actual = toClientCondition(client, clientCountyCodes, staffPersonCountyCode);

    // then
    assertThat(actual, is(equalTo(ClientCondition.SAME_COUNTY_SEALED)));
  }

  @Test
  public void toClientCondition_differentCountySealed_whenSealedClientFromSameCounty() {
    // given
    final Client client = new Client();
    client.setSensitivity(Sensitivity.SEALED);
    final List<Short> clientCountyCodes = Arrays.asList(new Short[] {1101});
    final Short staffPersonCountyCode = 1;

    // when
    final ClientCondition actual = toClientCondition(client, clientCountyCodes, staffPersonCountyCode);

    // then
    assertThat(actual, is(equalTo(ClientCondition.DIFFERENT_COUNTY_SEALED)));
  }

}