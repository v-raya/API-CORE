package gov.ca.cwds.data.es;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * 
 * @author CWDS API Team
 */
public enum ElasticSearchQuery {

  /**
   * Name
   */
  NAME {

    @Override
    boolean match(String term) {
      return term.matches("[a-z]+");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      queryBuilder.should(
          QueryBuilders.prefixQuery(ElasticSearchPerson.ESColumn.FIRST_NAME.getCol(), term));
      queryBuilder
          .should(QueryBuilders.prefixQuery(ElasticSearchPerson.ESColumn.LAST_NAME.getCol(), term));
      return queryBuilder;
    }

  },

  /**
   * Social Security Number and Birth Year
   */
  SSN {

    @Override
    boolean match(String term) {
      return term.matches("\\d{0,9}") || term.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      queryBuilder.should(QueryBuilders.prefixQuery("ssn", term.replace("-", "")));
      if (term.length() == 4) {
        String startDate = term + "-01-01";
        String endDate = term + "-12-31";
        queryBuilder.should(QueryBuilders.rangeQuery(DATE_OF_BIRTH).gte(startDate).lte(endDate));
      }
      return queryBuilder;
    }

  },

  /**
   * Date Of Birth
   */
  DOB {

    @Override
    boolean match(String term) {
      return term.matches("[0-9-/]+") && !term.matches("\\d{3}-\\d{2}-\\d{4}")
          && !term.matches("\\d{5,}") || term.matches("\\{1998}");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      String formattedDate = term;
      if (formattedDate.contains("/")) {
        try {
          DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
          Date dob = df.parse(formattedDate);
          if (dob != null) {
            DateFormat esdf = new SimpleDateFormat("yyyy-mm-dd");
            esdf.setLenient(false);
            formattedDate = esdf.format(dob);
          }
        } catch (Exception e) {
          return queryBuilder;
        }
      }
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        formatter = formatter.withResolverStyle(ResolverStyle.STRICT);
        LocalDate dateOfBirth = LocalDate.parse(formattedDate, formatter);
        formattedDate = dateOfBirth.format(formatter);
        queryBuilder.should(QueryBuilders.matchQuery(DATE_OF_BIRTH, formattedDate));
        return queryBuilder;
      } catch (Exception e) {
        return queryBuilder;
      }
    }

  },

  /**
   * UNKNOWN
   */
  UNKNOWN {

    @Override
    boolean match(String term) {
      return true;
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      return queryBuilder;
    }

  };

  private static final String DATE_OF_BIRTH = ElasticSearchPerson.ESColumn.BIRTH_DATE.getCol();

  public static ElasticSearchQuery getQueryFromTerm(String term) {
    for (ElasticSearchQuery query : values()) {
      if (query.match(term)) {
        return query;
      }

    }
    return UNKNOWN;
  }

  abstract boolean match(String term);

  abstract BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term);

}
