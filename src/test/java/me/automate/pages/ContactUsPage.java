package me.automate.pages;

import com.github.javafaker.Faker;
import me.automate.utilities.Driver;
import io.qameta.allure.Step;
import me.automate.utilities.WebUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactUsPage extends BasePage<ContactUsPage> {

    private final Actions actions = new Actions(Driver.getDriver());
    private final Faker faker = new Faker();
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String city = faker.address().city();
    private final String state = faker.address().state();
    private final String zip = faker.address().zipCode();
    private final String message = faker.lorem().characters(100);
    public static final String URL = MainPage.URL + "/contact";

    @FindBy(xpath = "//h2")
    private WebElement contactUsMessage;

    @FindBy(xpath = "//input[@id='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='firstName']/following-sibling::div[@class='valid-feedback']")
    private WebElement firstNameValidation;

    @FindBy(xpath = "//input[@id='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='City']")
    private WebElement cityInput;

    @FindBy(xpath = "//select[@id='addressState']")
    private WebElement selectState;

    @FindBy(xpath = "//input[@placeholder='Zip']")
    private WebElement zipInput;

    @FindBy(xpath = "//textarea[@id='messageBox']")
    private WebElement messageInput;

    @FindBy(xpath = "//label[contains(., 'Agree to terms and conditions')]/preceding-sibling::input")
    private WebElement termsAgreeBox;

    @FindBy(xpath = "//div[.='You must agree before submitting.']")
    private WebElement termsAgreeError;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public ContactUsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @Step("Check Contact Us page is loaded")
    @Override
    public ContactUsPage checkPageLoaded() {
        WebUtil.waitForVisibility(contactUsMessage);
        assertTrue(contactUsMessage.isDisplayed(), "Contact Us page did not load correctly.");
        return this;
    }

    @Step("Send form with details: First Name='{firstName}', Last Name='{lastName}', City='{city}', State='{state}', Zip='{zip}', Message='{message}', Terms Agreed='{termsAgree}'")
    public SuccessPage sendSuccessForm(String firstName, String lastName, String city, String state, String zip, String message, boolean termsAgree) {
        WebUtil.waitForVisibility(firstNameInput, lastNameInput, cityInput, selectState, zipInput, messageInput, termsAgreeBox, submitButton);

        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        cityInput.sendKeys(city);
        new Select(selectState).selectByVisibleText(state);
        zipInput.sendKeys(zip);
        messageInput.sendKeys(message);

        if (termsAgree && !termsAgreeBox.isSelected()) {
            termsAgreeBox.click();
        }

        WebUtil.waitForClickability(submitButton);
        actions.moveToElement(submitButton).click().perform();

        return new SuccessPage();
    }

    @Step("Send form with Faker-generated details")
    public SuccessPage sendSuccessFormWithFaker() {
        return sendSuccessForm(firstName, lastName, city, state, zip, message, true);
    }

    @Step("Send form with First and Last name only")
    public ContactUsPage sendFirstLastNameForm(String firstName, String lastName) {
        sendSuccessForm(firstName, lastName, "", "", "", "", false);
        return this;
    }

    @Step("Verify the validation message for First name")
    public ContactUsPage checkFirstNameValid() {
        assertTrue(firstNameValidation.isDisplayed(), "First name validation message is not displayed.");
        return this;
    }

    @Step("Verify the error message under the Terms and Conditions checkbox")
    public ContactUsPage checkTermsConditionError() {
        assertTrue(termsAgreeError.isDisplayed(), "Terms and Conditions error message is not displayed.");
        return this;
    }
}
