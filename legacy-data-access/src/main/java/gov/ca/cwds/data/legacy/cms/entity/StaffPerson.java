package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 * @author CWDS CALS API Team
 */
@Entity
@javax.persistence.Table(name = "STFPERST")
@NamedNativeQuery(
  name = StaffBySupervisor.NATIVE_FIND_STAFF_BY_SUPERVISOR_ID,
  query =
    "SELECT DISTINCT "
      + "  trim(user_id.LOGON_ID) AS racfId, "
      + "  stf.IDENTIFIER AS identifier, "
      + "  trim(stf.FIRST_NM) AS firstName, "
      + "  trim(stf.LAST_NM) AS lastName, "
      + "  stf.PHONE_NO AS phoneNumber, "
      + "  trim(stf.EMAIL_ADDR) AS email "
      + "FROM {h-schema}STFPERST stf "
      + "  LEFT JOIN {h-schema}STFUATHT a ON a.FKSTFPERST = stf.IDENTIFIER "
      + "  LEFT JOIN {h-schema}USERID_T user_id ON user_id.FKSTFPERST = stf.IDENTIFIER "
      + "WHERE a.FKASG_UNIT IN ( "
      + "  SELECT DISTINCT u.IDENTIFIER FROM {h-schema}ASG_UNIT u "
      + "      LEFT JOIN {h-schema}STFUATHT a ON a.FKASG_UNIT = u.IDENTIFIER "
      + "    WHERE a.UNTAUTH_CD = 'S' "
      + "      AND a.END_DT IS NULL "
      + "      AND a.FKSTFPERST = ?1) "
      + "  AND a.UNTAUTH_CD <> 'S' "
      + "  AND stf.END_DT IS NULL"
)
@SqlResultSetMapping(
  name = StaffBySupervisor.MAPPING_STAFF_BY_SUPERVISOR,
  classes =
  @ConstructorResult(
    targetClass = StaffBySupervisor.class,
    columns = {
      @ColumnResult(name = "identifier"),
      @ColumnResult(name = "racfId"),
      @ColumnResult(name = "firstName"),
      @ColumnResult(name = "lastName"),
      @ColumnResult(name = "phoneNumber", type = String.class),
      @ColumnResult(name = "email")
    }
  )
)
public class StaffPerson extends BaseStaffPerson {

  private static final long serialVersionUID = 5518501308828983602L;
}
