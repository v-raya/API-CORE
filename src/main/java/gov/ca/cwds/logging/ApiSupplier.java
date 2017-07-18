package gov.ca.cwds.logging;

import java.util.function.Supplier;

/**
 * Supplier wrapper for generic use.
 * 
 * @author CWDS API Team
 *
 * @param <T> type to supply
 */
public class ApiSupplier<T> {

  private Supplier<T> supplier;

  public ApiSupplier(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  T createContents() {
    return supplier.get();
  }

}
