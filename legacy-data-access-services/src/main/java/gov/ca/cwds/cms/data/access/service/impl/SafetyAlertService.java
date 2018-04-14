package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;

import javax.inject.Inject;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class SafetyAlertService {

  private final SafetyAlertDao safetyAlertDao;

  @Inject
  public SafetyAlertService(SafetyAlertDao safetyAlertDao) {
    this.safetyAlertDao = safetyAlertDao;
  }

  public Collection<SafetyAlert> findSafetyAlertsByClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return safetyAlertDao.findByClientId(clientId);
  }

  public void updateSafetyAlertsByClientId(
      String clientId, List<SafetyAlert> updatedSafetyAlerts) {
    Collection<SafetyAlert> persistedSafetyAlerts = safetyAlertDao.findByClientId(clientId);
    Map<Serializable, SafetyAlert> persistedMap =
        persistedSafetyAlerts
            .stream()
            .collect(Collectors.toMap(SafetyAlert::getPrimaryKey, Function.identity()));
    String userId = PrincipalUtils.getStaffPersonId();
    LocalDateTime now = LocalDateTime.now();
    for (SafetyAlert safetyAlert : updatedSafetyAlerts) {
      SafetyAlert persistedState = persistedMap.get(safetyAlert.getPrimaryKey());
      if (persistedState == null) {
        createSafetyAlert(userId, now, safetyAlert);
      } else {
        updateSafetyAlert(persistedState, safetyAlert, userId, now);
      }
    }
    Map<Serializable, SafetyAlert> updatedMap =
        updatedSafetyAlerts
            .stream()
            .collect(Collectors.toMap(SafetyAlert::getPrimaryKey, Function.identity()));
    for (SafetyAlert safetyAlert : persistedSafetyAlerts) {
      if (updatedMap.get(safetyAlert.getPrimaryKey()) == null) {
        safetyAlertDao.delete(safetyAlert.getPrimaryKey());
      }
    }
  }

  private void createSafetyAlert(String userId, LocalDateTime now, SafetyAlert safetyAlert) {
    safetyAlert.setThirdId(IdGenerator.generateId());
    safetyAlert.setLastUpdateId(userId);
    safetyAlert.setLastUpdateTime(now);
    safetyAlertDao.create(safetyAlert);
  }

  private void updateSafetyAlert(
      SafetyAlert persistedState, SafetyAlert newState, String userId, LocalDateTime now) {
    persistedState.setActivationDate(newState.getActivationDate());
    persistedState.setActivationExplanationText(newState.getActivationExplanationText());
    persistedState.setActivationGovernmentEntityType(newState.getActivationGovernmentEntityType());
    persistedState.setActivationReasonType(newState.getActivationReasonType());
    persistedState.setDeactivationDate(newState.getDeactivationDate());
    persistedState.setDeactivationExplanationText(newState.getDeactivationExplanationText());
    persistedState.setDeactivationGovernmentEntityType(
        newState.getDeactivationGovernmentEntityType());
    persistedState.setLastUpdateId(userId);
    persistedState.setLastUpdateTime(now);
  }
}
