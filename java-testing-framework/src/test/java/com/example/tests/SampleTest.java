package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest extends BaseTest {

    @BeforeAll
    public void setUp() {
        // Code to set up the test environment
    }

    @Test
    public void sampleTestMethod() {
        // Sample test logic
        assertTrue(true, "Sample test assertion");
    }

    @AfterEach
    public void tearDown() {
        // Code to clean up after the test
    }
}