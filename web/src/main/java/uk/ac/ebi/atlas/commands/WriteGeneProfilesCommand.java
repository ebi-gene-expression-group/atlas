package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import static java.lang.System.arraycopy;

@Named("streamGeneProfiles")
@Scope("prototype")
public class WriteGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private CSVWriter csvWriter;

    private ApplicationProperties applicationProperties;

    @Inject
    protected WriteGeneProfilesCommand(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected Long apply(RequestPreferences requestPreferences
                                                , ObjectInputStream<GeneProfile> inputStream) throws IOException {

        long count = 0;

        SortedSet<String> organismParts = requestPreferences.getOrganismParts();
        if (organismParts == null || organismParts.isEmpty()){
            organismParts = applicationProperties.getOrganismParts();
        }

        csvWriter.writeNext(buildCsvHeaders(organismParts));

        GeneProfile geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            csvWriter.writeNext(buildCsvRow(geneProfile, organismParts));
        }
        return count;
    }

    protected String[] buildCsvHeaders(Set<String> organismParts){
        return buildCsvRow(new String[]{"Gene name", "Gene Id"}, organismParts.toArray(new String[organismParts.size()]));
    }

    protected String[] buildCsvRow(final GeneProfile geneProfile, SortedSet<String> organismParts){
        String [] expressionLevels = new String[organismParts.size()];
        int i = 0;
        for (String organismPart : organismParts){
            expressionLevels[i++] = NumberUtils.roundToString(geneProfile.getExpressionLevel(organismPart));
        }
        return buildCsvRow(new String[]{geneProfile.getGeneName(), geneProfile.getGeneId()}, expressionLevels);
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values){

        int rowHeadersCount = rowHeaders.length;
        String [] csvRow = new String[rowHeadersCount + values.length];

        arraycopy(rowHeaders, 0, csvRow, 0, rowHeadersCount);
        arraycopy(values, 0, csvRow, 0 + rowHeadersCount, values.length);
        return csvRow;
    }


    public void setCsvWriter(CSVWriter csvWriter){
        this.csvWriter = csvWriter;
    }

}