package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

/**
 * Represents a CMS system code entry.
 * 
 * @author CWDS API Team
 */
public final class CmsSystemCode implements Serializable {

  /**
   * Base serialization value. Increment per class change.
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

  /**
   * Construct from field values.
   * 
   * @param sysId unique system code id
   * @param fksMetaT system code category
   * @param shortDsc short description (e.g., "California")
   * @param lgcId logical id. Usually zero-padded sort order (e.g., "0002")
   * @param inactvInd inactive flag (N or Y)
   * @param categoryId sub-category
   * @param otherCd optional, 2 character code, such as "CA" for the State of California.
   * @param longDsc long description. Only populated occasionally.
   */
  public CmsSystemCode(int sysId, String fksMetaT, String shortDsc, String lgcId, String inactvInd,
      String categoryId, String otherCd, String longDsc) {
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

    // WARNING: if a column has zero width, then String.split() will NOT yield a token.
    // For now, all columns in the incoming line should contain some value, even spaces.
    final String[] tokens = line.split(delim);

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

  /**
   * Getter for system code id.
   * 
   * @return sys id
   */
  public int getSysId() {
    return sysId;
  }

  /**
   * Getter for "meta" (system code category).
   * 
   * @return system code category
   */
  public String getFksMetaT() {
    return fksMetaT;
  }

  /**
   * Getter for short description, "California" instead of "CA".
   * 
   * @return short description
   */
  public String getShortDsc() {
    return shortDsc;
  }

  /**
   * Getter for logical id, the zero-padded sort order (e.g., "0001").
   * 
   * @return logical id
   */
  public String getLgcId() {
    return lgcId;
  }

  /**
   * Getter for inactive flag.
   * 
   * @return inactive flag
   */
  public String getInactvInd() {
    return inactvInd;
  }

  public String getCategoryId() {
    return categoryId;
  }

  /**
   * Getter for "other code", an optional short code for some categories.
   * 
   * @return other code
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * Getter for long description.
   * 
   * @return long description
   */
  public String getLongDsc() {
    return longDsc;
  }

}
