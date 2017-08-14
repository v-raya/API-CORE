package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Generic class represents a tuple of any number of columns of the same serializable type.
 * 
 * @author CWDS API Team
 * @param <T> tuple type
 */
public class VarargTuple<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  private final T[] columns;

  /**
   * Construct from variable arguments of type T.
   * 
   * @param values any number of T keys
   */
  @SafeVarargs
  public VarargTuple(T... values) {
    if (values == null || values.length == 0) {
      throw new ApiException("Column list cannot be null");
    }
    this.columns = ArrayUtils.toArray(values);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "tuple_" + ArrayUtils.toString(this.columns, "<null>").replace(',', '_');
  }

  /**
   * Get an unmodifiable list of columns.
   * 
   * @return T List of tuple columns
   */
  public List<T> getColumns() {
    return Collections.unmodifiableList(Arrays.asList(this.columns));
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  /**
   * Returns a column value by position.
   * 
   * @param pos zero-based column position
   * @return value in specified column
   */
  public final T getPosition(int pos) {
    return this.columns.length > pos ? this.columns[pos] : null;
  }

}
