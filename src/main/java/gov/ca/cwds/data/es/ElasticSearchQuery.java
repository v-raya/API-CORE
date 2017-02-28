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
      queryBuilder.should(QueryBuilders.matchQuery("firstName", term));
      queryBuilder.should(QueryBuilders.matchQuery("lastName", term));
      return queryBuilder;
    }

  },

  /**
   * Social Security Number
   */
  SSN {

    @Override
    boolean match(String term) {
      return term.matches("\\d+") || term.matches("\\d\\d\\d-\\d\\d-\\d\\d\\d\\d");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      queryBuilder.should(QueryBuilders.matchQuery("ssn", term.replace("-", "")));
      return queryBuilder;
    }

  },

  /**
   * Date Of Birth
   */
  DOB {

    @Override
    boolean match(String term) {
      return term.matches("[0-9-/]+") && !term.matches("\\d\\d\\d-\\d\\d-\\d\\d\\d\\d");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      if (term.contains("/")) {
        try {
          DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
          Date dob = df.parse(term);
          if (dob != null) {
            DateFormat esdf = new SimpleDateFormat("yyyy-mm-dd");
            queryBuilder.should(QueryBuilders.matchQuery("dateOfBirth", esdf.format(dob)));
            return queryBuilder;
          }
        } catch (Exception e) {
          return queryBuilder;
        }
      }
      queryBuilder.should(QueryBuilders.matchQuery("dateOfBirth", term));
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
