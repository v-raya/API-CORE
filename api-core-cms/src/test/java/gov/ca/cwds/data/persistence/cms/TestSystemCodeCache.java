package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.std.ApiMarker;

public class TestSystemCodeCache implements ApiMarker {

  private ApiSystemCodeDao sysCodeDao;
  private ApiSystemCodeCache sysCodeCache;

  private static TestSystemCodeCache instance;

  private TestSystemCodeCache() {
    sysCodeDao = new SystemCodeDaoFileImpl();
    sysCodeCache = new CmsSystemCodeCacheService(sysCodeDao);
    sysCodeCache.register();

    // ElasticSearchPerson.setSystemCodes(sysCodeCache);
  }

  public static synchronized void init() {
    if (instance == null) {
      instance = new TestSystemCodeCache();
    }
  }

}
