package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.species.Species;

import java.util.Map;

public class ApplicationProperties {
    protected ApplicationProperties() {
        throw new UnsupportedOperationException();
    }

    private static final Map<String, String> REFERENCE_EXPERIMENTS =
            ImmutableMap.<String, String>builder()
                    .put("anolis carolinensis", "E-MTAB-3727")
                    .put("arabidopsis lyrata", "E-MTAB-5072")
                    .put("arabidopsis thaliana", "E-GEOD-30720")
                    .put("beta vulgaris", "E-ENAD-2")
                    .put("bos taurus", "E-MTAB-2798")
                    .put("brachypodium distachyon", "E-MTAB-4401")
                    .put("brassica napus", "E-GEOD-77637")
                    .put("brassica oleracea", "E-GEOD-42891")
                    .put("brassica rapa", "E-GEOD-43245")
                    .put("caenorhabditis elegans", "E-MTAB-2812")
                    .put("chlamydomonas reinhardtii", "E-GEOD-62671")
                    .put("chlorocebus sabaeus", "E-MTAB-4404")
                    .put("danio rerio", "E-ERAD-475")
                    .put("drosophila melanogaster", "E-MTAB-4723")
                    .put("equus caballus", "E-GEOD-46858")
                    .put("gallus gallus", "E-MTAB-2797")
                    .put("glycine max", "E-GEOD-61857")
                    .put("homo sapiens", "E-MTAB-2836")
                    .put("hordeum vulgare", "E-MTAB-2809")
                    .put("macaca mulatta", "E-MTAB-2799")
                    .put("monodelphis domestica", "E-MTAB-3719")
                    .put("mus musculus", "E-MTAB-3718")
                    .put("musa acuminata", "E-ENAD-11")
                    .put("oryza sativa", "E-MTAB-2037")
                    .put("ovis aries", "E-MTAB-3838")
                    .put("papio anubis", "E-MTAB-2848")
                    .put("populus trichocarpa", "E-GEOD-81077")
                    .put("rattus norvegicus", "E-GEOD-53960")
                    .put("schistosoma mansoni", "E-MTAB-451")
                    .put("setaria italica", "E-GEOD-36391")
                    .put("solanum lycopersicum", "E-MTAB-4765")
                    .put("solanum tuberosum", "E-MTAB-552")
                    .put("sorghum bicolor", "E-MTAB-4400")
                    .put("sus scrofa", "E-MTAB-5895")
                    .put("theobroma cacao", "E-ENAD-1")
                    .put("trifolium pratense", "E-MTAB-5117")
                    .put("triticum aestivum", "E-MTAB-4260")
                    .put("vitis vinifera", "E-MTAB-4350")
                    .put("xenopus tropicalis", "E-MTAB-3726")
                    .put("zea mays", "E-MTAB-4342")
                    .build();

    public static String getBaselineReferenceExperimentAccession(Species species) {
        return REFERENCE_EXPERIMENTS.get(species.getReferenceName());
    }
}
