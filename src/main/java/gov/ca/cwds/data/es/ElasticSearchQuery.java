package gov.ca.cwds.data.es;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

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
      queryBuilder.should(QueryBuilders.prefixQuery("firstName", term));
      queryBuilder.should(QueryBuilders.prefixQuery("lastName", term));
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
      if (term.contains("/")) {
        try {
          DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
          Date dob = df.parse(term);
          if (dob != null) {
            DateFormat esdf = new SimpleDateFormat("yyyy-mm-dd");
            queryBuilder.should(QueryBuilders.matchQuery(DATE_OF_BIRTH, esdf.format(dob)));
            return queryBuilder;
          }
        } catch (Exception e) {
          return queryBuilder;
        }
      }
      queryBuilder.should(QueryBuilders.matchQuery(DATE_OF_BIRTH, term));
      return queryBuilder;
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

  private static final String DATE_OF_BIRTH = "dateOfBirth";

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
