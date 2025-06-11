package com.example.compliance.export;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class ExportReportingTest {

    @Test
    void shouldExportMessagesWithinDateRange() {
        Exporter exporter = new Exporter();
        List<Message> messages = Arrays.asList(
            new Message("user1", "msg1", LocalDateTime.now().minusDays(5)),
            new Message("user2", "msg2", LocalDateTime.now().minusDays(2)),
            new Message("user3", "msg3", LocalDateTime.now().minusDays(10))
        );
        LocalDateTime from = LocalDateTime.now().minusDays(6);
        LocalDateTime to = LocalDateTime.now();
        List<Message> exported = exporter.export(messages, from, to);
        assertThat(exported).extracting("content").containsExactlyInAnyOrder("msg1", "msg2");
    }

    @Test
    void shouldExportInCorrectFormat() {
        Exporter exporter = new Exporter();
        List<Message> messages = Collections.singletonList(
            new Message("user1", "msg1", LocalDateTime.now())
        );
        String csv = exporter.exportAsCSV(messages);
        assertThat(csv).contains("user1,msg1");
    }

    @Test
    void shouldHandleEmptyExport() {
        Exporter exporter = new Exporter();
        List<Message> exported = exporter.export(Collections.emptyList(), LocalDateTime.now().minusDays(1), LocalDateTime.now());
        assertThat(exported).isEmpty();
    }

    @Test
    void shouldRestrictExportToAuthorizedUsers() {
        Exporter exporter = new Exporter();
        boolean canExport = exporter.canExport("compliance_officer");
        boolean cannotExport = exporter.canExport("regular_user");
        assertThat(canExport).isTrue();
        assertThat(cannotExport).isFalse();
    }

    // Dummy classes for demonstration
    static class Message {
        String user;
        String content;
        LocalDateTime timestamp;
        Message(String user, String content, LocalDateTime timestamp) {
            this.user = user; this.content = content; this.timestamp = timestamp;
        }
    }

    static class Exporter {
        List<Message> export(List<Message> messages, LocalDateTime from, LocalDateTime to) {
            return messages.stream()
                .filter(m -> !m.timestamp.isBefore(from) && !m.timestamp.isAfter(to))
                .collect(Collectors.toList());
        }
        String exportAsCSV(List<Message> messages) {
            StringBuilder sb = new StringBuilder();
            for (Message m : messages) {
                sb.append(m.user).append(",").append(m.content).append("\n");
            }
            return sb.toString();
        }
        boolean canExport(String role) {
            return "compliance_officer".equals(role);
        }
    }
}