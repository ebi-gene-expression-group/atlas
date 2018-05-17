package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.ArrayDesign;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MockExperiment {

    private static final String SPECIES_NAME = "Homo sapiens";
    private static final SpeciesProperties SPECIES_PROPERTIES = SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.<ImmutableMap<String, String>>of());
    private static final String DESCRIPTION = "This is the experiment description";
    private static final String DISPLAY_NAME = "Experiment Display Name";
    private static final List<String> PUBMEDID = Arrays.asList("PUBMEDID");
    private static final List<String> DOI = Arrays.asList("100.100/doi");

    private static final List<String> PROVIDER_URL = Arrays.asList("http://www.provider.com","http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION = Arrays.asList("Baseline experiment data provider","Another baseline experiment data provider");

    private static final String EXPERIMENT_ACCESSION = RandomDataTestUtils.getRandomExperimentAccession();

    private static final ImmutableSet<String> ARRAY_DESIGN_ACCESSIONS = ImmutableSet.of("A-AFFY-44", "A-GEOD-20277");

    private static final List<ArrayDesign> arrayDesigns = ImmutableList.of(
            ArrayDesign.create("A-AFFY-44", "Affymetrix GeneChip Human Genome U133 Plus 2.0 [HG-U133_Plus_2]"),
            ArrayDesign.create("A-GEOD-20277", "TaqMan® Array Human MicroRNA A+B Cards Set v3.0")
    );

    private static final List<Contrast> contrasts = ImmutableList.of(
            new Contrast(
                    "g1_g2",
                    ARRAY_DESIGN_ACCESSIONS.iterator().next(),
                    MockAssayGroups.create().get(0),
                    MockAssayGroups.create().get(1),
                    "contrast"));

    public static BaselineExperiment createBaselineExperiment() {
        return createBaselineExperiment(EXPERIMENT_ACCESSION);
    }

    public static BaselineExperiment createBaselineExperiment(String accession) {
        return createBaselineExperiment(
                accession,
                mockExperimentDesign(MockAssayGroups.create()),
                MockAssayGroups.create(),
                ExperimentDisplayDefaults.simpleDefaults());
    }

    public static BaselineExperiment createBaselineExperiment(List<String> pubmedIds, List<String> dois) {
        return createBaselineExperiment(
                EXPERIMENT_ACCESSION,
                mockExperimentDesign(MockAssayGroups.create()),
                MockAssayGroups.create(),
                ExperimentDisplayDefaults.simpleDefaults(),
                pubmedIds,
                dois);
    }

    public static BaselineExperiment createBaselineExperiment(List<AssayGroup> assayGroups) {
        return createBaselineExperiment(
                mockExperimentDesign(assayGroups),
                assayGroups);
    }

    public static BaselineExperiment createBaselineExperiment(ExperimentDesign experimentDesign, List<AssayGroup> assayGroups) {
        return createBaselineExperiment(
                experimentDesign,
                assayGroups,
                ExperimentDisplayDefaults.simpleDefaults());
    }

    public static BaselineExperiment createBaselineExperiment(String accession, ExperimentDesign experimentDesign, List<AssayGroup> assayGroups) {
        return createBaselineExperiment(
                accession,
                experimentDesign,
                assayGroups,
                ExperimentDisplayDefaults.simpleDefaults());
    }

    public static BaselineExperiment createBaselineExperiment(ExperimentDesign experimentDesign,
                                                              List<AssayGroup> assayGroups,
                                                              ExperimentDisplayDefaults experimentDisplayDefaults) {
        return createBaselineExperiment(
                EXPERIMENT_ACCESSION,
                experimentDesign,
                assayGroups,
                experimentDisplayDefaults);
    }

    public static BaselineExperiment createBaselineExperiment(String accession,
                                                              ExperimentDesign experimentDesign,
                                                              List<AssayGroup> assayGroups,
                                                              ExperimentDisplayDefaults experimentDisplayDefaults) {

        return createBaselineExperiment(
                accession,
                experimentDesign,
                assayGroups,
                experimentDisplayDefaults,
                PUBMEDID, DOI);
    }

    public static BaselineExperiment createBaselineExperiment(String accession,
                                                              ExperimentDesign experimentDesign,
                                                              List<AssayGroup> assayGroups,
                                                              ExperimentDisplayDefaults experimentDisplayDefaults,
                                                              List<String> pubmedIds,
                                                              List<String> dois) {

        return new BaselineExperiment(
                ExperimentType.RNASEQ_MRNA_BASELINE,
                accession,
                new Date(),
                DESCRIPTION,
                DISPLAY_NAME,
                "",
                new Species(SPECIES_NAME, SPECIES_PROPERTIES),
                Sets.newHashSet(pubmedIds),
                Sets.newHashSet(dois),
                experimentDesign,
                assayGroups,
                PROVIDER_URL,
                PROVIDER_DESCRIPTION,
                Collections.<String>emptyList(),
                Collections.<String>emptyList(),
                experimentDisplayDefaults
        );
    }

    public static MicroarrayExperiment createMicroarrayExperiment() {

        return new MicroarrayExperiment(
                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                EXPERIMENT_ACCESSION,
                new Date(),
                contrasts.stream().map(contrast1 -> Pair.of(contrast1, true)).collect(Collectors.toList()),
                DESCRIPTION,
                new Species(SPECIES_NAME, SPECIES_PROPERTIES),
                mockExperimentDesign(MockAssayGroups.create()),
                Sets.newHashSet(PUBMEDID),
                Sets.newHashSet(DOI),
                arrayDesigns);
    }

    public static DifferentialExperiment createDifferentialExperiment() {

        return new DifferentialExperiment(
                EXPERIMENT_ACCESSION,
                new Date(),
                contrasts.stream().map(contrast1 -> Pair.of(contrast1, true)).collect(Collectors.toList()),
                DESCRIPTION,
                new Species(SPECIES_NAME, SPECIES_PROPERTIES),
                Sets.newHashSet(PUBMEDID),
                Sets.newHashSet(DOI),
                mockExperimentDesign(MockAssayGroups.create()));
    }


    public static ExperimentDesign mockExperimentDesign(List<AssayGroup> assayGroups){
        ExperimentDesign experimentDesign = new ExperimentDesign();
        for(AssayGroup assayGroup: assayGroups){
            String value1 = RandomStringUtils.random(5);
            String value2 = RandomStringUtils.random(5);
            for(String assay: assayGroup.assaysAnalyzedForThisDataColumn()){
                experimentDesign.putFactor(assay, "type1", value1);
                experimentDesign.putFactor(assay, "type2", value2);
            }
        }
        return experimentDesign;
    }

}
