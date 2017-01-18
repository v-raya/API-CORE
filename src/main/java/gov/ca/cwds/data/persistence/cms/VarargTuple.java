package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Generic class represents a tuple of any number of columns of the same serializable type.
 * 
 * @author CWDS API Team
 * @param <T> tuple type
 */
public class VarargTuple<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private final T[] columns;

  /**
   * Hide the default constructor.
   */
  // @SuppressWarnings("unused")
  // private VarargTuple() {
  // T t;
  // this.columns = (T[]) Array.newInstance(new T.getClass(), 0);
  // // this.columns = new T[0];
  // }

  /**
   * @param values any number of T keys
   */
  public VarargTuple(T... values) {
    ArrayUtils.toArray(values);
    this.columns = (T[]) Arrays.asList(values).toArray();
    int cntr = -1;
    for (T v : values) {
      this.columns[++cntr] = v;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "varKey_" + ArrayUtils.toString(this.columns, "<null>").replace(',', '_');
  }

  /**
   * 
   * @return T array of key columns
   */
  public T[] getColumns() {
    return columns;
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
