package com.example.compliance.archiving;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

class DataRetentionTest {

    @Test
    void shouldArchiveMessageAccordingToRetentionPolicy() {
        // Simulate a message archiving system
        MessageArchive archive = new MessageArchive(30); // 30 days retention
        Message msg = new Message("user1", "Hello", LocalDateTime.now().minusDays(31));
        archive.archive(msg);

        // Message older than retention should be deleted
        archive.purgeExpired();
        assertThat(archive.getArchivedMessages()).doesNotContain(msg);
    }

    @Test
    void shouldRetainMessageWithinRetentionPeriod() {
        MessageArchive archive = new MessageArchive(30);
        Message msg = new Message("user1", "Hello", LocalDateTime.now().minusDays(10));
        archive.archive(msg);

        archive.purgeExpired();
        assertThat(archive.getArchivedMessages()).contains(msg);
    }

    // Dummy classes for demonstration
    static class Message {
        String user;
        String content;
        LocalDateTime timestamp;
        Message(String user, String content, LocalDateTime timestamp) {
            this.user = user; this.content = content; this.timestamp = timestamp;
        }
        // equals and hashCode omitted for brevity
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Message)) return false;
            Message message = (Message) o;
            return Objects.equals(user, message.user) &&
                    Objects.equals(content, message.content) &&
                    Objects.equals(timestamp, message.timestamp);
        }
        @Override
        public int hashCode() {
            return Objects.hash(user, content, timestamp);
        }
    }

    static class MessageArchive {
        int retentionDays;
        List<Message> messages = new ArrayList<>();
        MessageArchive(int retentionDays) { this.retentionDays = retentionDays; }
        void archive(Message msg) { messages.add(msg); }
        void purgeExpired() {
            LocalDateTime cutoff = LocalDateTime.now().minusDays(retentionDays);
            messages.removeIf(m -> m.timestamp.isBefore(cutoff));
        }
        List<Message> getArchivedMessages() { return messages; }
    }
}