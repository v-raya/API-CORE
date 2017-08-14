package gov.ca.cwds.data.rules;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TriggerTablesDaoTest {

  @Test
  public void type() throws Exception {
    assertThat(TriggerTablesDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String laCountySpecificCode = null;
    TriggerTablesDao target = new TriggerTablesDao(laCountySpecificCode);
    assertThat(target, notNullValue());
  }

  @Test
  public void getLaCountySpecificCode_Args__() throws Exception {
    String laCountySpecificCode = null;
    TriggerTablesDao target = new TriggerTablesDao(laCountySpecificCode);
    String actual = target.getLaCountySpecificCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}

