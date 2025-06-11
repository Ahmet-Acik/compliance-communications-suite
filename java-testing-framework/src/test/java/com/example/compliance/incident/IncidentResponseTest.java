package com.example.compliance.incident;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class IncidentResponseTest {

    @Test
    void shouldDetectComplianceIncident() {
        IncidentManager manager = new IncidentManager();
        boolean detected = manager.detectIncident("Sensitive data leak detected");
        assertThat(detected).isTrue();
    }

    @Test
    void shouldNotDetectNonIncident() {
        IncidentManager manager = new IncidentManager();
        boolean detected = manager.detectIncident("Normal operation");
        assertThat(detected).isFalse();
    }

    @Test
    void shouldNotifyComplianceOfficerOnIncident() {
        IncidentManager manager = new IncidentManager();
        manager.detectIncident("Unauthorized access attempt");
        assertThat(manager.getNotifications())
            .contains("Compliance officer notified: Unauthorized access attempt");
    }

    @Test
    void shouldEscalateCriticalIncident() {
        IncidentManager manager = new IncidentManager();
        manager.detectIncident("CRITICAL: Data breach");
        assertThat(manager.getEscalations())
            .contains("Incident escalated: CRITICAL: Data breach");
    }

    // Dummy classes for demonstration
    static class IncidentManager {
        private final List<String> notifications = new ArrayList<>();
        private final List<String> escalations = new ArrayList<>();

        boolean detectIncident(String log) {
            if (log.contains("leak") || log.contains("Unauthorized") || log.contains("CRITICAL")) {
                notifications.add("Compliance officer notified: " + log);
                if (log.contains("CRITICAL")) {
                    escalations.add("Incident escalated: " + log);
                }
                return true;
            }
            return false;
        }

        List<String> getNotifications() {
            return notifications;
        }

        List<String> getEscalations() {
            return escalations;
        }
    }
}