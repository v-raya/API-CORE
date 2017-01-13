package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.rest.services.ServiceException;

public class CmsSystemCodeTest {

  @Test
  public void type() throws Exception {
    assertThat(CmsSystemCode.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    assertThat(target, notNullValue());
  }

  @Test(expected = ServiceException.class)
  public void produce_Args$String$String() throws Exception {
    String line = null;
    String delim = null;
    CmsSystemCode actual = CmsSystemCode.produce(line, delim);
    CmsSystemCode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void produce_Args$String_tab_delim_good() throws Exception {
    final String line = "4\tABS_BPTC\tArm\t25\tN\t \t \t ";
    final CmsSystemCode actual = CmsSystemCode.produce(line);
    final CmsSystemCode expected = new CmsSystemCode(4, "ABS_BPTC", "Arm", "25", "", "", "N", "");
    assertThat(actual.getFksMetaT(), is(equalTo(expected.getFksMetaT())));
  }

  @Test
  public void getSysId_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    int actual = target.getSysId();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFksMetaT_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    String actual = target.getFksMetaT();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getShortDsc_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    String actual = target.getShortDsc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLgcId_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    String actual = target.getLgcId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getInactvInd_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    String actual = target.getInactvInd();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCategoryId_Args$() throws Exception {
    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    String actual = target.getCategoryId();
    // then
    // e.g. : verify(mocked).called();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOtherCd_Args$() throws Exception {

    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getOtherCd();
    // then
    // e.g. : verify(mocked).called();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLongDsc_Args$() throws Exception {

    int sysId = 0;
    String fksMetaT = null;
    String shortDsc = null;
    String lgcId = null;
    String inactvInd = null;
    String categoryId = null;
    String otherCd = null;
    String longDsc = null;
    CmsSystemCode target = new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd,
        categoryId, otherCd, longDsc);
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getLongDsc();
    // then
    // e.g. : verify(mocked).called();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
