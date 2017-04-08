package gov.ca.cwds.data.std;

import java.io.Serializable;
import java.util.Map;

/**
 * Capable of reducing itself from a denormalized result set to a normalized consolidated Set of
 * type R.
 * 
 * <p>
 * An example result set is a DB2 materialized query table.
 * </p>
 * 
 * @author CWDS API Team
 * @param <R> type to reduce to
 */
public interface ApiReduce<R> extends Serializable {

  /**
   * Reduce and consolidate to target reduction type, R.
   * 
   * @param map consolidated Map of reduced results on R's key.
   */
  void reduce(Map<Object, R> map);

}
