package gov.ca.cwds.rest.validation;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

@SuppressWarnings("serial")
class TestSystemCodeCache implements SystemCodeCache {

  TestSystemCodeCache() {
    register();
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SystemCode getSystemCode(Short systemCodeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(String metaId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getSystemCodeShortDescription(Short systemCodeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Short systemCodeId, String metaId) {
    System.out.println("TestSystemCodeCache.verifyActiveSystemCodeIdForMeta -> systemCodeId: ["
        + systemCodeId + "]");
    if (456 == systemCodeId) {
      return false;
    } else if (6404 == systemCodeId) {
      return false;
    } else if (19 == systemCodeId) {
      return true;
    }

    return true;
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    System.out
        .println("TestSystemCodeCache.verifyActiveSystemCodeDescriptionForMeta -> shortDesc: ["
            + shortDesc + "]");
    if ("djdjskshahfdsa".equals(shortDesc)) {
      return false;
    } else if ("".equals(shortDesc)) {
      return false;
    } else if ("Breasts".equals(shortDesc)) {
      return true;
    }
    return true;
  }
}
