package com.example.compliance.integration;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class IntegrationCompatibilityTest {

    @Test
    void shouldIngestEmailMessages() {
        IntegrationManager manager = new IntegrationManager();
        boolean result = manager.ingest("email", "Test email message");
        assertThat(result).isTrue();
        assertThat(manager.getIngestedMessages()).contains("Test email message");
    }

    @Test
    void shouldIngestChatMessages() {
        IntegrationManager manager = new IntegrationManager();
        boolean result = manager.ingest("chat", "Test chat message");
        assertThat(result).isTrue();
        assertThat(manager.getIngestedMessages()).contains("Test chat message");
    }

    @Test
    void shouldRejectUnsupportedPlatform() {
        IntegrationManager manager = new IntegrationManager();
        boolean result = manager.ingest("fax", "Test fax message");
        assertThat(result).isFalse();
        assertThat(manager.getIngestedMessages()).doesNotContain("Test fax message");
    }

    @Test
    void shouldNormalizeMessageFormat() {
        IntegrationManager manager = new IntegrationManager();
        manager.ingest("email", "   Message with extra spaces   ");
        assertThat(manager.getIngestedMessages()).contains("Message with extra spaces");
    }

    // Dummy class for demonstration
    static class IntegrationManager {
        private final List<String> ingestedMessages = new ArrayList<>();
        private final Set<String> supportedPlatforms = new HashSet<>(Arrays.asList("email", "chat", "social"));

        boolean ingest(String platform, String message) {
            if (!supportedPlatforms.contains(platform)) return false;
            ingestedMessages.add(message.trim());
            return true;
        }

        List<String> getIngestedMessages() {
            return ingestedMessages;
        }
    }
}