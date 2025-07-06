package com.example.exammonitor.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

class UserManagementSeleniumTest extends SeleniumBaseTest {

    @Test
    void testListUsers() {
        // Login với admin
        login("admin", "admin123");
        
        // Click vào link Users
        WebElement usersLink = waitForElement(By.linkText("Users"));
        usersLink.click();
        
        // Kiểm tra đã đến trang Users
        waitForUrl("/users");
        assertTrue(driver.getCurrentUrl().contains("/users"));
        
        // Kiểm tra có bảng users
        assertTrue(isElementPresent(By.tagName("table")));
        
        // Kiểm tra có nút "Add New User"
        assertTrue(isElementPresent(By.linkText("Add New User")));
    }

    @Test
    void testAddNewUser() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Users
        driver.get("http://localhost:" + port + "/users");
        
        // Click "Add New User"
        WebElement addButton = waitForElement(By.linkText("Add New User"));
        addButton.click();
        
        // Kiểm tra đã đến form
        waitForUrl("/users/new");
        assertTrue(driver.getCurrentUrl().contains("/users/new"));
        
        // Điền form
        WebElement usernameField = waitForElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement fullNameField = driver.findElement(By.name("fullName"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement roleSelect = driver.findElement(By.name("role"));
        
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpass");
        fullNameField.sendKeys("Test User");
        emailField.sendKeys("test@example.com");
        roleSelect.sendKeys("INVIGILATOR");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra redirect về trang list
        waitForUrl("/users");
        assertTrue(driver.getCurrentUrl().contains("/users"));
    }

    @Test
    void testEditUser() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Users
        driver.get("http://localhost:" + port + "/users");
        
        // Click "Edit" cho user đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Edit"))) {
            WebElement editLink = driver.findElement(By.linkText("Edit"));
            editLink.click();
            
            // Kiểm tra đã đến form edit
            assertTrue(driver.getCurrentUrl().contains("/users/edit"));
            
            // Sửa thông tin
            WebElement fullNameField = waitForElement(By.name("fullName"));
            fullNameField.clear();
            fullNameField.sendKeys("Updated Name");
            
            // Submit
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/users");
            assertTrue(driver.getCurrentUrl().contains("/users"));
        }
    }

    @Test
    void testDeleteUser() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Users
        driver.get("http://localhost:" + port + "/users");
        
        // Click "Delete" cho user đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Delete"))) {
            WebElement deleteLink = driver.findElement(By.linkText("Delete"));
            deleteLink.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/users");
            assertTrue(driver.getCurrentUrl().contains("/users"));
        }
    }

    @Test
    void testUserFormValidation() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào form add user
        driver.get("http://localhost:" + port + "/users/new");
        
        // Submit form trống
        WebElement submitButton = waitForElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra vẫn ở form (không redirect)
        assertTrue(driver.getCurrentUrl().contains("/users/new"));
    }

    @Test
    void testInvigilatorCannotAccessUsers() {
        // Login với invigilator
        login("gv", "gv123");
        
        // Truy cập trang Users
        driver.get("http://localhost:" + port + "/users");
        
        // Kiểm tra bị từ chối truy cập
        assertTrue(driver.getCurrentUrl().contains("/error") || 
                  driver.getCurrentUrl().contains("/403") ||
                  driver.getPageSource().contains("Forbidden"));
    }
} 