package gov.ca.cwds.cms.data.access;

/**
 * @author CWDS CALS API Team
 */

public final class Constants {

  private Constants() {}

  public static final String Y = "Y";
  public static final String N = "N";
  public static final String SPACE = " ";

  public static class PhoneticSearchTables {

    public static final String SCP_PHTT = "SCP_PHTT";

    public static final String ADR_PHTT = "ADR_PHTT";

    private PhoneticSearchTables() {
    }

  }

  public static class StaffPersonPrivileges {

    public static final String USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT = "Resource Mgmt Placement Facility Maint";
    public static final String USR_PRV_SOC158_APPLICATION = "SOC158 Application";

    private StaffPersonPrivileges() {}
  }

  public static class Authorize {
    public static final String CLIENT_READ_CLIENT = "client:read:client";

    private Authorize() {
    }
  }


}
