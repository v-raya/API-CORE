package gov.ca.cwds.rules;

/**
 * @author CWDS TPT-2
 */
public class CountyDeterminationFact {

  public CountyDeterminationFact(String clientCounty) {
    this.clientCounty = clientCounty;
  }

  private String clientCounty;

  public String getClientCounty() {
    return clientCounty;
  }

  public void setClientCounty(String clientCounty) {
    this.clientCounty = clientCounty;
  }
}
