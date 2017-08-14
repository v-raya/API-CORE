package gov.ca.cwds.data.persistence.cms;

import java.util.List;

import gov.ca.cwds.data.Dao;

/**
 * DAO retrieves all codes for {@link ApiSystemCodeCache}.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface ApiSystemCodeDao extends Dao {

  /**
   * Get all system codes to populate {@link ApiSystemCodeCache}.
   * 
   * @return all system codes to populate {@link ApiSystemCodeCache}
   */
  List<CmsSystemCode> getAllSystemCodes();

}
