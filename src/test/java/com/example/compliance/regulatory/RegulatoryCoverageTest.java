package com.example.compliance.regulatory;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class RegulatoryCoverageTest {

    @Test
    void shouldSupportFINRARequirements() {
        RegulatoryManager manager = new RegulatoryManager(Arrays.asList("FINRA", "SEC", "GDPR"));
        assertThat(manager.isRegulationSupported("FINRA")).isTrue();
    }

    @Test
    void shouldSupportGDPRRequirements() {
        RegulatoryManager manager = new RegulatoryManager(Arrays.asList("FINRA", "SEC", "GDPR"));
        assertThat(manager.isRegulationSupported("GDPR")).isTrue();
    }

    @Test
    void shouldNotSupportUnknownRegulation() {
        RegulatoryManager manager = new RegulatoryManager(Arrays.asList("FINRA", "SEC", "GDPR"));
        assertThat(manager.isRegulationSupported("HIPAA")).isFalse();
    }

    @Test
    void shouldConfigureRetentionForRegulation() {
        RegulatoryManager manager = new RegulatoryManager(Arrays.asList("FINRA", "SEC"));
        manager.setRetentionPeriod("FINRA", 7);
        assertThat(manager.getRetentionPeriod("FINRA")).isEqualTo(7);
    }

    // Dummy class for demonstration
    static class RegulatoryManager {
        private final Set<String> supportedRegulations;
        private final Map<String, Integer> retentionPeriods = new HashMap<>();

        RegulatoryManager(List<String> supportedRegulations) {
            this.supportedRegulations = new HashSet<>(supportedRegulations);
        }

        boolean isRegulationSupported(String regulation) {
            return supportedRegulations.contains(regulation);
        }

        void setRetentionPeriod(String regulation, int years) {
            retentionPeriods.put(regulation, years);
        }

        Integer getRetentionPeriod(String regulation) {
            return retentionPeriods.get(regulation);
        }
    }
}