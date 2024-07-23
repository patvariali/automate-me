package me.automate.pages;

import com.github.javafaker.Faker;
import me.automate.utilities.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static me.automate.utilities.WebUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaitingPage extends BasePage<WaitingPage> {
    private final Actions actions = new Actions(Driver.getDriver());
    private final Faker faker = new Faker();
    private final String firstAccordInputFaker = faker.lorem().characters(910);
    private final String secondAccordInputFaker = faker.lorem().characters(910);
    public static final String URL = MainPage.URL + "/waiting";

    @FindBy(xpath = "//h2[.='Waiting']")
    private WebElement waitingMessage;

    @FindBy(xpath = "//button[@data-testid='enableShowAlertButton']")
    private WebElement enableShowAlertButton;

    @FindBy(xpath = "//button[@data-testid='ShowAlertButton']")
    private WebElement showAlertButton;

    @FindBy(xpath = "//button[.='Accordion Item #1']")
    private WebElement accordionOne;

    @FindBy(xpath = "//input[@id='accordion1Text']")
    private WebElement accordionOneInput;

    @FindBy(xpath = "//button[.='Accordion Item #2']")
    private WebElement accordionTwo;

    @FindBy(xpath = "//input[@id='accordion2Text']")
    private WebElement accordionTwoInput;

    @FindBy(xpath = "//button[.='Result']")
    private WebElement accordionResult;

    @FindBy(xpath = "//button[.='Show Accordion Message']")
    private WebElement accordionResultShowButton;

    public WaitingPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @Step("Check Waiting page is loaded")
    @Override
    public WaitingPage checkPageLoaded() {
        waitForVisibility(waitingMessage);
        assertTrue(waitingMessage.isDisplayed(), "Waiting page did not load properly");
        return this;
    }

    private WaitingPage clickButton(WebElement button) {
        waitForClickability(button);
        actions.moveToElement(button).click().perform();
        return this;
    }

    @Step("Click on 'Enable Show Alert Button'")
    public WaitingPage clickEnableShowAlertButton() {
        return clickButton(enableShowAlertButton);
    }

    @Step("Click on 'Show Alert Button'")
    public WaitingPage clickShowAlertButton() {
        return clickButton(showAlertButton);
    }

    @Step("Check Alert Message is: {alertMessage}")
    public WaitingPage checkAlertMessage(String alertMessage) {
        Alert alert = waitForAlert();
        assertEquals(alertMessage, alert.getText(), "Alert message did not match expected");
        return this;
    }

    public WaitingPage checkAlertMessage() {
        return checkAlertMessage(firstAccordInputFaker + " " + secondAccordInputFaker);
    }

    private WaitingPage fillAccordion(WebElement accordion, WebElement inputField, String input) {
        waitForClickability(accordion);
        actions.moveToElement(accordion).click().perform();
        waitForVisibility(inputField);
        inputField.sendKeys(input);
        return this;
    }

    @Step("Fill accordion one with input: {input}")
    public WaitingPage fillAccordionOne(String input) {
        return fillAccordion(accordionOne, accordionOneInput, input);
    }

    public WaitingPage fillAccordionOneFaker() {
        return fillAccordionOne(firstAccordInputFaker);
    }

    @Step("Fill accordion two with input: {input}")
    public WaitingPage fillAccordionTwo(String input) {
        return fillAccordion(accordionTwo, accordionTwoInput, input);
    }

    public WaitingPage fillAccordionTwoFaker() {
        return fillAccordionTwo(secondAccordInputFaker);
    }

    @Step("Show result alert")
    public WaitingPage showResultAlert() {
        clickButton(accordionResult);
        clickButton(accordionResultShowButton);
        return this;
    }
}
