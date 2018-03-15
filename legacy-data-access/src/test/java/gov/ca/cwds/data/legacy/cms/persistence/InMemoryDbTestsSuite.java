package gov.ca.cwds.data.legacy.cms.persistence;

import gov.ca.cwds.data.legacy.cms.dao.CaseDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientOtherEthnicitiesTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDaoTest;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CaseDaoTest.class,
    ClientDaoTest.class,
    ClientOtherEthnicitiesTest.class,
    ClientRelationshipDaoTest.class
})
public class InMemoryDbTestsSuite {
  @ClassRule
  public static final InMemoryTestResources inMemoryTestResources = InMemoryTestResources.getInstance();
}
