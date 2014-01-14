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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

//ToDo (B): maybe this should not be a hierarchy but should rather "use" an ExperimentDesignBuilder (family) to build ExperimentDesign differently depending on the ExperimentType.
//ToDo (B): Single Responsibility Principle...
public abstract class MageTabParser<T extends AbstractSDRFNode> {

    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet("compound", "irradiate");

    private static final String DOSE = "dose";

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private ValueAndUnitJoiner valueAndUnitJoiner;

    private ExperimentDesign experimentDesign;

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

        try {
            MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
            assayNodes = getAssayNodes(investigation.SDRF);
        } catch (ParseException | MalformedURLException e) {
            throw new IOException("Cannot read or parse SDRF file: ", e);
        }

        experimentDesign = new ExperimentDesign();

        SetMultimap<String, String> characteristicsOntologyTerms = HashMultimap.create();

        for (AssayNode<T> assayNode : assayNodes) {
            SourceNode sourceNode = findFirstUpstreamSourceNode(assayNode);

            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                addCharacteristicToExperimentDesign(assayNode.getName(), characteristicsAttribute);
                if (!Strings.isNullOrEmpty(characteristicsAttribute.termAccessionNumber)) {
                    characteristicsOntologyTerms.put(assayNode.getName(), characteristicsAttribute.termAccessionNumber);
                }
            }

            addFactorValues(assayNode);
        }

        addArrays(experimentDesign, assayNodes);

        return new MageTabParserOutput(experimentDesign, characteristicsOntologyTerms);
    }

    private void addCharacteristicToExperimentDesign(String name, CharacteristicsAttribute characteristicsAttribute) {
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

    protected void addFactorValues(AssayNode<T> assayNode) {

        String compoundFactorValue = null;
        String compoundFactorType = null;

        for (FactorValueAttribute factorValueAttribute : getFactorAttributes(assayNode.getSdrfNode())) {

            String factorType = factorValueAttribute.type; //(B) isn't that factorName
            String factorValue = cleanValueAndUnitIfNeeded(factorValueAttribute.getNodeName(), factorValueAttribute.unit);

            if (FACTORS_NEEDING_DOSE.contains(factorValueAttribute.type.toLowerCase())) {
                compoundFactorType = factorType;
                compoundFactorValue = factorValue;

            } else if (DOSE.equals(factorValueAttribute.type.toLowerCase())) {
                if (StringUtils.isNotEmpty(compoundFactorValue)) {

                    factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                    factorType = compoundFactorType;

                    compoundFactorType = null;
                    compoundFactorValue = null;
                } else {
                    throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                }

            }
            experimentDesign.putFactor(assayNode.getName(), factorType, factorValue);
        }

        //Add compound factor in a case there was no dose corresponding to it
        if (StringUtils.isNotEmpty(compoundFactorType) && StringUtils.isNotEmpty(compoundFactorValue)) {
            experimentDesign.putFactor(assayNode.getName(), compoundFactorType, compoundFactorValue);
        }
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
