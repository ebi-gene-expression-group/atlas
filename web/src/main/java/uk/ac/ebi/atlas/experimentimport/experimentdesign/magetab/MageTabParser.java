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

        Set<AssayNode<T>> assayNodes;
        ImmutableMap<String, String> factorNamesToType;

        try {
            MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
            assayNodes = getAssayNodes(investigation.SDRF);
            factorNamesToType = buildFactorNameToTypeMap(investigation.IDF);
        } catch (ParseException | MalformedURLException e) {
            throw new IOException("Cannot read or parse SDRF file: ", e);
        }

        ExperimentDesign experimentDesign = new ExperimentDesign();

        SetMultimap<String, String> characteristicsOntologyTerms = HashMultimap.create();

        for (AssayNode<T> assayNode : assayNodes) {
            SourceNode sourceNode = findFirstUpstreamSourceNode(assayNode);

            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                addCharacteristicToExperimentDesign(experimentDesign, assayNode.getName(), characteristicsAttribute);
                if (!Strings.isNullOrEmpty(characteristicsAttribute.termAccessionNumber)) {
                    characteristicsOntologyTerms.put(assayNode.getName(), characteristicsAttribute.termAccessionNumber);
                }
            }

            addFactorValues(experimentDesign, assayNode, factorNamesToType);
        }

        addArrays(experimentDesign, assayNodes);

        return new MageTabParserOutput(experimentDesign, characteristicsOntologyTerms);
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

    private void addCharacteristicToExperimentDesign(ExperimentDesign experimentDesign, String name, CharacteristicsAttribute characteristicsAttribute) {
        String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
        experimentDesign.putSample(name, characteristicsAttribute.type, value);
    }

    private SourceNode findFirstUpstreamSourceNode(AssayNode<T> assayNode) {
        Collection<SourceNode> sourceNodes = findUpstreamSourceNodes(assayNode);

        if (sourceNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between sdrfNode and sourceNode for sdrfNode: " + assayNode);
        }

        return sourceNodes.iterator().next();
    }

    protected void addFactorValues(ExperimentDesign experimentDesign, AssayNode<T> assayNode, ImmutableMap<String, String> factorNamesToType) {

        String compoundFactorValue = null;
        String compoundFactorType = null;
        String compoundFactorValueOntologyTerm = null;

        for (FactorValueAttribute factorValueAttribute : getFactorAttributes(assayNode.getSdrfNode())) {

            String factorName = factorValueAttribute.type; // the SDRF calls this type, but in the IDF the same value is actually factor name
            String factorValue = cleanValueAndUnitIfNeeded(factorValueAttribute.getNodeName(), factorValueAttribute.unit);
            String factorValueOntologyTerm = factorValueAttribute.termAccessionNumber;

            if (isFactorThatHasADose(factorValueAttribute)) {

                compoundFactorType = factorName;
                compoundFactorValue = factorValue;
                compoundFactorValueOntologyTerm = factorValueOntologyTerm;

            } else if (isDoseFactor(factorValueAttribute)) {

                if (StringUtils.isNotEmpty(compoundFactorValue)) {
                    factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                    factorName = compoundFactorType;
                    factorValueOntologyTerm = compoundFactorValueOntologyTerm;

                    compoundFactorType = null;
                    compoundFactorValue = null;
                    compoundFactorValueOntologyTerm = null;
                } else {
                    throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                }

            }

            String factorType = factorNamesToType.get(factorName);

            experimentDesign.putFactor(assayNode.getName(), factorType, factorValue, factorValueOntologyTerm);
        }

        //Add compound factor in a case there was no dose corresponding to it
        if (StringUtils.isNotEmpty(compoundFactorType) && StringUtils.isNotEmpty(compoundFactorValue)) {
            experimentDesign.putFactor(assayNode.getName(), compoundFactorType, compoundFactorValue, compoundFactorValueOntologyTerm);
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

    protected abstract List<FactorValueAttribute> getFactorAttributes(T sdrfNode);

    protected abstract void addArrays(ExperimentDesign experimentDesign, Set<AssayNode<T>> assayNodes);

    protected abstract Set<AssayNode<T>> getAssayNodes(SDRF sdrf);

    protected abstract Collection<SourceNode> findUpstreamSourceNodes(AssayNode<T> assayNode);

}
