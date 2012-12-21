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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignFeedbackPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentDesignFeedbackIT extends SeleniumFixture {

    private ExperimentDesignFeedbackPage subject;

    public void getStartingPage() {
        subject = new ExperimentDesignFeedbackPage(driver);
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

    @Test
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

    @Test
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