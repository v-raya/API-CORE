package gov.ca.cwds.rules;

import static gov.ca.cwds.Constants.BusinessRulesAgendaGroups.CLIENT_COUNTY_DETERMINATION_GROUP;
import static gov.ca.cwds.Constants.BusinessRulesAgendaGroups.CLIENT_COUNTY_DETERMINATION_SESSION;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author CWDS TPT-2
 */
public class RulesService {

  public CountyDeterminationFact determineClientCounty(
      CountyDeterminationFact countyDeterminationFact) {
    KieServices ks = KieServices.Factory.get();
    KieContainer kc = ks.getKieClasspathContainer();
    KieSession kSession = null;
    try {
      kSession = kc.newKieSession(CLIENT_COUNTY_DETERMINATION_SESSION);
      kSession.insert(countyDeterminationFact);
      kSession.setGlobal("countyDeterminationFact", countyDeterminationFact);
      kSession.getAgenda().getAgendaGroup(CLIENT_COUNTY_DETERMINATION_GROUP).setFocus();
      kSession.fireAllRules();
      return countyDeterminationFact;
    } finally {
      if (kSession != null) {
        kSession.dispose();
      }
    }
  }


}