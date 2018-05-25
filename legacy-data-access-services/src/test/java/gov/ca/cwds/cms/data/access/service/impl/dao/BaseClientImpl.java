package gov.ca.cwds.cms.data.access.service.impl.dao;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "CLIENT_T")
public class BaseClientImpl extends BaseClient {

}
