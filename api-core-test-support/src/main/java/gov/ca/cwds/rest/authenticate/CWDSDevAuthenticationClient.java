package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.ConfigUtils;

/**
 * 
 * This class is used to generate the token using json for Perry dev mode, and handles all the
 * redirect from clicking the login to the end to get the token.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSDevAuthenticationClient extends HttpClientBuild implements CWDSClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CWDSDevAuthenticationClient.class);

  private static final int GET_ONLY_ACCESS_CODE_VALUE = 11;

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";
  private static final String TOKEN_URL = "https://web.preint.cwds.io/perry/authn/token";
  private static final String PERRY_LOGIN_URL = "https://web.preint.cwds.io/perry/login";
  private static final String AUTH_LOGIN_URL = "https://web.preint.cwds.io/perry/authn/login";
  private static final String CALL_BACK_URL = "https://ferbapi.preint.cwds.io/swagger";

  private static final String ACCESS_CODE = "accessCode";
  private static final String LOCATION = "Location";
  private ConfigReader configReader;

  private HttpGet httpGet;
  private URI uri;
  private HttpResponse httpResponse;
  private String redirectUrl;
  private String token = null;
  private String userName;

  /**
   * @param configReader - configReader
   * @param userName - userName
   */
  public CWDSDevAuthenticationClient(ConfigReader configReader, String userName) {
    this.userName = userName;
    if (configReader == null) {
      this.configReader = new ConfigUtils();
    } else {
      this.configReader = configReader;
    }
  }

  /**
   * @return the valid token
   */
  @Override
  public String getToken() {
    try {
      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {}", configReader.readConfig().getTestUrl().getAuthLoginUrl());
      postParams.add(new BasicNameValuePair("callback", CALL_BACK_URL));
      postParams.add(new BasicNameValuePair("sp_id", null));
      httpGet = new HttpGet(AUTH_LOGIN_URL);
      uri = new URIBuilder(AUTH_LOGIN_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      httpResponse = httpClient.execute(httpGet, httpContext);
      redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info("Redirect URL: {}", redirectUrl);
      httpGet.releaseConnection();

      redirectUrl = giveUsernameCredentials();
      token = requestToken(redirectUrl);

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    } finally {
      this.httpGet.reset();
    }
    return token;
  }


  private String requestToken(String redirectUrl) throws IOException, URISyntaxException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET ACCESS CODE: {}", redirectUrl);
    httpGet = new HttpGet(redirectUrl);
    httpResponse = httpClient.execute(httpGet, httpContext);
    redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
    String accessCodeParm = redirectUrl.substring(redirectUrl.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + GET_ONLY_ACCESS_CODE_VALUE;
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
    return token;
  }

  private String giveUsernameCredentials() throws IOException {
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
    return redirectUrl;
  }

}
