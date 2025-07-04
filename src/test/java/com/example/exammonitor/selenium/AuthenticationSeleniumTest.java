package com.example.exammonitor.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationSeleniumTest extends SeleniumBaseTest {

    @Test
    void testLoginAsAdmin() {
        // Test login với admin
        login("admin", "admin123");
        
        // Kiểm tra đã login thành công
        assertTrue(driver.getCurrentUrl().contains("/"));
        
        // Kiểm tra có link Users (chỉ admin mới thấy)
        assertTrue(isElementPresent(By.linkText("Users")));
        
        // Kiểm tra có link Students
        assertTrue(isElementPresent(By.linkText("Students")));
        
        // Kiểm tra có link Exam Areas
        assertTrue(isElementPresent(By.linkText("Exam Areas")));
    }

    @Test
    void testLoginAsInvigilator() {
        // Test login với invigilator
        login("gv", "gv123");
        
        // Kiểm tra đã login thành công
        assertTrue(driver.getCurrentUrl().contains("/"));
        
        // Kiểm tra KHÔNG có link Users (invigilator không có quyền)
        assertFalse(isElementPresent(By.linkText("Users")));
        
        // Kiểm tra có link Students
        assertTrue(isElementPresent(By.linkText("Students")));
        
        // Kiểm tra có link Exam Areas
        assertTrue(isElementPresent(By.linkText("Exam Areas")));
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Test login với thông tin sai
        driver.get("http://localhost:" + port + "/login");
        
        WebElement usernameField = waitForElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        usernameField.sendKeys("invalid");
        passwordField.sendKeys("invalid");
        loginButton.click();
        
        // Kiểm tra vẫn ở trang login (không redirect)
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    void testLogout() {
        // Login trước
        login("admin", "admin123");
        
        // Logout
        logout();
        
        // Kiểm tra đã logout (redirect về login hoặc home)
        assertTrue(driver.getCurrentUrl().contains("/login") || 
                  driver.getCurrentUrl().equals("http://localhost:" + port + "/"));
    }

    @Test
    void testAccessControlForUnauthorizedUser() {
        // Truy cập trang Users mà không login
        driver.get("http://localhost:" + port + "/users");
        
        // Kiểm tra bị redirect về login
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    void testAccessControlForInvigilatorToUsers() {
        // Login với invigilator
        login("gv", "gv123");
        
        // Truy cập trang Users (không có quyền)
        driver.get("http://localhost:" + port + "/users");
        
        // Kiểm tra bị từ chối truy cập (403 hoặc redirect)
        assertTrue(driver.getCurrentUrl().contains("/error") || 
                  driver.getCurrentUrl().contains("/403") ||
                  driver.getPageSource().contains("Forbidden"));
    }
} 