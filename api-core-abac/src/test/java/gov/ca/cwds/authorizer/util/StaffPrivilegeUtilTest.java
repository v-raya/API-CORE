package gov.ca.cwds.authorizer.util;

import static gov.ca.cwds.authorizer.StaffPrivilegeType.COUNTY_SEALED;
import static gov.ca.cwds.authorizer.StaffPrivilegeType.COUNTY_SENSITIVE;
import static gov.ca.cwds.authorizer.StaffPrivilegeType.SOCIAL_WORKER_ONLY;
import static gov.ca.cwds.authorizer.StaffPrivilegeType.STATE_SEALED;
import static gov.ca.cwds.authorizer.StaffPrivilegeType.STATE_SENSITIVE;
import static gov.ca.cwds.util.PerryAccountUtils.initPerryAccountWithPrivileges;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Set;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class StaffPrivilegeUtilTest {

  @Test
  public void toStaffPersonPrivilegeTypes_empty_whenNoPerryPrivileges() {
    // given
    final PerryAccount input = new PerryAccount();

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.isEmpty(), is(true));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_empty_whenEmptyPerryPrivileges() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges();

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.isEmpty(), is(true));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_empty_whenUnknownPerryPrivileges() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("a", "bbb");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.isEmpty(), is(true));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenSocialWorker() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("CWS Case Management System");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(SOCIAL_WORKER_ONLY));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenCountySensitive() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("Sensitive Persons");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(COUNTY_SENSITIVE));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenStateSensitive() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("Sensitive Persons");
    input.setCountyName("State of California");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(STATE_SENSITIVE));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenCountySealed() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("Sealed");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(COUNTY_SEALED));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenStateSealed() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges("Sealed");
    input.setCountyName("State of California");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(STATE_SEALED));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenAllCountyLevels() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges(
        "CWS Case Management System",
        "Sensitive Persons",
        "Sealed",
        "...some noise"
    );
    input.setCountyName("any county name");

    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(3));
    assertThat(actual, containsInAnyOrder(SOCIAL_WORKER_ONLY, COUNTY_SENSITIVE, COUNTY_SEALED));
  }

  @Test
  public void toStaffPersonPrivilegeTypes_success_whenAllStateLevels() {
    // given
    final PerryAccount input = initPerryAccountWithPrivileges(
        "CWS Case Management System",
        "Sensitive Persons",
        "Sealed",
        "...some noise"
    );
    input.setCountyName("State of California");


    // when
    final Set<StaffPrivilegeType> actual = StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(input);

    // then
    assertThat(actual.size(), is(3));
    assertThat(actual, containsInAnyOrder(SOCIAL_WORKER_ONLY, STATE_SENSITIVE, STATE_SEALED));
  }

}
