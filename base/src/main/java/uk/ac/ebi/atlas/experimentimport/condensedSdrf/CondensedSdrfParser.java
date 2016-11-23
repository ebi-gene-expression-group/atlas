package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

/*
Crazy, but it's how it goes. If you're working with this code, consider removing FactorSet and replacing it with
immutable map, type -> Factor. The intent is kind of captured by hiding FactorSet behind the immutable FactorGroup
interface, but there's lots of test code looking very confused about adding factors to a "set".
 */
@Named
public class CondensedSdrfParser {

    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet("compound", "irradiate");

    private static final String DOSE = "dose";
    private static final String ONTOLOGY_TERM_DELIMITER = " ";

    // private static final int EXPERIMENT_ACCESSION_INDEX = 0;
    private static final int ASSAY_DESIGN_INDEX = 1;
    private static final int RUN_OR_ASSAY_INDEX = 2;
    private static final int FACTOR_OR_CHARACTERISTIC_INDEX = 3;
    private static final int FACTOR_TYPE_INDEX = 4;
    private static final int CHARACTERISTIC_TYPE_INDEX = 4;
    private static final int FACTOR_VALUE_INDEX = 5;
    private static final int CHARACTERISTIC_VALUE_INDEX = 5;
    private static final int ONTOLOGY_TERMS_INDEX = 6;

    private static final String FACTOR = "factor";
    private static final String CHARACTERISTIC = "characteristic";

    private final DataFileHub dataFileHub;
    private final IdfParser idfParser;
    // private final ValueAndUnitJoiner valueAndUnitJoiner;


    @Inject
    public CondensedSdrfParser(DataFileHub dataFileHub,
                               IdfParser idfParser) { //ValueAndUnitJoiner valueAndUnitJoiner) {
        this.dataFileHub = dataFileHub;
        this.idfParser = idfParser;
        // this.valueAndUnitJoiner = valueAndUnitJoiner;

    }


    public CondensedSdrfParserOutput parse(String experimentAccession, ExperimentType experimentType) throws CondensedSdrfParserException {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        TsvReader tsvReader = dataFileHub.getExperimentFiles(experimentAccession).condensedSdrf.get();

        ImmutableList.Builder<String[]> factorsBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<String[]> sampleCharacteristicsBuilder = new ImmutableList.Builder<>();
        for (String[] tsvLine : tsvReader.readAll()) {
            switch (tsvLine[FACTOR_OR_CHARACTERISTIC_INDEX]) {
                case FACTOR:
                    factorsBuilder.add(tsvLine);
                    break;
                case CHARACTERISTIC:
                    sampleCharacteristicsBuilder.add(tsvLine);
                    break;
                default:
                    throw new CondensedSdrfParserException(String.format(
                            "Parsing condensed SDRF for %s: Unknown factor/characteristic descriptor “%s”",
                            experimentAccession, tsvLine[FACTOR_OR_CHARACTERISTIC_INDEX]));
            }
        }

        Multimap<String, String[]> assayRunToTsvLines = mapFactorTsvLinesByAssayRun(factorsBuilder.build());
        addFactorValuesToExperimentDesign(experimentDesign, assayRunToTsvLines);
        addCharacteristicToExperimentDesign(experimentDesign, sampleCharacteristicsBuilder.build());

        addArraysToExperimentDesign(experimentDesign, assayRunToTsvLines);

        Pair<String, ImmutableSet<String>> idfParserOutput = idfParser.parse(experimentAccession);

        return new CondensedSdrfParserOutput(experimentAccession, experimentType, idfParserOutput.getLeft(), idfParserOutput.getRight(), experimentDesign);
    }


    private void addCharacteristicToExperimentDesign(ExperimentDesign experimentDesign, List<String[]> sampleCharacteristicsTsvLines) {

        for (String[] sampleCharacteristicTsvLine : sampleCharacteristicsTsvLines) {
            String header = sampleCharacteristicTsvLine[CHARACTERISTIC_TYPE_INDEX];
            String value = sampleCharacteristicTsvLine[CHARACTERISTIC_VALUE_INDEX]; // cleanValueAndUnitIfNeeded(sampleCharacteristicTsvLine[CHARACTERISTIC_VALUE_INDEX]);
            String characteristicValueOntologyTermAsString = sampleCharacteristicTsvLine.length > ONTOLOGY_TERMS_INDEX ? sampleCharacteristicTsvLine[ONTOLOGY_TERMS_INDEX] : null;
            OntologyTerm[] characteristicOntologyTerms = parseOntologyTerms(characteristicValueOntologyTermAsString);

            SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(header, value, characteristicOntologyTerms);

            if (experimentDesign.getFactors(sampleCharacteristicTsvLine[RUN_OR_ASSAY_INDEX]) != null) {
                experimentDesign.putSampleCharacteristic(sampleCharacteristicTsvLine[RUN_OR_ASSAY_INDEX], header, sampleCharacteristic);
            }
        }
    }

    private void addFactorValuesToExperimentDesign(ExperimentDesign experimentDesign, Multimap<String, String[]> factorsByAssayRun) {

        for (String runOrAssay : factorsByAssayRun.keys()) {

            String compoundFactorType = null;
            String compoundFactorValue = null;
            String compoundFactorValueOntologyTermAsString = null;

            for (String[] factorTsvLine : factorsByAssayRun.get(runOrAssay)) {

                String factorType = factorTsvLine[FACTOR_TYPE_INDEX];
                String factorValue = factorTsvLine[FACTOR_VALUE_INDEX];
                //cleanValueAndUnitIfNeeded(factorTsvLine[FACTOR_VALUE_INDEX]);
                String factorValueOntologyTermAsString = factorTsvLine.length > ONTOLOGY_TERMS_INDEX ? factorTsvLine[ONTOLOGY_TERMS_INDEX] : null;

                if (isFactorThatHasADose(factorType)) {

                    compoundFactorType = factorType;
                    compoundFactorValue = factorValue;
                    compoundFactorValueOntologyTermAsString = factorValueOntologyTermAsString;

                } else if (isDoseFactor(factorType)) {

                    if (!Strings.isNullOrEmpty(compoundFactorValue)) {
                        factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                        factorType = compoundFactorType;
                        factorValueOntologyTermAsString = compoundFactorValueOntologyTermAsString;

                        compoundFactorType = null;
                        compoundFactorValue = null;
                        compoundFactorValueOntologyTermAsString = null;
                    } else {
                        throw new CondensedSdrfParserException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                    }

                }

                experimentDesign.putFactor(runOrAssay, factorType, factorValue, parseOntologyTerms(factorValueOntologyTermAsString));
            }

            //Add compound factor in a case there was no dose corresponding to it
            if (!Strings.isNullOrEmpty(compoundFactorType) && !Strings.isNullOrEmpty(compoundFactorValue)) {
                experimentDesign.putFactor(runOrAssay, compoundFactorType, compoundFactorValue, parseOntologyTerms(compoundFactorValueOntologyTermAsString));
            }

        }

    }


    private boolean isFactorThatHasADose(String factorName) {
        return FACTORS_NEEDING_DOSE.contains(factorName.toLowerCase());
    }


    private boolean isDoseFactor(String factorName) {
        return DOSE.equals(factorName.toLowerCase());
    }


    private OntologyTerm[] parseOntologyTerms(String ontologyTermTsvField) {
        ImmutableSet.Builder<OntologyTerm> ontologyTermsBuilder = new ImmutableSet.Builder<>();

        if (!Strings.isNullOrEmpty(ontologyTermTsvField)) {
            for (String ontologyTermAsString : ontologyTermTsvField.split(ONTOLOGY_TERM_DELIMITER)) {
                ontologyTermsBuilder.add(OntologyTerm.createFromURI(ontologyTermAsString));
            }
        }

        Set<OntologyTerm> ontologyTerms = ontologyTermsBuilder.build();
        return ontologyTerms.toArray(new OntologyTerm[ontologyTerms.size()]);
    }


    private void addArraysToExperimentDesign(ExperimentDesign experimentDesign, Multimap<String, String[]> assayRunToTsvLines) {
        for (String assayRun : assayRunToTsvLines.keys()) {

            String assayDesign = assayRunToTsvLines.get(assayRun).iterator().next()[ASSAY_DESIGN_INDEX];
            for (String[] tsvLine : assayRunToTsvLines.get(assayRun)) {
                if (!tsvLine[ASSAY_DESIGN_INDEX].equals(assayDesign)) {
                    throw new CondensedSdrfParserException("Assays with multiple array designs are not supported.");
                }
            }

            experimentDesign.putArrayDesign(assayRun, assayDesign);
        }
    }


//    protected String cleanValueAndUnitIfNeeded(String value) {
//        if (!Strings.isNullOrEmpty(value)) {
//            value = value.replaceAll("( )+", " ").replaceAll("(_)+", "_").trim();
//            value = valueAndUnitJoiner.pluraliseAndJoin(value);
//        }
//        return value;
//    }


    private Multimap<String, String[]> mapFactorTsvLinesByAssayRun(List<String[]> factorTsvLines) {

        ImmutableMultimap.Builder<String, String[]> multimapBuilder = new ImmutableMultimap.Builder<>();
        for (String[] factorTsvLine : factorTsvLines) {
            multimapBuilder.put(factorTsvLine[RUN_OR_ASSAY_INDEX], factorTsvLine);
        }

        return multimapBuilder.build();

    }


    class CondensedSdrfParserException extends RuntimeException {
        CondensedSdrfParserException(String message) {
            super(message);
        }
    }

}
