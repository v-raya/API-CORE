package gov.ca.cwds.data.persistence.cms;

/**
 * @author CWDS CALS API Team
 */
@SuppressWarnings("squid:S1609") // this interface is not meant to be functional
public interface IBasePlacementHome<T extends BaseCountyLicenseCase> {

  T getCountyLicenseCase();
}
