package gov.ca.cwds.data;

import java.util.List;

import gov.ca.cwds.data.persistence.PersistentObject;

@FunctionalInterface
public interface ApiResultSetHandler<T extends PersistentObject> {

  List<T> handleResultSet(List<Object> incoming);

}
