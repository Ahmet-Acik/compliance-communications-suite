package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import com.example.config.DriverManager;

public class BaseTest {
    protected WebDriver driver;

    @BeforeAll
    public void setUp() {
        driver = DriverManager.getDriver();
        // Additional setup code can be added here
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }
}