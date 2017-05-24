package gov.ca.cwds.data;

import java.util.Date;
import java.util.List;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Interface with some common DAO functions
 * 
 * @author CWDS API Team
 * @param <T> persistence class type
 */
public interface BaseDao<T extends PersistentObject> extends Dao {

  /**
   * Fetch <strong>ALL</strong> records from the source.
   * 
   * @return List of persistence type T
   */
  List<T> findAll();

  /**
   * Fetch all records, which have changed since the specified moment in time.
   * 
   * @param datetime base search date
   * @return List of persistence type T
   */
  List<T> findAllUpdatedAfter(Date datetime);
}
