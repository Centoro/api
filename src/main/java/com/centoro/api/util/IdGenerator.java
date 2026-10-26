package com.centoro.api.util;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {
    
    // Core entity counters
    private final AtomicLong clientCounter = new AtomicLong(1);
    private final AtomicLong staffCounter = new AtomicLong(1);
    private final AtomicLong projectCounter = new AtomicLong(1);
    private final AtomicLong invoiceCounter = new AtomicLong(1);
    private final AtomicLong jobCardCounter = new AtomicLong(1);
    private final AtomicLong paymentCounter = new AtomicLong(1);
    private final AtomicLong expenseCounter = new AtomicLong(1);
    private final AtomicLong voucherCounter = new AtomicLong(1);
    private final AtomicLong assetCounter = new AtomicLong(1);
    private final AtomicLong chatCounter = new AtomicLong(1);
    
    // Activity & Audit
    private final AtomicLong activityCounter = new AtomicLong(1);
    
    // Financial
    private final AtomicLong journalCounter = new AtomicLong(1);
    private final AtomicLong payableCounter = new AtomicLong(1);
    private final AtomicLong taxPeriodCounter = new AtomicLong(1);
    private final AtomicLong budgetCounter = new AtomicLong(1);
    private final AtomicLong bankTransactionCounter = new AtomicLong(1);
    
    // Staff Management
    private final AtomicLong staffGroupCounter = new AtomicLong(1);
    private final AtomicLong staffFileCounter = new AtomicLong(1);
    private final AtomicLong staffNotificationCounter = new AtomicLong(1);
    private final AtomicLong staffBonusCounter = new AtomicLong(1);
    private final AtomicLong staffWarningCounter = new AtomicLong(1);
    private final AtomicLong payrollRecordCounter = new AtomicLong(1);
    
    // Communication
    private final AtomicLong chatMessageCounter = new AtomicLong(1);
    private final AtomicLong staffChatCounter = new AtomicLong(1);
    private final AtomicLong staffChatMessageCounter = new AtomicLong(1);
    private final AtomicLong websiteChatCounter = new AtomicLong(1);
    
    // Website
    private final AtomicLong websitePageCounter = new AtomicLong(1);
    private final AtomicLong websiteNewsCounter = new AtomicLong(1);
    private final AtomicLong websiteSlideCounter = new AtomicLong(1);
    private final AtomicLong websitePriceCounter = new AtomicLong(1);
    private final AtomicLong websiteStatsCounter = new AtomicLong(1);
    
    // Music
    private final AtomicLong musicTrackCounter = new AtomicLong(1);
    private final AtomicLong musicPackCounter = new AtomicLong(1);
    private final AtomicLong musicSampleCounter = new AtomicLong(1);
    private final AtomicLong fridayDropCounter = new AtomicLong(1);
    private final AtomicLong monthlyOfferCounter = new AtomicLong(1);
    
    // Marketing
    private final AtomicLong portfolioCounter = new AtomicLong(1);
    private final AtomicLong socialPostCounter = new AtomicLong(1);
    private final AtomicLong contractCounter = new AtomicLong(1);
    private final AtomicLong discountCounter = new AtomicLong(1);
    
    // Assets & Inventory
    private final AtomicLong assetBookingCounter = new AtomicLong(1);
    private final AtomicLong inventoryCounter = new AtomicLong(1);
    private final AtomicLong stockMovementCounter = new AtomicLong(1);
    
    // Email & Phone
    private final AtomicLong emailAccountCounter = new AtomicLong(1);
    private final AtomicLong emailCampaignCounter = new AtomicLong(1);
    private final AtomicLong emailTemplateCounter = new AtomicLong(1);
    private final AtomicLong phoneLineCounter = new AtomicLong(1);
    private final AtomicLong smsProviderCounter = new AtomicLong(1);
    private final AtomicLong smsCampaignCounter = new AtomicLong(1);
    private final AtomicLong phoneContactCounter = new AtomicLong(1);
    
    // Security & Settings
    private final AtomicLong iamRoleCounter = new AtomicLong(1);
    private final AtomicLong aiProviderCounter = new AtomicLong(1);
    private final AtomicLong aiAutomationCounter = new AtomicLong(1);
    private final AtomicLong integrationCounter = new AtomicLong(1);
    private final AtomicLong trackingRuleCounter = new AtomicLong(1);
    private final AtomicLong portRuleCounter = new AtomicLong(1);
    
    // Projects
    private final AtomicLong projectDealCounter = new AtomicLong(1);
    private final AtomicLong projectAutomationCounter = new AtomicLong(1);
    private final AtomicLong projectMessageCounter = new AtomicLong(1);
    private final AtomicLong projectFolderCounter = new AtomicLong(1);
    
    // Bin
    private final AtomicLong binItemCounter = new AtomicLong(1);
    private final AtomicLong trashCounter = new AtomicLong(1);
    
    // Calendar
    private final AtomicLong calendarNoteCounter = new AtomicLong(1);
    
    // Other
    private final AtomicLong pendingApprovalCounter = new AtomicLong(1);
    private final AtomicLong connectedDeviceCounter = new AtomicLong(1);
    private final AtomicLong ispPortCounter = new AtomicLong(1);
    private final AtomicLong infrastructureCounter = new AtomicLong(1);
    private final AtomicLong platformKeyCounter = new AtomicLong(1);
    private final AtomicLong managedSiteCounter = new AtomicLong(1);
    private final AtomicLong barcodeIntegrationCounter = new AtomicLong(1);
    private final AtomicLong paymentProviderCounter = new AtomicLong(1);
    private final AtomicLong quoteCounter = new AtomicLong(1);

    // ============ CORE ENTITY ID GENERATORS ============
    public String generateClientId() { return "CLT-" + String.format("%04d", clientCounter.getAndIncrement()); }
    public String generateStaffId() { return "STF-" + String.format("%04d", staffCounter.getAndIncrement()); }
    public String generateProjectId() { return "PRJ-" + String.format("%04d", projectCounter.getAndIncrement()); }
    public String generateInvoiceId() { return "INV-" + String.format("%04d", invoiceCounter.getAndIncrement()); }
    public String generateJobCardId() { return "JC-" + String.format("%04d", jobCardCounter.getAndIncrement()); }
    public String generatePaymentId() { return "PAY-" + String.format("%04d", paymentCounter.getAndIncrement()); }
    public String generateExpenseId() { return "EXP-" + String.format("%04d", expenseCounter.getAndIncrement()); }
    public String generateVoucherId() { return "VCH-" + String.format("%04d", voucherCounter.getAndIncrement()); }
    public String generateAssetId() { return "AST-" + String.format("%04d", assetCounter.getAndIncrement()); }
    public String generateChatId() { return "CHT-" + String.format("%04d", chatCounter.getAndIncrement()); }

    // ============ ACTIVITY & AUDIT ============
    public String generateActivityId() { return "ACT-" + String.format("%04d", activityCounter.getAndIncrement()); }

    // ============ FINANCIAL ============
    public String generateJournalId() { return "JRN-" + String.format("%04d", journalCounter.getAndIncrement()); }
    public String generatePayableId() { return "AP-" + String.format("%04d", payableCounter.getAndIncrement()); }
    public String generateTaxPeriodId() { return "TAX-" + String.format("%04d", taxPeriodCounter.getAndIncrement()); }
    public String generateBudgetId() { return "BDG-" + String.format("%04d", budgetCounter.getAndIncrement()); }
    public String generateBankTransactionId() { return "BNK-" + String.format("%04d", bankTransactionCounter.getAndIncrement()); }

    // ============ STAFF MANAGEMENT ============
    public String generateStaffGroupId() { return "SGR-" + String.format("%04d", staffGroupCounter.getAndIncrement()); }
    public String generateStaffFileId() { return "STFDOC-" + String.format("%04d", staffFileCounter.getAndIncrement()); }
    public String generateStaffNotificationId() { return "NTF-" + String.format("%04d", staffNotificationCounter.getAndIncrement()); }
    public String generateStaffBonusId() { return "BON-" + String.format("%04d", staffBonusCounter.getAndIncrement()); }
    public String generateStaffWarningId() { return "WRN-" + String.format("%04d", staffWarningCounter.getAndIncrement()); }
    public String generatePayrollRecordId() { return "PAYR-" + String.format("%04d", payrollRecordCounter.getAndIncrement()); }

    // ============ COMMUNICATION ============
    public String generateChatMessageId() { return "MSG-" + String.format("%04d", chatMessageCounter.getAndIncrement()); }
    public String generateStaffChatId() { return "SCH-" + String.format("%04d", staffChatCounter.getAndIncrement()); }
    public String generateStaffChatMessageId() { return "SMS-" + String.format("%04d", staffChatMessageCounter.getAndIncrement()); }
    public String generateWebsiteChatMessageId() { return "WCM-" + String.format("%04d", websiteChatCounter.getAndIncrement()); }

    // ============ WEBSITE ============
    public String generateWebsitePageId() { return "WP-" + String.format("%04d", websitePageCounter.getAndIncrement()); }
    public String generateWebsiteNewsId() { return "NWS-" + String.format("%04d", websiteNewsCounter.getAndIncrement()); }
    public String generateWebsiteSlideId() { return "SLD-" + String.format("%04d", websiteSlideCounter.getAndIncrement()); }
    public String generateWebsitePriceId() { return "PRC-" + String.format("%04d", websitePriceCounter.getAndIncrement()); }
    public String generateWebsiteStatsId() { return "WST-" + String.format("%04d", websiteStatsCounter.getAndIncrement()); }

    // ============ MUSIC ============
    public String generateMusicTrackId() { return "TRK-" + String.format("%04d", musicTrackCounter.getAndIncrement()); }
    public String generateMusicPackId() { return "PCK-" + String.format("%04d", musicPackCounter.getAndIncrement()); }
    public String generateMusicSampleId() { return "SMP-" + String.format("%04d", musicSampleCounter.getAndIncrement()); }
    public String generateFridayDropId() { return "DRP-" + String.format("%04d", fridayDropCounter.getAndIncrement()); }
    public String generateMonthlyOfferId() { return "MOF-" + String.format("%04d", monthlyOfferCounter.getAndIncrement()); }

    // ============ MARKETING ============
    public String generatePortfolioId() { return "PF-" + String.format("%04d", portfolioCounter.getAndIncrement()); }
    public String generateSocialPostId() { return "SPT-" + String.format("%04d", socialPostCounter.getAndIncrement()); }
    public String generateContractId() { return "CTR-" + String.format("%04d", contractCounter.getAndIncrement()); }
    public String generateDiscountId() { return "DSC-" + String.format("%04d", discountCounter.getAndIncrement()); }

    // ============ ASSETS & INVENTORY ============
    public String generateAssetBookingId() { return "BKG-" + String.format("%04d", assetBookingCounter.getAndIncrement()); }
    public String generateInventoryId() { return "ITM-" + String.format("%04d", inventoryCounter.getAndIncrement()); }
    public String generateStockMovementId() { return "STM-" + String.format("%04d", stockMovementCounter.getAndIncrement()); }

    // ============ EMAIL & PHONE ============
    public String generateEmailAccountId() { return "EAC-" + String.format("%04d", emailAccountCounter.getAndIncrement()); }
    public String generateEmailCampaignId() { return "EML-" + String.format("%04d", emailCampaignCounter.getAndIncrement()); }
    public String generateEmailTemplateId() { return "TPL-" + String.format("%04d", emailTemplateCounter.getAndIncrement()); }
    public String generatePhoneLineId() { return "PLN-" + String.format("%04d", phoneLineCounter.getAndIncrement()); }
    public String generateSmsProviderId() { return "SPR-" + String.format("%04d", smsProviderCounter.getAndIncrement()); }
    public String generateSmsCampaignId() { return "SMS-" + String.format("%04d", smsCampaignCounter.getAndIncrement()); }
    public String generatePhoneContactId() { return "PBK-" + String.format("%04d", phoneContactCounter.getAndIncrement()); }

    // ============ SECURITY & SETTINGS ============
    public String generateIamRoleId() { return "IAM-" + String.format("%04d", iamRoleCounter.getAndIncrement()); }
    public String generateAiProviderId() { return "AIP-" + String.format("%04d", aiProviderCounter.getAndIncrement()); }
    public String generateAiAutomationId() { return "AUT-" + String.format("%04d", aiAutomationCounter.getAndIncrement()); }
    public String generateIntegrationId() { return "ITG-" + String.format("%04d", integrationCounter.getAndIncrement()); }
    public String generateTrackingRuleId() { return "TRK-" + String.format("%04d", trackingRuleCounter.getAndIncrement()); }
    public String generatePortRuleId() { return "PRT-" + String.format("%04d", portRuleCounter.getAndIncrement()); }

    // ============ PROJECTS ============
    public String generateProjectDealId() { return "DEAL-" + String.format("%04d", projectDealCounter.getAndIncrement()); }
    public String generateProjectAutomationId() { return "AUTO-" + String.format("%04d", projectAutomationCounter.getAndIncrement()); }
    public String generateProjectMessageId() { return "PMG-" + String.format("%04d", projectMessageCounter.getAndIncrement()); }
    public String generateProjectFolderId() { return "FLD-" + String.format("%04d", projectFolderCounter.getAndIncrement()); }

    // ============ BIN ============
    public String generateBinItemId() { return "BIN-" + String.format("%04d", binItemCounter.getAndIncrement()); }
    public String generateTrashId() { return "TRSH-" + String.format("%04d", trashCounter.getAndIncrement()); }

    // ============ CALENDAR ============
    public String generateCalendarNoteId() { return "CND-" + String.format("%04d", calendarNoteCounter.getAndIncrement()); }

    // ============ OTHER ============
    public String generatePendingApprovalId() { return "APR-" + String.format("%04d", pendingApprovalCounter.getAndIncrement()); }
    public String generateConnectedDeviceId() { return "DEV-" + String.format("%04d", connectedDeviceCounter.getAndIncrement()); }
    public String generateIspPortId() { return "ISP-" + String.format("%04d", ispPortCounter.getAndIncrement()); }
    public String generateInfrastructureId() { return "INF-" + String.format("%04d", infrastructureCounter.getAndIncrement()); }
    public String generatePlatformKeyId() { return "PKY-" + String.format("%04d", platformKeyCounter.getAndIncrement()); }
    public String generateManagedSiteId() { return "MSI-" + String.format("%04d", managedSiteCounter.getAndIncrement()); }
    public String generateBarcodeIntegrationId() { return "BCI-" + String.format("%04d", barcodeIntegrationCounter.getAndIncrement()); }
    public String generatePaymentProviderId() { return "PPR-" + String.format("%04d", paymentProviderCounter.getAndIncrement()); }
    public String generateQuoteId() { return "QTE-" + String.format("%04d", quoteCounter.getAndIncrement()); }
}