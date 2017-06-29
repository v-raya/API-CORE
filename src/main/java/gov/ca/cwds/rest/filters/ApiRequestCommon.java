package gov.ca.cwds.rest.filters;

import java.util.Date;

import gov.ca.cwds.auth.User;
import gov.ca.cwds.data.std.ApiObjectIdentity;

public class ApiRequestCommon extends ApiObjectIdentity {

  private static final ThreadLocal<ApiRequestCommon> pegged = new ThreadLocal<>();

  private final Date requestBegin;

  private final User user;

  private ApiRequestCommon(User user) {
    this.requestBegin = new Date();
    this.user = user;
  }

  static void pegRequest(User user) {
    pegged.set(new ApiRequestCommon(user));
  }

  public static ApiRequestCommon getRequestCommon() {
    return pegged.get();
  }

  public Date getRequestBegin() {
    return requestBegin;
  }

  public User getUser() {
    return user;
  }

}
