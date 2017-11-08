package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.text.SimpleDateFormat;
import javax.persistence.ParameterMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */

public class SsaName3Dao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SsaName3Dao.class);

  private final SessionFactory sessionFactory;

  @Inject
  public SsaName3Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Call DB2 stored procedure SPSSANAME3 to insert soundex records for client search. Story
   * #146481759.
   *
   * @param parameterObject.tableName table name
   * @param crudOper CRUD operation (I/U/D)
   * @param identifier legacy identifier
   * @param nameCd name code
   * @param firstName first name
   * @param middleName middle name
   * @param lastName last name
   * @param streettNumber street number
   * @param streetName street name
   * @param gvrEntc government entity code
   * @param updateTimeStamp update timestamp
   * @param updateId updated by user id
   */
  public void callStoredProc(SsaName3ParameterObject parameterObject) {
    Session session = sessionFactory.getCurrentSession();
    final String STORED_PROC_NAME = "SPSSANAME3";
    final String schema =
        (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
    String strdtts =
        new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS")
            .format(parameterObject.getUpdateTimeStamp());

    try {
      ProcedureCall q = session.createStoredProcedureCall(schema + "." + STORED_PROC_NAME);

      q.registerStoredProcedureParameter("TABLENAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("CRUDFUNCT", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("IDENTIFIER", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("NAMECODE", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("FIRSTNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("MIDDLENAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("STREETNUM", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("STREETNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("GVRENTC", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTUPDTM", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTUPDID", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("RETSTATUS", String.class, ParameterMode.OUT);
      q.registerStoredProcedureParameter("RETMESSAG", String.class, ParameterMode.OUT);

      q.setParameter("TABLENAME", parameterObject.getTableName());
      q.setParameter("CRUDFUNCT", parameterObject.getCrudOper());
      q.setParameter("IDENTIFIER", parameterObject.getIdentifier());
      q.setParameter("NAMECODE", parameterObject.getNameCd());
      q.setParameter("FIRSTNAME", parameterObject.getFirstName());
      q.setParameter("MIDDLENAME", parameterObject.getMiddleName());
      q.setParameter("LASTNAME", parameterObject.getLastName());
      q.setParameter("STREETNUM", parameterObject.getStreettNumber());
      q.setParameter("STREETNAME", parameterObject.getStreetName());
      q.setParameter("GVRENTC", String.valueOf(parameterObject.getGvrEntc()));
      q.setParameter("LASTUPDTM", strdtts);
      q.setParameter("LASTUPDID", parameterObject.getUpdateId());

      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("TABLENAME = {}", parameterObject.getTableName());
        LOGGER.info("CRUDFUNCT = {}", parameterObject.getCrudOper());
        LOGGER.info("IDENTIFIER = {}", parameterObject.getIdentifier());
        LOGGER.info("NAMECODE = {}", parameterObject.getNameCd());
        LOGGER.info("FIRSTNAME = {}", parameterObject.getFirstName());
        LOGGER.info("MIDDLENAME = {}", parameterObject.getMiddleName());
        LOGGER.info("LASTNAME = {}", parameterObject.getLastName());
        LOGGER.info("STREETNUM = {}", parameterObject.getStreettNumber());
        LOGGER.info("STREETNAME = {}", parameterObject.getStreetName());
        LOGGER.info("GVRENTC = {}", String.valueOf(parameterObject.getGvrEntc()));
        LOGGER.info("LASTUPDTM = {}", strdtts);
        LOGGER.info("LASTUPDID = {}", parameterObject.getUpdateId());
      }

      q.execute();

      final String returnStatus = (String) q.getOutputParameterValue("RETSTATUS");
      final String returnMessage = (String) q.getOutputParameterValue("RETMESSAG");
      int returnCode = Integer.parseInt(returnStatus);

      LOGGER.info("storeProcReturnStatus: {}, storeProcreturnMessage: {}", returnStatus,
          returnMessage);
      /*
       * return code: 0=successful, 1=keys not generated, 2=Invalid parameters sent to stored
       * procedure 3=SQL failed, 4=Call to SSANAME3 DLL failed
       */
      if (returnCode != 0 && returnCode != 1) {
        LOGGER.error("Stored Procedure return message - {}", returnMessage);
        throw new DaoException("Stored Procedure returned with ERROR - {}" + returnMessage);
      }

    } catch (DaoException h) {
      throw new DaoException("Call to Stored Procedure failed - " + h, h);
    }
  }


}
