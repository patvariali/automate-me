package me.automate.test;

import me.automate.jupiter.annotation.meta.WebTest;
import me.automate.pages.WaitingPage;
import me.automate.utilities.Driver;
import org.junit.jupiter.api.Test;

@WebTest
class WaitingTest {

    @Test
    void verifyFirstAlertMessage() {
        Driver.open(WaitingPage.URL, WaitingPage.class)
                .clickEnableShowAlertButton()
                .clickShowAlertButton()
                .checkAlertMessage("False alarm!");
    }

    @Test
    void verifySecondAlertMessageWithStaticData() {
        String firstInput = "Test1";
        String secondInput = "Test2";

        Driver.open(WaitingPage.URL, WaitingPage.class)
                .fillAccordionOne(firstInput)
                .fillAccordionTwo(secondInput)
                .showResultAlert()
                .checkAlertMessage(firstInput + " " + secondInput);
    }

    @Test
    void verifySecondAlertMessageWithFaker() {
        Driver.open(WaitingPage.URL, WaitingPage.class)
                .fillAccordionOneFaker()
                .fillAccordionTwoFaker()
                .showResultAlert()
                .checkAlertMessage();
    }
}
