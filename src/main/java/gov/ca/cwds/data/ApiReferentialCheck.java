package gov.ca.cwds.data;

import java.util.function.Function;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * Validate foreign key relationships or other referential integrity. Trap referential integrity
 * (RI) errors as a <strong>last resort</strong>.
 * 
 * @author CWDS API Team
 * @param <T> persistent object
 * @see ApiHibernateInterceptor
 */
@FunctionalInterface
public interface ApiReferentialCheck<T extends PersistentObject>
    extends ApiMarker, Function<T, Boolean> {

}
