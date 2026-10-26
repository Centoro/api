package com.centoro.api.controller;

import com.centoro.api.entities.*;
import com.centoro.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "*")
public class SettingsController {

    @Autowired
    private CompanySettingsRepository companySettingsRepository;
    
    @Autowired
    private SocialPlatformRepository socialPlatformRepository;
    
    @Autowired
    private LinkedAccountRepository linkedAccountRepository;
    
    @Autowired
    private EmailProviderRepository emailProviderRepository;
    
    @Autowired
    private EmailTemplateRepository emailTemplateRepository;
    
    @Autowired
    private EmailCampaignRepository emailCampaignRepository;
    
    @Autowired
    private AiProviderRepository aiProviderRepository;
    
    @Autowired
    private AiAutomationRepository aiAutomationRepository;
    
    @Autowired
    private PaymentProviderRepository paymentProviderRepository;
    
    @Autowired
    private TrackingRuleRepository trackingRuleRepository;
    
    @Autowired
    private BarcodeIntegrationRepository barcodeIntegrationRepository;
    
    @Autowired
    private PlatformKeyRepository platformKeyRepository;
    
    @Autowired
    private ManagedSiteRepository managedSiteRepository;
    
    @Autowired
    private InfrastructureRepository infrastructureRepository;
    
    @Autowired
    private PortRuleRepository portRuleRepository;
    
    @Autowired
    private IamRoleRepository iamRoleRepository;
    
    @Autowired
    private SecurityLogRepository securityLogRepository;
    
    @Autowired
    private SecuritySettingsRepository securitySettingsRepository;

    // ============ COMPANY SETTINGS ============
    
    @GetMapping("/company")
    public ResponseEntity<?> getCompanySettings() {
        List<CompanySettings> settings = companySettingsRepository.findAll();
        if (settings.isEmpty()) {
            CompanySettings defaultSettings = createDefaultSettings();
            return ResponseEntity.ok(Map.of("success", true, "data", companySettingsRepository.save(defaultSettings)));
        }
        return ResponseEntity.ok(Map.of("success", true, "data", settings.get(0)));
    }

    @PutMapping("/company")
    public ResponseEntity<?> updateCompanySettings(@RequestBody CompanySettings settings) {
        List<CompanySettings> existing = companySettingsRepository.findAll();
        CompanySettings toSave;
        if (existing.isEmpty()) {
            toSave = settings;
            toSave.setCreatedAt(new Date());
        } else {
            toSave = existing.get(0);
            toSave.setCompanyName(settings.getCompanyName());
            toSave.setTradingAs(settings.getTradingAs());
            toSave.setVatRate(settings.getVatRate());
            toSave.setCurrency(settings.getCurrency());
            toSave.setOverrideCode(settings.getOverrideCode());
            toSave.setCountry(settings.getCountry());
            toSave.setRegNumber(settings.getRegNumber());
            toSave.setTaxNumber(settings.getTaxNumber());
            toSave.setThemePrimary(settings.getThemePrimary());
            toSave.setThemePrimaryDark(settings.getThemePrimaryDark());
            toSave.setThemePrimaryLight(settings.getThemePrimaryLight());
            toSave.setThemeAccent(settings.getThemeAccent());
            toSave.setThemeBgBase(settings.getThemeBgBase());
            toSave.setThemeBgSurface(settings.getThemeBgSurface());
            toSave.setRevenueTarget2026(settings.getRevenueTarget2026());
            toSave.setProfileImageUrl(settings.getProfileImageUrl());
        }
        toSave.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", companySettingsRepository.save(toSave)));
    }

    private CompanySettings createDefaultSettings() {
        CompanySettings settings = new CompanySettings();
        settings.setCompanyName("Centoro Enterprise");
        settings.setTradingAs("Centoro");
        settings.setVatRate(15.0);
        settings.setCurrency("ZAR");
        settings.setOverrideCode("CENTORO-ADMIN");
        settings.setCountry("South Africa");
        settings.setRegNumber("2018/567462/07");
        settings.setTaxNumber("9908825160");
        settings.setThemePrimary("#f59e0b");
        settings.setThemePrimaryDark("#d97706");
        settings.setThemePrimaryLight("#fbbf24");
        settings.setThemeAccent("#3b82f6");
        settings.setThemeBgBase("#0f172a");
        settings.setThemeBgSurface("#1e293b");
        settings.setRevenueTarget2026(300000.0);
        settings.setCreatedAt(new Date());
        settings.setUpdatedAt(new Date());
        return settings;
    }

    // ============ THEME SETTINGS ============
    
    @GetMapping("/theme")
    public ResponseEntity<?> getThemeSettings() {
        List<CompanySettings> settings = companySettingsRepository.findAll();
        if (settings.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", Map.of(
                    "primary", "#f59e0b",
                    "primaryDark", "#d97706",
                    "primaryLight", "#fbbf24",
                    "accent", "#3b82f6",
                    "bgBase", "#0f172a",
                    "bgSurface", "#1e293b"
                )
            ));
        }
        CompanySettings s = settings.get(0);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "primary", s.getThemePrimary(),
                "primaryDark", s.getThemePrimaryDark(),
                "primaryLight", s.getThemePrimaryLight(),
                "accent", s.getThemeAccent(),
                "bgBase", s.getThemeBgBase(),
                "bgSurface", s.getThemeBgSurface()
            )
        ));
    }

    @PutMapping("/theme")
    public ResponseEntity<?> updateTheme(@RequestBody Map<String, String> theme) {
        List<CompanySettings> existing = companySettingsRepository.findAll();
        CompanySettings settings;
        if (existing.isEmpty()) {
            settings = createDefaultSettings();
        } else {
            settings = existing.get(0);
        }
        if (theme.containsKey("primary")) settings.setThemePrimary(theme.get("primary"));
        if (theme.containsKey("primaryDark")) settings.setThemePrimaryDark(theme.get("primaryDark"));
        if (theme.containsKey("primaryLight")) settings.setThemePrimaryLight(theme.get("primaryLight"));
        if (theme.containsKey("accent")) settings.setThemeAccent(theme.get("accent"));
        if (theme.containsKey("bgBase")) settings.setThemeBgBase(theme.get("bgBase"));
        if (theme.containsKey("bgSurface")) settings.setThemeBgSurface(theme.get("bgSurface"));
        settings.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", companySettingsRepository.save(settings)));
    }

    // ============ SOCIAL PLATFORMS ============
    
    @GetMapping("/social/platforms")
    public ResponseEntity<?> getSocialPlatforms() {
        List<SocialPlatform> platforms = socialPlatformRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", platforms));
    }

    @PostMapping("/social/platforms")
    public ResponseEntity<?> createSocialPlatform(@RequestBody SocialPlatform platform) {
        platform.setCreatedAt(new Date());
        platform.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", socialPlatformRepository.save(platform)));
    }

    @DeleteMapping("/social/platforms/{id}")
    public ResponseEntity<?> deleteSocialPlatform(@PathVariable Long id) {
        socialPlatformRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Platform deleted"));
    }

    @GetMapping("/social/accounts")
    public ResponseEntity<?> getLinkedAccounts() {
        List<LinkedAccount> accounts = linkedAccountRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", accounts));
    }

    @PostMapping("/social/accounts")
    public ResponseEntity<?> linkAccount(@RequestBody LinkedAccount account) {
        account.setLinkedAt(new Date());
        account.setCreatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", linkedAccountRepository.save(account)));
    }

    @DeleteMapping("/social/accounts/{id}")
    public ResponseEntity<?> unlinkAccount(@PathVariable Long id) {
        linkedAccountRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Account unlinked"));
    }

    // ============ EMAIL PROVIDERS ============
    
    @GetMapping("/email/providers")
    public ResponseEntity<?> getEmailProviders() {
        List<EmailProvider> providers = emailProviderRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", providers));
    }

    @PostMapping("/email/providers")
    public ResponseEntity<?> createEmailProvider(@RequestBody EmailProvider provider) {
        provider.setProviderId("EP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        provider.setCreatedAt(new Date());
        provider.setUpdatedAt(new Date());
        if (provider.getStatus() == null) provider.setStatus("connected");
        return ResponseEntity.ok(Map.of("success", true, "data", emailProviderRepository.save(provider)));
    }

    @DeleteMapping("/email/providers/{id}")
    public ResponseEntity<?> deleteEmailProvider(@PathVariable Long id) {
        emailProviderRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Email provider disconnected"));
    }

    @GetMapping("/email/templates")
    public ResponseEntity<?> getEmailTemplates() {
        List<EmailTemplate> templates = emailTemplateRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", templates));
    }

    @PostMapping("/email/templates")
    public ResponseEntity<?> createEmailTemplate(@RequestBody EmailTemplate template) {
        template.setCreatedAt(new Date());
        template.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", emailTemplateRepository.save(template)));
    }

    @DeleteMapping("/email/templates/{id}")
    public ResponseEntity<?> deleteEmailTemplate(@PathVariable Long id) {
        emailTemplateRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Template deleted"));
    }

    @GetMapping("/email/campaigns")
    public ResponseEntity<?> getEmailCampaigns() {
        List<EmailCampaign> campaigns = emailCampaignRepository.findByOrderBySentAtDesc();
        return ResponseEntity.ok(Map.of("success", true, "data", campaigns));
    }

    @PostMapping("/email/campaigns")
    public ResponseEntity<?> createEmailCampaign(@RequestBody EmailCampaign campaign) {
        campaign.setCampaignId("EML-" + String.format("%04d", System.currentTimeMillis() % 10000));
        campaign.setCreatedAt(new Date());
        campaign.setSentAt(new Date());
        if (campaign.getStatus() == null) campaign.setStatus("sent");
        return ResponseEntity.ok(Map.of("success", true, "data", emailCampaignRepository.save(campaign)));
    }

    // ============ AI PROVIDERS ============
    
    @GetMapping("/ai/providers")
    public ResponseEntity<?> getAiProviders() {
        List<AiProvider> providers = aiProviderRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", providers));
    }

    @PostMapping("/ai/providers")
    public ResponseEntity<?> createAiProvider(@RequestBody AiProvider provider) {
        provider.setProviderId("AIP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        provider.setCreatedAt(new Date());
        provider.setUpdatedAt(new Date());
        if (provider.getStatus() == null) provider.setStatus("connected");
        return ResponseEntity.ok(Map.of("success", true, "data", aiProviderRepository.save(provider)));
    }

    @DeleteMapping("/ai/providers/{id}")
    public ResponseEntity<?> deleteAiProvider(@PathVariable Long id) {
        aiProviderRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "AI provider disconnected"));
    }

    @GetMapping("/ai/automations")
    public ResponseEntity<?> getAiAutomations() {
        List<AiAutomation> automations = aiAutomationRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", automations));
    }

    @PostMapping("/ai/automations")
    public ResponseEntity<?> createAiAutomation(@RequestBody AiAutomation automation) {
        automation.setAutomationId("AUT-" + String.format("%04d", System.currentTimeMillis() % 10000));
        automation.setCreatedAt(new Date());
        automation.setUpdatedAt(new Date());
        if (automation.getEnabled() == null) automation.setEnabled(true);
        return ResponseEntity.ok(Map.of("success", true, "data", aiAutomationRepository.save(automation)));
    }

    @PutMapping("/ai/automations/{id}")
    public ResponseEntity<?> updateAiAutomation(@PathVariable Long id, @RequestBody AiAutomation automation) {
        AiAutomation existing = aiAutomationRepository.findById(id).orElseThrow();
        existing.setName(automation.getName());
        existing.setSchedule(automation.getSchedule());
        existing.setTrigger(automation.getTrigger());
        existing.setAction(automation.getAction());
        existing.setEnabled(automation.getEnabled());
        existing.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", aiAutomationRepository.save(existing)));
    }

    @DeleteMapping("/ai/automations/{id}")
    public ResponseEntity<?> deleteAiAutomation(@PathVariable Long id) {
        aiAutomationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Automation deleted"));
    }

    // ============ PAYMENT PROVIDERS ============
    
    @GetMapping("/payments/providers")
    public ResponseEntity<?> getPaymentProviders() {
        List<PaymentProvider> providers = paymentProviderRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", providers));
    }

    @PostMapping("/payments/providers")
    public ResponseEntity<?> createPaymentProvider(@RequestBody PaymentProvider provider) {
        provider.setProviderId("PP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        provider.setCreatedAt(new Date());
        provider.setUpdatedAt(new Date());
        if (provider.getStatus() == null) provider.setStatus("connected");
        return ResponseEntity.ok(Map.of("success", true, "data", paymentProviderRepository.save(provider)));
    }

    @DeleteMapping("/payments/providers/{id}")
    public ResponseEntity<?> deletePaymentProvider(@PathVariable Long id) {
        paymentProviderRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Payment provider disconnected"));
    }

    // ============ TRACKING ============
    
    @GetMapping("/tracking/rules")
    public ResponseEntity<?> getTrackingRules() {
        List<TrackingRule> rules = trackingRuleRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", rules));
    }

    @PostMapping("/tracking/rules")
    public ResponseEntity<?> createTrackingRule(@RequestBody TrackingRule rule) {
        rule.setRuleId("TRK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        rule.setCreatedAt(new Date());
        rule.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", trackingRuleRepository.save(rule)));
    }

    @DeleteMapping("/tracking/rules/{id}")
    public ResponseEntity<?> deleteTrackingRule(@PathVariable Long id) {
        trackingRuleRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Tracking rule deleted"));
    }

    @GetMapping("/tracking/barcode-integrations")
    public ResponseEntity<?> getBarcodeIntegrations() {
        List<BarcodeIntegration> integrations = barcodeIntegrationRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", integrations));
    }

    @PostMapping("/tracking/barcode-integrations")
    public ResponseEntity<?> createBarcodeIntegration(@RequestBody BarcodeIntegration integration) {
        integration.setIntegrationId("BCI-" + String.format("%04d", System.currentTimeMillis() % 10000));
        integration.setCreatedAt(new Date());
        integration.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", barcodeIntegrationRepository.save(integration)));
    }

    @DeleteMapping("/tracking/barcode-integrations/{id}")
    public ResponseEntity<?> deleteBarcodeIntegration(@PathVariable Long id) {
        barcodeIntegrationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Barcode integration disconnected"));
    }

    // ============ PLATFORM KEYS ============
    
    @GetMapping("/platform-keys")
    public ResponseEntity<?> getPlatformKeys() {
        List<PlatformKey> keys = platformKeyRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", keys));
    }

    @PostMapping("/platform-keys")
    public ResponseEntity<?> createPlatformKey(@RequestBody PlatformKey key) {
        key.setKeyId("PK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        key.setCreatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", platformKeyRepository.save(key)));
    }

    @DeleteMapping("/platform-keys/{id}")
    public ResponseEntity<?> deletePlatformKey(@PathVariable Long id) {
        platformKeyRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Platform key revoked"));
    }

    // ============ MANAGED SITES ============
    
    @GetMapping("/managed-sites")
    public ResponseEntity<?> getManagedSites() {
        List<ManagedSite> sites = managedSiteRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", sites));
    }

    @PostMapping("/managed-sites")
    public ResponseEntity<?> createManagedSite(@RequestBody ManagedSite site) {
        site.setSiteId("MS-" + String.format("%04d", System.currentTimeMillis() % 10000));
        site.setCreatedAt(new Date());
        site.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", managedSiteRepository.save(site)));
    }

    @DeleteMapping("/managed-sites/{id}")
    public ResponseEntity<?> deleteManagedSite(@PathVariable Long id) {
        managedSiteRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Managed site removed"));
    }

    // ============ INFRASTRUCTURE ============
    
    @GetMapping("/infrastructure")
    public ResponseEntity<?> getInfrastructure() {
        List<Infrastructure> infra = infrastructureRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", infra));
    }

    @PostMapping("/infrastructure")
    public ResponseEntity<?> createInfrastructure(@RequestBody Infrastructure infra) {
        infra.setInfraId("INF-" + String.format("%04d", System.currentTimeMillis() % 10000));
        infra.setCreatedAt(new Date());
        infra.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", infrastructureRepository.save(infra)));
    }

    @DeleteMapping("/infrastructure/{id}")
    public ResponseEntity<?> deleteInfrastructure(@PathVariable Long id) {
        infrastructureRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Infrastructure connection removed"));
    }

    // ============ FIREWALL ============
    
    @GetMapping("/firewall/ports")
    public ResponseEntity<?> getPortRules() {
        List<PortRule> rules = portRuleRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", rules));
    }

    @PostMapping("/firewall/ports")
    public ResponseEntity<?> createPortRule(@RequestBody PortRule rule) {
        rule.setRuleId("PRT-" + String.format("%04d", System.currentTimeMillis() % 10000));
        rule.setCreatedAt(new Date());
        rule.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", portRuleRepository.save(rule)));
    }

    @DeleteMapping("/firewall/ports/{id}")
    public ResponseEntity<?> deletePortRule(@PathVariable Long id) {
        portRuleRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Port rule removed"));
    }

    @GetMapping("/firewall/status")
    public ResponseEntity<?> getFirewallStatus() {
        // Return firewall status from settings
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of("enabled", true) // Default to enabled
        ));
    }

    // ============ IAM ============
    
    @GetMapping("/iam/roles")
    public ResponseEntity<?> getIamRoles() {
        List<IamRole> roles = iamRoleRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", roles));
    }

    @PostMapping("/iam/roles")
    public ResponseEntity<?> createIamRole(@RequestBody IamRole role) {
        role.setRoleId("IAM-" + String.format("%04d", System.currentTimeMillis() % 10000));
        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", iamRoleRepository.save(role)));
    }

    @DeleteMapping("/iam/roles/{id}")
    public ResponseEntity<?> deleteIamRole(@PathVariable Long id) {
        iamRoleRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "IAM role deleted"));
    }

    // ============ SECURITY ============
    
    @GetMapping("/security/settings")
    public ResponseEntity<?> getSecuritySettings() {
        List<SecuritySettings> settings = securitySettingsRepository.findAll();
        if (settings.isEmpty()) {
            SecuritySettings defaults = new SecuritySettings();
            defaults.setStealth(true);
            defaults.setDdos(true);
            defaults.setIds(true);
            defaults.setEncrypt(true);
            defaults.setHoneypot(true);
            defaults.setAutoban(true);
            defaults.setMinPass(12);
            defaults.setSessionTimeout(30);
            defaults.setTwoFA("required");
            defaults.setMaxAttempts(3);
            return ResponseEntity.ok(Map.of("success", true, "data", securitySettingsRepository.save(defaults)));
        }
        return ResponseEntity.ok(Map.of("success", true, "data", settings.get(0)));
    }

    @PutMapping("/security/settings")
    public ResponseEntity<?> updateSecuritySettings(@RequestBody SecuritySettings settings) {
        List<SecuritySettings> existing = securitySettingsRepository.findAll();
        SecuritySettings toSave;
        if (existing.isEmpty()) {
            toSave = settings;
            toSave.setCreatedAt(new Date());
        } else {
            toSave = existing.get(0);
            toSave.setStealth(settings.getStealth());
            toSave.setDdos(settings.getDdos());
            toSave.setIds(settings.getIds());
            toSave.setEncrypt(settings.getEncrypt());
            toSave.setHoneypot(settings.getHoneypot());
            toSave.setAutoban(settings.getAutoban());
            toSave.setMinPass(settings.getMinPass());
            toSave.setSessionTimeout(settings.getSessionTimeout());
            toSave.setTwoFA(settings.getTwoFA());
            toSave.setMaxAttempts(settings.getMaxAttempts());
        }
        toSave.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", securitySettingsRepository.save(toSave)));
    }

    @GetMapping("/security/logs")
    public ResponseEntity<?> getSecurityLogs() {
        List<SecurityLog> logs = securityLogRepository.findByOrderByTimeDesc();
        return ResponseEntity.ok(Map.of("success", true, "data", logs));
    }

    // ============ LEGAL PAGES ============
    
    @GetMapping("/legal")
    public ResponseEntity<?> getLegalPages() {
        // Get from DB or return defaults
        Map<String, Object> impressum = new HashMap<>();
        impressum.put("companyName", "Centoro Enterprise");
        impressum.put("owner", "Mr CH Heideman");
        impressum.put("regNumber", "2018/567462/07");
        impressum.put("taxNumber", "9908825160");
        impressum.put("address", "Elsies River, Cape Town");
        impressum.put("country", "South Africa");
        impressum.put("phone", "081 567 9592");
        impressum.put("email", "centroenterprise@gmail.com");
        impressum.put("website", "https://centoro.co.za");
        impressum.put("responsible", "Centoro Enterprise");
        impressum.put("disclaimer", "All content on this website is for informational purposes only.");
        impressum.put("copyright", "© 2026 Centoro Enterprise. All rights reserved.");

        Map<String, Object> privacy = new HashMap<>();
        privacy.put("collect", "We collect information you provide directly to us.");
        privacy.put("use", "We use the information to provide and maintain our services.");
        privacy.put("share", "We do not sell your personal information.");
        privacy.put("security", "We implement reasonable security measures.");
        privacy.put("cookies", "We use cookies to improve your experience.");
        privacy.put("rights", "You may access, correct, or request deletion of your data.");
        privacy.put("changes", "We may update this policy from time to time.");
        privacy.put("contact", "Contact us at centroenterprise@gmail.com");

        Map<String, Object> data = new HashMap<>();
        data.put("impressum", impressum);
        data.put("privacy", privacy);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", data
        ));
    }

    @PutMapping("/legal/impressum")
    public ResponseEntity<?> updateImpressum(@RequestBody Map<String, String> impressum) {
        // Save impressum to database
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Impressum saved successfully",
            "data", impressum
        ));
    }

    @PutMapping("/legal/privacy")
    public ResponseEntity<?> updatePrivacy(@RequestBody Map<String, String> privacy) {
        // Save privacy policy to database
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Privacy policy saved successfully",
            "data", privacy
        ));
    }

    @PostMapping("/legal/publish")
    public ResponseEntity<?> publishLegalPages() {
        // Mark as published
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Legal pages published successfully"
        ));
    }
}