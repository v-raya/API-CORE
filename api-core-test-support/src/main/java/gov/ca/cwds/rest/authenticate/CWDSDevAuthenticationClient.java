package gov.ca.cwds.rest.authenticate;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSDevAuthenticationClient extends CWDSCommonProperties implements CWDSClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CWDSDevAuthenticationClient.class);

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";
  private static final String TOKEN_URL = "https://web.preint.cwds.io/perry/authn/token";
  private static final String PERRY_LOGIN_URL = "https://web.preint.cwds.io/perry/login";
  private static final String AUTH_LOGIN_URL = "https://web.preint.cwds.io/perry/authn/login";
  private static final String CALL_BACK_URL = "https://ferbapi.preint.cwds.io/swagger";

  private static final String ACCESS_CODE = "accessCode";
  private static final String LOCATION = "Location";
  private ConfigUtils configUtils = new ConfigUtils();

  private String userName;

  /**
   * Constructor
   * 
   * @param userName - userName
   */
  public CWDSDevAuthenticationClient(String userName) {
    this.userName = userName;
  }

  /**
   * @return the valid token
   */
  @Override
  public String getToken() {
    String token = null;

    try {

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {}", configUtils.getYamlValues().getTokenCredentials().getAuthLoginUrl());
      postParams.add(new BasicNameValuePair("callback", CALL_BACK_URL));
      postParams.add(new BasicNameValuePair("sp_id", null));
      HttpGet httpGet = new HttpGet(AUTH_LOGIN_URL);
      URI uri = new URIBuilder(AUTH_LOGIN_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
      String redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info("Redirect URL: {}", redirectUrl);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("POST: {}", PERRY_LOGIN_URL);
      HttpPost httpPost = new HttpPost(PERRY_LOGIN_URL);
      postParams.clear();
      postParams.add(new BasicNameValuePair("username", userName));
      httpPost.setEntity(new UrlEncodedFormEntity(postParams));
      httpResponse = httpClient.execute(httpPost, httpContext);
      LOGGER.info("Status: {}", httpResponse.getStatusLine());
      redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info("Redirect URL: {}", redirectUrl);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET ACCESS CODE: {}", redirectUrl);
      httpGet = new HttpGet(redirectUrl);
      httpResponse = httpClient.execute(httpGet, httpContext);
      redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
      String accessCodeParm = redirectUrl.substring(redirectUrl.indexOf(ACCESS_CODE));
      int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + 11;
      String accessCode = accessCodeParm.substring(startIndex);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET TOKEN: {}", TOKEN_URL);
      postParams.clear();
      postParams.add(new BasicNameValuePair(ACCESS_CODE, accessCode));
      httpGet = new HttpGet(TOKEN_URL);
      uri = new URIBuilder(TOKEN_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      httpResponse = httpClient.execute(httpGet, httpContext);
      token = EntityUtils.toString(httpResponse.getEntity());
      LOGGER.info("TOKEN: {}", token);

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    }
    return token;
  }

}
