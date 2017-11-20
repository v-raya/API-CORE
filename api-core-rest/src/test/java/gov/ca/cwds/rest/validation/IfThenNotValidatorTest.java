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
public class IfThenNotValidatorTest {

  private boolean abc;
  private short def;
  private boolean value;
  private short notValue;

  private IfThenNot constraintAnnotation = mock(IfThenNot.class);
  private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
  private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
  private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);

  private IfThenNotValidator ifThenNotValidator = new IfThenNotValidator();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
    when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
    when(constraintAnnotation.ifProperty()).thenReturn("abc");
    when(constraintAnnotation.thenProperty()).thenReturn("def");
    when(constraintAnnotation.ifValue()).thenReturn(true);
    when(constraintAnnotation.thenNotValue()).thenReturn((short) 1519);
  }

  @Test
  public void isValidReturnsFalseWhenAnnotationValidates() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    ifThenNotValidator.initialize(constraintAnnotation);
    IfThenNotValidatorTest bean = new IfThenNotValidatorTest();
    bean.abc = true;
    bean.def = 1519;
    assertThat(ifThenNotValidator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1))
        .buildConstraintViolationWithTemplate(contains("is not valid since abc is set to true"));
  }

  @Test
  public void isValidReturnsTrueWhenThenValueFalse() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    ifThenNotValidator.initialize(constraintAnnotation);
    IfThenNotValidatorTest bean = new IfThenNotValidatorTest();
    bean.abc = false;
    bean.def = 1519;
    assertThat(ifThenNotValidator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenThenNotValueIs1516() throws Exception {
    when(constraintAnnotation.required()).thenReturn(true);
    ifThenNotValidator.initialize(constraintAnnotation);
    IfThenNotValidatorTest bean = new IfThenNotValidatorTest();
    bean.abc = true;
    bean.def = 1516;
    assertThat(ifThenNotValidator.isValid(bean, context), is(equalTo(true)));
  }

  public boolean isAbc() {
    return abc;
  }

  public short getDef() {
    return def;
  }

  public boolean isValue() {
    return value;
  }

  public short getNotValue() {
    return notValue;
  }

}
