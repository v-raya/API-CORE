package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.BaseEntityEnumConverterTest.TestCharacterToEntityConverterEntity.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.BaseEntityEnumConverterTest.TestStringToEntityConverterEntity.YES;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import gov.ca.cwds.data.legacy.cms.entity.enums.BaseEntityEnumConverterTest.TestCharacterToEntityConverterEntity.TestCharacterToEntityConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.BaseEntityEnumConverterTest.TestStringToEntityConverterEntity.TestStringToEntityConverter;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class BaseEntityEnumConverterTest {

  private TestStringToEntityConverter testSubjectString = new TestStringToEntityConverter();
  private TestCharacterToEntityConverter testSubjectCharacter = new TestCharacterToEntityConverter();

  @Test
  public void convertToEntityAttribute_success_forAllStringPositiveCases() {
    assertThat(testSubjectString.convertToEntityAttribute(null), is(nullValue()));
    assertThat(testSubjectString.convertToEntityAttribute(" "), is(nullValue()));
    assertThat(testSubjectString.convertToEntityAttribute("      "), is(nullValue()));
    assertThat(testSubjectString.convertToEntityAttribute("Y"), is(equalTo(YES)));
    assertThat(testSubjectString.convertToEntityAttribute(" Y   "), is(equalTo(YES)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownStringValue() {
    testSubjectString.convertToEntityAttribute("Unknown Value");
  }

  @Test
  public void convertToEntityAttribute_success_forAllCharacterPositiveCases() {
    assertThat(testSubjectCharacter.convertToEntityAttribute("N"), is(NO));
    assertThat(testSubjectCharacter.convertToEntityAttribute(null), is(nullValue()));
    assertThat(testSubjectCharacter.convertToEntityAttribute(" "), is(nullValue()));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownCharacterValue() {
    testSubjectCharacter.convertToEntityAttribute("U");
  }

  enum TestStringToEntityConverterEntity implements EntityEnum<String> {
    YES("Y", "Yes");

    private final String code;
    private final String description;

    TestStringToEntityConverterEntity(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getDescription() {
      return description;
    }

    @Converter
    public static class TestStringToEntityConverter extends BaseEntityEnumConverter<TestStringToEntityConverterEntity, String> {

      private static final Map<String, TestStringToEntityConverterEntity> codeMap =
          Collections.unmodifiableMap(initializeMapping(TestStringToEntityConverterEntity.values()));

      @Override
      Map<String, TestStringToEntityConverterEntity> getCodeMap() {
        return codeMap;
      }
    }
  }

  enum TestCharacterToEntityConverterEntity implements EntityEnum<String> {
    NO("N", "No");

    private final String code;
    private final String description;

    TestCharacterToEntityConverterEntity(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getDescription() {
      return description;
    }

    @Converter
    public static class TestCharacterToEntityConverter extends BaseEntityEnumConverter<TestCharacterToEntityConverterEntity, String> {

      private static final Map<String, TestCharacterToEntityConverterEntity> codeMap =
          Collections.unmodifiableMap(initializeMapping(TestCharacterToEntityConverterEntity.values()));

      @Override
      Map<String, TestCharacterToEntityConverterEntity> getCodeMap() {
        return codeMap;
      }
    }
  }

}