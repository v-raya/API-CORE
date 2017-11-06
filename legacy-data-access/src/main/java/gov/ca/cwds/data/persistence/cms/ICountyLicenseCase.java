package gov.ca.cwds.data.persistence.cms;

import java.util.List;

/**
 * @author CWDS CALS API Team
 */
public interface ICountyLicenseCase<T extends BaseLicensingVisit, S extends BaseStaffPerson> {

  List<T> getLicensingVisits();

  S getStaffPerson();
}
