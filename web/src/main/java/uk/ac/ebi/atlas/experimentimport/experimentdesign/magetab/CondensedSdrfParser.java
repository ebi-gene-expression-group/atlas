package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import autovalue.shaded.com.google.common.common.collect.ImmutableList;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.UnitAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 24/07/15.
 */
public class CondensedSdrfParser {

    private static final Logger LOGGER = Logger.getLogger(CondensedSdrfParser.class);

    @Value("#{configuration['experiment.condensed-sdrf.path.template']}")
    private String sdrfPathTemplate;

    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet("compound", "irradiate");

    private static final String DOSE = "dose";
    private static final String ONTOLOGY_TERM_DELIMITER = " ";

    private static final int EXPERIMENT_ACCESSION_INDEX = 0;
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

    private TsvReaderBuilder tsvReaderBuilder;

    private MageTabLimpopoUtils mageTabLimpopoUtils;
    private ValueAndUnitJoiner valueAndUnitJoiner;

    @Inject
    public CondensedSdrfParser(TsvReaderBuilder tsvReaderBuilder, MageTabLimpopoUtils mageTabLimpopoUtils, ValueAndUnitJoiner valueAndUnitJoiner) {
        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(sdrfPathTemplate);
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
        this.valueAndUnitJoiner = valueAndUnitJoiner;
    }

    CondensedSdrfParserOutput parse(String experimentAccession) throws IOException, CondensedSdrfParserException {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        TsvReader tsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        ImmutableList.Builder<String[]> factorsBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<String[]> sampleCharacteristicsBuilder = new ImmutableList.Builder<>();

        for (String[] tsvLine : tsvReader.readAll()) {
            if (tsvLine[FACTOR_OR_CHARACTERISTIC_INDEX].equals(FACTOR)) {
                factorsBuilder.add(tsvLine);
            } else if (tsvLine[FACTOR_OR_CHARACTERISTIC_INDEX].equals(CHARACTERISTIC)) {
                sampleCharacteristicsBuilder.add(tsvLine);
            } else {
                LOGGER.warn(String.format(
                        "Parsing condensed SDRF for %s: Unknown factor/characteristic descriptor “%s”",
                        experimentAccession, tsvLine[FACTOR_OR_CHARACTERISTIC_INDEX]));
            }
        }

        Multimap<String, String[]> assayRunToTsvLines = mapFactorTsvLinesByAssayRun(factorsBuilder.build());
        addFactorValuesToExperimentDesign(experimentDesign, assayRunToTsvLines);
        addCharacteristicToExperimentDesign(experimentDesign, sampleCharacteristicsBuilder.build());

        addArrays(experimentDesign, assayRunToTsvLines);

        MAGETABInvestigation investigation = readMageTabFiles(experimentAccession);
        String title = mageTabLimpopoUtils.extractInvestigationTitle(investigation);
        ImmutableSet<String> pubmedIds = ImmutableSet.copyOf(mageTabLimpopoUtils.extractPubMedIdsFromIDF(investigation));

        return new CondensedSdrfParserOutput(title, pubmedIds, experimentDesign);
    }

    private void addCharacteristicToExperimentDesign(ExperimentDesign experimentDesign, List<String[]> sampleCharacteristicsTsvLines) {

        for (String[] sampleCharacteristicTsvLine : sampleCharacteristicsTsvLines) {
            String header = sampleCharacteristicTsvLine[CHARACTERISTIC_TYPE_INDEX];
            String value = sampleCharacteristicTsvLine[CHARACTERISTIC_VALUE_INDEX];
            //String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
            OntologyTerm[] characteristicOntologyTerms = parseOntologyTerms(sampleCharacteristicTsvLine[ONTOLOGY_TERMS_INDEX]);

            SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(header, value, characteristicOntologyTerms);
            experimentDesign.putSampleCharacteristic(sampleCharacteristicTsvLine[ASSAY_DESIGN_INDEX], header, sampleCharacteristic);
        }
    }

    protected void addFactorValuesToExperimentDesign(ExperimentDesign experimentDesign, Multimap<String, String[]> factorsByAssayRun) {

        for (String runOrAssay : factorsByAssayRun.keys()) {

            String compoundFactorType = null;
            String compoundFactorValue = null;
            String compoundFactorValueOntologyTermAsString = null;

            for (String[] factorTsvLine : factorsByAssayRun.get(runOrAssay)) {

                String factorType = factorTsvLine[FACTOR_TYPE_INDEX];
                String factorValue = factorTsvLine[FACTOR_VALUE_INDEX];
                //String factorValue = cleanValueAndUnitIfNeeded(factorValueAttribute.getNodeName(), factorValueAttribute.unit);
                String factorValueOntologyTermAsString = factorTsvLine[ONTOLOGY_TERMS_INDEX];

                if (isFactorThatHasADose(factorType)) {

                    compoundFactorType = factorType;
                    compoundFactorValue = factorValue;
                    compoundFactorValueOntologyTermAsString = factorValueOntologyTermAsString;

                } else if (isDoseFactor(factorType)) {

                    if (StringUtils.isNotEmpty(compoundFactorValue)) {
                        factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                        factorType = compoundFactorType;
                        factorValueOntologyTermAsString = compoundFactorValueOntologyTermAsString;

                        compoundFactorType = null;
                        compoundFactorValue = null;
                        compoundFactorValueOntologyTermAsString = null;
                    } else {
                        throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                    }

                }

                experimentDesign.putFactor(runOrAssay, factorType, factorValue, parseOntologyTerms(factorValueOntologyTermAsString));
            }

            //Add compound factor in a case there was no dose corresponding to it
            if (StringUtils.isNotEmpty(compoundFactorType) && StringUtils.isNotEmpty(compoundFactorValue)) {
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

    protected String cleanValueAndUnitIfNeeded(String value, UnitAttribute unit) {
        if (!StringUtils.isEmpty(value)) {
            value.replaceAll("( )+", " ").replaceAll("(_)+", "_").trim();
            if (unit != null) {
                if (StringUtils.isEmpty(unit.getAttributeType())) {
                    throw new IllegalStateException("Unable to find unit value for factor value: " + value);
                }
                value = valueAndUnitJoiner.pluraliseAndJoin(value, unit.getAttributeValue());
            }
        }
        return value;
    }

    private OntologyTerm[] parseOntologyTerms(String ontologyTermTsvField) {
        ImmutableSet.Builder<OntologyTerm> ontologyTermsBuilder = new ImmutableSet.Builder<>();

        if (!Strings.isNullOrEmpty(ontologyTermTsvField)) {
            for (String ontologyTermAsString : ontologyTermTsvField.split(ONTOLOGY_TERM_DELIMITER)) {
                ontologyTermsBuilder.add(OntologyTerm.create(ontologyTermAsString));
            }
        }

        Set<OntologyTerm> ontologyTerms = ontologyTermsBuilder.build();
        return ontologyTerms.toArray(new OntologyTerm[ontologyTerms.size()]);
    }

    protected void addArrays(ExperimentDesign experimentDesign, Multimap<String, String[]> assayRunToTsvLines) {
        for (String assayRun : assayRunToTsvLines.keys()) {

            String assayDesign = assayRunToTsvLines.get(assayRun).iterator().next()[ASSAY_DESIGN_INDEX];
            for (String[] tsvLine : assayRunToTsvLines.get(assayRun)) {
                if (!tsvLine[ASSAY_DESIGN_INDEX].equals(assayDesign)) {
                    throw new IllegalStateException("Assays with multiple array designs are not supported.");
                }
            }

            experimentDesign.putArrayDesign(assayRun, assayDesign);
        }
    }


    private MAGETABInvestigation readMageTabFiles(String experimentAccession) throws IOException {
        try {
            return mageTabLimpopoUtils.parseInvestigation(experimentAccession);
        } catch (ParseException | MalformedURLException e) {
            throw new IOException("Cannot read or parse SDRF file: ", e);
        }
    }


    private class CondensedSdrfParserException extends RuntimeException {
        public CondensedSdrfParserException(String message) {
            super(message);
        }

    }

    Multimap<String, String[]> mapFactorTsvLinesByAssayRun(List<String[]> factorTsvLines) {

        ImmutableMultimap.Builder<String, String[]> multimapBuilder = new ImmutableMultimap.Builder<>();

        for (String[] factorTsvLine : factorTsvLines) {
            multimapBuilder.put(factorTsvLine[RUN_OR_ASSAY_INDEX], factorTsvLine);
        }

        return multimapBuilder.build();
    }
}
