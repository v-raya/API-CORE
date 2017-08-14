package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.rest.api.ApiException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class VarargTupleTest {

  @Test
  public void type() throws Exception {
    assertThat(VarargTuple.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final Long[] values = {0L};
    VarargTuple<Long> target = new VarargTuple<>(values);
    assertThat(target, notNullValue());
  }

  @Test(expected = ApiException.class)
  public void toString_Args$_T$_null_arg() throws Exception {
    final Long[] values = null;
    VarargTuple<Long> target = new VarargTuple<>(values);
    final String actual = target.toString();
    final String expected = "tuple_{<null>}";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void toString_Args$() throws Exception {
    final Long[] values = {1L, 2L, 3L, 4L};
    VarargTuple<Long> target = new VarargTuple<>(values);
    final String actual = target.toString();
    final String expected = "tuple_{1_2_3_4}";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getColumns_Args$() throws Exception {
    VarargTuple<Long> target = new VarargTuple<>(1L);
    final List<Long> actual = target.getColumns();
    final List<Long> expected = Arrays.asList(new Long[] {1L});
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(VarargPrimaryKey.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void getPosition_Args$int() throws Exception {
    final String[] values = {"this", "is", "a", "test"};
    VarargTuple<String> target = new VarargTuple<>(values);
    int pos = 0;
    final String actual = target.getPosition(pos);
    final String expected = "this";
    assertThat(actual, is(equalTo(expected)));
  }

}
