package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

public class DeferredRegistry<T> implements Serializable {

  private static DeferredRegistry registry;

  private T wrapped;

  private DeferredRegistry(T t) {
    this.wrapped = t;
  }

  private static final synchronized <T> void makeRegistry(T t) {
    if (registry == null) {
      registry = new DeferredRegistry<T>(t);
    }
  }

  @SuppressWarnings("unchecked")
  public static final <T> DeferredRegistry<T> register(T t) {
    if (registry == null) {
      makeRegistry(t);
    }

    return registry;
  }

  @SuppressWarnings("unchecked")
  static final <T> T unwrap() {
    return ((DeferredRegistry<T>) registry.getDelegate()).getDelegate();
  }

  @SuppressWarnings("javadoc")
  public T getDelegate() {
    return wrapped;
  }

  @SuppressWarnings("javadoc")
  void setDelegate(T cache) {
    this.wrapped = cache;
  }

}
