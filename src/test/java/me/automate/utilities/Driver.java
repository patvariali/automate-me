package me.automate.utilities;

import me.automate.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Driver {

    private Driver() {}

    private static final InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            String browserType = System.getProperty("browser", ConfigurationReader.getProperty("browser"));

            switch (browserType.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*",
                            "--window-size=1920,1080",
                            "--disable-gpu",
                            "--headless");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Browser type not supported: " + browserType);
            }
        }
        return driverPool.get();
    }

    public static <T extends BasePage<T>> T open(String url, Class<T> pageClass) {
        WebDriver driver = getDriver();
        driver.get(url);

        try {
            T pageInstance = pageClass.getDeclaredConstructor().newInstance();
            pageInstance.checkPageLoaded();
            return pageInstance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to open page: " + pageClass.getName(), e);
        }
    }

    public static void closeDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
        }
    }
}
