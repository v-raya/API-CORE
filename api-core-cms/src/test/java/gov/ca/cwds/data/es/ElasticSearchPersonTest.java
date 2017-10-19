package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchPerson.ESColumn;
import gov.ca.cwds.data.es.ElasticSearchPerson.ESOptionalCollection;
import gov.ca.cwds.rest.validation.TestSystemCodeCache;

public class ElasticSearchPersonTest {

  private static final TestSystemCodeCache systemCodeCache = new TestSystemCodeCache();
  ElasticSearchPerson target = new ElasticSearchPerson();

  @Before
  public void setup() {
    target = new ElasticSearchPerson();
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void setOf_Args__Class__ObjectArray() throws Exception {
    target.clearOptionalCollections(ESOptionalCollection.NONE);
  }

  @Test
  public void clearOptionalCollections_Args__ObjectArray() throws Exception {
    target.clearOptionalCollections(ESOptionalCollection.REFERRAL);
  }

  @Test
  public void clearOptionalCollections_Args__() throws Exception {
    target.clearOptionalCollections();
  }

  // @Test
  // public void pullCol_Args__Map__Object() throws Exception {
  // Map<String, Object> m = new HashMap<String, Object>();
  // Object f = null;
  // Object actual = ElasticSearchPerson.pullCol(m, f);
  // Object expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void trim_Args__String() throws Exception {
    String s = null;
    String actual = ElasticSearchPerson.trim(s);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  // @Test
  // public void getSystemCodes_Args__() throws Exception {
  // gov.ca.cwds.rest.api.domain.cms.SystemCodeCache actual = ElasticSearchPerson.getSystemCodes();
  // assertThat(actual, notNullValue());
  // }

  @Test
  public void makeESPerson_Args__SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    ElasticSearchPerson actual = ElasticSearchPerson.makeESPerson(hit);
    assertThat(actual, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceType_Args__() throws Exception {
    String actual = target.getSourceType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceJson_Args__() throws Exception {
    String actual = target.getSourceJson();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceObj_Args__() throws Exception {
    Object actual = target.getSourceObj();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getGender_Args__() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGender_Args__String() throws Exception {
    String gender = null;
    target.setGender(gender);
  }

  @Test
  public void getDateOfBirth_Args__() throws Exception {
    String actual = target.getDateOfBirth();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDateOfBirth_Args__String() throws Exception {
    String dateOfBirth = null;
    target.setDateOfBirth(dateOfBirth);
  }

  @Test
  public void getSsn_Args__() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSsn_Args__String() throws Exception {
    String ssn = null;
    target.setSsn(ssn);
  }

  @Test
  public void getType_Args__() throws Exception {
    String actual = target.getType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setType_Args__String() throws Exception {
    String type = null;
    target.setType(type);
  }

  @Test
  public void getSensitivityIndicator_Args__() throws Exception {
    String actual = target.getSensitivityIndicator();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitivityIndicator_Args__String() throws Exception {
    // not sensitive:
    String sensitivityIndicator = null;
    target.setSensitivityIndicator(sensitivityIndicator);
    String actual = target.getSensitivityIndicator();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));

    // sensitive:
    sensitivityIndicator = "S";
    target.setSensitivityIndicator(sensitivityIndicator);
    actual = target.getSensitivityIndicator();
    expected = "S";
    assertThat(actual, is(equalTo(expected)));

    // sealed:
    sensitivityIndicator = "R";
    target.setSensitivityIndicator(sensitivityIndicator);
    actual = target.getSensitivityIndicator();
    expected = "R";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSource_Args__() throws Exception {
    String actual = target.getSource();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSource_Args__String() throws Exception {
    String source = null;
    target.setSource(source);
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setSourceType_Args__String() throws Exception {
    String sourceType = null;
    target.setSourceType(sourceType);
  }

  @Test
  public void setSourceJson_Args__String() throws Exception {
    String sourceJson = null;
    target.setSourceJson(sourceJson);
  }

  @Test
  public void setSourceObj_Args__Object() throws Exception {
    Object sourceObj = null;
    target.setSourceObj(sourceObj);
  }

  @Test
  public void getHighlightFields_Args__() throws Exception {
    String actual = target.getHighlightFields();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHighlightFields_Args__String() throws Exception {
    String highlightFields = null;
    target.setHighlightFields(highlightFields);
  }

  @Test
  public void toString_Args__() throws Exception {
    String actual = target.toString();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getHighlights_Args__() throws Exception {
    Map<String, String> actual = target.getHighlights();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setHighlights_Args__Map() throws Exception {
    Map<String, String> highlights = new HashMap<String, String>();
    target.setHighlights(highlights);
  }

  @Test
  public void getAddresses_Args__() throws Exception {
    List<ElasticSearchPersonAddress> actual = target.getAddresses();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAddresses_Args__List() throws Exception {
    List<ElasticSearchPersonAddress> addresses = new ArrayList<>();
    target.setAddresses(addresses);
  }

  @Test
  public void getPhones_Args__() throws Exception {
    List<ElasticSearchPersonPhone> actual = target.getPhones();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setPhones_Args__List() throws Exception {
    List<ElasticSearchPersonPhone> phones = new ArrayList<>();
    target.setPhones(phones);
  }

  @Test
  public void getLanguages_Args__() throws Exception {
    List<ElasticSearchPersonLanguage> actual = target.getLanguages();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLanguages_Args__List() throws Exception {
    List<ElasticSearchPersonLanguage> languages = new ArrayList<>();
    target.setLanguages(languages);
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_Args__String() throws Exception {
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setNameSuffix_Args__String() throws Exception {
    String nameSuffix = null;
    target.setNameSuffix(nameSuffix);
  }

  @Test
  public void getScreenings_Args__() throws Exception {
    List<ElasticSearchPersonScreening> actual = target.getScreenings();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setScreenings_Args__List() throws Exception {
    List<ElasticSearchPersonScreening> screenings = new ArrayList<>();
    target.setScreenings(screenings);
  }

  @Test
  public void isUpsert_Args__() throws Exception {
    boolean actual = target.isUpsert();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpsert_Args__boolean() throws Exception {
    boolean upsert = false;
    target.setUpsert(upsert);
  }

  @Test
  public void getReferrals_Args__() throws Exception {
    List<ElasticSearchPersonReferral> actual = target.getReferrals();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setReferrals_Args__List() throws Exception {
    List<ElasticSearchPersonReferral> referrals = new ArrayList<>();
    target.setReferrals(referrals);
  }

  @Test
  public void getRelationships_Args__() throws Exception {
    List<ElasticSearchPersonRelationship> actual = target.getRelationships();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setRelationships_Args__List() throws Exception {
    List<ElasticSearchPersonRelationship> relationships = new ArrayList<>();
    target.setRelationships(relationships);
  }

  @Test
  public void getCases_Args__() throws Exception {
    List<ElasticSearchPersonCase> actual = target.getCases();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setCases_Args__List() throws Exception {
    List<ElasticSearchPersonCase> cases = new ArrayList<>();
    target.setCases(cases);
  }

  @Test
  public void getLegacySourceTable_Args__() throws Exception {
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySourceTable_Args__String() throws Exception {
    String legacySourceTable = null;
    target.setLegacySourceTable(legacySourceTable);
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    Object actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__Object() throws Exception {
    ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void getAkas_Args__() throws Exception {
    List<ElasticSearchPersonAka> actual = target.getAkas();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAkas_Args__List() throws Exception {
    List<ElasticSearchPersonAka> akas = target.getAkas();
    target.setAkas(akas);
  }

  @Test
  public void pullCol_Args__Map__Object() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    ESColumn f = ESColumn.FIRST_NAME;
    Object actual = ElasticSearchPerson.pullCol(m, f);
    assertThat(actual, notNullValue());
  }

  @Test
  @Ignore
  public void hashCode_Args__() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
