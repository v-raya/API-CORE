package gov.ca.cwds.authorizer.util;

import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;

/**
 * @author CWDS TPT-3 Team
 */
public final class ClientConditionUtils {

  private ClientConditionUtils() {
  }

  // TODO(dd): Consider to move this to drools
  public static ClientCondition toClientCondition(final Client client,
      final Short clientCountyCode, final Short staffPersonCountyCode) {
    final Sensitivity sensitivity = client.getSensitivity();
    if (sensitivity == null || sensitivity == Sensitivity.NOT_APPLICABLE) {
      return ClientCondition.NO_CONDITIONS;
    }

    return sensitivity == Sensitivity.SENSITIVE
        ? getConditionWhenSensitive(clientCountyCode, staffPersonCountyCode)
        : getConditionWhenSealed(clientCountyCode, staffPersonCountyCode);
  }

  private static ClientCondition getConditionWhenSensitive(final Short clientCountyCode,
      final Short staffPersonCountyCode) {
    if (clientCountyCode == null) {
      return ClientCondition.NO_COUNTY_SENSITIVE;
    }

    return clientCountyCode.equals(staffPersonCountyCode)
        ? ClientCondition.SAME_COUNTY_SENSITIVE
        : ClientCondition.DIFFERENT_COUNTY_SENSITIVE;
  }

  private static ClientCondition getConditionWhenSealed(final Short clientCountyCode,
      final Short staffPersonCountyCode) {
    if (clientCountyCode == null) {
      return ClientCondition.NO_COUNTY_SEALED;
    }

    return clientCountyCode.equals(staffPersonCountyCode)
        ? ClientCondition.SAME_COUNTY_SEALED
        : ClientCondition.DIFFERENT_COUNTY_SEALED;
  }
}
