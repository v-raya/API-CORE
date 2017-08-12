package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class ApiSystemCodeCacheTest {

  public static final class TestSysCodeCache implements ApiSystemCodeCache {

    @Override
    public CmsSystemCode lookup(Integer sysId) {
      return null;
    }

    @Override
    public List<CmsSystemCode> getCategory(String meta) {
      return null;
    }

    @Override
    public CmsSystemCode lookupByCategoryAndShortDescription(String meta, String shortDesc) {
      return null;
    }

    @Override
    public boolean verifyCategoryAndSysCodeId(String meta, Integer sysId) {
      return false;
    }

    @Override
    public boolean verifyCategoryAndSysCodeShortDescription(String meta, String shortDesc) {
      return false;
    }

    @Override
    public String getCodeShortDescription(Integer codeId) {
      return null;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(ApiSystemCodeCache.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ApiSystemCodeCache target = new TestSysCodeCache();
    assertThat(target, notNullValue());
  }

  @Test
  public void register_Args__() throws Exception {
    ApiSystemCodeCache target = new TestSysCodeCache();
    target.register();
  }

  @Test
  public void global_Args__() throws Exception {
    ApiSystemCodeCache target = new TestSysCodeCache();
    target.register();

    ApiSystemCodeCache actual = ApiSystemCodeCache.global();
    assertThat(actual, is(notNullValue()));
  }

}
