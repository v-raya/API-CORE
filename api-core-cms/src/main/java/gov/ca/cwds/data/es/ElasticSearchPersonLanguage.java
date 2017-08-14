package gov.ca.cwds.data.es;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.data.ApiSysCodeAware;

/**
 * Languages.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public enum ElasticSearchPersonLanguage implements ApiSysCodeAware {

  ENGLISH(1253, "English", 7),

  SPANISH(1274, "Spanish", 1),

  AMERICAN_SIGN_LANGUAGE(1248, "American Sign Language", 13),

  ARABIC(1249, "Arabic", 14),

  ARMENIAN(1250, "Armenian", 15),

  CAMBODIAN(1251, "Cambodian", 19),

  CANTONESE(1252, "Cantonese", 74),

  FARSI(1254, "Farsi", 41),

  FILIPINO(3198, "Filipino", 49),

  FRENCH(1255, "French", 28),

  GERMAN(1267, "German", 29),

  HAWAIIAN(1268, "Hawaiian", 99),

  HEBREW(1256, "Hebrew", 33),

  HMONG(1257, "Hmong", 35),

  ILACANO(1258, "Ilacano", 77),

  INDOCHINESE(3199, "Indochinese", 99),

  ITALIAN(1259, "Italian", 42),

  JAPANESE(1260, "Japanese", 3),

  KOREAN(1261, "Korean", 4),

  LAO(1262, "Lao", 43),

  MANDARIN(1263, "Mandarin", 75),

  MIEN(1264, "Mien", 76),

  OTHER_CHINESE(1265, "Other Chinese", 2),

  OTHER_NON_ENGLISH(1266, "Other Non-English", 99),

  POLISH(1269, "Polish", 50),

  PORTUGUESE(1270, "Portuguese", 51),

  ROMANIAN(3200, "Romanian", 99),

  RUSSIAN(1271, "Russian", 54),

  SAMOAN(1272, "Samoan", 55),

  SIGN_LANGUAGE_NOT_ASL(1273, "Sign Language (Not ASL)", 78),

  TAGALOG(1275, "Tagalog", 5),

  THAI(1276, "Thai", 65),

  TURKISH(1277, "Turkish", 67),

  VIETNAMESE(1278, "Vietnamese", 69);

  private final int sysId;
  private final String description;
  private final int displayOrder;

  private static final Map<Integer, ElasticSearchPersonLanguage> mapBySysId = new HashMap<>();

  private ElasticSearchPersonLanguage(int sysId, String description, int displayOrder) {
    this.sysId = sysId;
    this.description = description;
    this.displayOrder = displayOrder;
  }

  /**
   * Getter for SYS_ID in CMS table SYS_CD_C.
   * 
   * @return SYS_ID
   */
  @Override
  public int getSysId() {
    return sysId;
  }

  @Override
  @JsonValue
  public String getDescription() {
    return description;
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  @Override
  public ApiSysCodeAware lookupBySysId(int sysId) {
    return ElasticSearchPersonLanguage.findBySysId(sysId);
  }

  public static ElasticSearchPersonLanguage findBySysId(int sysId) {
    return mapBySysId.get(sysId);
  }

  static {
    for (ElasticSearchPersonLanguage e : ElasticSearchPersonLanguage.values()) {
      mapBySysId.put(e.sysId, e);
    }
  }

}
