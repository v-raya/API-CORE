package gov.ca.cwds.rest.filters;

import java.util.Date;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Common information carrier for all requests. Includes the request start timestamp and user
 * information. Each request is separated by thread local.
 * 
 * @author CWDS API Team
 */
public class ApiRequestCommonInfo extends ApiObjectIdentity {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<ApiRequestCommonInfo> pegged = new ThreadLocal<>();

  private final Date requestBegin;

  private final String racf;

  private int sequenceExternalTable;

  private ApiRequestCommonInfo(String racf) {
    this.requestBegin = new Date();
    this.racf = racf;
    this.sequenceExternalTable = 0;
  }

  /**
   * Servlet filter marks the start of a web request. This method is only accessible by the filters
   * package.
   * 
   * <p>
   * In the future, this method should accept more parameters for other cross-cutting concerns, such
   * as user roles, authorization, staff id, and so forth.
   * </p>
   * 
   * @param user User with mainframe RACF id
   */
  static void startRequest(String racf) {
    pegged.set(new ApiRequestCommonInfo(racf));
  }

  /**
   * Retrieve common information for this thread. No need to synchronize this method, since it read
   * from ThreadLocal anyway.
   * 
   * @return common request information
   */
  public static ApiRequestCommonInfo getRequestCommon() {
    ImmutableList.Builder<ApiRequestCommonInfo> entities = new ImmutableList.Builder<>();
    entities.add(pegged.get());
    return entities.build().get(0);
  }

  @SuppressWarnings("javadoc")
  public Date getRequestBegin() {
    return requestBegin;
  }

  @SuppressWarnings("javadoc")
  public String getRacf() {
    return racf;
  }

  /**
   * Return the next available sequence number for External Table records.
   * 
   * <p>
   * Does <i>NOT</i> increment the sequence!
   * </p>
   * 
   * @return available sequence number
   */
  int getSequenceExternalTable() {
    return sequenceExternalTable;
  }

  /**
   * Increment and return the next available sequence number for External Table records.
   * 
   * @return available sequence number
   */
  int incrementAndGetSequenceExternalTable() {
    ++sequenceExternalTable;
    return sequenceExternalTable;
  }

}
