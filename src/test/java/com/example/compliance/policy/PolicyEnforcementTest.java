package com.example.compliance.policy;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class PolicyEnforcementTest {

    @Test
    void shouldDetectProhibitedKeyword() {
        PolicyManager manager = new PolicyManager(Arrays.asList("confidential", "secret"));
        String message = "This is a confidential document.";
        assertThat(manager.isPolicyViolated(message)).isTrue();
    }

    @Test
    void shouldAllowMessageWithoutProhibitedKeyword() {
        PolicyManager manager = new PolicyManager(Arrays.asList("confidential", "secret"));
        String message = "This is a public announcement.";
        assertThat(manager.isPolicyViolated(message)).isFalse();
    }

    @Test
    void shouldAlertOnPolicyViolation() {
        PolicyManager manager = new PolicyManager(Arrays.asList("restricted"));
        String message = "This contains restricted info.";
        manager.checkAndAlert(message);
        assertThat(manager.getAlerts()).contains("Policy violation detected: restricted");
    }

    @Test
    void shouldNotAlertWhenNoViolation() {
        PolicyManager manager = new PolicyManager(Arrays.asList("restricted"));
        String message = "General information.";
        manager.checkAndAlert(message);
        assertThat(manager.getAlerts()).isEmpty();
    }

    // Dummy class for demonstration
    static class PolicyManager {
        private final List<String> prohibitedKeywords;
        private final List<String> alerts = new ArrayList<>();

        PolicyManager(List<String> prohibitedKeywords) {
            this.prohibitedKeywords = prohibitedKeywords;
        }

        boolean isPolicyViolated(String message) {
            return prohibitedKeywords.stream().anyMatch(message.toLowerCase()::contains);
        }

        void checkAndAlert(String message) {
            for (String keyword : prohibitedKeywords) {
                if (message.toLowerCase().contains(keyword)) {
                    alerts.add("Policy violation detected: " + keyword);
                }
            }
        }

        List<String> getAlerts() {
            return alerts;
        }
    }
}