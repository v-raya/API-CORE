package gov.ca.cwds.authorizer.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;

/**
 * Utility class for {@link ClientCondition}'s.
 *
 * @author CWDS TPT-3 Team
 */
public final class ClientConditionUtils {

  private ClientConditionUtils() {}

  // TODO(dd): Consider moving these methods to Drools.
  /**
   *
   * @param sensitivity sensitivity
   * @param clientCountyCodes client county codes
   * @return client condition
   */
  public static ClientCondition toClientCondition(Sensitivity sensitivity,
      List<Short> clientCountyCodes) {
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Short staffPersonCountyCode = getStaffPersonCountyCode(perryAccount.getCountyCwsCode());

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

    return clientCountyCodes.contains(staffPersonCountyCode) ? ClientCondition.SAME_COUNTY_SENSITIVE
        : ClientCondition.DIFFERENT_COUNTY_SENSITIVE;
  }

  private static ClientCondition getConditionWhenSealed(final List<Short> clientCountyCodes,
      final Short staffPersonCountyCode) {
    if (isNoClientCounty(clientCountyCodes)) {
      return ClientCondition.NO_COUNTY_SEALED;
    }

    return clientCountyCodes.contains(staffPersonCountyCode) ? ClientCondition.SAME_COUNTY_SEALED
        : ClientCondition.DIFFERENT_COUNTY_SEALED;
  }

  private static boolean isNoClientCounty(List<Short> clientCountyCodes) {
    return clientCountyCodes == null || clientCountyCodes.isEmpty()
        || clientCountyCodes.size() == 1 && clientCountyCodes.get(0) == 0;
  }

  private static Short getStaffPersonCountyCode(final String staffCountyCodeString) {
    return StringUtils.isNotBlank(staffCountyCodeString) ? Short.valueOf(staffCountyCodeString)
        : null;
  }

}
