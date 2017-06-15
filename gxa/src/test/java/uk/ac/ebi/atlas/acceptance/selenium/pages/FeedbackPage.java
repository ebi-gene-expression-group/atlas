
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class FeedbackPage extends AtlasPage {

    @FindBy(id = "feedback-link")
    private WebElement feedbackLink;

    @FindBy(id = "feedback")
    private WebElement feedback;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "send-button")
    private WebElement send;

    @FindBy(id = "cancel-button")
    private WebElement cancel;

    @FindBy(id = "feedback-tips")
    private WebElement tips;

    FeedbackPage(WebDriver driver) {
        super(driver);
    }

    public FeedbackPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public void clickFeedbackLink() {
        feedbackLink.click();
    }

    public String getFeedbackText() {
        return feedback.getAttribute("value");
    }

    public void setFeedbackText(String text) {
        feedback.click();
        feedback.sendKeys(text);
    }

    public String getEmailText() {
        return email.getAttribute("value");
    }

    public void setEmailText(String text) {
        email.click();
        email.sendKeys(text);
    }

    public void clickSendButton() {
        final String feedbackTipsBeforeClick = this.getFeedbackTipsText();
        // to prevent spamming the feedback email
        ((JavascriptExecutor) driver).executeScript("$('#sendemail').val('false')");
        send.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(webDriver -> !FeedbackPage.this.getFeedbackTipsText().equals(feedbackTipsBeforeClick));

    }

    public void clickCancelButton() {
        cancel.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(webDriver -> !FeedbackPage.this.tips.isDisplayed());
    }

    public String getCancelButtonText() {
        return cancel.getText();
    }

    public String getFeedbackTipsText() {
        return tips.getText();
    }

    public boolean isFeedbackTipsShown() {
        return tips.isDisplayed();
    }
}