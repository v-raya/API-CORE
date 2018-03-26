package gov.ca.cwds.test.support;

import gov.ca.cwds.security.jwt.JwtConfiguration;
import gov.ca.cwds.security.jwt.JwtService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author CWDS TPT-2 Team
 */
public class JWTTokenProvider implements TokenProvider<JWTTokenProviderConf> {

  @Override
  public String doGetToken(JWTTokenProviderConf config) {
    JwtConfiguration configuration = null;

    try {
      configuration = getJwtConfiguration();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    JwtService jwtService = new JwtService(configuration);
    return jwtService.generate("id", "subject", config.getIdentity());
  }

  private JwtConfiguration getJwtConfiguration() throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("config/shiro.ini"));

    JwtConfiguration configuration = new JwtConfiguration();
    //JWT
    configuration.setTimeout(30);
    configuration.setIssuer(properties.getProperty("perryRealm.tokenIssuer"));
    configuration.setKeyStore(new JwtConfiguration.KeyStoreConfiguration());
    //KeyStore
    configuration.getKeyStore()
        .setPath(new File(properties.getProperty("perryRealm.keyStorePath")).getPath());
    configuration.getKeyStore().setPassword(properties.getProperty("perryRealm.keyStorePassword"));
    //Sign/Validate Key
    configuration.getKeyStore().setAlias(properties.getProperty("perryRealm.keyStoreAlias"));
    configuration.getKeyStore()
        .setKeyPassword(properties.getProperty("perryRealm.keyStoreKeyPassword"));
    //Enc Key
    configuration
        .setEncryptionEnabled(Boolean.valueOf(properties.getProperty("perryRealm.useEncryption")));
    configuration.getKeyStore()
        .setEncKeyPassword(properties.getProperty("perryRealm.encKeyPassword"));
    configuration.getKeyStore().setEncAlias(properties.getProperty("perryRealm.encKeyAlias"));
    configuration.setEncryptionMethod(properties.getProperty("perryRealm.encryptionMethod"));
    return configuration;
  }

}
