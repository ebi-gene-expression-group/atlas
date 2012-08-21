package uk.ac.ebi.atlas.acceptance.selenium;

import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VisualizeTopTenExpressionLevels {

	private WebDriver driver = new HtmlUnitDriver();

	private ExperimentPage subject;
	
	@Before
	public void initializeTest(){
        subject = new ExperimentPage(driver);
        subject.get();
    }

    @Test
    public void titleShallBeExperiment(){
        String title = driver.getTitle();
    	assertThat(title, is(subject.getTitle()));
    }

    @Test
    public void verifyMostExpressedTranscriptId(){
        assertThat(subject.getMostExpressedTranscriptId(), is("ENST1"));
    }

    @Test
    public void numberOfRowsInExpressionLevelsTableShallBeTen(){
        assertThat(subject.getTableRowCount(), is(10));
    }
}