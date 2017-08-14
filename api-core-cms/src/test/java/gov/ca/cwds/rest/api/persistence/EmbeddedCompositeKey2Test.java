package gov.ca.cwds.rest.api.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.data.persistence.EmbeddableCompositeKey2;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EmbeddedCompositeKey2Test {

  private String arg1 = "argument1";
  private String arg2 = "argument2";

  @Test
  public void constructorTest() throws Exception {
    EmbeddableCompositeKey2 vpk = new EmbeddableCompositeKey2(arg1, arg2);
    assertThat(vpk, is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(EmbeddableCompositeKey2.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void toStringTest() throws Exception {
    EmbeddableCompositeKey2 vpk = new EmbeddableCompositeKey2(arg1, arg2);
    assertThat(vpk.toString(), is(equalTo("embed_key2__{argument1_argument2}")));
  }

  @Test
  public void type() throws Exception {
    assertThat(EmbeddableCompositeKey2.class, notNullValue());
  }

  @Test
  public void testGetColumns() throws Exception {
    EmbeddableCompositeKey2 target = new EmbeddableCompositeKey2("one", "two");
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
  }

}
