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

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSAuthenticationClient extends HttpClientBuild implements CWDSClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CWDSAuthenticationClient.class);
  private static final String REDIRECT_URL_LOGGER = "Redirect URL: {}";
  private static final String POST_LOGGER = "POST : {}{}";

  private static final String SUBMIT_CONTINUE = "submit.Continue";
  private static final String STATE = "state";
  private static final String SCOPE = "scope";
  private static final String CLIENT_ID = "client_id";
  private static final String SUBMIT_VALIDATE = "submit.Validate";
  private static final String SELECTED_CONTACT = "selectedContact";
  private static final String GET_LOGGER = "GET: {}";
  private static final String ACCESS_CODE = "accessCode";
  private static final String EMAIL_CONTACT = "emailContact";
  private static final String VIEW = "View";
  private static final String SUBMIT_SIGNIN_RACF = "submit.Signin.RACF";
  private static final String PASSWORD = "Password"; // It's a query parameter, not password
  private static final String USERNAME = "Username";
  private static final String LOCATION = "Location";
  private static final String DEVICE_LOG_ID = "deviceLogID";
  private static final String VALUE = "value=";
  private static final String REQUEST_VERIFICATION_TOKEN = "__RequestVerificationToken";

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";
  private static final String AUTH_LOGIN_URL = "https://web.integration.cwds.io/perry/authn/login";
  private static final String TOKEN_URL = "https://web.integration.cwds.io/perry/authn/token";
  private static final String BASE_URL = "https://sectest.dss.ca.gov";
  private static final String CALL_BACK_URL = "https://ferbapi.integration.cwds.io/swagger";

  private String userName;
  private String password;

  private String token = null;
  private HttpGet httpGet;
  private URI uri;
  private HttpResponse httpResponse;
  private HttpPost httpPost;

  /**
   * @param userName - userName
   * @param password - password
   */
  public CWDSAuthenticationClient(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public static void main(String[] args) {
    String userName = "TESTPA";
    String password = "CWS1kids";
    CWDSAuthenticationClient cwdsAuthenticationClient =
        new CWDSAuthenticationClient(userName, password);
    cwdsAuthenticationClient.getToken();
  }

  /**
   * @return the valid token
   */
  @Override
  public String getToken() {

    try {

      // Login in expect 302 with redirect:
      // https://web.xxxx.cwds.io/perry/login
      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, AUTH_LOGIN_URL);
      postParams.add(new BasicNameValuePair("callback", CALL_BACK_URL));
      postParams.add(new BasicNameValuePair("sp_id", null));
      httpGet = new HttpGet(AUTH_LOGIN_URL);
      uri = new URIBuilder(AUTH_LOGIN_URL).addParameters(postParams).build();
      httpGet.setURI(uri);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, redirectUrl);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, redirectUrl);
      httpGet = new HttpGet(redirectUrl);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String location = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, location);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, location);
      httpGet = new HttpGet(location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      location = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, location);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {} {}", BASE_URL, location);
      httpGet = new HttpGet(BASE_URL);
      uri = new URIBuilder(BASE_URL).setPath(location).build();
      httpGet.setURI(uri);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String response = EntityUtils.toString(httpResponse.getEntity());
      String requestVerificationToken = getRequestVerificationToken(response);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(POST_LOGGER, BASE_URL, location);
      httpPost = new HttpPost(BASE_URL + location);
      postParams.clear();
      postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
      postParams.add(new BasicNameValuePair(USERNAME, userName));
      postParams.add(new BasicNameValuePair(PASSWORD, password));
      postParams.add(new BasicNameValuePair(SUBMIT_SIGNIN_RACF, "RACF"));
      postParams.add(new BasicNameValuePair(VIEW, "None"));
      httpPost.setEntity(new UrlEncodedFormEntity(postParams));
      httpResponse = httpClient.execute(httpPost, httpContext);
      location = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, location);
      httpPost.releaseConnection();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET : {}{}", BASE_URL, location);
      httpGet = new HttpGet(BASE_URL + location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      response = EntityUtils.toString(httpResponse.getEntity());
      requestVerificationToken = getRequestVerificationToken(response);
      String emailContact = getEmailContact(response);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(POST_LOGGER, BASE_URL, location);
      httpPost = new HttpPost(BASE_URL + location);
      postParams.clear();
      postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
      postParams.add(new BasicNameValuePair(EMAIL_CONTACT, emailContact));
      postParams.add(new BasicNameValuePair(ACCESS_CODE, emailContact));
      postParams.add(new BasicNameValuePair("submit.SendAccessCode", null));
      httpPost.setEntity(new UrlEncodedFormEntity(postParams));
      httpResponse = httpClient.execute(httpPost, httpContext);
      location = httpResponse.getFirstHeader(LOCATION).getValue();
      LOGGER.info(REDIRECT_URL_LOGGER, location);
      httpPost.releaseConnection();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET : {}{}", BASE_URL, location);
      httpGet = new HttpGet(BASE_URL + location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      response = EntityUtils.toString(httpResponse.getEntity());
      String deviceLogId = getDeviceLogicId(response);
      requestVerificationToken = getRequestVerificationToken(response);

      // expect 302 with redirectUrl
      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(POST_LOGGER, BASE_URL, location);
      httpPost = new HttpPost(BASE_URL + location);
      postParams.clear();
      postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
      postParams.add(new BasicNameValuePair(SELECTED_CONTACT, emailContact));
      postParams.add(new BasicNameValuePair(ACCESS_CODE, "123456"));
      postParams.add(new BasicNameValuePair(DEVICE_LOG_ID, deviceLogId));
      postParams.add(new BasicNameValuePair(SUBMIT_VALIDATE, null));
      httpPost.setEntity(new UrlEncodedFormEntity(postParams));
      httpResponse = httpClient.execute(httpPost, httpContext);
      location = httpResponse.getFirstHeader(LOCATION).getValue();
      redirectUrl = location.substring(location.indexOf('?') + 1);
      String[] values = redirectUrl.split("&");
      LOGGER.info(REDIRECT_URL_LOGGER, location);
      httpPost.releaseConnection();

      String accessCode = continueToCwsIntegration(location, values);
      token = requestToken(accessCode);

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    }
    return token;

  }

  private String continueToCwsIntegration(String location, String[] values) throws IOException {

    // expect 302 with redirectUrl
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("POST: {}", location);
    httpPost = new HttpPost(location);
    postParams.add(new BasicNameValuePair(CLIENT_ID, getRedirectUrlParam(values[0])));
    postParams.add(new BasicNameValuePair("redirect_uri", getRedirectUrlParam(values[1])));
    postParams.add(new BasicNameValuePair(SCOPE, getRedirectUrlParam(values[2])));
    postParams.add(new BasicNameValuePair("response_type", getRedirectUrlParam(values[3])));
    postParams.add(new BasicNameValuePair(STATE, getRedirectUrlParam(values[4])));
    postParams.add(new BasicNameValuePair(SUBMIT_CONTINUE, "Continue+to+CWDS+-+Integration"));
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATION).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    // expect 302 with redirectUrl to:
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("POST: {}", location);
    httpPost = new HttpPost(location);
    httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATION).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    // expect 302 with accessCode in the redirectUrl
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET : {}", location);
    httpGet = new HttpGet(location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATION).getValue();
    String accessCode = getAccessCode(location);
    LOGGER.info(REDIRECT_URL_LOGGER, location);
    return accessCode;
  }

  private String requestToken(String accessCode) throws URISyntaxException, IOException {

    // expect status code 200 with token
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

  private String getEmailContact(String response) {
    String emailId = response.substring(response.indexOf("input id=\"emailContact\""));
    int startIndex = emailId.indexOf(VALUE) + 7;
    int endIndex = emailId.indexOf('}') + 1;
    String actualEmailId = emailId.substring(startIndex, endIndex);
    return actualEmailId.replaceAll("&quot;", "\"");
  }

  private String getAccessCode(String location) {
    String accessCodeParm = location.substring(location.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + 11;
    return accessCodeParm.substring(startIndex);
  }

  private String getRedirectUrlParam(String location) {
    return location.substring(location.indexOf('=') + 1, location.length());
  }

  private String getDeviceLogicId(String response) {
    String deviceId = response.substring(response.indexOf(DEVICE_LOG_ID));
    int startIndex = deviceId.indexOf(VALUE) + 7;
    int endIndex = deviceId.indexOf("/>") - 2;
    return deviceId.substring(startIndex, endIndex);
  }

  private String getRequestVerificationToken(String response) {
    String verfiricationToken = response.substring(response.indexOf(REQUEST_VERIFICATION_TOKEN));
    int startIndex = verfiricationToken.indexOf(VALUE) + 7;
    int endIndex = verfiricationToken.indexOf("/>") - 2;
    return verfiricationToken.substring(startIndex, endIndex);
  }

}
