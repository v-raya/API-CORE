package gov.ca.cwds.data.persistence.cms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Basic system code cache facility to translate common CMS codes.
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeCache implements Serializable, Iterable<CmsSystemCode> {

  /**
   * Base serialization version. Increment per class change.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeCache.class);

  private static String fileLocation;

  static {
    fileLocation = "/" + CmsSystemCodeCache.class.getPackage().getName().replace('.', '/') + '/'
        + "system_codes.tsv";
  }

  private final Map<Integer, CmsSystemCode> idxSysId;
  private final Map<String, List<CmsSystemCode>> idxMeta;

  /**
   * Default constructor is off limits.
   * 
   * @see #produce()
   */
  @SuppressWarnings("unused")
  private CmsSystemCodeCache() {
    throw new ApiException("Access prohibited.");
  }

  /**
   * Construct the system code cache from a comprehensive list of codes.
   * 
   * @param codes all system codes.
   */
  protected CmsSystemCodeCache(final List<CmsSystemCode> codes) {
    Map<Integer, CmsSystemCode> anIdxSysId = new ConcurrentHashMap<>(codes.size());
    Map<String, List<CmsSystemCode>> anIdxMeta = new ConcurrentHashMap<>(codes.size());

    for (CmsSystemCode code : codes) {
      anIdxSysId.put(code.getSysId(), code);

      if (anIdxMeta.get(code.getFksMetaT()) == null) {
        anIdxMeta.put(code.getFksMetaT(), new ArrayList<>());
      }

      List<CmsSystemCode> category = anIdxMeta.get(code.getFksMetaT());
      category.add(code);
      anIdxMeta.put(code.getFksMetaT(), category);
    }

    this.idxSysId = Collections.unmodifiableMap(anIdxSysId);
    this.idxMeta = Collections.unmodifiableMap(anIdxMeta);
  }

  /**
   * Core method. Look up (translate) an incoming system id.
   * 
   * @param sysId unique system id
   * @return return the found system code or null if none found
   */
  public CmsSystemCode lookup(int sysId) {
    return this.idxSysId.get(sysId);
  }

  /**
   * Get all system codes for a system code category.
   * 
   * @param meta system code category (aka, "meta")
   * @return List of system codes for this category
   */
  public List<CmsSystemCode> getCategory(final String meta) {
    return this.idxMeta.get(meta);
  }

  /**
   * Produce the system cache facility from a reader.
   * 
   * @param reader reader of file, stream, whatever
   * @return initialized system cache
   * @throws IOException if unable to read
   */
  protected static CmsSystemCodeCache produce(BufferedReader reader) throws IOException {
    List<CmsSystemCode> codes = new ArrayList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("SYS_ID")) {
        continue;
      }
      LOGGER.trace("line: {}", line);
      codes.add(CmsSystemCode.produce(line));
    }

    return new CmsSystemCodeCache(codes);
  }

  /**
   * Convenience method. Produce the system cache facility from a file.
   * 
   * @param file source, delimited file
   * @return initialized system cache
   * @throws ServiceException if unable to parse the file
   */
  public static CmsSystemCodeCache produce(File file) throws ServiceException {
    CmsSystemCodeCache ret;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      ret = produce(reader);
    } catch (Exception e) {
      LOGGER.error("Unable to read system code file", e);
      throw new ServiceException("Unable to read system code file", e);
    }

    return ret;
  }

  /**
   * Convenience method. Produce the system cache facility from a file stored in this package.
   * 
   * @return initialized system cache
   * @throws ServiceException if unable to read the default system code file
   */
  public static CmsSystemCodeCache produce() throws ServiceException {
    LOGGER.info(CmsSystemCodeCache.class.getPackage().getName());
    return CmsSystemCodeCache
        .produce(new File(CmsSystemCodeCache.class.getResource(getFileLocation()).getFile()));
  }

  @Override
  public Iterator<CmsSystemCode> iterator() {
    return this.idxSysId.values().iterator();
  }

  @Override
  public void forEach(Consumer<? super CmsSystemCode> action) {
    // #137202471: Tech debt: Cobertura can't deal with Java 8 features.
    Iterable.super.forEach(action);
  }

  @Override
  public Spliterator<CmsSystemCode> spliterator() {
    return this.idxSysId.values().spliterator();
  }

  /**
   * Get the default location of the system codes file.
   * 
   * @return location of the system codes file
   */
  public static String getFileLocation() {
    return CmsSystemCodeCache.fileLocation;
  }

  /**
   * Set the default location of the system codes file. Used mostly to validate new codes.
   * 
   * @param fileLocation location of the system codes file
   */
  public static void setFileLocation(String fileLocation) {
    CmsSystemCodeCache.fileLocation = fileLocation;
  }

}
