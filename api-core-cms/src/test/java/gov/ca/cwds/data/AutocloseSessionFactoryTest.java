package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;

public class AutocloseSessionFactoryTest {

  @Test
  public void type() throws Exception {
    assertThat(AutocloseSessionFactory.class, notNullValue());
  }

  @Test
  @Ignore
  public void init_Args__String() throws Exception {
    String hibernateConfig = "hibernate.cfg.xml";
    AutocloseSessionFactory.init(hibernateConfig);
  }

  @Test
  @Ignore
  public void getSessionFactory_Args__() throws Exception {
    SessionFactory actual = AutocloseSessionFactory.getSessionFactory();
    SessionFactory expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
