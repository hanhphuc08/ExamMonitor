package com.example.exammonitor.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementSeleniumTest extends SeleniumBaseTest {

    @Test
    void testListStudents() {
        // Login với admin
        login("admin", "admin123");
        
        // Click vào link Students
        WebElement studentsLink = waitForElement(By.linkText("Students"));
        studentsLink.click();
        
        // Kiểm tra đã đến trang Students
        waitForUrl("/students");
        assertTrue(driver.getCurrentUrl().contains("/students"));
        
        // Kiểm tra có bảng students
        assertTrue(isElementPresent(By.tagName("table")));
        
        // Kiểm tra có nút "Add New Student"
        assertTrue(isElementPresent(By.linkText("Add New Student")));
    }

    @Test
    void testAddNewStudent() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");
        
        // Click "Add New Student"
        WebElement addButton = waitForElement(By.linkText("Add New Student"));
        addButton.click();
        
        // Kiểm tra đã đến form
        waitForUrl("/students/new");
        assertTrue(driver.getCurrentUrl().contains("/students/new"));
        
        // Điền form
        WebElement studentIdField = waitForElement(By.name("studentId"));
        WebElement fullNameField = driver.findElement(By.name("currentInfo.fullName"));
        WebElement emailField = driver.findElement(By.name("currentInfo.email"));
        WebElement phoneField = driver.findElement(By.name("currentInfo.phone"));
        WebElement majorField = driver.findElement(By.name("currentInfo.major"));
        WebElement classroomField = driver.findElement(By.name("currentInfo.classroom"));
        
        studentIdField.sendKeys("S001");
        fullNameField.sendKeys("Nguyen Van Test");
        emailField.sendKeys("test@student.edu");
        phoneField.sendKeys("0123456789");
        majorField.sendKeys("CNTT");
        classroomField.sendKeys("21CLC");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra redirect về trang list
        waitForUrl("/students");
        assertTrue(driver.getCurrentUrl().contains("/students"));
    }

    @Test
    void testEditStudent() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");
        
        // Click "Edit" cho student đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Edit"))) {
            WebElement editLink = driver.findElement(By.linkText("Edit"));
            editLink.click();
            
            // Kiểm tra đã đến form edit
            assertTrue(driver.getCurrentUrl().contains("/students/edit"));
            
            // Sửa thông tin
            WebElement fullNameField = waitForElement(By.name("currentInfo.fullName"));
            fullNameField.clear();
            fullNameField.sendKeys("Updated Student Name");
            
            // Submit
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/students");
            assertTrue(driver.getCurrentUrl().contains("/students"));
        }
    }

    @Test
    void testDeleteStudent() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");
        
        // Click "Delete" cho student đầu tiên (nếu có)
        if (isElementPresent(By.linkText("Delete"))) {
            WebElement deleteLink = driver.findElement(By.linkText("Delete"));
            deleteLink.click();
            
            // Kiểm tra redirect về trang list
            waitForUrl("/students");
            assertTrue(driver.getCurrentUrl().contains("/students"));
        }
    }

    @Test
    void testStudentFormValidation() {
        // Login với admin
        login("admin", "admin123");
        
        // Vào form add student
        driver.get("http://localhost:" + port + "/students/new");
        
        // Submit form trống
        WebElement submitButton = waitForElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Kiểm tra vẫn ở form (không redirect)
        assertTrue(driver.getCurrentUrl().contains("/students/new"));
    }

    @Test
    void testInvigilatorCanAccessStudents() {
        // Login với invigilator
        login("gv", "gv123");
        
        // Click vào link Students
        WebElement studentsLink = waitForElement(By.linkText("Students"));
        studentsLink.click();
        
        // Kiểm tra đã đến trang Students
        waitForUrl("/students");
        assertTrue(driver.getCurrentUrl().contains("/students"));
        
        // Kiểm tra có bảng students
        assertTrue(isElementPresent(By.tagName("table")));
    }
} 