package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DaoExceptionTest {

  @Test
  public void type() throws Exception {
    assertThat(DaoException.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    DaoException target = new DaoException();
    assertThat(target, notNullValue());
  }

}
