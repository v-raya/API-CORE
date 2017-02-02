package gov.ca.cwds.data;

import java.util.Date;
import java.util.List;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Interface with some common DAO functions
 * 
 * @author CWDS API Team
 * @param <T> persistence class
 */
public interface BaseDao<T extends PersistentObject> extends Dao {

  /**
   * @return List of T
   */
  List<T> findAll();

  /**
   * @param datetime base search date
   * @return List of T
   */
  List<T> findAllUpdatedAfter(Date datetime);
}
