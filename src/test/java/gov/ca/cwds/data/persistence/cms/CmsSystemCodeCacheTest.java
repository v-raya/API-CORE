package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.rest.services.ServiceException;

public class CmsSystemCodeCacheTest {

  private static CmsSystemCodeCache makeCache() throws IOException {
    return CmsSystemCodeCache.produce();
  }

  private static BufferedReader makeBufferedReader() throws IOException {
    return new BufferedReader(new FileReader(new File(CmsSystemCodeCache.class
        .getResource("/" + CmsSystemCodeCache.class.getPackage().getName().replace('.', '/') + '/'
            + "test_system_codes.tsv")
        .getFile())));
  }

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
    final CmsSystemCodeCache actual = CmsSystemCodeCache.produce();
    final CmsSystemCode code = actual.lookup(280);
    // then
    // e.g. : verify(mocked).called();
    assertTrue(code != null);
  }

  @Test
  public void produce_Args$BufferedReader() throws Exception {
    BufferedReader reader = mock(BufferedReader.class);
    final CmsSystemCodeCache actual = CmsSystemCodeCache.produce(reader);
    // assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void produce_Args$BufferedReader_matches() throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(new File(CmsSystemCodeCache.class
        .getResource("/" + CmsSystemCodeCache.class.getPackage().getName().replace('.', '/') + '/'
            + "test_system_codes.tsv")
        .getFile())));
    final CmsSystemCodeCache cache = CmsSystemCodeCache.produce(reader);
    assertThat(cache, hasItem(cache.lookup(4)));
  }

  @Test
  public void produce_Args$BufferedReader_empty() throws Exception {
    BufferedReader reader = mock(BufferedReader.class);
    final CmsSystemCodeCache cache = CmsSystemCodeCache.produce(reader);
    assertThat(cache, not(hasItem(cache.lookup(4))));
  }

  @Test(expected = ServiceException.class)
  public void produce_Args$File_T$NPE() throws Exception {
    File file = mock(File.class);
    final CmsSystemCodeCache actual = CmsSystemCodeCache.produce(file);
    CmsSystemCodeCache expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
