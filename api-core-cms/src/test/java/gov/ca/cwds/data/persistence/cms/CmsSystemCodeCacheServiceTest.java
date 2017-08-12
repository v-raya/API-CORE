package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CmsSystemCodeCacheServiceTest {

  private static CmsSystemCodeCacheService makeCache() throws IOException {
    return new CmsSystemCodeCacheService(new SystemCodeDaoFileImpl());
  }

  private static BufferedReader makeBufferedReader() throws IOException {
    return new BufferedReader(new FileReader(new File(CmsSystemCodeCacheService.class
        .getResource("/" + CmsSystemCodeCacheService.class.getPackage().getName().replace('.', '/')
            + '/' + "test_system_codes.tsv")
        .getFile())));
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsSystemCodeCacheService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsSystemCodeCacheService target = makeCache();
    assertThat(target, notNullValue());
  }

  @Test
  public void lookup_Args$int() throws Exception {
    ApiSystemCodeCache target = makeCache();

    int sysId = 297;
    final CmsSystemCode actual = target.lookup(sysId);
    assertTrue("CLNTRELC".equals(actual.getFksMetaT()));
  }

  @Test
  public void getCategory_Args$String() throws Exception {
    ApiSystemCodeCache target = makeCache();

    final String meta = "CLT_CONC";
    List<CmsSystemCode> actual = target.getCategory(meta);
    assertTrue(actual.size() > 0);
  }

  // #137202471: Tech debt: Cobertura can't deal with Java 8 features.
  // Cobertura can't handle interface Iterable<CmsSystemCode>, which is required for
  // CoreMatchers.hasItem().
  // assertThat(cache, not(hasItem(cache.lookup(4))));

  // @Test
  public void testEquals_Args$Object() throws Exception {
    EqualsVerifier.forClass(CmsSystemCodeCacheService.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

}
