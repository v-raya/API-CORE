package gov.ca.cwds.rest.validation;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

@SuppressWarnings("serial")
public class TestSystemCodeCache implements SystemCodeCache {

  public TestSystemCodeCache() {
    register();
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    return null;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    return null;
  }

  @Override
  public SystemCode getSystemCode(Number systemCodeId) {
    if (1828 == systemCodeId.intValue()) {
      return new SystemCode(systemCodeId.shortValue(), null, null, null, "California", "CA", null,
          null, null);
    }

    return null;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(String metaId) {
    return null;
  }

  @Override
  public String getSystemCodeShortDescription(Number systemCodeId) {
    return null;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId,
      boolean checkCategoryIdValueIsZero) {
    if (456 == systemCodeId.intValue()) {
      return false;
    } else if (6404 == systemCodeId.intValue()) {
      return false;
    } else if (19 == systemCodeId.intValue()) {
      return true;
    }

    return true;
  }

  @Override
  public boolean verifyActiveLogicalIdForMeta(String systemCodeId, String metaId) {
    if ("524".equals(systemCodeId)) {
      return false;
    } else if ("4046".equals(systemCodeId)) {
      return false;
    } else if ("10".equals(systemCodeId)) {
      return true;
    } else if ("foo".equals(systemCodeId) || "bar".equals(systemCodeId)) {
      return false;
    }

    return true;
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    if ("djdjskshahfdsa".equals(shortDesc)) {
      return false;
    } else if ("".equals(shortDesc)) {
      return false;
    } else if ("Breasts".equals(shortDesc)) {
      return true;
    }

    return true;
  }

  @Override
  public SystemCodeDescriptor getSystemCodeDescriptor(Number systemCodeId) {
    // TODO Auto-generated method stub
    return null;
  }
}
