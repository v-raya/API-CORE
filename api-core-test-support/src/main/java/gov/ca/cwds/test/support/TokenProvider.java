package gov.ca.cwds.test.support;

/**
 * @author CWDS TPT-2 Team
 */
public interface TokenProvider<T extends AuthParams> {

  String doGetToken(T config);

}
