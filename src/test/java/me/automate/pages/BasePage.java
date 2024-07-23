package me.automate.pages;

import io.qameta.allure.Step;
import me.automate.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static me.automate.utilities.WebUtil.waitForClickability;

public abstract class BasePage<T extends BasePage<?>> {

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public abstract T checkPageLoaded();

    @FindBy(xpath = "//a[.='Home']")
    private WebElement headerHomeButton;

    @FindBy(xpath = "//a[.='Contact Us']")
    private WebElement headerContactUs;

    @FindBy(xpath = "//a[.='Waiting']")
    private WebElement headerWaiting;

    @Step("Go to Home from header")
    public MainPage clickHomeHeader() {
        waitForClickability(headerHomeButton);
        headerHomeButton.click();
        return new MainPage();
    }

    @Step("Go to Contact Us from header")
    public ContactUsPage clickContactUs() {
        waitForClickability(headerContactUs);
        headerContactUs.click();
        return new ContactUsPage();
    }

    @Step("Go to Waiting from header")
    public WaitingPage clickWaitingHeader() {
        waitForClickability(headerWaiting);
        headerWaiting.click();
        return new WaitingPage();
    }


}
