package com.example.exammonitor.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

class ExamAreaManagementSeleniumTest extends SeleniumBaseTest {

    @Test
    void testListExamAreas() {
        // Login với admin
        login("admin", "admin123");
        
        // Click vào link Exam Areas
        WebElement examAreasLink = waitForElement(By.linkText("Exam Areas"));
        examAreasLink.click();
        
        // Kiểm tra đã đến trang Exam Areas
        waitForUrl("/examareas");
        assertTrue(driver.getCurrentUrl().contains("/examareas"));
        
        // Kiểm tra có bảng exam areas
        assertTrue(isElementPresent(By.tagName("table")));
        
        // Kiểm tra có nút "Add New Exam Area"
        assertTrue(isElementPresent(By.linkText("Add New Exam Area")));
    }

    @Test
    void testAddNewExamArea() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");
        
        // Click "Add New Exam Area"
        WebElement addButton = waitForElement(By.linkText("Add New Exam Area"));
        addButton.click();
        
        // Kiểm tra đã đến form
        waitForUrl("/examareas/new");
        assertTrue(driver.getCurrentUrl().contains("/examareas/new"));
        
        // Điền form
        WebElement nameField = waitForElement(By.name("name"));
        WebElement descriptionField = driver.findElement(By.name("description"));
        
        nameField.sendKeys("Khu Test");
        descriptionField.sendKeys("Khu thi thử nghiệm");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra redirect về trang list
        waitForUrl("/examareas");
        assertTrue(driver.getCurrentUrl().contains("/examareas"));
    }

    @Test
    void testEditExamArea() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");
        
        // Click "Edit" cho exam area đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Edit"))) {
            WebElement editLink = driver.findElement(By.linkText("Edit"));
            editLink.click();
            
            // Kiểm tra đã đến form edit
            assertTrue(driver.getCurrentUrl().contains("/examareas/edit"));
            
            // Sửa thông tin
            WebElement nameField = waitForElement(By.name("name"));
            nameField.clear();
            nameField.sendKeys("Updated Exam Area");
            
            // Submit
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/examareas");
            assertTrue(driver.getCurrentUrl().contains("/examareas"));
        }
    }

    @Test
    void testDeleteExamArea() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");
        
        // Click "Delete" cho exam area đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Delete"))) {
            WebElement deleteLink = driver.findElement(By.linkText("Delete"));
            deleteLink.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/examareas");
            assertTrue(driver.getCurrentUrl().contains("/examareas"));
        }
    }

    @Test
    void testExamAreaFormValidation() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào form add exam area
        driver.get("http://localhost:" + port + "/examareas/new");
        
        // Submit form trống
        WebElement submitButton = waitForElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra vẫn ở form (không redirect)
        assertTrue(driver.getCurrentUrl().contains("/examareas/new"));
    }

    @Test
    void testInvigilatorCanAccessExamAreas() {
        // Login với invigilator
        login("gv", "gv123");
        
        // Click vào link Exam Areas
        WebElement examAreasLink = waitForElement(By.linkText("Exam Areas"));
        examAreasLink.click();
        
        // Kiểm tra đã đến trang Exam Areas
        waitForUrl("/examareas");
        assertTrue(driver.getCurrentUrl().contains("/examareas"));
        
        // Kiểm tra có bảng exam areas
        assertTrue(isElementPresent(By.tagName("table")));
    }
} 