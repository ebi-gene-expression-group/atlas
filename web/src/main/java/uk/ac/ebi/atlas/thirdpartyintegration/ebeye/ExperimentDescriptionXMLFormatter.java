package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.inject.Named;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
public class ExperimentDescriptionXMLFormatter {

    private static final String HEADER = "<database>\n" +
            "<name>Expression Atlas</name>\n" +
            "<description>\n" +
            "A semantically enriched database of publicly available gene and transcript expression data. The data is re-analysed in-house to detect genes showing interesting baseline and differential expression patterns under the conditions of the original experiment\n" +
            "</description>\n" +
            "<release_date>{0}</release_date>\n" +
            "<entry_count>{1}</entry_count>\n"  +
            "<entries>\n";

    private static final String ENTRY = "<entry id=\"{0}\">\n" +
            "<name>{0}</name>\n" +
            "<description>{1}</description>\n" +
            "<cross_references>\n" +
            "<ref dbname=\"arrayexpress\" dbkey=\"{0}\"/>\n" +
            "</cross_references>\n" +
            "</entry>";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("d-MMM-yyyy");

    public String formatFooter() {
        return "</entries>\n" +
                "</database>";
    }

    public String formatHeader(int entryCount, Date releaseDate) {
        return MessageFormat.format(HEADER, formatDate(releaseDate), entryCount);
    }

    public String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public String formatExperimentDescription(ExperimentDescription ed) {
        String escapedDescription = StringEscapeUtils.escapeXml(ed.getDescription());
        return MessageFormat.format(ENTRY, ed.getAccession(), escapedDescription);
    }
}
