package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * Domain API {@link Request} class for ElasticSearch.
 * 
 * <p>
 * This class was originally designed to generate nested boolean queries in ElasticSearch 1.x, but
 * ElasticSearch 2.x prefers MUST/MUST_NOT/SHOULD over AND/OR/NOT and even deprecated the latter.
 * </p>
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public final class ESSearchRequest extends DomainObject implements Request {

  private static final long serialVersionUID = 1L;

  // ================
  // ENUMS:
  // ================

  /**
   * 
   * <p>
   * ORIGINAL IDEA: nest logical operations like a traditional database WHERE clause, but ES now
   * prefers "must", "must not", "should".
   * </p>
   * 
   * @author CWDS API Team
   */
  public enum LogicalOperation {

    /**
     * Legacy ElasticSearch operation, boolean AND.
     */
    AND("AND"),

    /**
     * Legacy ElasticSearch operation, boolean OR.
     */
    OR("OR"),

    /**
     * Legacy ElasticSearch operation, boolean NOT.
     */
    NOT("OR"),

    /**
     * Preferred ElasticSearch operation, MUST.
     */
    MUST("MUST"),

    /**
     * Preferred ElasticSearch operation, MUST NOT.
     */
    MUST_NOT("MUST_NOT"),

    /**
     * Preferred ElasticSearch operation, SHOULD.
     * 
     * <p>
     * Perceived accuracy of the normative "should" depends on the query type.
     * </p>
     */
    SHOULD("SHOULD");

    /**
     * The actual ElasticSearch operation command.
     */
    private final String text;

    /**
     * Enum constructor sets immutable members.
     * 
     * @param text ElasticSearch operation text
     */
    LogicalOperation(String text) {
      this.text = text;
    }

    /**
     * Getter for ElasticSearch operation text.
     * 
     * @return ElasticSearch operation text
     */
    public final String text() {
      return text;
    }
  }

  /**
   * Type of ElasticSearch query.
   * 
   * <p>
   * Some query types take multiple parameters, such as range and terms queries.
   * </p>
   * 
   * @author CWDS API Team
   */
  public enum QueryType {

    /**
     * ElasticSearch query type, match.
     */
    MATCH("match"),

    /**
     * ElasticSearch query type, term.
     */
    TERM("term"),

    /**
     * ElasticSearch query type, terms.
     */
    TERMS("terms"),

    /**
     * ElasticSearch query type, fuzzy.
     */
    FUZZY("FUZZY"),

    /**
     * ElasticSearch query type, range.
     */
    RANGE("RANGE"),

    /**
     * ElasticSearch query type, simple.
     */
    SIMPLE("SIMPLE"),

    /**
     * ElasticSearch query type, regular expression.
     */
    REGEXP("REGEXP"),

    /**
     * ElasticSearch query type, wildcard.
     */
    WILDCARD("WILDCARD"),

    /**
     * ElasticSearch query type, "all".
     */
    ALL("ALL");

    private final String text;

    /**
     * Enum constructor sets immutable members.
     * 
     * @param text ElasticSearch operation text
     */
    QueryType(String text) {
      this.text = text;
    }

    /**
     * Getter for ElasticSearch query text.
     * 
     * @return ElasticSearch query text
     */
    public final String text() {
      return text;
    }
  }

  public enum ElementType {
    GROUP, FIELD_TERM
  }

  // ================
  // INNER CLASSES:
  // ================

  /**
   * Node in a tree of groupings and search terms.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  @FunctionalInterface
  public static interface ESSearchElement extends Serializable {
    ElementType getElementType();
  }

  /**
   * Logical grouping node, contains any number of search terms or groups.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  public static final class ESSearchGroup implements ESSearchElement, Serializable {

    private static final long serialVersionUID = -4882405417378548558L;

    private LogicalOperation logic = LogicalOperation.OR;
    private List<ESSearchElement> elems = new ArrayList<>();

    public ESSearchGroup() {}

    public ESSearchGroup(LogicalOperation logic, List<ESSearchElement> elems) {
      super();
      this.logic = logic;
      this.elems = elems;
    }

    public ESSearchGroup(LogicalOperation logic) {
      super();
      this.logic = logic;
    }

    public LogicalOperation getLogic() {
      return logic;
    }

    public void setLogic(LogicalOperation logic) {
      this.logic = logic;
    }

    public List<ESSearchElement> getElems() {
      return elems;
    }

    public void setElems(List<ESSearchElement> elems) {
      this.elems = elems;
    }

    public void addElem(ESSearchElement elem) {
      elems.add(elem);
    }

    @Override
    public ElementType getElementType() {
      return ElementType.GROUP;
    }

    @Override
    public int hashCode() {
      int prime = 31;
      int result = 1;
      result = prime * result + ((elems == null) ? 0 : elems.hashCode());
      result = prime * result + ((logic == null) ? 0 : logic.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ESSearchGroup other = (ESSearchGroup) obj;
      if (elems == null) {
        if (other.elems != null)
          return false;
      } else if (!elems.equals(other.elems))
        return false;
      if (logic != other.logic)
        return false;
      return true;
    }
  }

  /**
   * Individual search term, comprised of field name, value to search, and query type.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  public static final class ESFieldSearchEntry implements ESSearchElement {

    private static final long serialVersionUID = 1L;

    /**
     * Document field to search.
     */
    private String field;

    /**
     * Value to search for.
     */
    private String value;

    @JsonProperty("query_type")
    private QueryType queryType = QueryType.ALL;

    @SuppressWarnings("javadoc")
    public ESFieldSearchEntry() {
      // Default no-op
    }

    public ESFieldSearchEntry(String field, String value, QueryType queryType) {
      super();
      this.field = field;
      this.value = value;
      this.queryType = queryType;
    }

    @JsonCreator
    public ESFieldSearchEntry(@JsonProperty("field") String field,
        @JsonProperty("value") String value) {
      super();
      this.field = field;
      this.value = value;
    }

    public String getField() {
      return field;
    }

    public String getValue() {
      return value;
    }

    public QueryType getQueryType() {
      return queryType;
    }

    public void setField(String field) {
      this.field = field;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public void setQueryType(QueryType queryType) {
      this.queryType = queryType;
    }

    @Override
    public int hashCode() {
      int prime = 31;
      int result = 1;
      result = prime * result + ((field == null) ? 0 : field.hashCode());
      result = prime * result + ((queryType == null) ? 0 : queryType.hashCode());
      return prime * result + ((value == null) ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ESFieldSearchEntry other = (ESFieldSearchEntry) obj;
      if (field == null) {
        if (other.field != null)
          return false;
      } else if (!field.equals(other.field))
        return false;
      if (queryType != other.queryType)
        return false;
      if (value == null) {
        if (other.value != null)
          return false;
      } else if (!value.equals(other.value))
        return false;
      return true;
    }

    @Override
    public ElementType getElementType() {
      return ElementType.FIELD_TERM;
    }
  }

  // ================
  // MEMBERS:
  // ================

  @JsonProperty("cwds_search")
  private ESSearchGroup root = new ESSearchGroup();

  @JsonProperty("document_type")
  private String documentType = "person";

  // ================
  // CTORS:
  // ================

  @SuppressWarnings("javadoc")
  public ESSearchRequest() {
    // Default constructor.
  }

  // ================
  // ACCESSORS:
  // ================

  public ESSearchGroup getRoot() {
    return root;
  }

  public void setRoot(ESSearchGroup root) {
    this.root = root;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  // ================
  // COMPARISON:
  // ================

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
