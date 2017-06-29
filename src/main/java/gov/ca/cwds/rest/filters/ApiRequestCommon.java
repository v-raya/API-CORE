package gov.ca.cwds.rest.filters;

import java.util.Date;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.auth.User;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Common information carrier for all requests. Includes the request start timestamp and user
 * information. Each request is separated by thread local.
 * 
 * @author CWDS API Team
 */
public class ApiRequestCommon extends ApiObjectIdentity {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<ApiRequestCommon> pegged = new ThreadLocal<>();

  private final Date requestBegin;

  private final User user;

  private ApiRequestCommon(User user) {
    this.requestBegin = new Date();
    this.user = user;
  }

  /**
   * Servlet filter marks the start of a web request. This method is only accessible by the filters
   * package.
   * 
   * @param user User with mainframe RACF id
   */
  static void startRequest(User user) {
    pegged.set(new ApiRequestCommon(user));
  }

  /**
   * Retrieve common information for this thread.
   * 
   * @return common request information
   */
  public static ApiRequestCommon getRequestCommon() {
    ImmutableList.Builder<ApiRequestCommon> entities = new ImmutableList.Builder<>();
    entities.add(pegged.get());
    return entities.build().get(0);
  }

  @SuppressWarnings("javadoc")
  public Date getRequestBegin() {
    return requestBegin;
  }

  @SuppressWarnings("javadoc")
  public User getUser() {
    return user;
  }

}
