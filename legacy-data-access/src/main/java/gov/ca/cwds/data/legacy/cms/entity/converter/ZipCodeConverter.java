package gov.ca.cwds.data.legacy.cms.entity.converter;

/**
 * Created by TPT2 on 12/20/2017.
 */
public class ZipCodeConverter extends BaseZipConverter<Integer> {
  public ZipCodeConverter() {
    super(5);
  }

  @Override
  Integer valueOf(String s) {
    return Integer.valueOf(s);
  }
}