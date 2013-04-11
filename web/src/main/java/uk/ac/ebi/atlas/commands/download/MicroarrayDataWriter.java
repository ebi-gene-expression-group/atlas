package uk.ac.ebi.atlas.commands.download;

import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

//@Named
public abstract class MicroarrayDataWriter extends ExpressionsWriterImpl {


    private GeneNamesProvider geneNamesProvider;
    private DesignElementMappingProvider designElementMappingProvider;

    private String arrayDesignAccession;

    @Inject
    public MicroarrayDataWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider, DesignElementMappingProvider designElementMappingProvider) {
        super(csvReaderBuilder, geneNamesProvider);
        this.geneNamesProvider = geneNamesProvider;
        this.designElementMappingProvider = designElementMappingProvider;
    }

    @Override
    protected String getGeneName(String accession) {
        checkNotNull(arrayDesignAccession, "Array design should not be null");
        String ensGeneId = designElementMappingProvider.getEnsGeneId(arrayDesignAccession, accession);
        return StringUtils.isNotBlank(ensGeneId) ? geneNamesProvider.getGeneName(ensGeneId): "";
    }

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }
}
