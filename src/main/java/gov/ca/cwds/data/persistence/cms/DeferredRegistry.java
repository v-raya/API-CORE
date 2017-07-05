package gov.ca.cwds.data.persistence.cms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Store generic type instance statically for easy retrieval.
 * 
 * @param <T> type to wrap
 * @author CWDS API Team
 */
public final class DeferredRegistry<T extends ApiMarker> implements ApiMarker {

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
  private static Map<Class<?>, DeferredRegistry<ApiMarker>> registry = new ConcurrentHashMap<>();

  private final T wrapped;
  private final Class<T> klass;

  private DeferredRegistry(Class<T> klass, T t) {
    this.wrapped = t;
    this.klass = klass;
  }

  /**
   * Register this instance. Allows overwrite of already registered instance.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @param t serializable type
   * @param overwrite overwrite if already registered
   */
  @SuppressWarnings("unchecked")
  public static final <T extends ApiMarker> void register(Class<T> klass, T t, boolean overwrite) {
    if (overwrite || !registry.containsKey(klass)) {
      registry.put(klass, (DeferredRegistry<ApiMarker>) new DeferredRegistry<>(klass, t));
    }
  }

  /**
   * Register this instance.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @param t serializable type
   */
  public static final <T extends ApiMarker> void register(Class<T> klass, T t) {
    register(klass, t, false);
  }

  /**
   * Unwrap the underlying of the given type.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @return the underlying
   */
  @SuppressWarnings("unchecked")
  public static final <T extends ApiMarker> T unwrap(Class<?> klass) {
    return ((DeferredRegistry<T>) registry.get(klass)).wrapped;
  }

  @SuppressWarnings("javadoc")
  public Class<T> getKlass() {
    return klass;
  }

}
