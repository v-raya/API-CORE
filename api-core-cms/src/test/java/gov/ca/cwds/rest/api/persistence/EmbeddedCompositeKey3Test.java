package gov.ca.cwds.rest.api.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.data.persistence.EmbeddableCompositeKey3;

public class EmbeddedCompositeKey3Test {

  private String arg1 = "argument1";
  private String arg2 = "argument2";
  private String arg3 = "argument3";

  @Test
  public void constructorTest() throws Exception {
    EmbeddableCompositeKey3 vpk = new EmbeddableCompositeKey3(arg1, arg2, arg3);
    assertThat(vpk, is(notNullValue()));
  }

  // @Test
  // public void equalsHashCodeWork() {
  // EqualsVerifier.forClass(EmbeddableCompositeKey3.class).suppress(Warning.NONFINAL_FIELDS,
  // Warning.ALL_FIELDS_SHOULD_BE_USED, Warning.INHERITED_DIRECTLY_FROM_OBJECT).verify();
  // }

  @Test
  public void toStringTest() throws Exception {
    EmbeddableCompositeKey3 vpk = new EmbeddableCompositeKey3(arg1, arg2, arg3);
    assertThat(vpk.toString(), is(equalTo("embed_key3__{argument1_argument2_argument3}")));
  }

  @Test
  public void type() throws Exception {
    assertThat(EmbeddableCompositeKey3.class, notNullValue());
  }

  @Test
  public void testGetColumns() throws Exception {
    EmbeddableCompositeKey3 target = new EmbeddableCompositeKey3("one", "two", "three");
    {
      final String actual = target.getId1();
      final String expected = "one";
      assertThat(actual, is(equalTo(expected)));
    }

    {
      final String actual = target.getId2();
      final String expected = "two";
      assertThat(actual, is(equalTo(expected)));
    }

    {
      final String actual = target.getId3();
      final String expected = "three";
      assertThat(actual, is(equalTo(expected)));
    }
  }

}
