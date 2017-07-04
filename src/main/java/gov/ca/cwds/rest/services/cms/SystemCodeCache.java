package gov.ca.cwds.rest.services.cms;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

/**
 * Interface for system code cache facility.
 * 
 * @author CWDS API Team
 */
public interface SystemCodeCache {

  /**
   * Get all system meta definitions.
   * 
   * @return All system meta definitions.
   * @param systemCodeId System code identifier.
   * @return System code if found, otherwise null.
   */
  Set<SystemMeta> getSystemMetas();

  /**
   * Get system code identified by given system code identifier.
   * 
   * @param systemCodeId System code identifier.
   * @return System code if found, otherwise null.
   */
  SystemCode getSystemCode(final Short systemCodeId);

  /**
   * Get system codes for given meta identifier.
   * 
   * @param metaId Meta identifier.
   * @return System codes for given meta identifier.
   */
  Set<SystemCode> getSystemCodesForMeta(final String metaId);

  /**
   * Get short description of system code identified by given system code id.
   * 
   * @param systemCodeId
   * @return Short description of system code identified by given system code id. Returns null if
   *         system code not found.
   */
  String getSystemCodeShortDescription(final Short systemCodeId);

}
