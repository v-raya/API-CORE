package gov.ca.cwds.rest.validation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

public class SystemCodeIdValidatorTest {

  public static final int INVALID_SYSTEM_CODE_VALUE = 456;
  public static final int ANOTHER_INVALID_SYSTEM_CODE_VALUE = 6404;
  public static final Short VALID_SYSTEM_CODE_VALUE = new Short((short)19);
  public static final int VALID_LOGICAL_CODE_VALUE = 10;
  public static final int INVALID_LOGICAL_CODE_VALUE = 4046;
  private SystemCodeIdValidator target;

  private static final class AnnoTestBean implements Serializable {

    @ValidSystemCodeId(category = "ABS_BPTC", required = true)
    private Short prop1;

    private String prop2;

    private String fieldName;

    public AnnoTestBean() {

    }

    public AnnoTestBean(Short prop1, String prop2 ) {
      this.prop1 = prop1;
      this.prop2 = prop2;
    }

    Short getProp1() {
      return prop1;
    }

    void setProp1(Short prop1) {
      this.prop1 = prop1;
    }

    String getProp2() {
      return prop2;
    }

    void setProp2(String prop2) {
      this.prop2 = prop2;
    }

  }

  private static Validator validator;

  @BeforeClass
  public static void setupClass() {
    SystemCodeCache systemCodeCache = new TestSystemCodeCache();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Before
  public void before(){
    target = new SystemCodeIdValidator();
  }

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeIdValidator.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void initialize_Args__CmsSysCodeId() throws Exception {
    ValidSystemCodeId anno = mock(ValidSystemCodeId.class);
    target.initialize(anno);
  }

  @Test
  public void isValid_Args__Object__ConstraintValidatorContext() throws Exception {
    Short value = new Short((short) INVALID_SYSTEM_CODE_VALUE);
    ConstraintValidatorContext context_ = mock(ConstraintValidatorContext.class);
    boolean actual = target.isValid(value, context_);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void validateManually() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean(VALID_SYSTEM_CODE_VALUE, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    System.out.println(violations);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void validateManually2() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean((short) ANOTHER_INVALID_SYSTEM_CODE_VALUE, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    System.out.println(violations);
    assertFalse(violations.isEmpty());
  }
}
