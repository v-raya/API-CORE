package gov.ca.cwds.data.std;

/**
 * Represents an object capable of holding multiple languages.
 * 
 * @author CWDS API Team
 */
public interface ApiMultipleLanguagesAware {

  /**
   * Get all languages available on this object.
   * 
   * @return array of languages
   */
  ApiLanguageAware[] getLanguages();

}
