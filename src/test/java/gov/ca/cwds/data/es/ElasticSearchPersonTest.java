package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.junit.Assert;
import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchPerson.ESColumn;

/**
 * NOTE: cannot define all test cases for CMS persistence classes found in CWDS API because they are
 * not available in api-core.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String id = null;
    String firstName = null;
    String lastName = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    String sourceType = null;
    String sourceJson = null;
    String highlight = null;
    ElasticSearchPerson target = new ElasticSearchPerson(id, firstName, lastName, gender, birthDate,
        ssn, sourceType, sourceJson, highlight);
    assertThat(target, notNullValue());
  }

  @Test
  public void pullCol_Args$Map$Object() throws Exception {
    // given
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "fred";
    m.put(ESColumn.FIRST_NAME.getCol(), value);

    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void pullCol_Args$Map$Object_null_value() throws Exception {
    // given
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = null;
    m.put(ESColumn.FIRST_NAME.getCol(), value);

    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void pullCol_Args$Map$Object_unknown_key() throws Exception {
    // given
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "something";
    m.put("who knows", value);

    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Assert.assertTrue("unknown column in map",
        actual instanceof String && StringUtils.isEmpty((String) actual));
  }

  @Test
  public void trim_Args$String() throws Exception {
    // given
    String s = null;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = ElasticSearchPerson.trim(s);
    // then
    // e.g. : verify(mocked).called();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void makeESPerson_Args$SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    Map<String, Object> m = new HashMap<>();
    m.put(ESColumn.FIRST_NAME.getCol(), "john");
    when(hit.getSource()).thenReturn(m);

    ElasticSearchPerson actual = ElasticSearchPerson.makeESPerson(hit);
    ElasticSearchPerson expected =
        new ElasticSearchPerson("", "john", "", "", "", "", null, null, "");
    assertThat(actual, is(equalTo(expected)));
  }

}
