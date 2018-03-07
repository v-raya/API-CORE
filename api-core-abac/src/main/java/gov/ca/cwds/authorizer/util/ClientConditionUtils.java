package gov.ca.cwds.authorizer.util;

import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public final class ClientConditionUtils {

  private ClientConditionUtils() {
  }

  // TODO(dd): Consider to move this to drools
  public static ClientCondition toClientCondition(final Client client,
      final List<Short> clientCountyCodes, final Short staffPersonCountyCode) {
    final Sensitivity sensitivity = client.getSensitivity();
    if (sensitivity == null || sensitivity == Sensitivity.NOT_APPLICABLE) {
      return ClientCondition.NO_CONDITIONS;
    }

    return sensitivity == Sensitivity.SENSITIVE
        ? getConditionWhenSensitive(clientCountyCodes, staffPersonCountyCode)
        : getConditionWhenSealed(clientCountyCodes, staffPersonCountyCode);
  }

  private static ClientCondition getConditionWhenSensitive(final List<Short> clientCountyCodes,
      final Short staffPersonCountyCode) {
    if (isNoClientCounty(clientCountyCodes)) {
      return ClientCondition.NO_COUNTY_SENSITIVE;
    }

    return clientCountyCodes.contains(staffPersonCountyCode)
        ? ClientCondition.SAME_COUNTY_SENSITIVE
        : ClientCondition.DIFFERENT_COUNTY_SENSITIVE;
  }

  private static ClientCondition getConditionWhenSealed(final List<Short> clientCountyCodes,
      final Short staffPersonCountyCode) {
    if (isNoClientCounty(clientCountyCodes)) {
      return ClientCondition.NO_COUNTY_SEALED;
    }

    return clientCountyCodes.contains(staffPersonCountyCode)
        ? ClientCondition.SAME_COUNTY_SEALED
        : ClientCondition.DIFFERENT_COUNTY_SEALED;
  }

  private static boolean isNoClientCounty(List<Short> clientCountyCodes) {
    return clientCountyCodes == null || clientCountyCodes.isEmpty()
        || clientCountyCodes.size() == 1 && clientCountyCodes.get(0) == 0;
  }
}
