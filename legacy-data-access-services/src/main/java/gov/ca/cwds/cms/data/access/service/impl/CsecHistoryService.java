package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

/** @author CWDS TPT-3 Team */
public class CsecHistoryService {

  private final CsecHistoryDao csecHistoryDao;

  @Inject
  public CsecHistoryService(CsecHistoryDao csecHistoryDao) {
    this.csecHistoryDao = csecHistoryDao;
  }

  public List<CsecHistory> findByClientId(@Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return csecHistoryDao.findByClientId(clientId);
  }

  public void updateCsecHistoriesByClientId(
      String clientId, List<CsecHistory> updatedCsecHistories) {

    Collection<CsecHistory> persistedCsecHistories = csecHistoryDao.findByClientId(clientId);
    Map<Serializable, CsecHistory> persistedMap =
        persistedCsecHistories
            .stream()
            .collect(Collectors.toMap(CsecHistory::getPrimaryKey, Function.identity()));

    String userId = PrincipalUtils.getStaffPersonId();
    LocalDateTime now = LocalDateTime.now();
    for (CsecHistory csecHistory : updatedCsecHistories) {
      CsecHistory persistedState = persistedMap.get(csecHistory.getPrimaryKey());
      if (persistedState == null) {
        createCsecHistory(userId, now, csecHistory);
      } else {
        updateCsecHistory(persistedState, csecHistory, userId, now);
      }
    }
    Map<Serializable, CsecHistory> updatedMap =
        updatedCsecHistories
            .stream()
            .collect(Collectors.toMap(CsecHistory::getPrimaryKey, Function.identity()));
    for (CsecHistory csecHistory : persistedCsecHistories) {
      Serializable primaryKey = csecHistory.getPrimaryKey();
      if (updatedMap.get(primaryKey) == null) {
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

  private void updateCsecHistory(
      CsecHistory persistedState, CsecHistory newState, String userId, LocalDateTime now) {
    persistedState.setEndDate(newState.getEndDate());
    persistedState.setSexualExploitationType(newState.getSexualExploitationType());
    persistedState.setStartDate(newState.getStartDate());
    persistedState.setLastUpdateId(userId);
    persistedState.setLastUpdateTime(now);
  }
}
