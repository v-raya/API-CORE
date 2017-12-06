package gov.ca.cwds.data.legacy.cms.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "CASE_LDT")
public class CaseLoad extends BaseCaseLoad {

  private static final long serialVersionUID = 3059074296496554765L;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCASE_LDT")
  private List<StaffPersonCaseLoad> staffPersonCaseLoads;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCASE_LDT")
  private List<CaseAssignment> caseAssignments;

  @NotFound(action = NotFoundAction.IGNORE)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "IDENTIFIER", referencedColumnName = "FKCASE_LDT")
  private CaseLoadWeighting caseLoadWeighting;

  public List<StaffPersonCaseLoad> getStaffPersonCaseLoads() {
    return staffPersonCaseLoads;
  }

  public void setStaffPersonCaseLoads(List<StaffPersonCaseLoad> staffPersonCaseLoad) {
    this.staffPersonCaseLoads = staffPersonCaseLoad;
  }

  public List<CaseAssignment> getCaseAssignments() {
    return caseAssignments;
  }

  public void setCaseAssignments(
      List<CaseAssignment> caseAssignments) {
    this.caseAssignments = caseAssignments;
  }

  public CaseLoadWeighting getCaseLoadWeighting() {
    return caseLoadWeighting;
  }

  public void setCaseLoadWeighting(
      CaseLoadWeighting caseLoadWeightings) {
    this.caseLoadWeighting = caseLoadWeightings;
  }
}
