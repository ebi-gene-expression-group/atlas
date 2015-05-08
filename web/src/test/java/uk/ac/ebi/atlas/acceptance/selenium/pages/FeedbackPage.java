/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !FeedbackPage.this.getFeedbackTipsText().equals(feedbackTipsBeforeClick);
            }
        });

    }

    public void clickCancelButton() {
        cancel.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !FeedbackPage.this.tips.isDisplayed();
            }
        });
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