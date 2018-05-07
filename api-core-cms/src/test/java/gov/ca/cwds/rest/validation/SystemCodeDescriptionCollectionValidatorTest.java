package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * CWDS API Team
 */
public class SystemCodeDescriptionCollectionValidatorTest {
  private static final class AnnoTestBean implements Serializable {

    @ValidSystemCodeDescription(category = "ABS_BPTC", required = true)
    private List<String> prop1;

    private String prop2;

    public AnnoTestBean() {

    }

    public AnnoTestBean(List<String> prop1, String prop2) {
      this.prop1 = prop1;
      this.prop2 = prop2;
    }

    List<String> getProp1() {
      return prop1;
    }

    void setProp1(List<String> prop1) {
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
  private static SystemCodeCache systemCodeCache;

  @BeforeClass
  public static void setupClass() {
    SystemCodeCache systemCodeCache = new TestSystemCodeCache();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeDescriptionCollectionValidator.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SystemCodeDescriptionCollectionValidator target = new SystemCodeDescriptionCollectionValidator();
    assertThat(target, notNullValue());
  }

  @Test
  public void initialize_Args__CmsSysCodeId() throws Exception {
    SystemCodeDescriptionCollectionValidator target = new SystemCodeDescriptionCollectionValidator();
  }

  @Test
  public void isValid_Args__Object__ConstraintValidatorContext() throws Exception {
    SystemCodeDescriptionCollectionValidator target = new SystemCodeDescriptionCollectionValidator();
    ValidSystemCodeDescription anno = mock(ValidSystemCodeDescription.class);
    target.initialize(anno);

    List<String> value = Arrays.asList("");
    ConstraintValidatorContext context_ = mock(ConstraintValidatorContext.class);
    boolean actual = target.isValid(value, context_);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void validateManually() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean(Arrays.asList("Breasts"), "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void validateManually2() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean(Arrays.asList("djdjskshahfdsa"), "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    assertFalse(violations.isEmpty());
  }
}