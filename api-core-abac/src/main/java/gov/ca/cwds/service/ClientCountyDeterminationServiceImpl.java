package gov.ca.cwds.service;

import com.google.inject.Inject;
import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import java.util.List;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationServiceImpl implements ClientCountyDeterminationService {

  private CountyDeterminationDao countyDeterminationDao;

  /**
   * Default no-arg constructor.
   */
  protected ClientCountyDeterminationServiceImpl() {
  }

  @Inject
  public ClientCountyDeterminationServiceImpl(CountyDeterminationDao countyDeterminationDao) {
    this.countyDeterminationDao = countyDeterminationDao;
  }

  /**
   * This method determines client county by Client ID
   *
   * @param clientId Client ID
   * @return client county
   */
  @Override
  public Short getClientCountyById(String clientId) {

    //check CLIENT_CNTY table first
    List<Short> countyFromClientCountyTableList = countyDeterminationDao
        .getClientCountyFromClientCountyTable(clientId);
    if (!countyFromClientCountyTableList.isEmpty()) {
      return countyFromClientCountyTableList.get(0);
    }

    //I. Active Case for the Client (only one active Case should exist for a Client via CHILD_CLIENT)
    List<Short> countyByActiveCaseList = countyDeterminationDao
        .getClientCountyByActiveCase(clientId);
    if (!countyByActiveCaseList.isEmpty()) {
      return countyByActiveCaseList.get(0);
    }

    //II. Active Referral for the Client (if Client is in more than one active referral, the first
    // Referral found is used)
    List<Short> countyByActiveReferralList = countyDeterminationDao
        .getCountyByActiveReferrals(clientId);
    if (!countyByActiveReferralList.isEmpty()) {
      return countyByActiveReferralList.get(0);
    }

    //III. Closed Case/Referral for the Client (if more than one Closed Case/Referral exists
    // for Client, select the case/referral with the MAX End Date, with the Case winning if there is a tie)
    List<Short> countyByClosedCaseList = countyDeterminationDao
        .getClientCountyByClosedCase(clientId);
    if (!countyByClosedCaseList.isEmpty()) {
      return countyByClosedCaseList.get(0);
    }

    List<Short> countyByClosedReferralList = countyDeterminationDao
        .getClientCountyByClosedReferral(clientId);
    if (!countyByClosedReferralList.isEmpty()) {
      return countyByClosedReferralList.get(0);
    }

    // IV. 4. Any Client’s Active Case, with whom this Client is related
    // (if more than one related client with an Active case, the first Case found is used)
    List<Short> countyByClientAnyActiveCaseList = countyDeterminationDao
        .getClientByClientAnyActiveCase(clientId);
    if (!countyByClientAnyActiveCaseList.isEmpty()) {
      return countyByClientAnyActiveCaseList.get(0);
    }

    // V. Any Client’s Closed Case, with whom this Client is related
    // ( if more than one related client with a Closed Case, the first Case found is used)
    List<Short> countyByClientAnyClosedCaseList = countyDeterminationDao
        .getClientByClientAnyClosedCase(clientId);
    if (!countyByClientAnyClosedCaseList.isEmpty()) {
      return countyByClientAnyClosedCaseList.get(0);
    }

    //no county found for this client
    return null;
  }
}
