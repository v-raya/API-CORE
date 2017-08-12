package gov.ca.cwds.data.rules;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TriggerTableExceptionTest {

  @Test
  public void type() throws Exception {
    assertThat(TriggerTableException.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    TriggerTableException target = new TriggerTableException();
    assertThat(target, notNullValue());
  }

}
