package uk.ac.ebi.atlas.model;

/**
 * This class is a factory of GeneProfile Builders, required to create instances of sequences of profiles in a streaming process fashion.
 * It is injected using spring lookup-method (application-context.xml). In Spring 3.0 no equivalent annotation has yet been implemented for this feature.
 * If you need to build a single GeneProfile you should rather directly inject GeneProfile.Builder.
 */
public abstract class GeneProfileBuilderFactory {

    public GeneProfile.Builder with(String geneId) {
        GeneProfile.Builder geneProfileBuilder = createNew();
        return geneProfileBuilder.forGeneId(geneId);
    }

    protected abstract GeneProfile.Builder createNew();
}
