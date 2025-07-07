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

        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");

        // Nếu chưa có student nào, thêm mới một student test
        if (!isElementPresent(By.cssSelector("a.btn-edit"))) {
            WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
            addButton.click();
            waitForUrl("/students/new");
            WebElement idField = waitForElement(By.name("studentId"));
            WebElement statusField = driver.findElement(By.name("status"));
            WebElement nameField = driver.findElement(By.name("currentInfo.fullName"));
            WebElement emailField = driver.findElement(By.name("currentInfo.email"));
            // Điền các trường bắt buộc
            String uniqueId = "SVTestList" + System.currentTimeMillis();
            idField.sendKeys(uniqueId);
            statusField.sendKeys("Hoạt động"); // hoặc statusField.sendKeys("active");
            nameField.sendKeys("Sinh viên test list");
            emailField.sendKeys("svtestlist" + System.currentTimeMillis() + "@example.com");
            // Các trường không bắt buộc
            try { driver.findElement(By.name("currentInfo.phone")).sendKeys("0123456789"); } catch (Exception ignored) {}
            try { driver.findElement(By.name("currentInfo.major")).sendKeys("CNTT"); } catch (Exception ignored) {}
            try { driver.findElement(By.name("currentInfo.classroom")).sendKeys("21IT1"); } catch (Exception ignored) {}
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            waitForUrl("/students");
        }

        // Kiểm tra đã đến trang Students
        assertTrue(driver.getCurrentUrl().contains("/students"));

        // Kiểm tra có bảng students
        assertTrue(isElementPresent(By.tagName("table")));

        // Kiểm tra có nút "Thêm sinh viên"
        assertTrue(isElementPresent(By.cssSelector("a.add-btn")));
    }

    @Test
    void testAddNewStudent() {
        // Login với admin
        login("admin", "admin123");

        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");

        // Click "Thêm sinh viên mới"
        WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
        addButton.click();
        waitForUrl("/students/new");
        assertTrue(driver.getCurrentUrl().contains("/students/new"));

        // Điền form
        WebElement idField = waitForElement(By.name("studentId"));
        WebElement statusField = driver.findElement(By.name("status"));
        WebElement nameField = driver.findElement(By.name("currentInfo.fullName"));
        WebElement emailField = driver.findElement(By.name("currentInfo.email"));
        String uniqueId = "SVTestAdd" + System.currentTimeMillis();
        idField.sendKeys(uniqueId);
        statusField.sendKeys("Hoạt động"); // hoặc statusField.sendKeys("active");
        nameField.sendKeys("Sinh viên test add");
        emailField.sendKeys("svtestadd" + System.currentTimeMillis() + "@example.com");
        try { driver.findElement(By.name("currentInfo.phone")).sendKeys("0123456789"); } catch (Exception ignored) {}
        try { driver.findElement(By.name("currentInfo.major")).sendKeys("CNTT"); } catch (Exception ignored) {}
        try { driver.findElement(By.name("currentInfo.classroom")).sendKeys("21IT1"); } catch (Exception ignored) {}
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
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

        // Vào trang Students
        driver.get("http://localhost:" + port + "/students");

        // Nếu chưa có student nào, thêm mới một student test (dưới quyền admin)
        if (!isElementPresent(By.cssSelector("a.btn-edit"))) {
            // Đăng nhập lại bằng admin để thêm dữ liệu
            login("admin", "admin123");
            driver.get("http://localhost:" + port + "/students");
            WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
            addButton.click();
            waitForUrl("/students/new");
            WebElement idField = waitForElement(By.name("studentId"));
            WebElement statusField = driver.findElement(By.name("status"));
            WebElement nameField = driver.findElement(By.name("currentInfo.fullName"));
            WebElement emailField = driver.findElement(By.name("currentInfo.email"));
            String uniqueId = "SVTestInvigilator" + System.currentTimeMillis();
            idField.sendKeys(uniqueId);
            statusField.sendKeys("Hoạt động"); // hoặc statusField.sendKeys("active");
            nameField.sendKeys("Sinh viên test invigilator");
            emailField.sendKeys("svtestinvigilator" + System.currentTimeMillis() + "@example.com");
            try { driver.findElement(By.name("currentInfo.phone")).sendKeys("0123456789"); } catch (Exception ignored) {}
            try { driver.findElement(By.name("currentInfo.major")).sendKeys("CNTT"); } catch (Exception ignored) {}
            try { driver.findElement(By.name("currentInfo.classroom")).sendKeys("21IT1"); } catch (Exception ignored) {}
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            waitForUrl("/students");
            // Đăng nhập lại bằng invigilator
            login("gv", "gv123");
            driver.get("http://localhost:" + port + "/students");
        }

        // Kiểm tra đã đến trang Students
        assertTrue(driver.getCurrentUrl().contains("/students"));

        // Kiểm tra có bảng students
        assertTrue(isElementPresent(By.tagName("table")));
    }
} 