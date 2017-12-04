package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS CALS API Team
 */
@NamedQuery(
    name = "Client.find",
    query = "SELECT c FROM Client c"
        + " JOIN c.placementEpisodes pe"
        + " JOIN pe.outOfHomePlacements ohp"
        + " JOIN ohp.placementHome ph"
        + " WHERE ph.licenseNo = :licenseNumber AND c.identifier = :childId"
)
@NamedQuery(
    name = "Client.findAll",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.licenseNo = :licenseNumber" +
        " ORDER BY c.identifier "
)
@NamedQuery(
    name = "Client.findByFacilityId",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.id = :facilityId"
)
@SuppressWarnings("squid:S3437") //LocalDate is serializable
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CLIENT_T")
public class Client extends CmsPersistentObject implements IClient, PersistentObject {

  private static final long serialVersionUID = -1570433180700848831L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @Column(name = "ADPTN_STCD", nullable = false, length = 1)
  private String adptnStcd;

  @Column(name = "ALN_REG_NO", nullable = false, length = 12)
  private String alnRegNo;

  @Column(name = "BIRTH_DT", nullable = true)
  private LocalDate birthDt;

  @Column(name = "BR_FAC_NM", nullable = false, length = 35)
  private String brFacNm;

  @Column(name = "B_STATE_C", nullable = false)
  private Short bStateC;

  @Column(name = "B_CNTRY_C", nullable = false)
  private Short bCntryC;

  @Column(name = "CHLD_CLT_B", nullable = false, length = 1)
  private String chldCltB;

  @Column(name = "COM_FST_NM", nullable = false, length = 20)
  private String comFstNm;

  @Column(name = "COM_LST_NM", nullable = false, length = 25)
  private String comLstNm;

  @Column(name = "COM_MID_NM", nullable = false, length = 20)
  private String comMidNm;

  @Column(name = "CONF_EFIND", nullable = false, length = 1)
  private String confEfind;

  @Column(name = "CONF_ACTDT", nullable = true)
  private LocalDate confActdt;

  @Column(name = "CREATN_DT", nullable = false)
  private LocalDate creatnDt;

  @Column(name = "DEATH_DT", nullable = true)
  private LocalDate deathDt;

  @Column(name = "DTH_RN_TXT", nullable = true, length = 10)
  private String dthRnTxt;

  @Column(name = "DRV_LIC_NO", nullable = false, length = 20)
  private String drvLicNo;

  @Column(name = "D_STATE_C", nullable = false)
  private Short dStateC;

  @Column(name = "GENDER_CD", nullable = false, length = 1)
  private String genderCd;

  @Column(name = "I_CNTRY_C", nullable = false)
  private Short iCntryC;

  @Column(name = "IMGT_STC", nullable = false)
  private Short imgtStc;

  @Column(name = "INCAPC_CD", nullable = false, length = 2)
  private String incapcCd;

  @Column(name = "LITRATE_CD", nullable = false, length = 1)
  private String litrateCd;

  @Column(name = "MAR_HIST_B", nullable = false, length = 1)
  private String marHistB;

  @Column(name = "MRTL_STC", nullable = false)
  private Short mrtlStc;

  @Column(name = "MILT_STACD", nullable = false, length = 1)
  private String miltStacd;

  @Column(name = "NMPRFX_DSC", nullable = false, length = 6)
  private String nmprfxDsc;

  @Column(name = "NAME_TPC", nullable = false)
  private Short nameTpc;

  @Column(name = "OUTWRT_IND", nullable = false, length = 1)
  private String outwrtInd;

  @Column(name = "P_ETHNCTYC", nullable = false)
  private Short pEthnctyc;

  @Column(name = "P_LANG_TPC", nullable = false)
  private Short pLangTpc;

  @Column(name = "RLGN_TPC", nullable = false)
  private Short rlgnTpc;

  @Column(name = "S_LANG_TC", nullable = false)
  private Short sLangTc;

  @Column(name = "SENSTV_IND", nullable = false, length = 1)
  private String senstvInd;

  @Column(name = "SNTV_HLIND", nullable = false, length = 1)
  private String sntvHlind;

  @Column(name = "SS_NO", nullable = false, length = 9)
  private String ssNo;

  @Column(name = "SSN_CHG_CD", nullable = false, length = 1)
  private String ssnChgCd;

  @Column(name = "SUFX_TLDSC", nullable = false, length = 4)
  private String sufxTldsc;

  @Column(name = "UNEMPLY_CD", nullable = false, length = 2)
  private String unemplyCd;

  @Column(name = "COMMNT_DSC", nullable = false, length = 120)
  private String commntDsc;

  @Column(name = "EST_DOB_CD", nullable = false, length = 1)
  private String estDobCd;

  @Column(name = "BP_VER_IND", nullable = false, length = 1)
  private String bpVerInd;

  @Column(name = "HISP_CD", nullable = false, length = 1)
  private String hispCd;

  @Column(name = "CURRCA_IND", nullable = false, length = 1)
  private String currcaInd;

  @Column(name = "CURREG_IND", nullable = false, length = 1)
  private String curregInd;

  @Column(name = "COTH_DESC", nullable = false, length = 25)
  private String cothDesc;

  @Column(name = "PREVCA_IND", nullable = false, length = 1)
  private String prevcaInd;

  @Column(name = "PREREG_IND", nullable = false, length = 1)
  private String preregInd;

  @Column(name = "POTH_DESC", nullable = false, length = 25)
  private String pothDesc;

  @Column(name = "HCARE_IND", nullable = false, length = 1)
  private String hcareInd;

  @Column(name = "LIMIT_IND", nullable = false, length = 1)
  private String limitInd;

  @Column(name = "BIRTH_CITY", nullable = false, length = 35)
  private String birthCity;

  @Column(name = "HEALTH_TXT", nullable = true, length = 10)
  private String healthTxt;

  @Column(name = "MTERM_DT", nullable = true)
  private LocalDate mtermDt;

  @Column(name = "FTERM_DT", nullable = true)
  private LocalDate ftermDt;

  @Column(name = "ZIPPY_IND", nullable = false, length = 1)
  private String zippyInd;

  @Column(name = "DEATH_PLC", nullable = true, length = 35)
  private String deathPlc;

  @Column(name = "TR_MBVRT_B", nullable = false, length = 1)
  private String trMbvrtB;

  @Column(name = "TRBA_CLT_B", nullable = false, length = 1)
  private String trbaCltB;

  @Column(name = "SOC158_IND", nullable = false, length = 1)
  private String soc158Ind;

  @Column(name = "DTH_DT_IND", nullable = false, length = 1)
  private String dthDtInd;

  @Column(name = "EMAIL_ADDR", nullable = true, length = 50)
  private String emailAddr;

  @Column(name = "ADJDEL_IND", nullable = true, length = 1)
  private String adjdelInd;

  @Column(name = "ETH_UD_CD", nullable = true, length = 1)
  private String ethUdCd;

  @Column(name = "HISP_UD_CD", nullable = true, length = 1)
  private String hispUdCd;

  @Column(name = "SOCPLC_CD", nullable = false, length = 1)
  private String socplcCd;

  @Column(name = "CL_INDX_NO", nullable = true, length = 12)
  private String clIndxNo;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
  @OrderBy("removalDt DESC")
  private Set<PlacementEpisode> placementEpisodes = new HashSet<>();

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }


  public String getAdptnStcd() {
    return adptnStcd;
  }

  public void setAdptnStcd(String adptnStcd) {
    this.adptnStcd = adptnStcd;
  }


  public String getAlnRegNo() {
    return alnRegNo;
  }

  public void setAlnRegNo(String alnRegNo) {
    this.alnRegNo = alnRegNo;
  }

  public LocalDate getBirthDt() {
    return birthDt;
  }

  public void setBirthDt(LocalDate birthDt) {
    this.birthDt = birthDt;
  }


  public String getBrFacNm() {
    return brFacNm;
  }

  public void setBrFacNm(String brFacNm) {
    this.brFacNm = brFacNm;
  }

  public Short getbStateC() {
    return bStateC;
  }

  public void setbStateC(Short bStateC) {
    this.bStateC = bStateC;
  }


  public Short getbCntryC() {
    return bCntryC;
  }

  public void setbCntryC(Short bCntryC) {
    this.bCntryC = bCntryC;
  }


  public String getChldCltB() {
    return chldCltB;
  }

  public void setChldCltB(String chldCltB) {
    this.chldCltB = chldCltB;
  }


  public String getComFstNm() {
    return comFstNm;
  }

  public void setComFstNm(String comFstNm) {
    this.comFstNm = comFstNm;
  }


  public String getComLstNm() {
    return comLstNm;
  }

  public void setComLstNm(String comLstNm) {
    this.comLstNm = comLstNm;
  }


  public String getComMidNm() {
    return comMidNm;
  }

  public void setComMidNm(String comMidNm) {
    this.comMidNm = comMidNm;
  }


  public String getConfEfind() {
    return confEfind;
  }

  public void setConfEfind(String confEfind) {
    this.confEfind = confEfind;
  }


  public LocalDate getConfActdt() {
    return confActdt;
  }

  public void setConfActdt(LocalDate confActdt) {
    this.confActdt = confActdt;
  }


  public LocalDate getCreatnDt() {
    return creatnDt;
  }

  public void setCreatnDt(LocalDate creatnDt) {
    this.creatnDt = creatnDt;
  }


  public LocalDate getDeathDt() {
    return deathDt;
  }

  public void setDeathDt(LocalDate deathDt) {
    this.deathDt = deathDt;
  }


  public String getDthRnTxt() {
    return dthRnTxt;
  }

  public void setDthRnTxt(String dthRnTxt) {
    this.dthRnTxt = dthRnTxt;
  }


  public String getDrvLicNo() {
    return drvLicNo;
  }

  public void setDrvLicNo(String drvLicNo) {
    this.drvLicNo = drvLicNo;
  }


  public Short getdStateC() {
    return dStateC;
  }

  public void setdStateC(Short dStateC) {
    this.dStateC = dStateC;
  }


  public String getGenderCd() {
    return genderCd;
  }

  public void setGenderCd(String genderCd) {
    this.genderCd = genderCd;
  }


  public Short getiCntryC() {
    return iCntryC;
  }

  public void setiCntryC(Short iCntryC) {
    this.iCntryC = iCntryC;
  }

  public Short getImgtStc() {
    return imgtStc;
  }

  public void setImgtStc(Short imgtStc) {
    this.imgtStc = imgtStc;
  }

  public String getIncapcCd() {
    return incapcCd;
  }

  public void setIncapcCd(String incapcCd) {
    this.incapcCd = incapcCd;
  }


  public String getLitrateCd() {
    return litrateCd;
  }

  public void setLitrateCd(String litrateCd) {
    this.litrateCd = litrateCd;
  }


  public String getMarHistB() {
    return marHistB;
  }

  public void setMarHistB(String marHistB) {
    this.marHistB = marHistB;
  }

  public Short getMrtlStc() {
    return mrtlStc;
  }

  public void setMrtlStc(Short mrtlStc) {
    this.mrtlStc = mrtlStc;
  }

  public String getMiltStacd() {
    return miltStacd;
  }

  public void setMiltStacd(String miltStacd) {
    this.miltStacd = miltStacd;
  }

  public String getNmprfxDsc() {
    return nmprfxDsc;
  }

  public void setNmprfxDsc(String nmprfxDsc) {
    this.nmprfxDsc = nmprfxDsc;
  }


  public Short getNameTpc() {
    return nameTpc;
  }

  public void setNameTpc(Short nameTpc) {
    this.nameTpc = nameTpc;
  }

  public String getOutwrtInd() {
    return outwrtInd;
  }

  public void setOutwrtInd(String outwrtInd) {
    this.outwrtInd = outwrtInd;
  }

  public Short getpEthnctyc() {
    return pEthnctyc;
  }

  public void setpEthnctyc(Short pEthnctyc) {
    this.pEthnctyc = pEthnctyc;
  }


  public Short getpLangTpc() {
    return pLangTpc;
  }

  public void setpLangTpc(Short pLangTpc) {
    this.pLangTpc = pLangTpc;
  }


  public Short getRlgnTpc() {
    return rlgnTpc;
  }

  public void setRlgnTpc(Short rlgnTpc) {
    this.rlgnTpc = rlgnTpc;
  }


  public Short getsLangTc() {
    return sLangTc;
  }

  public void setsLangTc(Short sLangTc) {
    this.sLangTc = sLangTc;
  }

  public String getSenstvInd() {
    return senstvInd;
  }

  public void setSenstvInd(String senstvInd) {
    this.senstvInd = senstvInd;
  }


  public String getSntvHlind() {
    return sntvHlind;
  }

  public void setSntvHlind(String sntvHlind) {
    this.sntvHlind = sntvHlind;
  }


  public String getSsNo() {
    return ssNo;
  }

  public void setSsNo(String ssNo) {
    this.ssNo = ssNo;
  }


  public String getSsnChgCd() {
    return ssnChgCd;
  }

  public void setSsnChgCd(String ssnChgCd) {
    this.ssnChgCd = ssnChgCd;
  }

  public String getSufxTldsc() {
    return sufxTldsc;
  }

  public void setSufxTldsc(String sufxTldsc) {
    this.sufxTldsc = sufxTldsc;
  }


  public String getUnemplyCd() {
    return unemplyCd;
  }

  public void setUnemplyCd(String unemplyCd) {
    this.unemplyCd = unemplyCd;
  }

  public String getCommntDsc() {
    return commntDsc;
  }

  public void setCommntDsc(String commntDsc) {
    this.commntDsc = commntDsc;
  }

  public String getEstDobCd() {
    return estDobCd;
  }

  public void setEstDobCd(String estDobCd) {
    this.estDobCd = estDobCd;
  }

  public String getBpVerInd() {
    return bpVerInd;
  }

  public void setBpVerInd(String bpVerInd) {
    this.bpVerInd = bpVerInd;
  }

  public String getHispCd() {
    return hispCd;
  }

  public void setHispCd(String hispCd) {
    this.hispCd = hispCd;
  }

  public String getCurrcaInd() {
    return currcaInd;
  }

  public void setCurrcaInd(String currcaInd) {
    this.currcaInd = currcaInd;
  }

  public String getCurregInd() {
    return curregInd;
  }

  public void setCurregInd(String curregInd) {
    this.curregInd = curregInd;
  }

  public String getCothDesc() {
    return cothDesc;
  }

  public void setCothDesc(String cothDesc) {
    this.cothDesc = cothDesc;
  }

  public String getPrevcaInd() {
    return prevcaInd;
  }

  public void setPrevcaInd(String prevcaInd) {
    this.prevcaInd = prevcaInd;
  }

  public String getPreregInd() {
    return preregInd;
  }

  public void setPreregInd(String preregInd) {
    this.preregInd = preregInd;
  }

  public String getPothDesc() {
    return pothDesc;
  }

  public void setPothDesc(String pothDesc) {
    this.pothDesc = pothDesc;
  }

  public String getHcareInd() {
    return hcareInd;
  }

  public void setHcareInd(String hcareInd) {
    this.hcareInd = hcareInd;
  }

  public String getLimitInd() {
    return limitInd;
  }

  public void setLimitInd(String limitInd) {
    this.limitInd = limitInd;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }


  public String getHealthTxt() {
    return healthTxt;
  }

  public void setHealthTxt(String healthTxt) {
    this.healthTxt = healthTxt;
  }

  public LocalDate getMtermDt() {
    return mtermDt;
  }

  public void setMtermDt(LocalDate mtermDt) {
    this.mtermDt = mtermDt;
  }


  public LocalDate getFtermDt() {
    return ftermDt;
  }

  public void setFtermDt(LocalDate ftermDt) {
    this.ftermDt = ftermDt;
  }


  public String getZippyInd() {
    return zippyInd;
  }

  public void setZippyInd(String zippyInd) {
    this.zippyInd = zippyInd;
  }

  public String getDeathPlc() {
    return deathPlc;
  }

  public void setDeathPlc(String deathPlc) {
    this.deathPlc = deathPlc;
  }


  public String getTrMbvrtB() {
    return trMbvrtB;
  }

  public void setTrMbvrtB(String trMbvrtB) {
    this.trMbvrtB = trMbvrtB;
  }

  public String getTrbaCltB() {
    return trbaCltB;
  }

  public void setTrbaCltB(String trbaCltB) {
    this.trbaCltB = trbaCltB;
  }


  public String getSoc158Ind() {
    return soc158Ind;
  }

  public void setSoc158Ind(String soc158Ind) {
    this.soc158Ind = soc158Ind;
  }


  public String getDthDtInd() {
    return dthDtInd;
  }

  public void setDthDtInd(String dthDtInd) {
    this.dthDtInd = dthDtInd;
  }


  public String getEmailAddr() {
    return emailAddr;
  }

  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }

  public String getAdjdelInd() {
    return adjdelInd;
  }

  public void setAdjdelInd(String adjdelInd) {
    this.adjdelInd = adjdelInd;
  }

  public String getEthUdCd() {
    return ethUdCd;
  }

  public void setEthUdCd(String ethUdCd) {
    this.ethUdCd = ethUdCd;
  }


  public String getHispUdCd() {
    return hispUdCd;
  }

  public void setHispUdCd(String hispUdCd) {
    this.hispUdCd = hispUdCd;
  }


  public String getSocplcCd() {
    return socplcCd;
  }

  public void setSocplcCd(String socplcCd) {
    this.socplcCd = socplcCd;
  }

  public String getClIndxNo() {
    return clIndxNo;
  }

  public void setClIndxNo(String clIndxNo) {
    this.clIndxNo = clIndxNo;
  }

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }



  @Override
  public Set<PlacementEpisode> getPlacementEpisodes() {
    return placementEpisodes;
  }

  public void setPlacementEpisodes(Set<PlacementEpisode> placementEpisodes) {
    this.placementEpisodes = placementEpisodes;
  }

}
