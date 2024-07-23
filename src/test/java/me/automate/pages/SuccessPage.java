package me.automate.pages;

import me.automate.utilities.Driver;
import io.qameta.allure.Step;
import me.automate.utilities.WebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static me.automate.utilities.WebUtil.waitForPageToLoad;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuccessPage extends BasePage<SuccessPage>{
    public SuccessPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h2")
    private WebElement successMessage;

    public SuccessPage checkSuccessMessage() {
        waitForPageToLoad(10);
        checkPageLoaded();
        return this;
    }

    @Step("Check Success page is loaded and the success message is visible")
    @Override
    public SuccessPage checkPageLoaded() {
        WebUtil.waitForVisibility(successMessage);
        assertTrue(successMessage.isDisplayed(), "Success page did not loaded properly");
        return this;
    }
}
