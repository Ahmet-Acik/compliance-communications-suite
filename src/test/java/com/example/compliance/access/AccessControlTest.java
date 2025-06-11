package com.example.compliance.access;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class AccessControlTest {

    @Test
    void shouldAllowAccessForAuthorizedUser() {
        AccessManager manager = new AccessManager();
        manager.addUserRole("alice", "admin");
        assertThat(manager.canAccess("alice", "auditTrail")).isTrue();
    }

    @Test
    void shouldDenyAccessForUnauthorizedUser() {
        AccessManager manager = new AccessManager();
        manager.addUserRole("bob", "user");
        assertThat(manager.canAccess("bob", "auditTrail")).isFalse();
    }

    static class AccessManager {
        Map<String, String> userRoles = new HashMap<>();
        void addUserRole(String user, String role) { userRoles.put(user, role); }
        boolean canAccess(String user, String resource) {
            String role = userRoles.get(user);
            return "admin".equals(role) && "auditTrail".equals(resource);
        }
    }
}