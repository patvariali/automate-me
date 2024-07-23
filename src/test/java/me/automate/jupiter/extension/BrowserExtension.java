package me.automate.jupiter.extension;

import lombok.extern.slf4j.Slf4j;
import me.automate.utilities.Driver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class BrowserExtension implements TestExecutionExceptionHandler,
        AfterEachCallback,
        LifecycleMethodExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenShot();
        capturePageSourceWithStyles(Driver.getDriver());
        browserConsoleLogs();
        throw throwable;
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenShot();
        capturePageSourceWithStyles(Driver.getDriver());
        browserConsoleLogs();
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenShot();
        capturePageSourceWithStyles(Driver.getDriver());
        browserConsoleLogs();
        throw throwable;
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] captureScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    @Attachment(value = "Page source", type = "text/html")
    public String capturePageSourceWithStyles(WebDriver driver) {
        if (driver instanceof JavascriptExecutor) {
            try {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                String cssStyles = (String) jsExecutor.executeScript(
                        "return Array.from(document.styleSheets).flatMap(sheet => " +
                                "Array.from(sheet.cssRules || []).map(rule => rule.cssText)).join('\\n')");
                String pageSource = driver.getPageSource();
                String styleTag = "<style>" + cssStyles + "</style>";
                pageSource = pageSource.replace("</head>", styleTag + "</head>");
                return pageSource;
            } catch (JavascriptException e) {
                log.error("Failed to execute JavaScript for fetching CSS styles. Returning page source without styles.");
                return driver.getPageSource();
            }
        } else {
            log.error("Driver does not support JavaScript execution.");
            return driver.getPageSource();
        }
    }


    @Attachment(value = "{attachName}", type = "text/plain")
    private String attachAsText(String attachName, String message) {
        return message;
    }

    private void browserConsoleLogs() {
        if (Driver.getDriver() != null) {
            LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.BROWSER);

            StringBuilder allLogs = new StringBuilder();
            for (LogEntry logEntry : logEntries) {
                allLogs.append(new Date(logEntry.getTimestamp()))
                        .append(" ")
                        .append(logEntry.getLevel())
                        .append(" ")
                        .append(logEntry.getMessage())
                        .append("\n");
            }

            attachAsText("Browser console logs", String.valueOf(allLogs));
        }
    }

    private void doScreenShot() {
        if (Driver.getDriver() != null) {
            Allure.addAttachment(
                    "Screenshot of the test end",
                    new ByteArrayInputStream(
                            Objects.requireNonNull(
                                    ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES)
                            )
                    )
            );
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Driver.closeDriver();
    }
}

