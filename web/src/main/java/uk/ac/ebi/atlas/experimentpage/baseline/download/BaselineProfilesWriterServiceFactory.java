package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineProfilesWriterServiceFactory {

    private final BaselineProfilesTSVWriter tsvWriter;

    private final SolrQueryService solrQueryService;

    private final CoexpressedGenesService coexpressedGenesService;

    @Inject
    public BaselineProfilesWriterServiceFactory(BaselineProfilesTSVWriter tsvWriter, SolrQueryService
            solrQueryService, JdbcTemplate jdbcTemplate){
        this.tsvWriter = tsvWriter;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = new CoexpressedGenesService(new CoexpressedGenesDao(jdbcTemplate));
    }


    BaselineProfilesWriterService create(BaselineProfileInputStreamFactory inputStreamFactory){
        return new BaselineProfilesWriterService(new BaselineProfilesWriter(tsvWriter,inputStreamFactory,
                solrQueryService),
                coexpressedGenesService);
    }
}
