package me.automate.pages;

import io.qameta.allure.Step;
import me.automate.utilities.ConfigurationReader;
import me.automate.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static me.automate.utilities.WebUtil.waitForVisibility;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPage extends BasePage<MainPage>{
    public MainPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h2")
    private WebElement welcomeMessage;

    public static final String URL = ConfigurationReader.getProperty("url");


    @Step("Check Main page is loaded")
    @Override
    public MainPage checkPageLoaded() {
        waitForVisibility(welcomeMessage);
        assertTrue(welcomeMessage.isDisplayed(), "Main Page did not loaded properly");
        return this;
    }
}
