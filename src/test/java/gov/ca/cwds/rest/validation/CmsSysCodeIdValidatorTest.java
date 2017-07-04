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

import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.TestSystemCodeCache;

public class CmsSysCodeIdValidatorTest {

  private static final class AnnoTestBean implements Serializable {

    @CmsSysCodeId(category = "ABS_BPTC", required = true)
    private Short prop1;

    private String prop2;

    public AnnoTestBean() {

    }

    public AnnoTestBean(Short prop1, String prop2) {
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
    TestSystemCodeCache.init();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsSysCodeIdValidator.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsSysCodeIdValidator target = new CmsSysCodeIdValidator();
    assertThat(target, notNullValue());
  }

  @Test
  public void initialize_Args__CmsSysCodeId() throws Exception {
    CmsSysCodeIdValidator target = new CmsSysCodeIdValidator();
    CmsSysCodeId anno = mock(CmsSysCodeId.class);
    target.initialize(anno);
  }

  @Test
  public void isValid_Args__Object__ConstraintValidatorContext() throws Exception {
    CmsSysCodeIdValidator target = new CmsSysCodeIdValidator();
    Short value = new Short((short) 456);
    ConstraintValidatorContext context_ = mock(ConstraintValidatorContext.class);
    boolean actual = target.isValid(value, context_);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void validateManually() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean((short) 19, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    System.out.println(violations);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void validateManually2() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean((short) 6404, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    System.out.println(violations);
    assertFalse(violations.isEmpty());
  }

}

