package gov.ca.cwds.data.persistence.cms;

import java.util.List;

import gov.ca.cwds.data.Dao;

/**
 * DAO retrieves all codes for {@link ISystemCodeCache}.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface ISystemCodeDao extends Dao {

  /**
   * Get all system codes to populate {@link ISystemCodeCache}.
   * 
   * @return all system codes to populate {@link ISystemCodeCache}
   */
  List<CmsSystemCode> getAllSystemCodes();

}
