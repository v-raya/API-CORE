//package gov.ca.cwds.cms.data.access.dto;
//
///**
// * @author CWDS TPT-3 Team
// */
//public class ClientRelationshipDTO {
//  @Id
//  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
//  private String identifier;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @Fetch(FetchMode.SELECT)
//  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
//  private Client primaryClient;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @Fetch(FetchMode.SELECT)
//  @JoinColumn(name = "FKCLIENT_0", referencedColumnName = "IDENTIFIER")
//  private Client secondaryClient;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @Fetch(FetchMode.SELECT)
//  @JoinColumn(name = "CLNTRELC", referencedColumnName = "SYS_ID")
//  private ClientRelationshipType type;
//
//  @Column(name = "START_DT")
//  private LocalDate startDate;
//
//  @Column(name = "END_DT")
//  private LocalDate endDate;
//
//  @Type(type = "yes_no")
//  @Column(name = "ABSENT_CD")
//  private boolean absentParentIndicator;
//
//  @Column(name = "SAME_HM_CD")
//  @Convert(converter = YesNoUnknownConverter.class)
//  private YesNoUnknown sameHomeStatus;
//
//}
