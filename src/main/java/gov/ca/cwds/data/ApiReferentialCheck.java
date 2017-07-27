package gov.ca.cwds.data;

import java.util.function.Function;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * Validate foreign key relationships or other referential integrity.
 * 
 * @author CWDS API Team
 * @param <T> persistent object
 */
@FunctionalInterface
public interface ApiReferentialCheck<T extends PersistentObject>
    extends ApiMarker, Function<T, Boolean> {

}
