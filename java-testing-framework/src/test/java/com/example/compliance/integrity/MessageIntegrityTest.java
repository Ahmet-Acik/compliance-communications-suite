package com.example.compliance.integrity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class MessageIntegrityTest {

    @Test
    void shouldDetectTamperedMessage() {
        IntegrityManager manager = new IntegrityManager();
        Message original = new Message("user1", "Important message", "hash123");
        Message tampered = new Message("user1", "Important message!", "hash999");
        assertThat(manager.isTampered(original, tampered)).isTrue();
    }

    @Test
    void shouldNotDetectTamperingForIdenticalMessages() {
        IntegrityManager manager = new IntegrityManager();
        Message original = new Message("user1", "Important message", "hash123");
        Message copy = new Message("user1", "Important message", "hash123");
        assertThat(manager.isTampered(original, copy)).isFalse();
    }

    @Test
    void shouldGenerateMessageHash() {
        IntegrityManager manager = new IntegrityManager();
        String hash = manager.generateHash("Test content");
        assertThat(hash).isNotNull();
        assertThat(hash).isEqualTo(Integer.toString("Test content".hashCode()));
    }

    // Dummy classes for demonstration
    static class Message {
        String user;
        String content;
        String hash;
        Message(String user, String content, String hash) {
            this.user = user;
            this.content = content;
            this.hash = hash;
        }
    }

    static class IntegrityManager {
        boolean isTampered(Message original, Message other) {
            return !Objects.equals(original.hash, other.hash);
        }

        String generateHash(String content) {
            // Simple hash for demonstration
            return Integer.toString(content.hashCode());
        }
    }
}