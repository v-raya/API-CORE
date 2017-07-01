package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface for CMS system code cache facility to translate common CMS codes.
 * 
 * @param <T> type to wrap
 * @author CWDS API Team
 */
public class DeferredRegistry<T extends Serializable> implements Serializable {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Java loses type info on static generic members. Naughty Java!
   * 
   * <p>
   * Static variables and methods of a class are shared among ALL instances. That's why it's illegal
   * to refer to the type parameters of a type declaration in a static method or initializer, or in
   * the declaration or initializer of a static variable.
   * </p>
   */
  private static Map<Class<?>, DeferredRegistry<Serializable>> registry = new ConcurrentHashMap<>();

  private T wrapped;

  private DeferredRegistry(T t) {
    this.wrapped = t;
  }

  /**
   * Register this instance.
   * 
   * @param t serializable type
   */
  static final <T extends Serializable> void register(Class<?> klass, T t) {
    if (!registry.containsKey(klass)) {
      registry.put(klass, new DeferredRegistry<Serializable>(t));
    }
  }

  /**
   * @return the underlying
   */
  @SuppressWarnings("unchecked")
  static final <T extends Serializable> T unwrap(Class<?> klass) {
    return ((DeferredRegistry<T>) registry.get(klass)).getDelegate();
  }

  @SuppressWarnings("javadoc")
  protected T getDelegate() {
    return wrapped;
  }

}
