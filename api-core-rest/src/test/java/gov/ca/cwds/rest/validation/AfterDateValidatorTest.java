package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AfterDateValidatorTest {

  private String abc;
  private String def;

  private AfterDateValid constraintAnnotation = mock(AfterDateValid.class);
  private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
  private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
  private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);

  private AfterDateValidator afterDateValidator = new AfterDateValidator();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
    when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
    when(constraintAnnotation.ifProperty()).thenReturn("abc");
    when(constraintAnnotation.thenProperty()).thenReturn("def");
  }

  @Test
  public void isValidReturnsFalseWhenThenPropertyIsLesser() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    afterDateValidator.initialize(constraintAnnotation);
    AfterDateValidatorTest bean = new AfterDateValidatorTest();
    bean.abc = "1995-10-23";
    bean.def = "1994-10-23";
    assertThat(afterDateValidator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1))
        .buildConstraintViolationWithTemplate(contains("should be greater than or equal to"));
  }

  @Test
  public void isValidReturnsTrueWhenThenPropertyIsGreater() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    afterDateValidator.initialize(constraintAnnotation);
    AfterDateValidatorTest bean = new AfterDateValidatorTest();
    bean.abc = "1995-10-23";
    bean.def = "1996-10-23";
    assertThat(afterDateValidator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenThenPropertyIsEqualToIfProperty() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    afterDateValidator.initialize(constraintAnnotation);
    AfterDateValidatorTest bean = new AfterDateValidatorTest();
    bean.abc = "1995-10-23";
    bean.def = "1995-10-23";
    assertThat(afterDateValidator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnTrueWhenThenPropertyNull() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    afterDateValidator.initialize(constraintAnnotation);
    AfterDateValidatorTest bean = new AfterDateValidatorTest();
    bean.abc = "1995-10-23";
    bean.def = null;
    assertThat(afterDateValidator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnFalseForInvalidDate() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    afterDateValidator.initialize(constraintAnnotation);
    AfterDateValidatorTest bean = new AfterDateValidatorTest();
    bean.abc = "1995-10-23";
    bean.def = "19:18/2005";
    assertThat(afterDateValidator.isValid(bean, context), is(equalTo(false)));
  }

  public String getAbc() {
    return abc;
  }

  public String getDef() {
    return def;
  }

}
