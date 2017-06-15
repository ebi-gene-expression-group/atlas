package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.removeEnd;

public class ExperimentDesignFileWriter {

    private static final String ONTOLOGY_TERM_DELIMITER = " ";

    private static final String SAMPLE_CHARACTERISTICS_NAME_HEADER_TEMPLATE = "Sample Characteristic[{0}]";
    private static final String SAMPLE_CHARACTERISTICS_ONTOLOGY_TERM_HEADER_TEMPLATE = "Sample Characteristic Ontology Term[{0}]";
    private static final String FACTOR_NAME_HEADER_TEMPLATE = "Factor Value[{0}]";
    private static final String FACTOR_VALUE_ONTOLOGY_TERM_TEMPLATE = "Factor Value Ontology Term[{0}]";

    private TsvWriter tsvWriter;
    private ExperimentType experimentType;

    public ExperimentDesignFileWriter(TsvWriter tsvWriter, ExperimentType experimentType){
        this.tsvWriter = tsvWriter;
        this.experimentType = experimentType;
    }

    public void write(ExperimentDesign experimentDesign) throws IOException {
        String[] columnHeaders = buildColumnHeaders(experimentType, experimentDesign);
        tsvWriter.writeNext(columnHeaders);
        tsvWriter.writeAll(asTableOntologyTermsData(experimentDesign));
        tsvWriter.close();
    }

    String[] buildColumnHeaders(ExperimentType experimentType, ExperimentDesign experimentDesign) {
        List<String> headers = Lists.newArrayList(getCommonColumnHeaders(experimentType));
        headers.addAll(toHeaders(experimentDesign.getSampleHeaders(), SAMPLE_CHARACTERISTICS_NAME_HEADER_TEMPLATE, SAMPLE_CHARACTERISTICS_ONTOLOGY_TERM_HEADER_TEMPLATE));
        headers.addAll(toHeaders(experimentDesign.getFactorHeaders(), FACTOR_NAME_HEADER_TEMPLATE, FACTOR_VALUE_ONTOLOGY_TERM_TEMPLATE));

        return headers.toArray(new String[headers.size()]);
    }

    private List<String> toHeaders(SortedSet<String> propertyNames, final String headerTemplate1, final String headerTemplate2) {
        List<String> headers = new ArrayList<>();
        for (String propertyName: propertyNames){
            headers.add(MessageFormat.format(headerTemplate1, propertyName));
            headers.add(MessageFormat.format(headerTemplate2, propertyName));
        }
        return headers;
    }

    private List<String> getCommonColumnHeaders(ExperimentType experimentType){
        switch(experimentType.getParent()){
            case MICROARRAY_ANY:
                return Lists.newArrayList("Assay", "Array");
            case RNASEQ_MRNA_BASELINE:
            case RNASEQ_MRNA_DIFFERENTIAL:
            case PROTEOMICS_BASELINE:
                return Lists.newArrayList("Run");
            case SINGLE_CELL_RNASEQ_MRNA_BASELINE:
                return Lists.newArrayList("Cell");
            default:
                throw new IllegalStateException("Invalid parent type: " + experimentType.getParent());
        }
    }

    List<String[]> asTableOntologyTermsData(ExperimentDesign experimentDesign) {
        List<String[]> tableData = Lists.newArrayList();
        tableData.addAll(experimentDesign.getAllRunOrAssay().stream().map(runOrAssay -> composeTableRowWithOntologyTerms(experimentDesign, runOrAssay)).collect(Collectors.toList()));
        return tableData;
    }

    private String[] composeTableRowWithOntologyTerms(ExperimentDesign experimentDesign, String runOrAssay) {
        List<String> row = Lists.newArrayList(runOrAssay);

        String arrayDesign = experimentDesign.getArrayDesign(runOrAssay);
        if (!Strings.isNullOrEmpty(arrayDesign)) {
            row.add(arrayDesign);
        }

        for (String sampleHeader : experimentDesign.getSampleHeaders()) {
            SampleCharacteristic sampleCharacteristic = experimentDesign.getSampleCharacteristic(runOrAssay, sampleHeader);
            addSampleCharacteristicValue(row, sampleCharacteristic);
            addSampleCharacteristicOntologyTerm(row, sampleCharacteristic);
        }

        for (String factorHeader : experimentDesign.getFactorHeaders()) {
            Factor factor = experimentDesign.getFactor(runOrAssay, factorHeader);
            addFactorValue(row, factor);
            addFactorValueOntologyTerm(row, factor);
        }

        return row.toArray(new String[row.size()]);
    }

    private void addFactorValue(List<String> row, Factor factor) {
        String factorValue = (factor == null) ? null : factor.getValue();
        row.add(factorValue);
    }

    private void addFactorValueOntologyTerm(List<String> row, Factor factor) {
        String factorValueOntologyTermId = (factor == null || factor.getValueOntologyTerms().isEmpty()) ? null : joinURIs(factor.getValueOntologyTerms());
        row.add(factorValueOntologyTermId);
    }

    private void addSampleCharacteristicValue(List<String> row, SampleCharacteristic sampleCharacteristic) {
        String value = (sampleCharacteristic == null) ? null : sampleCharacteristic.value();
        row.add(value);
    }

    private void addSampleCharacteristicOntologyTerm(List<String> row, SampleCharacteristic sampleCharacteristic) {
        String ontologyTermId = (sampleCharacteristic == null || sampleCharacteristic.valueOntologyTerms().isEmpty()) ? null : joinURIs(sampleCharacteristic.valueOntologyTerms());
        row.add(ontologyTermId);
    }

    private static String joinURIs(Set<OntologyTerm> ontologyTerms) {
        StringBuilder sb = new StringBuilder();
        for (OntologyTerm ontologyTerm : ontologyTerms) {
            sb.append(ontologyTerm.uri()).append(ONTOLOGY_TERM_DELIMITER);
        }

        return removeEnd(sb.toString(), ONTOLOGY_TERM_DELIMITER);
    }
}
