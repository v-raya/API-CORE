package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;

/** @author CWDS TPT-3 Team */
public class CsecHistoryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CsecHistoryService.class);

  private final CsecHistoryDao csecHistoryDao;

  @Inject
  public CsecHistoryService(CsecHistoryDao csecHistoryDao) {
    this.csecHistoryDao = csecHistoryDao;
  }

  public List<CsecHistory> findByClientId(@Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return csecHistoryDao.findByClientId(clientId);
  }

  public void updateCsecHistoriesByClientId(String clientId,
      List<CsecHistory> updatedCsecHistories) {

    final Collection<CsecHistory> persistedCsecHistories = csecHistoryDao.findByClientId(clientId);
    final Map<Serializable, CsecHistory> persistedMap = persistedCsecHistories.stream()
        .collect(Collectors.toMap(CsecHistory::getPrimaryKey, Function.identity()));

    final String userId = PrincipalUtils.getStaffPersonId();
    final LocalDateTime now = LocalDateTime.now();
    for (CsecHistory csecHistory : updatedCsecHistories) {
      final CsecHistory persistedState = persistedMap.get(csecHistory.getPrimaryKey());
      if (persistedState == null) {
        createCsecHistory(userId, now, csecHistory);
      } else {
        updateCsecHistory(persistedState, csecHistory, userId, now);
      }
    }

    final Map<Serializable, CsecHistory> updatedMap = updatedCsecHistories.stream()
        .collect(Collectors.toMap(CsecHistory::getPrimaryKey, Function.identity()));

    // WARNING: IBM doesn't delete from CSECHIST. Why are we??
    for (CsecHistory csecHistory : persistedCsecHistories) {
      final Serializable primaryKey = csecHistory.getPrimaryKey();
      if (updatedMap.get(primaryKey) == null) {
        LOGGER.warn("****** DELETE FROM CSECHIST! key: {} ******", primaryKey);
        csecHistoryDao.delete(primaryKey);
      }
    }
  }

  private void createCsecHistory(String userId, LocalDateTime now, CsecHistory csecHistory) {
    csecHistory.setThirdId(IdGenerator.generateId());
    csecHistory.setLastUpdateId(userId);
    csecHistory.setLastUpdateTime(now);
    csecHistory.setCreationTimestamp(now);
    csecHistoryDao.create(csecHistory);
  }

  private void updateCsecHistory(CsecHistory persistedState, CsecHistory newState, String userId,
      LocalDateTime now) {
    persistedState.setEndDate(newState.getEndDate());
    persistedState.setSexualExploitationType(newState.getSexualExploitationType());
    persistedState.setStartDate(newState.getStartDate());
    persistedState.setLastUpdateId(userId);
    persistedState.setLastUpdateTime(now);
  }

}
