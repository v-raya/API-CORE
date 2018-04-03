package gov.ca.cwds.data.legacy.cms.persistence;

import gov.ca.cwds.data.legacy.cms.dao.CaseDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientOtherEthnicitiesTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientServiceProviderDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.CreditReportHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.FCEligibilityDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthReferralDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthScreeningDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.MedicalEligibilityApplicationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.OptimisticLockingTest;
import gov.ca.cwds.data.legacy.cms.dao.ParentalRightsTerminationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ReferralDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SchoolOriginHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SpecialEducationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.TribalAncestryNotificationDaoTest;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CaseDaoTest.class,
    ClientDaoTest.class,
    ClientOtherEthnicitiesTest.class,
    ClientRelationshipDaoTest.class,
    ClientServiceProviderDaoTest.class,
    CreditReportHistoryDaoTest.class,
    CsecHistoryDaoTest.class,
    DasHistoryDaoTest.class,
    DeliveredServiceDaoTest.class,
    FCEligibilityDaoTest.class,
    HealthInterventionPlanDaoTest.class,
    HealthReferralDaoTest.class,
    HealthScreeningDaoTest.class,
    MedicalEligibilityApplicationDaoTest.class,
    NearFatalityDaoTest.class,
    OptimisticLockingTest.class,
    ParentalRightsTerminationDaoTest.class,
    PaternityDetailDaoTest.class,
    PlacementEpisodeDaoTest.class,
    ReferralDaoTest.class,
    SafetyAlertDaoTest.class,
    SchoolOriginHistoryDaoTest.class,
    SpecialEducationDaoTest.class,
    TribalAncestryNotificationDaoTest.class
})
public class InMemoryDbTestsSuite {
  @ClassRule
  public static final InMemoryTestResources inMemoryTestResources = InMemoryTestResources.getInstance();
}
