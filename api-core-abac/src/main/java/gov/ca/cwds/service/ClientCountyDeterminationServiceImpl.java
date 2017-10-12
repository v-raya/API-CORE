package gov.ca.cwds.service;

import com.google.inject.Inject;
import gov.ca.cwds.data.dao.cms.CaseDao;
import gov.ca.cwds.data.dao.cms.ClientDao;
import gov.ca.cwds.data.dao.cms.ReferralClientDao;
import gov.ca.cwds.data.dao.cms.ReferralDao;
import gov.ca.cwds.rules.CountyDeterminationFact;
import gov.ca.cwds.rules.RulesService;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationServiceImpl implements ClientCountyDeterminationService {

  private CaseDao caseDao;
  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private ReferralDao referralDao;

  /**
   * Default no-arg constructor.
   */
  protected ClientCountyDeterminationServiceImpl() {
  }

  @Inject
  public ClientCountyDeterminationServiceImpl(CaseDao caseDao, ClientDao clientDao,
      ReferralClientDao referralClientDao, ReferralDao referralDao) {
    this.caseDao = caseDao;
    this.clientDao = clientDao;
    this.referralClientDao = referralClientDao;
    this.referralDao = referralDao;
  }

  @Override
  public String getClientCountyById(String clientId) {





    return null;
  }

}
