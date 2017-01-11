package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCache.CmsSystemCode;

public class CmsSystemCodeCacheTest {

  @Test
  public void type() throws Exception {
    assertThat(CmsSystemCodeCache.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsSystemCodeCache target = makeCache();
    assertThat(target, notNullValue());
  }

  @Test
  public void lookup_Args$int() throws Exception {
    CmsSystemCodeCache target = makeCache();
    // given
    int sysId = 297;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final CmsSystemCode actual = target.lookup(sysId);
    // then
    // e.g. : verify(mocked).called();
    assertTrue("CLNTRELC".equals(actual.getFksMetaT()));
  }

  @Test
  public void getCategory_Args$String() throws Exception {
    CmsSystemCodeCache target = makeCache();
    // given
    final String meta = "CLT_CONC";
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    List<CmsSystemCode> actual = target.getCategory(meta);
    // then
    // e.g. : verify(mocked).called();
    assertTrue(actual.size() > 0);
  }

  // @Test
  // public void produce_Args$File() throws Exception {
  // // given
  // File file = mock(File.class);
  // // e.g. : given(mocked.called()).willReturn(1);
  // // when
  // CmsSystemCodeCache actual = CmsSystemCodeCache.produce(file);
  // // then
  // // e.g. : verify(mocked).called();
  // CmsSystemCodeCache expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void produce_Args$() throws Exception {
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    CmsSystemCodeCache actual = CmsSystemCodeCache.produce();
    final CmsSystemCode code = actual.lookup(280);
    // then
    // e.g. : verify(mocked).called();
    assertTrue(code != null);
  }

  private static CmsSystemCodeCache makeCache() throws IOException {
    return CmsSystemCodeCache.produce();
  }

}
