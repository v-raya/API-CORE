package gov.ca.cwds.cms.data.access;

/** @author CWDS CALS API Team */
public final class Constants {

  private Constants() {}

  public static final String Y = "Y";
  public static final String N = "N";
  public static final String SPACE = " ";

  public static class PhoneticSearchTables {

    public static final String SCP_PHTT = "SCP_PHTT";

    public static final String ADR_PHTT = "ADR_PHTT";

    public static final String CLT_PHNT = "CLT_PHNT";

    private PhoneticSearchTables() {}
  }

  public static class PhoneticPrimaryNameCode {
    public static final String OTHER_CLIENT_NAME = "N";
    public static final String PLACEMENT_HOME_ADDRESS = "P";
  }

  public static class SsaName3StoredProcedureCrudOperationCode {
    public static final String INSERT_OPERATION_CODE = "I";
  }

  public static class StaffPersonPrivileges {

    public static final String USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT =
        "Resource Mgmt Placement Facility Maint";
    public static final String USR_PRV_SOC158_APPLICATION = "SOC158 Application";

    private StaffPersonPrivileges() {}
  }

  public static class Authorize {
    public static final String CLIENT_READ_CLIENT = "client:read:client";
    public static final String CLIENT_READ_CLIENT_ID = "client:read:clientId";

    private Authorize() {}
  }
}
