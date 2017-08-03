package gov.ca.cwds.rest.validation;

import static org.junit.Assert.*;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import java.io.Serializable;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogicalIdValidatorTest {

  private SystemCodeIdValidator target;

  private static Validator validator;

  public static final String VALID_LOGICAL_CODE_VALUE = "10";
  public static final String INVALID_LOGICAL_CODE_VALUE = "4046";

  @BeforeClass
  public static void setupClass() {
    SystemCodeCache systemCodeCache = new TestSystemCodeCache();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Before
  public void before() {
    target = new SystemCodeIdValidator();
  }

  @Test
  public void validatorShouldNotBeNullWhenConstructed() throws Exception {
    assertNotNull(target);
  }

  @Test
  public void shouldPassValidationWhenNotAValidCode() throws Exception {
    // final AnnoTestBean bean = new AnnoTestBean((short) VALID_SYSTEM_CODE_VALUE, "two");
    final AnnoTestBean bean = new AnnoTestBean(VALID_LOGICAL_CODE_VALUE, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void shouldFailValidationWhenNotAValidCode() throws Exception {
    final AnnoTestBean bean = new AnnoTestBean( INVALID_LOGICAL_CODE_VALUE, "two");
    Set<ConstraintViolation<AnnoTestBean>> violations = validator.validate(bean);
    assertFalse(violations.isEmpty());
  }
  private static final class AnnoTestBean implements Serializable {

    @ValidLogicalId(category = "GVR_ENTC", required = true)
    private String prop1;

    private String prop2;

    private String fieldName;

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
}