package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

@Named("streamGeneProfiles")
@Scope("prototype")
public class StreamGeneProfilesCommand extends GeneProfilesInputStreamCommand<Long> {

    private OutputStreamWriter outputStreamWriter;

    private ApplicationProperties applicationProperties;

    @Inject
    protected StreamGeneProfilesCommand(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected Long apply(RequestPreferences requestPreferences
                                                , ObjectInputStream<GeneProfile> inputStream) throws IOException {

        long count = 0;

        CSVWriter csvWriter = new CSVWriter(outputStreamWriter, '\t'/*, CSVWriter.NO_QUOTE_CHARACTER*/);

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
        csvWriter.flush();
        csvWriter.close();
        return count;
    }

    protected String[] buildCsvHeaders(Set<String> organismParts){
        return buildCsvRow(new String[]{"Gene name", "Gene Id"}, organismParts.toArray(new String[organismParts.size()]));
    }

    protected String[] buildCsvRow(final GeneProfile geneProfile, SortedSet<String> organismParts){
        String [] expressionLevels = new String[organismParts.size()];
        int i = 0;
        for (String organismPart : organismParts){
            expressionLevels[i++] = "" + geneProfile.getExpressionLevel(organismPart);
        }
        return buildCsvRow(new String[]{geneProfile.getGeneName(), geneProfile.getGeneId()}, expressionLevels);
    }

    protected String[] buildCsvRow(String[] rowHeaders, String[] values){

        int rowHeadersCount = rowHeaders.length;
        String [] csvRow = new String[rowHeadersCount + values.length];

        for (int i = 0; i < rowHeadersCount; i++){
            csvRow[i] = rowHeaders[i];
        }
        for (int i = 0; i < values.length; i++){
            csvRow[i + rowHeadersCount] = values[i];
        }
        return csvRow;
    }


    public void setOutputStreamWriter(OutputStreamWriter outputStreamWriter){
        this.outputStreamWriter = outputStreamWriter;
    }

}