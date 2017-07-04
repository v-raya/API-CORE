package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import io.dropwizard.jackson.Jackson;

public class CachingSystemCodeServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private SystemMetaListResponse systemMetaListResponse;
  private SystemCodeListResponse systemCodeListResponse;
  private SystemCodeDao systemCodeDao;
  private SystemMetaDao systemMetaDao;
  private CachingSystemCodeService cachingSystemCodeService;

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);

    systemMetaListResponse = MAPPER.readValue(
        fixture("fixtures/rest/services/cms/CachingSystemCodeService/test-syetm-codes-meta.json"),
        SystemMetaListResponse.class);
    gov.ca.cwds.data.persistence.cms.SystemMeta[] persistenceSystemMetas =
        createPersistenceSystemMetas(systemMetaListResponse);

    systemCodeListResponse = MAPPER.readValue(
        fixture("fixtures/rest/services/cms/CachingSystemCodeService/test-system-codes.json"),
        SystemCodeListResponse.class);
    gov.ca.cwds.data.persistence.cms.SystemCode[] persistenceSystemCodes =
        createPersistenceSystemCodes(systemCodeListResponse);

    when(systemMetaDao.findAll()).thenReturn(persistenceSystemMetas);
    when(systemCodeDao.findByForeignKeyMetaTable("ADDR_TPC")).thenReturn(persistenceSystemCodes);
    when(systemCodeDao.findBySystemCodeId((short) 27))
        .thenReturn(getPersistenceSystemCode(systemCodeListResponse, (short) 27));

    cachingSystemCodeService = new CachingSystemCodeService(systemCodeDao, systemMetaDao, 1);
  }

  @Test
  public void testAllSystemMetas() throws Exception {
    SystemMetaListResponse actualSystemMetaListResponse =
        (SystemMetaListResponse) cachingSystemCodeService.find(null);
    Assert.assertNotNull(actualSystemMetaListResponse);
    Assert.assertEquals(systemMetaListResponse, actualSystemMetaListResponse);

    Set<SystemMeta> actualMetas = cachingSystemCodeService.getSystemMetas();
    Assert.assertNotNull(actualMetas);
    Assert.assertEquals(systemMetaListResponse.getSystemMetas(), actualMetas);
  }

  @Test
  public void testSystemCodesForMeta() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("ADDR_TPC");
    Assert.assertNotNull(actualSystemCodeListResponse);
    Assert.assertEquals(systemCodeListResponse, actualSystemCodeListResponse);

    Set<SystemCode> actualCodes = cachingSystemCodeService.getSystemCodesForMeta("ADDR_TPC");
    Assert.assertNotNull(actualCodes);
    Assert.assertEquals(systemCodeListResponse.getSystemCodes(), actualCodes);
  }

  @Test
  public void testNotExtistingSystemMeta() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("hjgjgjgjgjhgj");
    Assert.assertNull(actualSystemCodeListResponse);
  }

  @Test
  public void testSystemCode() throws Exception {
    SystemCode expectedSystemCode = new SystemCode((short) 27, (short) 0, "N", "", "Business",
        "0001", "0000000000", "ADDR_TPC", "");
    SystemCode actualSystemCode = cachingSystemCodeService.getSystemCode((short) 27);
    Assert.assertNotNull(actualSystemCode);
    Assert.assertEquals(expectedSystemCode, actualSystemCode);
  }

  @Test
  public void testCacheReLoading() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("ADDR_TPC");
    Assert.assertNotNull(actualSystemCodeListResponse);
    Assert.assertEquals(systemCodeListResponse, actualSystemCodeListResponse);

    /**
     * Wait to invalidate cache.
     */
    Thread.currentThread().sleep(2000);

    SystemCode expectedSystemCode = new SystemCode((short) 27, (short) 0, "N", "", "Business",
        "0001", "0000000000", "ADDR_TPC", "");
    SystemCode actualSystemCode = cachingSystemCodeService.getSystemCode((short) 27);
    Assert.assertNotNull(actualSystemCode);
    Assert.assertEquals(expectedSystemCode, actualSystemCode);

    /**
     * Wait to invalidate cache.
     */
    Thread.currentThread().sleep(2000);

    Set<SystemCode> systemCodesForMeta = cachingSystemCodeService.getSystemCodesForMeta("ADDR_TPC");
    Assert.assertNotNull(systemCodesForMeta);
    Assert.assertEquals(9, systemCodesForMeta.size());

  }

  private gov.ca.cwds.data.persistence.cms.SystemMeta[] createPersistenceSystemMetas(
      SystemMetaListResponse systemMetaListResponse) {
    Set<SystemMeta> systemMetas = systemMetaListResponse.getSystemMetas();
    Set<gov.ca.cwds.data.persistence.cms.SystemMeta> persistenceSystemMetas = new HashSet<>();

    for (SystemMeta syetmMeta : systemMetas) {
      persistenceSystemMetas.add(syetmMeta.createPersistenceSystemMeta());
    }

    return persistenceSystemMetas.toArray(new gov.ca.cwds.data.persistence.cms.SystemMeta[0]);
  }

  private gov.ca.cwds.data.persistence.cms.SystemCode[] createPersistenceSystemCodes(
      SystemCodeListResponse systemCodeListResponse) {

    Set<SystemCode> systemCodes = systemCodeListResponse.getSystemCodes();
    Set<gov.ca.cwds.data.persistence.cms.SystemCode> persistenceSystemCodes = new HashSet<>();

    for (SystemCode systemCode : systemCodes) {
      persistenceSystemCodes.add(systemCode.createPersistenceSystemCode());
    }

    return persistenceSystemCodes.toArray(new gov.ca.cwds.data.persistence.cms.SystemCode[0]);
  }

  private gov.ca.cwds.data.persistence.cms.SystemCode getPersistenceSystemCode(
      SystemCodeListResponse systemCodeListResponse, Short systemCodeId) {

    Set<SystemCode> systemCodes = systemCodeListResponse.getSystemCodes();
    gov.ca.cwds.data.persistence.cms.SystemCode persistenceSystemCode = null;

    for (SystemCode systemCode : systemCodes) {
      if (systemCodeId.equals(systemCode.getSystemId())) {
        persistenceSystemCode = systemCode.createPersistenceSystemCode();
        break;
      }
    }

    return persistenceSystemCode;
  }
}
