package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Basic system code cache facility to translate common CMS codes.
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeCacheService
    implements Serializable, ApiSystemCodeCache, Iterable<CmsSystemCode> {

  /**
   * Base serialization version. Increment per class change.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeCacheService.class);

  private final transient ApiSystemCodeDao dao;

  /**
   * Key = system code id, value = CMS system code record
   */
  private final Map<Integer, CmsSystemCode> idxSysId;

  /**
   * Key = meta (aka, "category"), value = list of CMS system code records
   */
  private final Map<String, List<CmsSystemCode>> idxMeta;

  /**
   * Key = meta + "_" + short description, value = CMS system code record
   */
  private final Map<String, CmsSystemCode> idxCategoryShortDesc;

  /**
   * Default constructor is off limits.
   * 
   * @see #produce()
   */
  @SuppressWarnings("unused")
  private CmsSystemCodeCacheService() {
    throw new ApiException("Access prohibited.");
  }

  /**
   * Construct the system code cache from a comprehensive list of codes.
   * 
   * @param dao DAO to pull system codes.
   */
  @Inject
  public CmsSystemCodeCacheService(ApiSystemCodeDao dao) {
    this.dao = dao;
    final List<CmsSystemCode> codes = this.dao.getAllSystemCodes();
    Map<Integer, CmsSystemCode> anIdxSysId = new ConcurrentHashMap<>(codes.size());
    Map<String, List<CmsSystemCode>> anIdxMeta = new ConcurrentHashMap<>(codes.size());
    Map<String, CmsSystemCode> anIdxCategoryShortDesc = new ConcurrentHashMap<>(codes.size());

    for (CmsSystemCode code : codes) {
      anIdxSysId.put(code.getSysId(), code);
      anIdxCategoryShortDesc
          .put(code.getFksMetaT().toUpperCase() + "_" + code.getShortDsc().toUpperCase(), code);

      if (anIdxMeta.get(code.getFksMetaT()) == null) {
        anIdxMeta.put(code.getFksMetaT(), new ArrayList<>());
      }

      List<CmsSystemCode> category = anIdxMeta.get(code.getFksMetaT());
      category.add(code);
      anIdxMeta.put(code.getFksMetaT(), category);
    }

    this.idxSysId = Collections.unmodifiableMap(anIdxSysId);
    this.idxMeta = Collections.unmodifiableMap(anIdxMeta);
    this.idxCategoryShortDesc = Collections.unmodifiableMap(anIdxCategoryShortDesc);
    LOGGER.debug("total records in system code cache: {}", idxSysId.size());
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache#lookup(int)
   */
  @Override
  public CmsSystemCode lookup(int sysId) {
    return this.idxSysId.get(sysId);
  }

  @Override
  public CmsSystemCode lookupByCategoryAndShortDesc(String meta, String shortDesc) {
    final String key = meta.toUpperCase() + "_" + shortDesc.toUpperCase();
    return idxCategoryShortDesc.get(key);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache#getCategory(java.lang.String)
   */
  @Override
  public List<CmsSystemCode> getCategory(final String meta) {
    return this.idxMeta.get(meta);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache#getCodeShortDescription(Integer)
   */
  @Override
  public String getCodeShortDescription(Integer codeId) {
    String codeDesc = null;
    if (codeId != null) {
      final CmsSystemCode sysCode = lookup(codeId);
      if (sysCode != null) {
        codeDesc = sysCode.getShortDsc();
      }
    }
    return codeDesc;
  }

  // #137202471: Tech debt: Cobertura doesn't recognize Java 8 features.
  @Override
  public Iterator<CmsSystemCode> iterator() {
    return this.idxSysId.values().iterator();
  }

  @Override
  public void forEach(Consumer<? super CmsSystemCode> action) {
    Iterable.super.forEach(action);
  }

  @Override
  public Spliterator<CmsSystemCode> spliterator() {
    return this.idxSysId.values().spliterator();
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
