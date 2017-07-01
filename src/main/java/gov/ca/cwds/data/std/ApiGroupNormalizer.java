package gov.ca.cwds.data.std;

import java.util.Map;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Object can reduce a denormalized result set of its own type to a normalized, consolidated Map of
 * type N.
 * 
 * <p>
 * An example result set is a DB2 materialized query table.
 * </p>
 * 
 * @author CWDS API Team
 * @param <N> type to reduce to
 */
public interface ApiGroupNormalizer<N extends PersistentObject> extends ApiMarker {

  /**
   * Convenience method returns class type of N.
   * 
   * @return class N
   */
  Class<N> getNormalizationClass();

  /**
   * Reduce and consolidate to target reduction type, N.
   * 
   * @param map consolidated Map of reduced results on N's key.
   * @return the normalized object
   */
  N normalize(Map<Object, N> map);

  /**
   * Gets the group's unique identifier (aka., "key"), such as CLIENT_T.IDENTIFIER.
   * 
   * <p>
   * On a normalized parent class, this is typically the parent primary key.
   * </p>
   * 
   * @return grouping key or identifier
   */
  Object getNormalizationGroupKey();

}
