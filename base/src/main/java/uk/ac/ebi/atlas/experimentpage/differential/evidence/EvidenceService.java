package uk.ac.ebi.atlas.experimentpage.differential.evidence;

import com.google.auto.value.AutoValue;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceService<Expr extends DifferentialExpression,
        E extends DifferentialExperiment, StreamOptions extends DifferentialProfileStreamOptions, Prof extends Profile<Contrast, Expr, Prof>> {


    private final ProfileStreamFactory<Contrast, Expr, E, StreamOptions, Prof> differentialProfileStreamFactory;
    private final DataFileHub dataFileHub;
    private final String expressionAtlasVersion;


    public EvidenceService(ProfileStreamFactory<Contrast, Expr, E, StreamOptions, Prof> differentialProfileStreamFactory, DataFileHub dataFileHub, String expressionAtlasVersion) {
        this.differentialProfileStreamFactory = differentialProfileStreamFactory;
        this.dataFileHub = dataFileHub;
        this.expressionAtlasVersion = expressionAtlasVersion;
    }

    public JsonArray evidenceForExperiment(E experiment, StreamOptions streamOptions) {
        if(! experiment.getSpecies().isUs()
                || experiment.getType().isMicroRna()
                || cellLineAsSampleCharacteristicButNoDiseaseAsFactor(experiment.getExperimentDesign())){
            return new JsonArray();
        }

        Map<Contrast, DiseaseAssociation> diseaseAssociations = getDiseaseAssociations(experiment);
        if (diseaseAssociations.size() == 0) {
            return new JsonArray();
        }

        String methodDescription = getMethodDescriptionFromAnalysisMethodsFile(experiment);

        Map<String, Map<Contrast, Integer>> rankPerContrastPerGene = getPercentileRanks(experiment);

        JsonArray result = new JsonArray();

        for (Prof profile : new IterableObjectInputStream<>(differentialProfileStreamFactory.create(experiment, streamOptions))) {
            for (Contrast contrast : diseaseAssociations.keySet()) {
                Expr expression = profile.getExpression(contrast);
                if (expression != null) {
                    result.addAll(piecesOfEvidence(
                            experiment,
                            methodDescription,
                            diseaseAssociations.get(contrast),
                            expression,
                            rankPerContrastPerGene.get(profile.getId()).get(contrast),
                            profile.getId(),
                            contrast
                            )
                    );
                }
            }
        }

        return result;
    }

    JsonArray piecesOfEvidence(E experiment, String methodDescription,
                               DiseaseAssociation linkToDisease,
                               Expr expression, Integer foldChangeRank,
                               String ensemblGeneId, Contrast contrast) {
        JsonArray result = new JsonArray();
        for (OntologyTerm diseaseUri : linkToDisease.diseaseInfo().valueOntologyTerms()) {
            result.add(pieceOfEvidence(
                    experiment,
                    methodDescription,
                    diseaseUri,
                    linkToDisease.biosampleInfo(),
                    Pair.of(linkToDisease.testSampleLabel(), linkToDisease.referenceSampleLabel()),
                    linkToDisease.confidence(),
                    expression,
                    foldChangeRank,
                    ensemblGeneId,
                    contrast,
                    linkToDisease.isCttvPrimary(),
                    linkToDisease.organismPart())
            );
        }
        return result;
    }

    JsonObject pieceOfEvidence(E experiment, String methodDescription,
                               OntologyTerm diseaseUri,
                               SampleCharacteristic biosampleInfo,
                               Pair<String, String> testAndReferenceLabels,
                               DiseaseAssociation.CONFIDENCE confidence,
                               Expr expression, Integer foldChangeRank,
                               String ensemblGeneId, Contrast contrast,
                               boolean isCttvPrimary,
                               SampleCharacteristic organismPart) {

        return withLiteratureReferences(
                associationRecord(
                        uniqueAssociationFields(
                                ensemblGeneId,
                                experiment.getAccession(),
                                contrast.getDisplayName()
                        ),
                        target(ensemblGeneId,
                                isCttvPrimary,
                                expression
                        ),
                        disease(
                                diseaseUri,
                                biosampleInfo
                        ),
                        evidence(
                                experiment,
                                ensemblGeneId,
                                expression,
                                foldChangeRank,
                                testAndReferenceLabels,
                                contrast,
                                confidence,
                                methodDescription,
                                organismPart)
                ), experiment.getPubMedIds()
        );
    }

    JsonObject withLiteratureReferences(JsonObject object, List<String> pubmedIds) {
        if (!pubmedIds.isEmpty()) {
            JsonObject literature = new JsonObject();
            JsonArray references = new JsonArray();
            for (String pubmedId : pubmedIds) {
                JsonObject reference = new JsonObject();
                reference.addProperty("lit_id", MessageFormat.format(
                        "http://europepmc.org/abstract/MED/{0}", pubmedId
                ));
                references.add(reference);
            }
            literature.add("references", references);
            object.add("literature", literature);
        }

        return object;
    }

    JsonObject evidence(E experiment, String ensemblGeneId,
                        Expr expression, Integer foldChangeRank,
                        Pair<String, String> testAndReferenceLabels,
                        Contrast contrast,
                        DiseaseAssociation.CONFIDENCE confidence,
                        String methodDescription,
                        SampleCharacteristic organismPart) {
        JsonObject result = new JsonObject();
        result.addProperty("is_associated", true);
        result.addProperty("unique_experiment_reference", MessageFormat.format("STUDYID_{0}", experiment.getAccession()));
        result.add("urls", linkUrls(experiment.getAccession(), ensemblGeneId));
        result.add("evidence_codes", evidenceCodes(experiment.getType()));
        result.add("log2_fold_change", log2FoldChange(expression, foldChangeRank));
        result.addProperty("test_sample", testAndReferenceLabels.getLeft());
        result.addProperty("reference_sample", testAndReferenceLabels.getRight());
        result.addProperty("date_asserted", new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'").format(experiment.getLastUpdate()));
        result.addProperty("experiment_overview", experiment.getDescription());
        result.addProperty("comparison_name", contrast.getDisplayName());
        result.addProperty("organism_part", organismPartProperty(organismPart));
        result.addProperty("test_replicates_n", contrast.getTestAssayGroup().getReplicates());
        result.addProperty("reference_replicates_n", contrast.getReferenceAssayGroup().getReplicates());
        result.addProperty("confidence_level", confidence.name().toLowerCase());
        result.add("resource_score", resourceScore(expression, methodDescription));
        result.add("provenance_type", provenanceType());
        return result;
    }

    JsonObject linkUrl(String niceName, String url) {
        JsonObject result = new JsonObject();
        result.addProperty("nice_name", niceName);
        result.addProperty("url", url);

        return result;
    }

    String organismPartProperty(SampleCharacteristic organismPart){
        return organismPart.valueOntologyTerms().stream().findFirst().map(o -> o.uri()).orElse(organismPart.value());
    }

    JsonArray linkUrls(String experimentAccession, String ensemblGeneId) {
        JsonArray result = new JsonArray();
        result.add(linkUrl(
                "ArrayExpress Experiment overview",
                MessageFormat.format("http://identifiers.org/arrayexpress/{0}", experimentAccession)
        ));
        result.add(linkUrl(
                "Gene expression in Expression Atlas",
                MessageFormat.format("http://www.ebi.ac.uk/gxa/experiments/{0}?geneQuery={1}", experimentAccession, ensemblGeneId) //change me to the new format!
        ));
        result.add(linkUrl(
                "Baseline gene expression in Expression Atlas",
                MessageFormat.format("http://www.ebi.ac.uk/gxa/genes/{0}", ensemblGeneId)
        ));
        return result;
    }

    /*
    original comment:
    fix me- we also need to account for the GSEA codes:
	http://purl.obolibrary.org/obo/ECO:0000358 differential geneset expression evidence from microarray experiment
	http://purl.obolibrary.org/obo/ECO:0000359 differential geneset expression evidence from RNA-seq experiment
	But we are not including this data in the JSON report for now.
     */
    JsonArray evidenceCodes(ExperimentType experimentType) {
        JsonArray result = new JsonArray();
        if (experimentType.isMicroarray()) {
            result.add(new JsonPrimitive("http://purl.obolibrary.org/obo/ECO_0000356"));
        } else if (experimentType.isRnaSeqDifferential()) {
            result.add(new JsonPrimitive("http://purl.obolibrary.org/obo/ECO_0000357"));
        }
        return result;
    }

    JsonObject log2FoldChange(Expr expression, Integer foldChangeRank) {
        JsonObject result = new JsonObject();
        result.addProperty("value", expression.getFoldChange());
        result.addProperty("percentile_rank", foldChangeRank);
        return result;
    }

    double getPValue(Expr expression) {
        return Double.valueOf(String.format("%3.2e", expression.getPValue() == 0.0 ? 1e-234 : expression.getPValue()));
    }

    JsonObject resourceScore(Expr expression, String methodDescription) {
        JsonObject result = new JsonObject();
        /*
        probability estimates shouldn't be zero but sometimes we get them from the pipeline as rounding errors
        use the smallest positive double greater than zero,
         */
        result.addProperty("value", getPValue(expression));

        JsonObject method = new JsonObject();
        method.addProperty("description", methodDescription);
        result.add("method", method);
        result.addProperty("type", "pvalue");
        return result;
    }

    JsonObject provenanceType() {
        JsonObject result = new JsonObject();
        JsonObject database = new JsonObject();
        database.addProperty("version", expressionAtlasVersion);
        database.addProperty("id", "Expression_Atlas");
        result.add("database", database);
        return result;
    }

    String geneUri(String ensemblGeneId) {
        return MessageFormat.format("http://identifiers.org/ensembl/{0}", ensemblGeneId);
    }

    String experimentAccessionUri(String experimentAccession) {
        return MessageFormat.format("http://identifiers.org/gxa.expt/{0}", experimentAccession);
    }

    //https://github.com/opentargets/json_schema/blob/master/src/bioentity/disease.json
    JsonObject disease(OntologyTerm diseaseUri, SampleCharacteristic biosampleInfo) {
        JsonObject result = new JsonObject();
        result.addProperty("id", diseaseUri.uri());
        result.add("biosample", biosampleInfo(biosampleInfo));
        return result;
    }

    JsonObject biosampleInfo(SampleCharacteristic biosampleInfo) {
        JsonObject result = new JsonObject();
        result.addProperty("name", biosampleInfo.value());
        Optional<OntologyTerm> ontologyTerm = FluentIterable.from(biosampleInfo.valueOntologyTerms()).first();
        if (ontologyTerm.isPresent()) {
            result.addProperty("id", ontologyTerm.get().uri());
        }
        return result;
    }

    JsonObject uniqueAssociationFields(String ensemblGeneId, String experimentAccession, String comparisonName) {
        JsonObject result = new JsonObject();
        result.addProperty("geneID", geneUri(ensemblGeneId));
        result.addProperty("study_id", experimentAccessionUri(experimentAccession));
        result.addProperty("comparison_name", comparisonName);
        return result;
    }

    String activity(boolean isCttvPrimary, Expr expression) {
        return MessageFormat.format(
                "http://identifiers.org/cttv.activity/{0}",
                isCttvPrimary
                        ? expression.getFoldChange() > 0
                        ? "increased_transcript_level"
                        : "decreased_transcript_level"
                        : "unknown"
        );
    }

    JsonObject target(String ensemblGeneId, boolean isCttvPrimary, Expr expression) {
        JsonObject result = new JsonObject();
        result.addProperty("id", geneUri(ensemblGeneId));
        result.addProperty("target_type", "http://identifiers.org/cttv.target/transcript_evidence");
        result.addProperty("activity", activity(isCttvPrimary, expression));

        return result;
    }

    JsonObject associationRecord(JsonObject uniqueAssociationFields,
                                 JsonObject target,
                                 JsonObject disease,
                                 JsonObject evidence) {
        JsonObject result = new JsonObject();
        result.addProperty("sourceID", "expression_atlas");
        result.addProperty("type", "rna_expression");
        result.addProperty("access_level", "public");
        result.add("unique_association_fields", uniqueAssociationFields);
        result.add("target", target);
        result.add("disease", disease);
        result.add("evidence", evidence);
        return result;
    }


    Map<Contrast, DiseaseAssociation> getDiseaseAssociations(DifferentialExperiment experiment) {
        Map<Contrast, DiseaseAssociation> result = new HashMap<>();
        for (Contrast contrast : experiment.getDataColumnDescriptors()) {
            Optional<SampleCharacteristic> biosampleInfo = getBiosampleInfo(experiment.getExperimentDesign(), contrast.getTestAssayGroup());
            Optional<SampleCharacteristic> diseaseInfo = getDiseaseInfo(experiment.getExperimentDesign(), contrast.getTestAssayGroup());

            if (biosampleInfo.isPresent() && diseaseInfo.isPresent()) {
                result.put(
                        contrast,
                        DiseaseAssociation.create(
                                biosampleInfo.get(), experiment.getExperimentDesign(), contrast, experiment.doesContrastHaveCttvPrimaryAnnotation(contrast), diseaseInfo.get()
                        )
                );
            }
        }
        return result;
    }

    @AutoValue
    static abstract class DiseaseAssociation {
        enum CONFIDENCE {
            LOW, MEDIUM, HIGH
        }

        public abstract SampleCharacteristic biosampleInfo();

        public abstract String referenceSampleLabel();

        public abstract String testSampleLabel();

        public abstract SampleCharacteristic diseaseInfo();

        public abstract CONFIDENCE confidence();

        public abstract boolean isCttvPrimary();

        public abstract SampleCharacteristic organismPart();


        public static DiseaseAssociation create(SampleCharacteristic biosampleInfo, ExperimentDesign experimentDesign, Contrast contrast,
                                                boolean isCttvPrimary, SampleCharacteristic diseaseInfo){
            String referenceSampleLabel = factorBasedSummaryLabel(experimentDesign, contrast.getReferenceAssayGroup());
            String testSampleLabel = factorBasedSummaryLabel(experimentDesign, contrast.getTestAssayGroup());
            DiseaseAssociation.CONFIDENCE confidence = determineStudyConfidence(experimentDesign, diseaseInfo, contrast.getTestAssayGroup(), isCttvPrimary);
            SampleCharacteristic organismPart = Optional.fromNullable(experimentDesign.getSampleCharacteristic(contrast.getTestAssayGroup().getFirstAssayAccession(), "organism part")).or(SampleCharacteristic.create("organism part", ""));
            return new AutoValue_EvidenceService_DiseaseAssociation(biosampleInfo, referenceSampleLabel, testSampleLabel, diseaseInfo, confidence, isCttvPrimary, organismPart);
        }
    }

    /*
    e.g. "induced into quiescence; serum starved"
    See:
    https://github.com/opentargets/json_schema/blob/master/src/evidence/expression.json
    "test_sample", "reference_sample"
     */
    static String factorBasedSummaryLabel(final ExperimentDesign experimentDesign, AssayGroup assayGroup) {
        return Joiner.on("; ").join(FluentIterable.from(experimentDesign.getFactorValues(assayGroup.getFirstAssayAccession()).values()).filter(StringUtils::isNotEmpty));
    }

    /*
    https://github.com/opentargets/json_schema/blob/master/src/bioentity/disease.json#L26
    This will either be an organism part, a cell line, or a cell type.
    When we couldn't determine this we used to issue a warning asking curators to curate this.
     */
    Optional<SampleCharacteristic> getBiosampleInfo(final ExperimentDesign experimentDesign, AssayGroup testAssayGroup) {
        return FluentIterable.from(
                new SampleCharacteristic[]{
                        experimentDesign.getSampleCharacteristic(testAssayGroup.getFirstAssayAccession(), "organism part"),
                        experimentDesign.getSampleCharacteristic(testAssayGroup.getFirstAssayAccession(), "cell line"),
                        experimentDesign.getSampleCharacteristic(testAssayGroup.getFirstAssayAccession(), "cell type")
                }
        ).firstMatch(Predicates.<SampleCharacteristic>notNull());
    }

    /*
    We expect zero or one but the original code represented this as a set.
    Original comment:
    # Go through the types (should probably always only be one)...
     */
    Optional<SampleCharacteristic> getDiseaseInfo(final ExperimentDesign experimentDesign, AssayGroup testAssayGroup) {
        return FluentIterable.from(experimentDesign.getSampleCharacteristics(testAssayGroup.getFirstAssayAccession())).filter(
                sampleCharacteristic -> sampleCharacteristic.header().toLowerCase().contains("disease")
                        && !StringUtils.containsAny(sampleCharacteristic.value().toLowerCase(),
                        "normal", "healthy", "control")
        ).first();
    }

    /*
    Does this contrast represent association with diseases?

    If the disease is in the experimental factors, and there are no other factors, the confidence is "high".
    If the disease is in the factors but there are other factors e.g. a treatment or
    genotype etc, the confidence is "medium". If the disease is only in the
    characteristics and something else is the factor e.g a treatment, the
    confidence is "low".
    */
    private static DiseaseAssociation.CONFIDENCE determineStudyConfidence(ExperimentDesign experimentDesign, SampleCharacteristic diseaseCharacteristic,
                                                                   AssayGroup testAssayGroup, boolean isCttvPrimary) {
        FactorSet factors = experimentDesign.getFactors(testAssayGroup.getFirstAssayAccession());
        if (!factors.factorsByType.keySet().contains(Factor.normalize(diseaseCharacteristic.header()))) {
            return DiseaseAssociation.CONFIDENCE.LOW;
        } else {
            if (factors.size() > 1 || !isCttvPrimary) {
                return DiseaseAssociation.CONFIDENCE.MEDIUM;
            } else {
                return DiseaseAssociation.CONFIDENCE.HIGH;
            }
        }
    }


    Map<String, Map<Contrast, Integer>> getPercentileRanks(E experiment) {
        return readPercentileRanks(experiment, dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).percentileRanks.get());
    }

    private Map<String, Map<Contrast, Integer>> readPercentileRanks(E experiment, ObjectInputStream<String[]> lines) {
        Map<Integer, Contrast> whichContrastInWhichLine = percentileRanksColumnsFromHeader(lines.readNext(), experiment);
        Map<String, Map<Contrast, Integer>> result = new HashMap<>();
        for (String[] line : new IterableObjectInputStream<>(lines)) {
            Map<Contrast, Integer> resultForThisGene = new HashMap<>();

            for (Map.Entry<Integer, Contrast> e : whichContrastInWhichLine.entrySet()) {
                String value = line[e.getKey()];
                if (!"NA".equals(value)) {
                    resultForThisGene.put(e.getValue(), Integer.parseInt(value));
                }
            }
            result.put(line[0], resultForThisGene);
        }
        return result;
    }

    private Map<Integer, Contrast> percentileRanksColumnsFromHeader(String[] header, E experiment) {
        ImmutableMap.Builder<Integer, Contrast> b = ImmutableMap.builder();
        for (int i = 1; i < header.length; i++) {
            b.put(i, experiment.getDataColumnDescriptor(StringUtils.trim(header[i])));
        }
        return b.build();
    }

    String getMethodDescriptionFromAnalysisMethodsFile(E experiment) {
        for (String[] line : dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get().readAll()) {
            if (line[0].toLowerCase().contains("differential expression")) {
                return line[1].trim().replace("<.+?>", "");
            }
        }
        return "";
    }

    /*
    If something's a factor then it is also a characteristic unless we've made a mistake.
    Example mistake was E-GEOD-23764.
     */
    boolean cellLineAsSampleCharacteristicButNoDiseaseAsFactor(ExperimentDesign experimentDesign){
        return (experimentDesign.getSampleHeaders().contains("cell line") || experimentDesign.getFactorHeaders().contains("cell line"))  && ! experimentDesign.getFactorHeaders().contains("disease");
    }

}
