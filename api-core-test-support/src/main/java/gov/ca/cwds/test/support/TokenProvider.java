package gov.ca.cwds.test.support;

/**
 * @author CWDS TPT-2 Team
 */
public interface TokenProvider<T> {

  String doGetToken(T config);

}
