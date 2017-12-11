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

import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class NotEqualValidatorTest {

  private String abc;
  private String def;

  private NotEqual constraintAnnotation = mock(NotEqual.class);
  private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
  private ConstraintViolationBuilder builder = mock(ConstraintViolationBuilder.class);
  private NodeBuilderCustomizableContext nodeBuilder = mock(NodeBuilderCustomizableContext.class);

  private NotEqualValidator validator = new NotEqualValidator();

  @Before
  public void setup() throws Exception {
    when(constraintAnnotation.ifProperty()).thenReturn("abc");
    when(constraintAnnotation.thenProperty()).thenReturn("def");
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
    when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
  }

  @Test
  public void isValidReturnsFalseWhenIfAndThenPropertySame() throws Exception {
    validator.initialize(constraintAnnotation);
    NotEqualValidatorTest bean = new NotEqualValidatorTest();
    bean.abc = "abc";
    bean.def = "abc";
    assertThat(validator.isValid(bean, context), is(equalTo(false)));
    verify(context, times(1)).buildConstraintViolationWithTemplate(contains("cant be same as abc"));
  }

  @Test
  public void isValidReturnsTrueWhenIfAndThenPropertyDifferent() throws Exception {
    validator.initialize(constraintAnnotation);
    NotEqualValidatorTest bean = new NotEqualValidatorTest();
    bean.abc = "abc";
    bean.def = "def";
    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  @Test
  public void isValidReturnsTrueWhenThenPropertyNull() throws Exception {
    validator.initialize(constraintAnnotation);
    NotEqualValidatorTest bean = new NotEqualValidatorTest();
    bean.abc = "abc";
    bean.def = null;
    assertThat(validator.isValid(bean, context), is(equalTo(true)));
  }

  public String getAbc() {
    return abc;
  }

  public String getDef() {
    return def;
  }

}
