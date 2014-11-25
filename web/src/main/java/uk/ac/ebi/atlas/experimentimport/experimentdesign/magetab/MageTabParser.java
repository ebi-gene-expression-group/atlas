/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.IDF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.SDRF;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AbstractSDRFNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.UnitAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//ToDo (B): maybe this should not be a hierarchy but should rather "use" an ExperimentDesignBuilder (family) to build ExperimentDesign differently depending on the ExperimentType.
//ToDo (B): Single Responsibility Principle...
public abstract class MageTabParser<T extends AbstractSDRFNode> {

    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet("compound", "irradiate");

    private static final String DOSE = "dose";
    private static final String ONTOLOGY_TERM_DELIMITER = "\\|";

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private ValueAndUnitJoiner valueAndUnitJoiner;

    @Inject
    public void setValueAndUnitJoiner(ValueAndUnitJoiner valueAndUnitJoiner) {
        this.valueAndUnitJoiner = valueAndUnitJoiner;
    }

    @Inject
    protected void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    public MageTabParserOutput parse(String experimentAccession)  throws IOException{

        MAGETABInvestigation investigation = readMageTabFiles(experimentAccession);

        Set<NamedSdrfNode<T>> namedSdrfNodes = getAssayNodes(investigation.SDRF);
        ImmutableMap<String, String> factorNamesToType = buildFactorNameToTypeMap(investigation.IDF);

        ExperimentDesign experimentDesign = new ExperimentDesign();

        for (NamedSdrfNode<T> namedSdrfNode : namedSdrfNodes) {
            SourceNode sourceNode = findFirstUpstreamSourceNode(namedSdrfNode);

            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                addCharacteristicToExperimentDesign(experimentDesign, namedSdrfNode.getName(), characteristicsAttribute);
            }

            addFactorValues(experimentDesign, namedSdrfNode, factorNamesToType);
        }

        addArrays(experimentDesign, namedSdrfNodes);

        String title = mageTabLimpopoUtils.extractInvestigationTitle(investigation);
        ImmutableSet<String> pubmedIds = ImmutableSet.copyOf(mageTabLimpopoUtils.extractPubMedIdsFromIDF(investigation));

        return new MageTabParserOutput(title, pubmedIds, experimentDesign);
    }

    private MAGETABInvestigation readMageTabFiles(String experimentAccession) throws IOException {
        try {
            return mageTabLimpopoUtils.parseInvestigation(experimentAccession);
        } catch (ParseException | MalformedURLException e) {
            throw new IOException("Cannot read or parse SDRF file: ", e);
        }
    }

    private ImmutableMap<String, String> buildFactorNameToTypeMap(IDF idf) {
        Iterator<String> experimentalFactorNames = idf.experimentalFactorName.iterator();
        Iterator<String> experimentalFactorTypes = idf.experimentalFactorType.iterator();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        while (experimentalFactorNames.hasNext()) {
            String name = experimentalFactorNames.next();
            String type = experimentalFactorTypes.next();
            builder.put(name, type);
        }

        return builder.build();
    }

    private void addCharacteristicToExperimentDesign(ExperimentDesign experimentDesign, String runOrAssay, CharacteristicsAttribute characteristicsAttribute) {
        String header = characteristicsAttribute.type;
        String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
        OntologyTerm[] characteristicOntologyTerms = parseOntologyTerms(characteristicsAttribute.termAccessionNumber, characteristicsAttribute.termSourceREF);

        SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(header, value, characteristicOntologyTerms);
        experimentDesign.putSampleCharacteristic(runOrAssay, header, sampleCharacteristic);
    }

    private SourceNode findFirstUpstreamSourceNode(NamedSdrfNode<T> namedSdrfNode) {
        Collection<SourceNode> sourceNodes = findUpstreamSourceNodes(namedSdrfNode);

        if (sourceNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between sdrfNode and sourceNode for sdrfNode: " + namedSdrfNode);
        }

        return sourceNodes.iterator().next();
    }

    protected void addFactorValues(ExperimentDesign experimentDesign, NamedSdrfNode<T> namedSdrfNode, ImmutableMap<String, String> factorNamesToType) {

        String compoundFactorValue = null;
        String compoundFactorName = null;
        String compoundFactorValueOntologyTermAccessionNumbers = null;
        String compoundFactorValueOntologyTermSourceRefs = null;

        String runOrAssay = namedSdrfNode.getName();

        for (FactorValueAttribute factorValueAttribute : getFactorAttributes(namedSdrfNode)) {

            String factorName = factorValueAttribute.type; // the SDRF calls this type, but in the IDF the same value is actually factor name
            String factorValue = cleanValueAndUnitIfNeeded(factorValueAttribute.getNodeName(), factorValueAttribute.unit);
            String factorValueOntologyTermAccessionNumbers = factorValueAttribute.termAccessionNumber;
            String factorValueOntologyTermSourceRefs = factorValueAttribute.termSourceREF;

            if (isFactorThatHasADose(factorValueAttribute)) {

                compoundFactorName = factorName;
                compoundFactorValue = factorValue;
                compoundFactorValueOntologyTermAccessionNumbers = factorValueOntologyTermAccessionNumbers;
                compoundFactorValueOntologyTermSourceRefs = factorValueOntologyTermSourceRefs;

            } else if (isDoseFactor(factorValueAttribute)) {

                if (StringUtils.isNotEmpty(compoundFactorValue)) {
                    factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                    factorName = compoundFactorName;
                    factorValueOntologyTermAccessionNumbers = compoundFactorValueOntologyTermAccessionNumbers;
                    factorValueOntologyTermSourceRefs = compoundFactorValueOntologyTermSourceRefs;

                    compoundFactorName = null;
                    compoundFactorValue = null;
                    compoundFactorValueOntologyTermAccessionNumbers = null;
                    compoundFactorValueOntologyTermSourceRefs = null;
                } else {
                    throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                }

            }

            String factorType = factorNamesToType.get(factorName);
            experimentDesign.putFactor(runOrAssay, factorType, factorValue, parseOntologyTerms(factorValueOntologyTermAccessionNumbers, factorValueOntologyTermSourceRefs));
        }

        //Add compound factor in a case there was no dose corresponding to it
        if (StringUtils.isNotEmpty(compoundFactorName) && StringUtils.isNotEmpty(compoundFactorValue)) {
            String compoundFactorType = factorNamesToType.get(compoundFactorName);
            experimentDesign.putFactor(runOrAssay, compoundFactorType, compoundFactorValue, parseOntologyTerms(compoundFactorValueOntologyTermAccessionNumbers, compoundFactorValueOntologyTermSourceRefs));
        }
    }

    private boolean isFactorThatHasADose(FactorValueAttribute factorValueAttribute) {
        return FACTORS_NEEDING_DOSE.contains(factorValueAttribute.type.toLowerCase());
    }

    private boolean isDoseFactor(FactorValueAttribute factorValueAttribute) {
        return DOSE.equals(factorValueAttribute.type.toLowerCase());
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

    private OntologyTerm[] parseOntologyTerms(String accessionNumberField, String sourceRefField) {
        ImmutableSet.Builder<OntologyTerm> ontologyTermsBuilder = new ImmutableSet.Builder<>();

        if (accessionNumberField == null) {
            return ontologyTermsBuilder.build().toArray(new OntologyTerm[0]);
        }

        String[] accessionNumbers = accessionNumberField.split(ONTOLOGY_TERM_DELIMITER);
        String[] sourceRefs = sourceRefField.split(ONTOLOGY_TERM_DELIMITER);

        for (int i = 0 ; i < accessionNumbers.length ; i++) {
            if (!accessionNumbers[i].isEmpty()) {
                ontologyTermsBuilder.add(new OntologyTerm(accessionNumbers[i], sourceRefs[i]));
            }
        }
        return ontologyTermsBuilder.build().toArray(new OntologyTerm[0]);
    }

    protected abstract List<FactorValueAttribute> getFactorAttributes(NamedSdrfNode<T> sdrfNodeWrapper);

    protected abstract void addArrays(ExperimentDesign experimentDesign, Set<NamedSdrfNode<T>> namedSdrfNodes);

    protected abstract Set<NamedSdrfNode<T>> getAssayNodes(SDRF sdrf);

    protected abstract Collection<SourceNode> findUpstreamSourceNodes(NamedSdrfNode<T> namedSdrfNode);

}
