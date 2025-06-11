package com.example.compliance.search;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

class SearchEDiscoveryTest {

    @Test
    void shouldFindMessagesByKeyword() {
        SearchManager manager = new SearchManager(Arrays.asList(
            new Message("user1", "This is a compliance message"),
            new Message("user2", "Another message"),
            new Message("user3", "Compliance check required")
        ));
        List<Message> results = manager.searchByKeyword("compliance");
        assertThat(results).hasSize(2);
        assertThat(results).extracting("content")
            .allMatch(content -> ((String) content).toLowerCase().contains("compliance"));
    }

    @Test
    void shouldReturnEmptyWhenNoMatch() {
        SearchManager manager = new SearchManager(Arrays.asList(
            new Message("user1", "This is a compliance message")
        ));
        List<Message> results = manager.searchByKeyword("nonexistent");
        assertThat(results).isEmpty();
    }

    @Test
    void shouldSupportCaseInsensitiveSearch() {
        SearchManager manager = new SearchManager(Arrays.asList(
            new Message("user1", "Sensitive Data"),
            new Message("user2", "sensitive data found")
        ));
        List<Message> results = manager.searchByKeyword("SENSITIVE");
        assertThat(results).hasSize(2);
    }

    @Test
    void shouldLimitResultsForEDiscovery() {
        SearchManager manager = new SearchManager(Arrays.asList(
            new Message("user1", "Message 1"),
            new Message("user2", "Message 2"),
            new Message("user3", "Message 3")
        ));
        List<Message> results = manager.searchWithLimit("Message", 2);
        assertThat(results).hasSize(2);
    }

    // Dummy classes for demonstration
    static class Message {
        String user;
        String content;
        Message(String user, String content) {
            this.user = user;
            this.content = content;
        }
    }

    static class SearchManager {
        private final List<Message> messages;

        SearchManager(List<Message> messages) {
            this.messages = messages;
        }

        List<Message> searchByKeyword(String keyword) {
            return messages.stream()
                .filter(m -> m.content.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        }

        List<Message> searchWithLimit(String keyword, int limit) {
            return messages.stream()
                .filter(m -> m.content.toLowerCase().contains(keyword.toLowerCase()))
                .limit(limit)
                .collect(Collectors.toList());
        }
    }
}