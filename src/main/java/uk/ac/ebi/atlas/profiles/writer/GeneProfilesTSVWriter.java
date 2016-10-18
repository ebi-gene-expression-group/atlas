package uk.ac.ebi.atlas.profiles.writer;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public abstract class GeneProfilesTSVWriter<T extends Profile, K, O extends ProfileStreamOptions> implements AutoCloseable {

    private static final String GENE_NAME_COLUMN_NAME = "Gene Name";
    private static final String GENE_ID_COLUMN_NAME = "Gene ID";
    private final CsvWriterFactory csvWriterFactory;

    private CSVWriter csvWriter;
    private Writer responseWriter;

    public GeneProfilesTSVWriter(CsvWriterFactory csvWriterFactory) {
        this.csvWriterFactory = csvWriterFactory;
    }

    public long write(GeneProfilesList<T> geneProfilesList, Set<K> conditions, O options, boolean isGeneSet) throws
            IOException {

        responseWriter.write(getTsvFileMasthead(options, isGeneSet) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options,isGeneSet));

        for (T profile : geneProfilesList) {
            String[] csvRow = buildCsvRow(profile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return (long) geneProfilesList.size();
    }

    public long write(ObjectInputStream<T> inputStream, Set<K> conditions, O options, boolean isGeneSet) throws IOException {

        responseWriter.write(getTsvFileMasthead(options, isGeneSet) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options, isGeneSet));

        long count = 0;
        T geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            String[] csvRow = buildCsvRow(geneProfile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return count;
    }

    public long write(Iterable<T> inputStream, Set<K> conditions, O options, boolean isGeneSet) throws IOException {

        responseWriter.write(getTsvFileMasthead(options, isGeneSet) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options, isGeneSet));

        long count = 0;
        for (T geneProfile : inputStream) {
            ++count;
            String[] csvRow = buildCsvRow(geneProfile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return count;
    }

    @Override
    public void close() throws IOException {
        csvWriter.close();
    }

    protected abstract String[] getConditionColumnHeaders(Set<K> conditions);

    protected abstract String getTsvFileMasthead(O options, boolean isGeneSet);

    protected String[] getProfileIdColumnHeaders(O options, boolean isGeneSet){
        return new String[]{GENE_ID_COLUMN_NAME, GENE_NAME_COLUMN_NAME};
    }

    protected String[] buildCsvColumnHeaders(Set<K> conditionValues, O options, boolean isGeneSet) {
        String[] profileIdColumnHeaders = getProfileIdColumnHeaders(options, isGeneSet);
        String[] conditionColumnHeaders = getConditionColumnHeaders(conditionValues);
        return buildCsvRow(profileIdColumnHeaders, conditionColumnHeaders);
    }

    String[] buildCsvRow(final T geneProfile, Set<K> conditions) {
        String[] expressionLevels = extractConditionLevels(geneProfile, conditions);

        String[] rowHeaders = getRowHeaders(geneProfile);
        return buildCsvRow(rowHeaders, expressionLevels);
    }

    String[] getRowHeaders(T geneProfile) {
        String geneProfileId = geneProfile.getId();
        String geneName = geneProfile.getName();
        String secondaryRowHeader = getSecondaryRowHeader(geneProfile);

        //for Microarray experiment
        if (!StringUtils.isBlank(secondaryRowHeader)) {
            return new String[]{geneProfileId, geneName, secondaryRowHeader};
        }
        //For geneSet gene Id is null
        if(geneProfileId == null) {
            return new String[]{geneName};
        }
        return new String[]{geneProfileId, geneName};
    }

    protected abstract String getSecondaryRowHeader(T profile);

    protected abstract String[] extractConditionLevels(T geneProfile, Set<K> conditions);

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

    public void setResponseWriter(Writer responseWriter) {
        this.responseWriter = responseWriter;
        csvWriter = csvWriterFactory.createTsvWriter(responseWriter);
    }

}