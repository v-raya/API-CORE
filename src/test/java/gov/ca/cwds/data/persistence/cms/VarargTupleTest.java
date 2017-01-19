package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class VarargTupleTest {

  @Test
  public void type() throws Exception {
    assertThat(VarargTuple.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    Object[] values = null;
    VarargTuple target = new VarargTuple(values);
    assertThat(target, notNullValue());
  }

  @Test
  public void toString_Args$() throws Exception {
    Object[] values = null;
    VarargTuple target = new VarargTuple(values);
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.toString();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "tuple_{<null>}";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getColumns_Args$() throws Exception {
    VarargTuple<Long> target = new VarargTuple<>(1L);
    final Long[] actual = target.getColumns();
    final Long[] expected = {1L};
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(VarargPrimaryKey.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void getPosition_Args$int() throws Exception {
    Object[] values = null;
    VarargTuple target = new VarargTuple(values);
    int pos = 0;
    Object actual = target.getPosition(pos);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
