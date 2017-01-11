package gov.ca.cwds.data.persistence.cms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Basic system code cache facility to translate common CMS codes.
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeCache implements Serializable {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeCache.class);

  private static final int INITIALIZE_SIZE = 7253;

  private final Map<Integer, CmsSystemCode> idxSysId;
  private final Map<String, List<CmsSystemCode>> idxMeta;

  /**
   * Default constructor is off limits.
   */
  @SuppressWarnings("unused")
  private CmsSystemCodeCache() {
    throw new ApiException("Not supported.");
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
      anIdxSysId.put(code.sysId, code);

      if (anIdxMeta.get(code.fksMetaT) == null) {
        anIdxMeta.put(code.fksMetaT, new ArrayList<>());
      }

      List<CmsSystemCode> category = anIdxMeta.get(code.fksMetaT);
      category.add(code);
      anIdxMeta.put(code.fksMetaT, category);
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
   * Represents a CMS system code entry.
   * 
   * @author CWDS API Team
   */
  public static class CmsSystemCode implements Serializable {

    /**
     * Base serialization value. Increment by class change.
     */
    private static final long serialVersionUID = 1L;

    private final int sysId;
    private final String fksMetaT;
    private final String shortDsc;
    private final String lgcId;
    private final String inactvInd;
    private final String categoryId;
    private final String otherCd;
    private final String longDsc;

    public CmsSystemCode(int sysId, String fksMetaT, String shortDsc, String lgcId,
        String inactvInd, String categoryId, String otherCd, String longDsc) {
      this.sysId = sysId;
      this.fksMetaT = fksMetaT;
      this.shortDsc = shortDsc;
      this.lgcId = lgcId;
      this.inactvInd = inactvInd;
      this.categoryId = categoryId;
      this.otherCd = otherCd;
      this.longDsc = longDsc;
    }

    /**
     * Produce a {@link CmsSystemCode} from a delimited String.
     * 
     * <p>
     * Expected file layout.
     * </p>
     * 
     * <p>
     * SYS_ID, FKS_META_T, SHORT_DSC, LGC_ID, INACTV_IND, CATEGORY_ID, OTHER_CD, LONG_DSC
     * </p>
     * 
     * @param line delimited system code line to parse
     * @param delim chose delimiter
     * @return prepared CmsSystemCode
     */
    public static CmsSystemCode produce(final String line, final String delim) {
      int sysId;
      String fksMetaT;
      String shortDsc;
      String lgcId;
      String inactvInd;
      String categoryId;
      String otherCd;
      String longDsc;

      // WARNING: if a column has no width, then String.split() will NOT yield a token.
      // For now, all columns in the incoming line should contain some value, even spaces.
      final String[] tokens = line.split("\t");

      sysId = Integer.parseInt(tokens[0]);
      fksMetaT = tokens[1].trim();
      shortDsc = tokens[2].trim();
      lgcId = tokens[3].trim();
      inactvInd = tokens[4].trim();
      categoryId = tokens[5].trim();
      otherCd = tokens[6].trim();
      longDsc = tokens[7].trim();

      return new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd, categoryId, otherCd,
          longDsc);
    }

    /**
     * See method {@link #produce(String, String)}. Delimiter defaults to tab.
     * 
     * @param line delimited system code line to parse
     * @return prepared CmsSystemCode
     */
    public static CmsSystemCode produce(final String line) {
      return produce(line, "\t");
    }

    public int getSysId() {
      return sysId;
    }

    public String getFksMetaT() {
      return fksMetaT;
    }

    public String getShortDsc() {
      return shortDsc;
    }

    public String getLgcId() {
      return lgcId;
    }

    public String getInactvInd() {
      return inactvInd;
    }

    public String getCategoryId() {
      return categoryId;
    }

    public String getOtherCd() {
      return otherCd;
    }

    public String getLongDsc() {
      return longDsc;
    }

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
   * @throws IOException if unable to read
   */
  public static CmsSystemCodeCache produce(File file) throws IOException {
    CmsSystemCodeCache ret;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      ret = produce(reader);
    }

    return ret;
  }

  /**
   * Convenience method. Produce the system cache facility from a file stored in this package.
   * 
   * @return initialized system cache
   * @throws IOException if unable to read
   */
  public static CmsSystemCodeCache produce() throws IOException {
    LOGGER.info(CmsSystemCodeCache.class.getPackage().getName());
    return CmsSystemCodeCache.produce(new File(CmsSystemCodeCache.class
        .getResource("/" + CmsSystemCodeCache.class.getPackage().getName().replace('.', '/') + '/'
            + "system_codes.tsv")
        .getFile()));
  }

}
