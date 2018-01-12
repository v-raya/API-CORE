package gov.ca.cwds.data.legacy.cms.entity;

import java.util.List;

/**
 * @author CWDS CALS API Team
 */
public interface ICountyLicenseCase<T extends BaseLicensingVisit, S extends BaseStaffPerson> {

  List<T> getLicensingVisits();

  S getStaffPerson();
}
