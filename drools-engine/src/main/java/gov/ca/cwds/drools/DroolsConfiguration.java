package gov.ca.cwds.drools;

/**
 * @author CWDS CALS API Team
 */

public interface DroolsConfiguration<I> {

  String getAgendaGroup();

  String getDroolsSessionName();

  default Object getValidatedFact(I input) {
    return input;
  }
}
