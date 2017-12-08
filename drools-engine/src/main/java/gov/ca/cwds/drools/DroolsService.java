package gov.ca.cwds.drools;

import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.HashSet;
import java.util.Set;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author CWDS CALS API Team
 */

public class DroolsService {

  public Set<IssueDetails> performBusinessRules(Object obj,
      DroolsConfiguration droolsConfiguration) throws DroolsException {

    KieSession kSession = null;
    try {
      KieContainer kieContainer = droolsConfiguration.getKieContainer();
      kSession = kieContainer.newKieSession(droolsConfiguration.getSessionName());
      kSession.insert(obj);
      Set<IssueDetails> validationDetailsList = new HashSet<>();
      kSession.setGlobal("validationDetailsList", validationDetailsList);
      kSession.getAgenda().getAgendaGroup(droolsConfiguration.getAgendaGroup()).setFocus();
      kSession.fireAllRules();
      return validationDetailsList;
    } finally {
      if (kSession != null) {
        kSession.dispose();
      }
    }
  }

}
