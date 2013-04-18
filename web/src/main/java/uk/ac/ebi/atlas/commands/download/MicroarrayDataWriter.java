package uk.ac.ebi.atlas.commands.download;

import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

class MicroarrayDataWriter extends ExpressionsWriterImpl {


    private GeneNamesProvider geneNamesProvider;
    private DesignElementMappingProvider designElementMappingProvider;

    private String arrayDesignAccession;

    @Inject
    public MicroarrayDataWriter(TsvReaderUtils tsvReaderUtils, GeneNamesProvider geneNamesProvider, DesignElementMappingProvider designElementMappingProvider) {
        super(tsvReaderUtils, geneNamesProvider);
        this.geneNamesProvider = geneNamesProvider;
        this.designElementMappingProvider = designElementMappingProvider;
    }

    @Override
    protected String getGeneName(String accession) {
        checkNotNull(arrayDesignAccession, "Array design should not be null");
        String ensGeneId = designElementMappingProvider.getEnsGeneId(arrayDesignAccession, accession);
        if (StringUtils.isNotBlank(ensGeneId)){
            return geneNamesProvider.getGeneName(ensGeneId);
        }
        return StringUtils.EMPTY;
    }

    public void setArrayDesignAccession(String arrayDesignAccession) {
        this.arrayDesignAccession = arrayDesignAccession;
    }

    @Override
    protected String formatUrl(String fileUrlTemplate, String experimentAccession) {
        return MessageFormat.format(fileUrlTemplate, experimentAccession, arrayDesignAccession);
    }
}
