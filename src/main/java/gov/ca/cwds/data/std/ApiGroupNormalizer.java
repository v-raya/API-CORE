package gov.ca.cwds.data.std;

import java.io.Serializable;
import java.util.Map;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Object can reduce a denormalized result set of its own type to a normalized, consolidated Map of
 * type R.
 * 
 * <p>
 * An example result set is a DB2 materialized query table.
 * </p>
 * 
 * @author CWDS API Team
 * @param <R> type to reduce to
 */
public interface ApiGroupNormalizer<R extends PersistentObject> extends Serializable {

  /**
   * Convenience method returns class type of R.
   * 
   * @return class R
   */
  Class<R> getReductionClass();

  /**
   * Reduce and consolidate to target reduction type, R.
   * 
   * @param map consolidated Map of reduced results on R's key.
   */
  void reduce(Map<Object, R> map);

  /**
   * Gets the group's identifier, such as CLIENT_T.IDENTIFIER.
   * 
   * <p>
   * On a normalized parent class, this is typically the primary key.
   * </p>
   * 
   * @return grouping key or identifier
   */
  Object getGroupKey();

}
