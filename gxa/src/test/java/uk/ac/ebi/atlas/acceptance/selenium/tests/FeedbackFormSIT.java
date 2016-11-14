
package uk.ac.ebi.atlas.acceptance.selenium.tests;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.FeedbackHomePage;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FeedbackFormSIT extends SinglePageSeleniumFixture {

    private FeedbackHomePage subject;

    public void getStartingPage() {
        subject = new FeedbackHomePage(driver);
        subject.get();
    }

    @Test
    public void emptyFeedbackForm() {
        subject.clickFeedbackLink();
        assertThat(subject.getFeedbackTipsText(), is("Please fill this form and click the Send button."));
        assertThat(subject.getFeedbackText(), is(""));
        assertThat(subject.getEmailText(), is(""));
    }

    @Test
    public void sendEmptyFeedback() throws InterruptedException {
        subject.clickFeedbackLink();
        assertThat(subject.getFeedbackTipsText(), is("Please fill this form and click the Send button."));
        assertThat(subject.getFeedbackText(), is(""));
        subject.clickSendButton();
        assertThat(subject.getFeedbackTipsText(), is("Length of the feedback field must be between 3 and 10000 characters."));
    }

    @Test
    public void cancelFeedback() {
        subject.clickFeedbackLink();
        assertThat(subject.getFeedbackTipsText(), is("Please fill this form and click the Send button."));
        assertThat(subject.getFeedbackText(), is(""));
        subject.clickCancelButton();
        assertThat(subject.getFeedbackText(), is(""));
        assertThat(subject.isFeedbackTipsShown(), is(false));
    }

    @Ignore // Flakey
    public void sendTestFeedback() throws InterruptedException {
        subject.clickFeedbackLink();
        assertThat(subject.getFeedbackText(), is(""));
        subject.setFeedbackText("Test Feedback");
        assertThat(subject.getFeedbackText(), is("Test Feedback"));
        subject.clickSendButton();
        assertThat(subject.getCancelButtonText(), is("Close"));
        subject.clickCancelButton();
        assertThat(subject.isFeedbackTipsShown(), is(false));
    }

    @Ignore // Flakey
    public void sendTestFeedbackAndEmail() throws InterruptedException {
        subject.clickFeedbackLink();
        assertThat(subject.getFeedbackText(), is(""));
        subject.setFeedbackText("Test Feedback");
        assertThat(subject.getFeedbackText(), is("Test Feedback"));
        subject.setEmailText("atlas-feedback@ebi.ac.uk");
        assertThat(subject.getEmailText(), is("atlas-feedback@ebi.ac.uk"));
        subject.clickSendButton();
        assertThat(subject.getCancelButtonText(), is("Close"));
        subject.clickCancelButton();
        assertThat(subject.isFeedbackTipsShown(), is(false));
    }

}