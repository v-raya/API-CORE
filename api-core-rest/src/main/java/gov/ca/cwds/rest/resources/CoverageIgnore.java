package gov.ca.cwds.rest.resources;

/**
 * Marker interface tells Cobertura to ignore a method.
 * 
 * <p>
 * Cobertura struggles with Java 8 features, such as Lambda and interface defaults, though those
 * features were released years ago.
 * <p>
 * 
 * @author CWDS API Team
 */
public @interface CoverageIgnore {
  // Marker interface. No implementation.
}
