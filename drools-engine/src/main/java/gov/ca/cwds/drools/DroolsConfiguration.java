package gov.ca.cwds.drools;

import org.kie.api.runtime.KieContainer;

/**
 * @author CWDS CALS API Team
 */

public class DroolsConfiguration<T> {

  private String sessionName;
  private String agendaGroup;
  private String kieContainerId;

  public DroolsConfiguration(String sessionName, String agendaGroup, String kieContainerId) {
    this.sessionName = sessionName;
    this.agendaGroup = agendaGroup;
    this.kieContainerId = kieContainerId;
  }

  public KieContainer getKieContainer() throws DroolsException {
    return DroolsKieContainerHolder.INSTANCE.getKieContainer(kieContainerId);
  }

  public Object getValidatedFact(T input) {
    return input;
  }

  public String getSessionName() {
    return sessionName;
  }

  public void setSessionName(String sessionName) {
    this.sessionName = sessionName;
  }

  public String getAgendaGroup() {
    return agendaGroup;
  }

  public void setAgendaGroup(String agendaGroup) {
    this.agendaGroup = agendaGroup;
  }

}
