package me.automate.test;

import me.automate.jupiter.annotation.meta.WebTest;
import me.automate.pages.ContactUsPage;
import me.automate.utilities.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@WebTest
class ContactUsTest {

    @Test
    void successfulContactUsTest() {
        Driver.open(ContactUsPage.URL, ContactUsPage.class)
                .sendSuccessForm("First", "Last", "Minneapolis", "Minnesota", "55408", "It's a test", true)
                .checkSuccessMessage();
    }

    @Test
    void successfulContactUsFakerTest() {
        Driver.open(ContactUsPage.URL, ContactUsPage.class)
                .sendSuccessFormWithFaker()
                .checkSuccessMessage();
    }

    @Test
    void firstNameLastNameContactUsTest() {
        Driver.open(ContactUsPage.URL, ContactUsPage.class)
                .sendFirstLastNameForm("First", "Last")
                .checkFirstNameValid()
                .checkTermsConditionError();
    }

    @Test
    void explicitlyFailedTest() {
        Driver.open(ContactUsPage.URL, ContactUsPage.class);
        fail("Explicitly failed test");
    }
}
