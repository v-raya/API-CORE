package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.Client;

public class SsaName3DaoTest extends LegacyBarneyTest<Client> {

  SsaName3Dao target;
  Query<Client> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new SsaName3Dao(sessionFactory);
    q = queryInator();

    when(proc.getOutputParameterValue("RETSTATUS")).thenReturn("0");
    when(proc.getOutputParameterValue("RETMESSAG")).thenReturn("hello world");
  }

  @Test
  public void type() throws Exception {
    assertThat(SsaName3Dao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void callStoredProc() throws Exception {
    final SsaName3ParameterObject pod = new SsaName3ParameterObject();
    pod.setCrudOper("U");
    pod.setUpdateTimeStamp(new Date());

    target.callStoredProc(pod);
  }

}

