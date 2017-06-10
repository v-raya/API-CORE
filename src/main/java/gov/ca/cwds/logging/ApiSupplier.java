package gov.ca.cwds.logging;

import java.util.function.Supplier;

public class ApiSupplier<T> {

  private Supplier<T> supplier;

  public ApiSupplier(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  T createContents() {
    return supplier.get();
  }

}
