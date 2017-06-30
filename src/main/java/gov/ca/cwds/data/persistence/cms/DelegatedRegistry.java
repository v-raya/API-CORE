package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

public final class DelegatedRegistry<T> implements Serializable {

  private static DelegatedRegistry registry;

  private T cache;

  private DelegatedRegistry(T t) {
    this.cache = t;
  }

  public T getCache() {
    return cache;
  }

  void setCache(T cache) {
    this.cache = cache;
  }

  @SuppressWarnings("unchecked")
  private static final synchronized <T> void makeRegistry(T t) {
    if (registry == null) {
      registry = new DelegatedRegistry<T>(t);
    }
  }

  @SuppressWarnings("unchecked")
  public static final <T> DelegatedRegistry<T> register(T t) {
    if (registry == null) {
      makeRegistry(t);
    }

    return registry;
  }

}
