package gov.ca.cwds.drools;

import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Drools Service.
 * @author CWDS CALS API Team
 */

public class DroolsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DroolsService.class);

  private static final String VAR_VALIDATION_DETAILS_LIST = "validationDetailsList";
  private static final String VAR_AUTHORIZATION_RESULT = "authorizationResult";

  /**
   *  Processing Business Rules.
   *
   * @param droolsConfiguration configuration.
   * @param facts facts for rules.
   * @return set of Issues.
   */
  public Set<IssueDetails> performBusinessRules(DroolsConfiguration droolsConfiguration,
      Object... facts) {
    KieSession kSession = null;
    try {
      kSession = initSession(droolsConfiguration);
      final Set<IssueDetails> validationDetailsList = new HashSet<>();
      kSession.setGlobal(VAR_VALIDATION_DETAILS_LIST, validationDetailsList);
      for (Object fact : facts) {
        kSession.insert(fact);
      }
      final int rulesFired = kSession.fireAllRules();
      log(droolsConfiguration, facts, rulesFired, validationDetailsList);
      return validationDetailsList;
    } catch (DroolsException e) {
      throw new IllegalStateException(e);
    } finally {
      disposeSessionSafely(kSession);
    }
  }

  /**
   * Perform authorisation rules.
   *
   * @param droolsConfiguration configuration.
   * @param facts facts for rules.
   * @return boolean result.
   */
  public boolean performAuthorizationRules(final DroolsConfiguration droolsConfiguration,
      final Collection<Object> facts) {
    KieSession kieSession = null;
    try {
      kieSession = initSession(droolsConfiguration);
      kieSession.setGlobal(VAR_AUTHORIZATION_RESULT, Boolean.FALSE);
      for (Object fact : facts) {
        kieSession.insert(fact);
      }
      final int rulesFired = kieSession.fireAllRules();
      final Boolean authorizationResult = (Boolean) kieSession.getGlobal(VAR_AUTHORIZATION_RESULT);
      log(droolsConfiguration, facts, rulesFired, authorizationResult);
      return authorizationResult;
    } catch (DroolsException e) {
      throw new IllegalStateException(e);
    } finally {
      disposeSessionSafely(kieSession);
    }
  }

  private static KieSession initSession(final DroolsConfiguration droolsConfiguration)
      throws DroolsException {
    final KieContainer kieContainer = droolsConfiguration.getKieContainer();
    final KieSession kSession = kieContainer.newKieSession(droolsConfiguration.getSessionName());
    kSession.getAgenda().getAgendaGroup(droolsConfiguration.getAgendaGroup()).setFocus();
    return kSession;
  }

  private static void disposeSessionSafely(final KieSession kSession) {
    if (kSession != null) {
      kSession.dispose();
    }
  }

  private void log(DroolsConfiguration droolsConfiguration, Object facts,
      int rulesFired, Object invocationResult) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Drools configuration: {}", droolsConfiguration);
      LOGGER.debug("Drools facts: {}", facts);
      LOGGER.debug("Rules fired: {}", rulesFired);
      LOGGER.debug("Drools invocation result: {}", invocationResult);
    }
  }

}
