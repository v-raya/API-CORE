package gov.ca.cwds.data.es.client;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

public class RawCase extends ClientReference implements NeutronJdbcReader<RawCase> {

  private static final long serialVersionUID = 1L;

  // ====================================
  // CASE_T: (is there an open case)
  // =====================================

  @Column(name = "CAS_IDENTIFIER")
  private String openCaseId;

  @Column(name = "CAS_RSP_AGY_CD")
  private String openCaseResponsibleAgencyCode;

  @Override
  public RawCase read(ResultSet rs) throws SQLException {
    super.read(rs);
    openCaseId = trimToEmpty(rs.getString("CAS_IDENTIFIER"));
    openCaseResponsibleAgencyCode = trimToEmpty(rs.getString("CAS_RSP_AGY_CD"));
    return this;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new VarargPrimaryKey(getCltId(), openCaseId);
  }

  public String getOpenCaseId() {
    return openCaseId;
  }

  public void setOpenCaseId(String openCaseId) {
    this.openCaseId = openCaseId;
  }

  public String getOpenCaseResponsibleAgencyCode() {
    return openCaseResponsibleAgencyCode;
  }

  public void setOpenCaseResponsibleAgencyCode(String openCaseResponsibleAgencyCode) {
    this.openCaseResponsibleAgencyCode = openCaseResponsibleAgencyCode;
  }

}