package com.example.pages;

import org.openqa.selenium.WebElement;

public class BasePage {
    protected void waitForElementToBeVisible(WebElement element) {
        // Implementation for waiting for an element to be visible
    }

    protected void clickElement(WebElement element) {
        // Implementation for clicking an element
    }

    protected String getElementText(WebElement element) {
        // Implementation for getting text from an element
        return element.getText();
    }

    protected void enterText(WebElement element, String text) {
        // Implementation for entering text into an element
        element.sendKeys(text);
    }
}