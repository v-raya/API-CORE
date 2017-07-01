package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Store generic type instance statically for easy retrieval.
 * 
 * @param <T> type to wrap
 * @author CWDS API Team
 */
public final class DeferredRegistry<T extends Serializable> implements Serializable {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Java loses type info on static generic members. Naughty Java!
   * 
   * <p>
   * A class's static variables and methods are shared among ALL instances. Therefore, it is
   * illogical to refer to type parameters of type declarations in a static methods or initializers,
   * or in declarations or initializers of static variables because they'd all refer to the same
   * type.
   * </p>
   */
  private static Map<Class<?>, DeferredRegistry<Serializable>> registry = new ConcurrentHashMap<>();

  private final T wrapped;
  private final Class<T> klass;

  private DeferredRegistry(Class<T> klass, T t) {
    this.wrapped = t;
    this.klass = klass;
  }

  /**
   * Register this instance.
   * 
   * @param t serializable type
   */
  @SuppressWarnings("unchecked")
  static final <T extends Serializable> void register(Class<T> klass, T t) {
    if (!registry.containsKey(klass)) {
      registry.put(klass, (DeferredRegistry<Serializable>) new DeferredRegistry<>(klass, t));
    }
  }

  /**
   * Unwrap the underlying of the given type.
   * 
   * @return the underlying
   */
  @SuppressWarnings("unchecked")
  static final <T extends Serializable> T unwrap(Class<?> klass) {
    return ((DeferredRegistry<T>) registry.get(klass)).wrapped;
  }

  @SuppressWarnings("javadoc")
  public Class<T> getKlass() {
    return klass;
  }

}
