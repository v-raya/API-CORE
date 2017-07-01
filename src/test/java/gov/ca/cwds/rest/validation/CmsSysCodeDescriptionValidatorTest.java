package gov.ca.cwds.rest.validation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.TestSystemCodeCache;

public class CmsSysCodeDescriptionValidatorTest {

  private static final class AnnoTestBean implements Serializable {

    @CmsSysCodeDescription(category = "", required = true)
    private String prop1;

    private String prop2;

    public AnnoTestBean() {

    }

    public AnnoTestBean(String prop1, String prop2) {
      this.prop1 = prop1;
      this.prop2 = prop2;
    }

    String getProp1() {
      return prop1;
    }

    void setProp1(String prop1) {
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
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsSysCodeDescriptionValidator.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsSysCodeDescriptionValidator target = new CmsSysCodeDescriptionValidator();
    assertThat(target, notNullValue());
  }

  @Test
  public void initialize_Args__CmsSysCodeId() throws Exception {
    CmsSysCodeDescriptionValidator target = new CmsSysCodeDescriptionValidator();
    CmsSysCodeDescription anno = mock(CmsSysCodeDescription.class);
    target.initialize(anno);
  }

  @Test
  public void isValid_Args__Object__ConstraintValidatorContext() throws Exception {
    CmsSysCodeDescriptionValidator target = new CmsSysCodeDescriptionValidator();
    String value = "";
    ConstraintValidatorContext context_ = mock(ConstraintValidatorContext.class);
    // when(context_.)
    boolean actual = target.isValid(value, context_);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void validateManually() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean("one", "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    assertFalse(violations.isEmpty());

  }

}

