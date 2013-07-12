package uk.ac.ebi.atlas.expdesign;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AbstractSDRFNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.FactorValueAttribute;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.UnitAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ExperimentDesignMageTabParser<T extends AbstractSDRFNode> {

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private PropertyMergeService propertyMergeService;

    private MAGETABInvestigation investigation;

    private ExperimentDesign experimentDesign;

    @Inject
    public void setPropertyMergeService(PropertyMergeService propertyMergeService) {
        this.propertyMergeService = propertyMergeService;
    }

    @Inject
    protected void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    public ExperimentDesign parse(String experimentAccession)  throws ExperimentDesignWritingException{


        try {
            investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
        } catch (ParseException | MalformedURLException e) {
            throw new ExperimentDesignWritingException("Cannot read or parse SDRF file: ", e);
        }

        experimentDesign = new ExperimentDesign();

        addCharacteristics();
        addFactorValues();

        addArrays(experimentDesign);

        return experimentDesign;
    }

    protected MAGETABInvestigation getInvestigation() {
        return investigation;
    }

    protected void addCharacteristics() {

        Map<String, T> assayNameToNode = getAssayNameToNode();
        for (String assayName : assayNameToNode.keySet()) {

            Collection<SourceNode> sourceNodes = getUpstreamSourceNodes(assayName, assayNameToNode.get(assayName));

            if (sourceNodes.size() != 1) {
                throw new IllegalStateException("There is no one to one mapping between sdrfNode and sourceNode for sdrfNode: " + assayName);
            }

            SourceNode sourceNode = sourceNodes.iterator().next();
            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {


                String value = cleanValueAndUnitIfNeeded(characteristicsAttribute.getNodeName(), characteristicsAttribute.unit);
                experimentDesign.putSample(assayName, characteristicsAttribute.type, value);
            }
        }

    }

    // Set containing all factors for which values need doses merged into them
    private static final Set<String> FACTORS_NEEDING_DOSE = Sets.newHashSet();


    static {
        FACTORS_NEEDING_DOSE.add("compound");
        FACTORS_NEEDING_DOSE.add("irradiate");
    }

    private static final String DOSE = "dose";

    protected void addFactorValues() {

        String compoundFactorValue = null;
        String compoundFactorType = null;


        Map<String, T> assayNameToNode = getAssayNameToNode();

        for (String assayName : assayNameToNode.keySet()) {

            for (FactorValueAttribute factor : getFactorAttributes(assayNameToNode.get(assayName))) {

                String factorType = factor.type;
                String factorValue = cleanValueAndUnitIfNeeded(factor.getNodeName(), factor.unit);

                if (FACTORS_NEEDING_DOSE.contains(factor.type.toLowerCase())) {
                    compoundFactorType = factorType;
                    compoundFactorValue = factorValue;

                } else if (DOSE.equals(factor.type.toLowerCase())) {
                    if (StringUtils.isNotEmpty(compoundFactorValue)) {

                        factorValue = Joiner.on(" ").join(compoundFactorValue, factorValue);
                        factorType = compoundFactorType;

                        compoundFactorType = null;
                        compoundFactorValue = null;
                    } else {
                        throw new IllegalStateException(DOSE + " : " + factorValue + " has no corresponding value for any of the following factors: " + FACTORS_NEEDING_DOSE);
                    }

                }
                experimentDesign.putFactor(assayName, factorType, factorValue);
            }

            //Add compound factor in a case there was no dose corresponding to it
            if (StringUtils.isNotEmpty(compoundFactorType) && StringUtils.isNotEmpty(compoundFactorValue)) {
                experimentDesign.putFactor(assayName, compoundFactorType, compoundFactorValue);
            }

        }
    }

    protected String cleanValueAndUnitIfNeeded(String value, UnitAttribute unit) {
        if (!StringUtils.isEmpty(value)) {
            value.replaceAll("( )+", " ").replaceAll("(_)+", "_").trim();
            if (unit != null) {
                if (StringUtils.isEmpty(unit.getAttributeType())) {
                    throw new IllegalStateException("Unable to find unit value for factor value: " + value);
                }
                value = propertyMergeService.mergeValueAndUnit(value, unit.getAttributeValue());
            }
        }
        return value;
    }

    protected abstract List<FactorValueAttribute> getFactorAttributes(T node);

    protected abstract void addArrays(ExperimentDesign experimentDesign);

    protected abstract Map<String, T> getAssayNameToNode();

    protected abstract Collection<SourceNode> getUpstreamSourceNodes(String assayName, T assayNode);

}
