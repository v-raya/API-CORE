package gov.ca.cwds.logging;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.function.Supplier;

import org.junit.Test;

public class ApiSupplierTest {

  private static final class TestOnlySupplier implements Supplier<String> {

    @Override
    public String get() {
      return "hello world";
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(ApiSupplier.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    Supplier<String> supplier = new TestOnlySupplier();
    ApiSupplier target = new ApiSupplier(supplier);
    assertThat(target, notNullValue());
  }

  @Test
  public void createContents_Args__() throws Exception {
    Supplier<String> supplier = new TestOnlySupplier();
    ApiSupplier target = new ApiSupplier(supplier);
    Object actual = target.createContents();
    assertThat(actual, notNullValue());
  }

}
