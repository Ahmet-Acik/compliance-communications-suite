package com.example.compliance.user;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class UserExperienceTest {

    @Test
    void shouldDisplayOnboardingInstructions() {
        UserPortal portal = new UserPortal();
        String instructions = portal.getOnboardingInstructions("compliance_officer");
        assertThat(instructions).contains("Welcome").contains("compliance");
    }

    @Test
    void shouldAllowUserToNavigateToComplianceDashboard() {
        UserPortal portal = new UserPortal();
        boolean canNavigate = portal.canNavigateTo("compliance_dashboard");
        assertThat(canNavigate).isTrue();
    }

    @Test
    void shouldProvideHelpResources() {
        UserPortal portal = new UserPortal();
        List<String> resources = portal.getHelpResources();
        assertThat(resources).isNotEmpty();
        assertThat(resources).contains("FAQ", "Contact Support");
    }

    @Test
    void shouldLogUserFeedback() {
        UserPortal portal = new UserPortal();
        portal.submitFeedback("Great onboarding!");
        assertThat(portal.getFeedback()).contains("Great onboarding!");
    }

    // Dummy class for demonstration
    static class UserPortal {
        private final List<String> feedback = new ArrayList<>();

        String getOnboardingInstructions(String role) {
            return "Welcome " + role + ", here are your compliance onboarding steps.";
        }

        boolean canNavigateTo(String page) {
            return "compliance_dashboard".equals(page);
        }

        List<String> getHelpResources() {
            return Arrays.asList("FAQ", "Contact Support", "User Guide");
        }

        void submitFeedback(String fb) {
            feedback.add(fb);
        }

        List<String> getFeedback() {
            return feedback;
        }
    }
}