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
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
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

        Set<NamedSdrfNode<T>> namedSdrfNodes;
        ImmutableMap<String, String> factorNamesToType;

        try {
            MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
            namedSdrfNodes = getAssayNodes(investigation.SDRF);
            factorNamesToType = buildFactorNameToTypeMap(investigation.IDF);
        } catch (ParseException | MalformedURLException e) {
            throw new IOException("Cannot read or parse SDRF file: ", e);
        }

        ExperimentDesign experimentDesign = new ExperimentDesign();

        SetMultimap<String, String> ontologyTermIdsByAssayAccession = HashMultimap.create();

        for (NamedSdrfNode<T> namedSdrfNode : namedSdrfNodes) {
            SourceNode sourceNode = findFirstUpstreamSourceNode(namedSdrfNode);

            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                addCharacteristicToExperimentDesign(experimentDesign, namedSdrfNode.getName(), characteristicsAttribute);

                if (!Strings.isNullOrEmpty(characteristicsAttribute.termAccessionNumber)) {
                    ontologyTermIdsByAssayAccession.put(namedSdrfNode.getName(), characteristicsAttribute.termAccessionNumber);
                }
            }

            addFactorValues(experimentDesign, namedSdrfNode, factorNamesToType);
        }

        addArrays(experimentDesign, namedSdrfNodes);

        return new MageTabParserOutput(experimentDesign, ontologyTermIdsByAssayAccession);
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
        String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
        Optional<OntologyTerm> characteristicOntologyTerm = OntologyTerm.createOptional(characteristicsAttribute.termAccessionNumber, characteristicsAttribute.termSourceREF);
        SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(runOrAssay, value, characteristicOntologyTerm);
        experimentDesign.putSampleCharacteristic(runOrAssay, characteristicsAttribute.type, sampleCharacteristic);
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
        String compoundFactorValueOntologyTermAccessionNumber = null;
        String compoundFactorValueOntologyTermSourceRef = null;

        String runOrAssay = namedSdrfNode.getName();

        for (FactorValueAttribute factorValueAttribute : getFactorAttributes(namedSdrfNode)) {

            String factorName = factorValueAttribute.type; // the SDRF calls this type, but in the IDF the same value is actually factor name
            String factorValue = cleanValueAndUnitIfNeeded(factorValueAttribute.getNodeName(), factorValueAttribute.unit);
            String factorValueOntologyTermAccessionNumber = factorValueAttribute.termAccessionNumber;
            String factorValueOntologyTermSourceRef = factorValueAttribute.termSourceREF;

            if (isFactorThatHasADose(factorValueAttribute)) {

                compoundFactorName = factorName;
                compoundFactorValue = factorValue;
                compoundFactorValueOntologyTermAccessionNumber = factorValueOntologyTermAccessionNumber;
                compoundFactorValueOntologyTermSourceRef = factorValueOntologyTermSourceRef;

            } else if (isDoseFactor(factorValueAttribute)) {

                if (StringUtils.isNotEmpty(compoundFactorValue)) {
                    factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                    factorName = compoundFactorName;
                    factorValueOntologyTermAccessionNumber = compoundFactorValueOntologyTermAccessionNumber;
                    factorValueOntologyTermSourceRef = compoundFactorValueOntologyTermSourceRef;

                    compoundFactorName = null;
                    compoundFactorValue = null;
                    compoundFactorValueOntologyTermAccessionNumber = null;
                    compoundFactorValueOntologyTermSourceRef = null;
                } else {
                    throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                }

            }

            String factorType = factorNamesToType.get(factorName);
            Optional<OntologyTerm> ontologyTerm = OntologyTerm.createOptional(factorValueOntologyTermAccessionNumber, factorValueOntologyTermSourceRef);
            experimentDesign.putFactor(runOrAssay, factorType, factorValue, ontologyTerm);
        }

        //Add compound factor in a case there was no dose corresponding to it
        if (StringUtils.isNotEmpty(compoundFactorName) && StringUtils.isNotEmpty(compoundFactorValue)) {
            String compoundFactorType = factorNamesToType.get(compoundFactorName);
            Optional<OntologyTerm> ontologyTerm = OntologyTerm.createOptional(compoundFactorValueOntologyTermAccessionNumber, compoundFactorValueOntologyTermSourceRef);
            experimentDesign.putFactor(runOrAssay, compoundFactorType, compoundFactorValue, ontologyTerm);
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

    protected abstract List<FactorValueAttribute> getFactorAttributes(NamedSdrfNode<T> sdrfNodeWrapper);

    protected abstract void addArrays(ExperimentDesign experimentDesign, Set<NamedSdrfNode<T>> namedSdrfNodes);

    protected abstract Set<NamedSdrfNode<T>> getAssayNodes(SDRF sdrf);

    protected abstract Collection<SourceNode> findUpstreamSourceNodes(NamedSdrfNode<T> namedSdrfNode);

}
