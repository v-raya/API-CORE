package gov.ca.cwds.rest.api.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.data.persistence.EmbeddablePrimaryKey;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EmbeddedPrimaryKeyTest {

  private String argument1 = "argument1";
  private String argument2 = "argument2";
  private String argument3 = "argument3";

  @Test
  public void constructorTest() throws Exception {
    EmbeddablePrimaryKey vpk = new EmbeddablePrimaryKey(argument1, argument2, argument3);
    assertThat(vpk, is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(EmbeddablePrimaryKey.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void toStringTest() throws Exception {
    EmbeddablePrimaryKey vpk = new EmbeddablePrimaryKey(argument1, argument2, argument3);
    assertThat(vpk.toString(), is(equalTo("embed_key__{argument1_argument2_argument3}")));
  }

  @Test
  public void type() throws Exception {
    assertThat(EmbeddablePrimaryKey.class, notNullValue());
  }

  @Test
  public void testGetColumns() throws Exception {
    EmbeddablePrimaryKey target = new EmbeddablePrimaryKey("one", "two");
    {
      final String actual = target.getId1();
      final String expected = "one";
      assertThat(actual, is(equalTo(expected)));
    }
  }

}
