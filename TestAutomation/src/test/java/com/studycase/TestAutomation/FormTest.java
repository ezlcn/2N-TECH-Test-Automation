package com.studycase.TestAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class FormTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\chromedriver-win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.2ntech.com.tr/hr");

        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    
    @Test
    public void testFormSubmission() {
        
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        nameField.sendKeys("Test User2");

        WebElement birthField = driver.findElement(By.id("birth"));
        birthField.sendKeys("1990-01-12");

        WebElement tcField = driver.findElement(By.id("tcKimlik"));
        tcField.sendKeys("12345678922");

        WebElement phoneField = driver.findElement(By.id("phone"));
        phoneField.sendKeys("05551245644");

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("testuser3@example.com");

        
        WebElement cvUpload = driver.findElement(By.id("cv_field"));
        cvUpload.sendKeys("\\cv_path");

        WebElement educationButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Lisans')]")));
        educationButton.click();

        
        WebElement kvkkCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("pdp1")));
        if (!kvkkCheckbox.isSelected()) {
            kvkkCheckbox.click();
        }

        
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        nextButton.click();

        
        WebElement testEngineerOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'group') and .//span[text()='Test Engineer']]")));
        testEngineerOption.click();

        
        WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class, 'text-white') and contains(text(), 'Gönder')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'Form Başarı ile gönderildi')]")));
        Assert.assertTrue(successMessage.isDisplayed(), "Başarı mesajı görünmüyor.");
    }
    @AfterClass
    public void tearDown() {
      
        if (driver != null) {
            driver.quit();
        }
    }
}