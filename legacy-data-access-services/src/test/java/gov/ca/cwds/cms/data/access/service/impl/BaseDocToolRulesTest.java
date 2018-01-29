package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertTrue;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;

/** @author CWDS CALS API Team */
public abstract class BaseDocToolRulesTest {

  protected DroolsService droolsService;
  protected PerryAccount principal;

  @Before
  public void setUpBase() {
    droolsService = new DroolsService();
    principal = getPrincipal(getPrivilege());
  }

  public abstract String getPrivilege();

  protected void assertRuleSatisfied(String ruleName, BusinessValidationException e) {
    assertTrue(
        e.getValidationDetailsList()
            .stream()
            .noneMatch(issueDetails -> issueDetails.getCode().equals(ruleName)));
  }

  protected void assertRuleViolatedOnce(String ruleName, BusinessValidationException e) {
    assertTrue(
        e.getValidationDetailsList()
                .stream()
                .filter(issueDetails -> issueDetails.getCode().equals(ruleName))
                .count()
            == 1);
  }

  protected void assertRuleViolated(String ruleName, BusinessValidationException e, int count) {
    assertTrue(
        e.getValidationDetailsList()
            .stream()
            .filter(issueDetails -> issueDetails.getCode().equals(ruleName))
            .count()
            == count);
  }



  protected void assertRuleValid(String ruleName, BusinessValidationException e) {
    assertTrue(
        e.getValidationDetailsList()
            .stream()
            .noneMatch(issueDetails -> issueDetails.getCode().equals(ruleName)));
  }

  private static PerryAccount getPrincipal(String privilege) {
    PerryAccount perryAccount = new PerryAccount();
    Set<String> privileges = new HashSet<>();
    privileges.add(privilege);
    perryAccount.setPrivileges(privileges);
    return perryAccount;
  }
}
