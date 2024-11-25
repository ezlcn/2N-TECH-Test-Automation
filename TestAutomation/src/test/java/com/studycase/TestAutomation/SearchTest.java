package com.studycase.TestAutomation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SearchTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\chromedriver-win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://2nhaber.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSearchAndNavigate() {
        try {
            
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".elementor-widget-cmsmasters-search__popup-trigger-inner-icon")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);

            
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='s']")));

            
            searchInput.clear();
            searchInput.sendKeys("İstanbul");
            searchInput.sendKeys(Keys.ENTER);

            
            List<WebElement> articles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("article.cmsmasters-blog__post")));
            System.out.println("Bulunan makale sayısı: " + articles.size());

            
            for (int i = 0; i < articles.size(); i++) {
                articles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("article.cmsmasters-blog__post")));
                WebElement article = articles.get(i);
                WebElement articleLink = article.findElement(By.tagName("a"));

                
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", articleLink);
                    wait.until(ExpectedConditions.elementToBeClickable(articleLink)).click();
                } catch (ElementClickInterceptedException e) {
                    System.out.println("Click intercepted. Trying JavaScript click.");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", articleLink);
                }

                
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Navigated to URL: " + currentUrl);
                WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
                System.out.println("Makale Başlığı: " + title.getText());

                
                driver.navigate().back();

                
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("article.cmsmasters-blog__post")));
            }
        } catch (Exception e) {
            System.out.println("Test sırasında hata oluştu: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
