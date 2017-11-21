package gov.ca.cwds.drools;

import gov.ca.cwds.rest.exception.IssueDetails;
import java.util.HashSet;
import java.util.Set;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author CWDS CALS API Team
 */

public class DroolsService {

  public Set<IssueDetails> validate(Object obj,
      DroolsConfiguration<?> configuration) {
    KieServices ks = KieServices.Factory.get();
    KieContainer kc = ks.getKieClasspathContainer();
    KieSession kSession = null;
    try {
      kSession = kc.newKieSession(configuration.getDroolsSessionName());
      kSession.insert(obj);
      Set<IssueDetails> validationDetailsList = new HashSet<>();
      kSession.setGlobal("validationDetailsList", validationDetailsList);
      kSession.getAgenda().getAgendaGroup(configuration.getAgendaGroup()).setFocus();
      kSession.fireAllRules();
      return validationDetailsList;
    } finally {
      if (kSession != null) {
        kSession.dispose();
      }
    }
  }


}
