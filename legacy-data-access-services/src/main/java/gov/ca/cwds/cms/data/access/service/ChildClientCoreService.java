package gov.ca.cwds.cms.data.access.service;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.dao.ChildClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.CreditReportHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.FCEligibilityDao;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDao;
import gov.ca.cwds.data.legacy.cms.dao.HealthReferralDao;
import gov.ca.cwds.data.legacy.cms.dao.HealthScreeningDao;
import gov.ca.cwds.data.legacy.cms.dao.MedicalEligibilityApplicationDao;
import gov.ca.cwds.data.legacy.cms.dao.ParentalRightsTerminationDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.SchoolOriginHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialEducationDao;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.CreditReportHistory;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.HealthReferral;
import gov.ca.cwds.data.legacy.cms.entity.HealthScreening;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory;
import gov.ca.cwds.data.legacy.cms.entity.SpecialEducation;
import java.util.Collection;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientCoreService extends ClientCoreService {

  @Inject private HealthInterventionPlanDao healthInterventionPlanDao;
  @Inject private ParentalRightsTerminationDao parentalRightsTerminationDao;
  @Inject private MedicalEligibilityApplicationDao medicalEligibilityApplicationDao;
  @Inject private FCEligibilityDao fcEligibilityDao;
  @Inject private CsecHistoryDao csecHistoryDao;
  @Inject private PaternityDetailDao paternityDetailDao;
  @Inject private CreditReportHistoryDao creditReportHistoryDao;
  @Inject private SpecialEducationDao specialEducationDao;
  @Inject private HealthReferralDao healthReferralDao;
  @Inject private SchoolOriginHistoryDao schoolOriginHistoryDao;
  @Inject private HealthScreeningDao healthScreeningDao;
  @Inject private ChildClientDao childClientDao;

  @Inject
  public ChildClientCoreService(ClientDao crudDao) {
    super(crudDao);
  }

  @Override
  public void beforeBusinessValidation(DataAccessBundle bundle) {
    super.beforeBusinessValidation(bundle);

    ChildClientEntityAwareDTO clientEntityAwareDTO = (ChildClientEntityAwareDTO) bundle.getAwareDto();

    ChildClient childClient = (ChildClient) clientEntityAwareDTO.getEntity();
    final String childClientId = childClient.getIdentifier();

    if (childClient.getAfdcFcEligibilityIndicatorVar()) {
      List<FCEligibility> fcEligibilities = fcEligibilityDao.findByChildClientId(childClientId);
      clientEntityAwareDTO.getFcEligibilities().addAll(fcEligibilities);
    }

    List<HealthInterventionPlan> activeHealthInterventionPlans =
        healthInterventionPlanDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.getActiveHealthInterventionPlans().addAll(activeHealthInterventionPlans);

    List<ParentalRightsTermination> parentalRightsTerminations =
        parentalRightsTerminationDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.getParentalRightsTerminations().addAll(parentalRightsTerminations);

    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        medicalEligibilityApplicationDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

    List<CsecHistory> csecHistories = csecHistoryDao.findByClientId(childClientId);
    clientEntityAwareDTO.getCsecHistories().addAll(csecHistories);

    List<PaternityDetail> paternityDetails = paternityDetailDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.setPaternityDetails(paternityDetails);

    List<CreditReportHistory> creditReportHistories =
        creditReportHistoryDao.findByClientId(childClientId);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistories);

    List<SpecialEducation> specialEducations = specialEducationDao.findByClientId(childClientId);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducations);

    List<HealthReferral> healthReferrals = healthReferralDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.getHealthReferrals().addAll(healthReferrals);

    final Collection<SchoolOriginHistory> schoolOriginHistories = schoolOriginHistoryDao
        .findByClientId(childClientId);
    clientEntityAwareDTO.getSchoolOriginHistories().addAll(schoolOriginHistories);

    List<HealthScreening> healthScreenings = healthScreeningDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.getHealthScreenings().addAll(healthScreenings);
  }
}
