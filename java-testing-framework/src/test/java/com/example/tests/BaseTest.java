package com.example.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import com.example.config.DriverManager;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        // Additional setup code can be added here
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}