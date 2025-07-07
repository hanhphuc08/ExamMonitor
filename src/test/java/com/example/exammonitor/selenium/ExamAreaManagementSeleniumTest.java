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

        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");

        // Nếu chưa có exam area nào, thêm mới một exam area test
        if (!isElementPresent(By.cssSelector("a.btn-edit"))) {
            WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
            addButton.click();
            waitForUrl("/examareas/new");
            WebElement idField = waitForElement(By.name("id"));
            WebElement nameField = driver.findElement(By.name("name"));
            // Điền các trường bắt buộc
            String uniqueId = "KVTestList" + System.currentTimeMillis();
            idField.sendKeys(uniqueId);
            nameField.sendKeys("Khu vực test list");
            try { driver.findElement(By.name("description")).sendKeys("Khu vực cho test list"); } catch (Exception ignored) {}
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            waitForUrl("/examareas");
        }

        // Kiểm tra đã đến trang Exam Areas
        assertTrue(driver.getCurrentUrl().contains("/examareas"));

        // Kiểm tra có bảng exam areas
        assertTrue(isElementPresent(By.tagName("table")));

        // Kiểm tra có nút "Thêm khu vực thi"
        assertTrue(isElementPresent(By.cssSelector("a.add-btn")));
    }

    @Test
    void testAddNewExamArea() {
        // Login với admin
        login("admin", "admin123");

        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");

        // Click "Thêm khu vực thi"
        WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
        addButton.click();
        waitForUrl("/examareas/new");
        assertTrue(driver.getCurrentUrl().contains("/examareas/new"));

        // Điền form
        WebElement idField = waitForElement(By.name("id"));
        WebElement nameField = driver.findElement(By.name("name"));
        String uniqueId = "KVTestAdd" + System.currentTimeMillis();
        idField.sendKeys(uniqueId);
        nameField.sendKeys("Khu vực test add");
        try { driver.findElement(By.name("description")).sendKeys("Khu vực cho test add"); } catch (Exception ignored) {}
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        waitForUrl("/examareas");
        assertTrue(driver.getCurrentUrl().contains("/examareas"));
    }

    @Test
    void testEditExamArea() {
        // Login với admin
        login("admin", "admin123");

        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");

        // Click "Chỉnh sửa" cho exam area đầu tiên (nếu có)
        if (isElementPresent(By.cssSelector("a.btn-edit"))) {
            WebElement editLink = driver.findElement(By.cssSelector("a.btn-edit"));
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

        // Click "Xóa" cho exam area đầu tiên (nếu có)
        if (isElementPresent(By.cssSelector("a.btn-delete"))) {
            WebElement deleteLink = driver.findElement(By.cssSelector("a.btn-delete"));
            try {
                deleteLink.click();
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                System.out.println(driver.getPageSource());
                throw e;
            }
        }
        // Kiểm tra redirect về trang list
        waitForUrl("/examareas");
        assertTrue(driver.getCurrentUrl().contains("/examareas"));
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

        // Vào trang Exam Areas
        driver.get("http://localhost:" + port + "/examareas");

        // Nếu chưa có exam area nào, thêm mới một exam area test (dưới quyền admin)
        if (!isElementPresent(By.cssSelector("a.btn-edit"))) {
            // Đăng nhập lại bằng admin để thêm dữ liệu
            login("admin", "admin123");
            driver.get("http://localhost:" + port + "/examareas");
            WebElement addButton = waitForElement(By.cssSelector("a.add-btn"));
            addButton.click();
            waitForUrl("/examareas/new");
            WebElement idField = waitForElement(By.name("id"));
            WebElement nameField = driver.findElement(By.name("name"));
            String uniqueId = "KVTestInvigilator" + System.currentTimeMillis();
            idField.sendKeys(uniqueId);
            nameField.sendKeys("Khu vực test invigilator");
            try { driver.findElement(By.name("description")).sendKeys("Khu vực cho test invigilator"); } catch (Exception ignored) {}
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();
            waitForUrl("/examareas");
            // Đăng nhập lại bằng invigilator
            login("gv", "gv123");
            driver.get("http://localhost:" + port + "/examareas");
        }

        // Kiểm tra đã đến trang Exam Areas
        assertTrue(driver.getCurrentUrl().contains("/examareas"));

        // Kiểm tra có bảng exam areas hoặc empty state
        boolean hasTable = isElementPresent(By.tagName("table"));
        boolean hasEmpty = isElementPresent(By.className("empty-state"));
        assertTrue(hasTable || hasEmpty);
    }
}