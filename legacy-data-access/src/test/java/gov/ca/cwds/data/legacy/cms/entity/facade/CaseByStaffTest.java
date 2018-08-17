package gov.ca.cwds.data.legacy.cms.entity.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType;

public class CaseByStaffTest extends BaseLegacyDataAccessTest<Client> {

  CaseByStaff target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    final String identifier = DEFAULT_CLIENT_ID;
    final String caseName = null;
    final String clientIdentifier = null;
    final String clientFirstName = null;
    final String clientLastName = null;
    final String activeServiceComponent = null;
    final String assignmentIdentifier = null;
    final String assignmentTypeCode = null;
    LocalDate assignmentStartDate = null;
    target =
        new CaseByStaff(identifier, caseName, clientIdentifier, clientFirstName, clientLastName,
            activeServiceComponent, assignmentIdentifier, assignmentTypeCode, assignmentStartDate);
  }

  @Test
  public void type() throws Exception {
    assertThat(CaseByStaff.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getIdentifier_A$() throws Exception {
    final String actual = target.getIdentifier();
    final String expected = DEFAULT_CLIENT_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIdentifier_A$String() throws Exception {
    final String identifier_ = null;
    target.setIdentifier(identifier_);
  }

  @Test
  public void getCaseName_A$() throws Exception {
    final String actual = target.getCaseName();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCaseName_A$String() throws Exception {
    final String caseName_ = null;
    target.setCaseName(caseName_);
  }

  @Test
  public void getClientIdentifier_A$() throws Exception {
    final String actual = target.getClientIdentifier();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientIdentifier_A$String() throws Exception {
    final String clientIdentifier_ = null;
    target.setClientIdentifier(clientIdentifier_);
  }

  @Test
  public void getClientFirstName_A$() throws Exception {
    final String actual = target.getClientFirstName();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientFirstName_A$String() throws Exception {
    final String clientFirstName_ = null;
    target.setClientFirstName(clientFirstName_);
  }

  @Test
  public void getClientLastName_A$() throws Exception {
    final String actual = target.getClientLastName();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientLastName_A$String() throws Exception {
    final String clientLastName_ = null;
    target.setClientLastName(clientLastName_);
  }

  @Test
  public void getActiveServiceComponent_A$() throws Exception {
    final String actual = target.getActiveServiceComponent();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setActiveServiceComponent_A$String() throws Exception {
    final String activeServiceComponent_ = null;
    target.setActiveServiceComponent(activeServiceComponent_);
  }

  @Test
  public void getAssignmentIdentifier_A$() throws Exception {
    final String actual = target.getAssignmentIdentifier();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignmentIdentifier_A$String() throws Exception {
    final String assignmentIdentifier_ = null;
    target.setAssignmentIdentifier(assignmentIdentifier_);
  }

  @Test
  public void getAssignmentType_A$() throws Exception {
    final AssignmentType actual = target.getAssignmentType();
    final AssignmentType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignmentType_A$AssignmentType() throws Exception {
    final AssignmentType assignmentType = AssignmentType.PRIMARY;
    target.setAssignmentType(assignmentType);
  }

  @Test
  public void getAssignmentStartDate_A$() throws Exception {
    final LocalDate actual = target.getAssignmentStartDate();
    final LocalDate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignmentStartDate_A$LocalDate() throws Exception {
    final LocalDate assignmentStartDate_ = LocalDate.now();
    target.setAssignmentStartDate(assignmentStartDate_);
  }

}
