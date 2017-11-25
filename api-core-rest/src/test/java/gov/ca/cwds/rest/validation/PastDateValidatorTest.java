package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;

import org.junit.Before;
import org.junit.Test;

public class PastDateValidatorTest {

  ConstraintValidatorContext context;
  PastDate constraintAnnotation;
  PastDateValidator target;

  @Before
  public void setup() throws Exception {
    constraintAnnotation = mock(PastDate.class);
    context = mock(ConstraintValidatorContext.class);

    when(constraintAnnotation.format()).thenReturn("yyyy-MM-dd");
    when(constraintAnnotation.required()).thenReturn(true);

    target = new PastDateValidator();
    target.initialize(constraintAnnotation);
  }

  @Test
  public void initialize() throws Exception {
    target.initialize(constraintAnnotation);
  }

  @Test
  public void isValid__simple() throws Exception {
    final String value = "2017-11-25";
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(true)));
  }

  @Test
  public void isValid__not_required() throws Exception {
    when(constraintAnnotation.required()).thenReturn(false);
    final String value = "2017-11-25";
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(true)));
  }

  @Test
  public void isValid__null_required() throws Exception {
    final String value = null;
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(false)));
  }

  @Test
  public void isValid__null_not_required() throws Exception {
    when(constraintAnnotation.required()).thenReturn(false);
    final String value = null;
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(false)));
  }

  @Test
  public void isValid__invalid_date() throws Exception {
    final String value = "more Phineas, less Candice";
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(false)));
  }

  @Test
  public void isValid__future_date() throws Exception {
    final String value = "2045=12-25";
    final boolean actual = target.isValid(value, context);
    assertThat(actual, is(equalTo(false)));
  }

}
