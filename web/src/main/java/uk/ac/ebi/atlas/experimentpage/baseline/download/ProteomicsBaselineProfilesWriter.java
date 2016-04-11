package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfilesWriter extends BaselineProfilesWriter {

    @Inject
    public ProteomicsBaselineProfilesWriter(BaselineProfilesTSVWriter tsvWriter,
                                            ProteomicsBaselineProfileInputStreamFactory proteomicsInputStreamFactory,
                                            SolrQueryService solrQueryService) {
        super(tsvWriter, proteomicsInputStreamFactory, solrQueryService);
    }
}
