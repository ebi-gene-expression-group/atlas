package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineProfilesWriterServiceFactory {

    ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriter;

    private final SolrQueryService solrQueryService;

    private final CoexpressedGenesService coexpressedGenesService;

    @Inject
    public BaselineProfilesWriterServiceFactory(BaselineProfilesTSVWriter tsvWriter, SolrQueryService
            solrQueryService, JdbcTemplate jdbcTemplate){
        this(new ProfilesWriter<>(new BaselineProfileStreamFilters(), tsvWriter),
                solrQueryService,new CoexpressedGenesService(new CoexpressedGenesDao(jdbcTemplate)) );
    }

    BaselineProfilesWriterServiceFactory(ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriter,
                                         SolrQueryService solrQueryService, CoexpressedGenesService coexpressedGenesService){
        this.profilesWriter = profilesWriter;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
    }


    BaselineProfilesWriterService create(BaselineProfileInputStreamFactory inputStreamFactory){
        return new BaselineProfilesWriterService(inputStreamFactory,profilesWriter, solrQueryService,coexpressedGenesService);
    }
}
