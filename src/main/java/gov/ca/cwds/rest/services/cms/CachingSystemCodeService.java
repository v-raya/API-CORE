package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;

public class CachingSystemCodeService extends SystemCodeService implements SystemCodeCache {

  private static final long serialVersionUID = 1468150983558929580L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CachingSystemCodeService.class);

  /**
   * Seconds after which cache entries will be invalidated for refresh. Default is 15 days.
   */
  private static final long DEFAULT_SECONDS_TO_REFRESH_CACHE = 15 * 24 * 60 * 60;

  /**
   * System codes cache.
   */
  private LoadingCache<CacheKey, Object> systemCodeCache;

  /**
   * Construct the object
   * 
   * @param systemCodeDao System codes DAO
   * @param systemMetaDao System meta DAO
   */
  @Inject
  public CachingSystemCodeService(SystemCodeDao systemCodeDao, SystemMetaDao systemMetaDao) {
    this(systemCodeDao, systemMetaDao, DEFAULT_SECONDS_TO_REFRESH_CACHE);
  }

  /**
   * Construct the object
   * 
   * @param systemCodeDao System codes DAO
   * @param systemMetaDao System meta DAO
   * @param secondsToRefreshCache Seconds after which cache entries will be invalidated for refresh.
   */
  public CachingSystemCodeService(SystemCodeDao systemCodeDao, SystemMetaDao systemMetaDao,
      long secondsToRefreshCache) {
    super(systemCodeDao, systemMetaDao);
    // register();

    SystemCodeCacheLoader cacheLoader = new SystemCodeCacheLoader(this);
    systemCodeCache = CacheBuilder.newBuilder()
        .refreshAfterWrite(secondsToRefreshCache, TimeUnit.SECONDS).build(cacheLoader);

    /**
     * Pre-load cache
     */
    // try {
    // Map<CacheKey, Object> systemCodes = cacheLoader.loadAll();
    // systemCodeCache.putAll(systemCodes);
    // } catch (Exception e) {
    // LOGGER.error("Error loading system codes", e);
    // throw new ServiceException(e);
    // }
  }

  @Override
  public Response find(Serializable key) {
    Response response = null;

    if (key == null || StringUtils.isBlank(key.toString())) {
      response = (Response) getFromCache(CacheKey.createForAllMetas());

      // } else if ("ALL_SYTEM_CODES".equals(key.toString().toUpperCase())) {
      // Set<SystemCode> systemCodes = getAllSystemCodes();
      // response = new SystemCodeListResponse(systemCodes);

    } else {
      response = (Response) getFromCache(CacheKey.createForMeta(key.toString()));
    }

    return response;
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    Set<SystemMeta> systemMetas = new HashSet<>();
    CacheKey cacheKey = CacheKey.createForAllMetas();
    SystemMetaListResponse systemMetaListResponse = (SystemMetaListResponse) getFromCache(cacheKey);
    if (systemMetaListResponse != null) {
      systemMetas = systemMetaListResponse.getSystemMetas();
    }
    return systemMetas;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    Set<SystemCode> systemCodes = new HashSet<>();
    Set<SystemMeta> systemMetas = getAllSystemMetas();
    if (systemMetas != null) {
      for (SystemMeta systemMeta : systemMetas) {
        Set<SystemCode> systemCodesForMeta =
            getSystemCodesForMeta(systemMeta.getLogicalTableDsdName());
        systemCodes.addAll(systemCodesForMeta);
      }
    }
    return systemCodes;
  }

  @Override
  public SystemCode getSystemCode(final Number systemCodeId) {
    if (systemCodeId == null) {
      return null;
    }

    CacheKey cacheKey = CacheKey.createForSystemCode(systemCodeId);
    SystemCode systemCode = (SystemCode) getFromCache(cacheKey);
    return systemCode;
  }

  @Override
  public String getSystemCodeShortDescription(final Number systemCodeId) {
    String shortDescription = null;
    SystemCode systemCode = getSystemCode(systemCodeId);
    if (systemCode != null) {
      shortDescription = systemCode.getShortDescription();
    }
    return shortDescription;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(final String metaId) {
    Set<SystemCode> systemCodes = new HashSet<>();

    if (!StringUtils.isBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      SystemCodeListResponse systemCodeListResponse =
          (SystemCodeListResponse) getFromCache(cacheKey);
      if (systemCodeListResponse != null) {
        systemCodes = systemCodeListResponse.getSystemCodes();
      }
    }

    return systemCodes;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId) {
    boolean valid = false;
    Set<SystemCode> systemCodes = getSystemCodesForMeta(metaId);
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        if (systemCodeId.equals(systemCode.getSystemId())) {
          valid = "N".equals(systemCode.getInactiveIndicator().toUpperCase());
        }
      }
    }

    return valid;
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    boolean valid = false;
    Set<SystemCode> systemCodes = getSystemCodesForMeta(metaId);
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        if (StringUtils.equalsIgnoreCase(StringUtils.trim(shortDesc),
            StringUtils.trim(systemCode.getShortDescription()))) {
          valid = "N".equals(systemCode.getInactiveIndicator().toUpperCase());
        }
      }
    }

    return valid;
  }

  /**
   * Get cached object identified by given cache key.
   * 
   * @param key Cache key
   * @return Cached object if found, otherwise null.
   */
  private Object getFromCache(CacheKey cacheEntryKey) {
    Object obj = null;
    try {
      obj = systemCodeCache.get(cacheEntryKey);
    } catch (Exception e) {
      LOGGER.warn("getFromCache -> Unable to load object for key: " + cacheEntryKey);
    }

    return obj;
  }

  /**
   * =============================================================================== <br>
   * Cache loader for system metas and system codes. <br>
   * ===============================================================================
   */
  private static class SystemCodeCacheLoader extends CacheLoader<CacheKey, Object> {

    private SystemCodeService systemCodeService;

    /**
     * Construct the object
     * 
     * @param systemCodeService
     */
    SystemCodeCacheLoader(SystemCodeService systemCodeService) {
      this.systemCodeService = systemCodeService;
    }

    @Override
    public Object load(CacheKey key) throws Exception {
      Object objectToCache = null;

      if (CacheKey.ALL_METAS_TYPE.equals(key.getType())) {
        /**
         * Load all system metas
         */
        SystemMetaListResponse metaList =
            (SystemMetaListResponse) systemCodeService.loadSystemMetas();
        objectToCache = metaList;

      } else if (CacheKey.META_ID_TYPE.equals(key.getType())) {
        /**
         * Add SystemCodeListResponse objects keyed by Meta ID.
         */
        SystemCodeListResponse sysCodeList =
            (SystemCodeListResponse) systemCodeService.loadSystemCodesForMeta(key.getValue());
        objectToCache = sysCodeList;

      } else if (CacheKey.SYSTEM_CODE_ID_TYPE.equals(key.getType())) {
        /**
         * Add SystemCode objects keyed by System Code ID.
         */
        SystemCode systemCode = (SystemCode) systemCodeService.loadSystemCode(key.getValue());
        objectToCache = systemCode;
      }

      return objectToCache;
    }

    /**
     * Loads all system code cache entries.
     * 
     * @return All system code cache entries.
     * @throws Exception
     */
    public Map<CacheKey, Object> loadAll() throws Exception {
      LOGGER.info("Loading all system code cache...");
      Map<CacheKey, Object> systemCodeMap = new HashMap<>();

      /**
       * Load all system metas and add SystemMetaListResponse object to cache.
       */
      CacheKey cacheKey = CacheKey.createForAllMetas();
      SystemMetaListResponse metaList = (SystemMetaListResponse) load(cacheKey);
      Set<SystemMeta> systemMetaSet = new HashSet<>();
      if (metaList != null) {
        systemCodeMap.put(cacheKey, metaList);
        systemMetaSet = metaList.getSystemMetas();
      }

      for (SystemMeta sysMeta : systemMetaSet) {
        String metaId = sysMeta.getLogicalTableDsdName();

        /**
         * Add SystemCodeListResponse objects keyed by Meta ID.
         */
        cacheKey = CacheKey.createForMeta(metaId);
        SystemCodeListResponse sysCodeList = (SystemCodeListResponse) load(cacheKey);
        Set<SystemCode> sysCodes = new HashSet<>();
        if (sysCodeList != null) {
          systemCodeMap.put(cacheKey, sysCodeList);
          sysCodes = sysCodeList.getSystemCodes();
        }

        /**
         * Add SystemCode objects keyed by System Code ID.
         */
        for (SystemCode sysCode : sysCodes) {
          cacheKey = CacheKey.createForSystemCode(sysCode.getSystemId());
          systemCodeMap.put(cacheKey, sysCode);
        }
      }

      LOGGER.info("Loaded all system code cache. Size: " + systemCodeMap.size());
      return systemCodeMap;
    }
  }

  /**
   * =============================================================================== <br>
   * Cache key. <br>
   * ===============================================================================
   */
  private static class CacheKey {

    private static String ALL_METAS_TYPE = "ALL_METAS";
    private static String META_ID_TYPE = "META_ID";
    private static String SYSTEM_CODE_ID_TYPE = "SYSTEM_CODE_ID";

    private Serializable value;
    private String type;

    private CacheKey(String type, Serializable value) {
      this.type = type;
      this.value = value;
    }

    public Serializable getValue() {
      return value;
    }

    public String getType() {
      return type;
    }

    private static CacheKey createForAllMetas() {
      return new CacheKey(ALL_METAS_TYPE, ALL_METAS_TYPE);
    }

    private static CacheKey createForMeta(Serializable value) {
      return new CacheKey(META_ID_TYPE, value);
    }

    private static CacheKey createForSystemCode(Serializable value) {
      return new CacheKey(SYSTEM_CODE_ID_TYPE, value);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
  }
}
