package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.*;

import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class AddressDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private AddressDao addressDao = null;

  @Before
  public void before() {
    addressDao = new AddressDao(sessionFactory);
  }

  @Test
  public void testConstructor() {
    assertEquals(sessionFactory, addressDao.getSessionFactory());
  }
}
