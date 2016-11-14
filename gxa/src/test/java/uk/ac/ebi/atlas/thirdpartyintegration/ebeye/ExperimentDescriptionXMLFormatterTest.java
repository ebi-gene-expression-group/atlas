package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentDescriptionXMLFormatterTest {

    private ExperimentDescriptionXMLFormatter subject = new ExperimentDescriptionXMLFormatter();

    @Test
    public void dateFormat() throws ParseException {
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("21/12/2012");
        assertThat(subject.formatDate(date1), is("21-Dec-2012"));

        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("2/12/2012");
        assertThat(subject.formatDate(date2), is("2-Dec-2012"));
    }

    @Test
    public void header() throws ParseException {
        int entryCount = 100;
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("2/12/2012");

        String header = "<database>\n" +
                "<name>Expression Atlas</name>\n" +
                "<description>\n" +
                "A semantically enriched database of publicly available gene and transcript expression data. The data is re-analysed in-house to detect genes showing interesting baseline and differential expression patterns under the conditions of the original experiment\n" +
                "</description>\n" +
                "<release_date>2-Dec-2012</release_date>\n" +
                "<entry_count>100</entry_count>\n"  +
                "<entries>\n";

        assertThat(subject.formatHeader(entryCount, date2), is(header));
    }

    @Test
    public void footer() {
        assertThat(subject.formatFooter(), is("</entries>\n" +
                "</database>"));
    }

    @Test
    public void entry() {
        String result = "<entry id=\"E-MEXP-1743\">\n" +
                "<name>E-MEXP-1743</name>\n" +
                "<description>SALK 098602 mutant vs wild-type Col-0</description>\n" +
                "<cross_references>\n" +
                "<ref dbname=\"arrayexpress\" dbkey=\"E-MEXP-1743\"/>\n" +
                "</cross_references>\n" +
                "</entry>";

        ExperimentDescription ed = new ExperimentDescription("E-MEXP-1743", "SALK 098602 mutant vs wild-type Col-0");

        assertThat(subject.formatExperimentDescription(ed), is(result));
    }

    @Test
    public void specialCharactersInDescriptionAreEscaped() {
        String result = "<entry id=\"E-GEOD-31677\">\n" +
                "<name>E-GEOD-31677</name>\n" +
                "<description>Long-term Salt &amp; Water Stress in Grapes</description>\n" +
                "<cross_references>\n" +
                "<ref dbname=\"arrayexpress\" dbkey=\"E-GEOD-31677\"/>\n" +
                "</cross_references>\n" +
                "</entry>";

        ExperimentDescription ed = new ExperimentDescription("E-GEOD-31677", "Long-term Salt & Water Stress in Grapes");

        assertThat(subject.formatExperimentDescription(ed), is(result));
    }

}
