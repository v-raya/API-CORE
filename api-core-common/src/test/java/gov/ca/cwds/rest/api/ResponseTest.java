package gov.ca.cwds.rest.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ResponseTest {

  private static final class TestResponse implements Response {

    @Override
    public boolean hasMessages() {
      return Response.super.hasMessages();
    }

    @Override
    public List<ErrorMessage> getMessages() {
      return Response.super.getMessages();
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(Response.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    Response target = new TestResponse();
    assertThat(target, notNullValue());
  }

  @Test
  public void hasMessages_Args__() throws Exception {
    Response target = new TestResponse();
    boolean actual = target.hasMessages();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessages_Args__() throws Exception {
    Response target = new TestResponse();
    List<ErrorMessage> actual = target.getMessages();
    List<ErrorMessage> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
