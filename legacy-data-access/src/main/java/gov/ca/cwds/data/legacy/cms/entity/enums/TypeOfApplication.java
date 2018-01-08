package gov.ca.cwds.data.legacy.cms.entity.enums;

import javax.persistence.Converter;
import java.util.Collections;
import java.util.Map;

public enum TypeOfApplication implements EntityEnum<String> {
    NEW_APPLICATION("NW", "New Application"),
    REDETERMINATION_APPLICATION("RD", "Redetermination Appication"),
    RETROACTIVE_APPLICATION("RT", "Retroactive Application");

    private final String code;
    private final String description;

    TypeOfApplication(String code, String description) {
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
    public static class TypeOfApplicationConverter
            extends BaseEntityEnumConverter<TypeOfApplication, String> {

        private static final Map<String, TypeOfApplication> codeMap =
                Collections.unmodifiableMap(initializeMapping(TypeOfApplication.values()));

        @Override
        Map<String, TypeOfApplication> getCodeMap() {
            return codeMap;
        }
    }
}
