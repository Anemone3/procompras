package com.procompras.engine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class SeleniumBrowser {
    private final WebDriver driver;

    public SeleniumBrowser() {

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/brave");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");

        this.driver = new ChromeDriver(options);
        this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    public void open(String url) {
        this.driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }


    public WebDriverWait getDriverWait() {
        return getDriverWait(20);
    }

    public WebDriverWait getDriverWait(long seconds) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(seconds));
    }


    public void executeScript(String script) {
        ((JavascriptExecutor) this.driver).executeScript(script);
    }

    public void executeScript(String script, WebElement webElement) {
        ((JavascriptExecutor) this.driver).executeScript(script, webElement);
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
