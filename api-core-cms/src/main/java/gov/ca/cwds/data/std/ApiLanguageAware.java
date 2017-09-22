package gov.ca.cwds.data.std;

/**
 * Interface defines naming standard methods for persistence classes that reference language. Allows
 * DAO and service classes to operate on language-aware objects without knowledge of their
 * implementation.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface ApiLanguageAware extends ApiMarker {

  /**
   * Getter for language SYS ID code.
   * 
   * @return SYS ID code
   */
  Integer getLanguageSysId();

  /**
   * Get if this primary language
   * 
   * @return Is this primary language.
   */
  default Boolean getPrimary() {
    return Boolean.FALSE;
  }
}
