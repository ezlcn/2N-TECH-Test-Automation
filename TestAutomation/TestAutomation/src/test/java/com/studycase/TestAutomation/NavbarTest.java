package com.studycase.TestAutomation;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NavbarTest {
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
    public void testNavbarLinks() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
           

           
            WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
            List<WebElement> footerLinks = footer.findElements(By.cssSelector("a[href]"));
          

            for (WebElement link : footerLinks) {
                try {
                   
                    wait.until(ExpectedConditions.visibilityOf(link));
                    wait.until(ExpectedConditions.elementToBeClickable(link));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);

                    String href = link.getAttribute("href");
                    if (href == null || href.isEmpty()) {
                     
                        continue;
                    }

                  
                    link.click();

                    
                    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                    if (tabs.size() > 1) {
                        driver.switchTo().window(tabs.get(1));
                        

                        wait.until(ExpectedConditions.urlContains(href));
                        driver.close();
                        driver.switchTo().window(tabs.get(0));
                      
                    } else {
                        wait.until(ExpectedConditions.urlContains(href));
                     
                        driver.navigate().back();
                    }
                } catch (Exception e) {
                   
                }
            }
        } catch (Exception e) {
           
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
           
        }
       
    }
}
