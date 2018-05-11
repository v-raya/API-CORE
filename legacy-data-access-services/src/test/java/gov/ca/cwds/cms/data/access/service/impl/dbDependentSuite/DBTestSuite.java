package gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite;

import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.R10030DBTest;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Suite.class)
@PrepareForTest({PrincipalUtils.class})
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*"})
@Suite.SuiteClasses({
//  SafetyAlertServiceTest.class,
//  CsecHistoryServiceTest.class,
//  R08840DBTest.class,
//  R08861DBTest.class,
//  R01128Test.class,
  R10030DBTest.class
})
public class DBTestSuite {
  @ClassRule
  public static final InMemoryTestResources inMemoryTestResources =
      InMemoryTestResources.getInstance();
}
