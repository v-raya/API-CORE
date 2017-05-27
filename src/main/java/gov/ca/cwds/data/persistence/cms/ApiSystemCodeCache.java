package gov.ca.cwds.data.persistence.cms;

import java.util.List;

/**
 * Interface for CMS system code cache facility to translate common CMS codes.
 * 
 * <p>
 * Concrete implementations of the system code cache might read from different sources, allow
 * refresh/reload on demand, poll for changes every few minutes, and so forth. Callers should
 * separate themselves from concrete implementations and should reference this interface instead of
 * specific implementations.
 * </p>
 * 
 * @author CWDS API Team
 * @see CmsSystemCodeCacheService
 */
public interface ApiSystemCodeCache {

  /**
   * Core method. Look up (translate) an incoming system id.
   * 
   * @param sysId unique system id
   * @return return the found system code or null if none found
   */
  CmsSystemCode lookup(int sysId);

  /**
   * Get all system codes for a system code category.
   * 
   * @param meta system code category (aka, "meta")
   * @return List of system codes for this category
   */
  List<CmsSystemCode> getCategory(final String meta);

  /**
   * Look up CMS system code record by its category and short description.
   * 
   * @param meta lookup category
   * @param shortDesc short description
   * @return system code record
   */
  CmsSystemCode lookupByCategoryAndShortDesc(final String meta, final String shortDesc);

  /**
   * Get system code short description for given system code id.
   * 
   * @param codeId Unique system code id.
   * @return System code short description if available, otherwise returns null.
   */
  String getCodeShortDescription(Integer codeId);
}
