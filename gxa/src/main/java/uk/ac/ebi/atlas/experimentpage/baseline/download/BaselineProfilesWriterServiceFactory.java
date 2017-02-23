package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineProfilesWriterServiceFactory {

    private final Resource tsvFileMastheadTemplateResource;
    private final CsvWriterFactory csvWriterFactory;
    private final SolrQueryService solrQueryService;
    private final CoexpressedGenesService coexpressedGenesService;

    @Inject
    public BaselineProfilesWriterServiceFactory(
            @Value("classpath:/file-templates/download-headers-baseline.txt") Resource tsvFileMastheadTemplateResource,
            CsvWriterFactory csvWriterFactory,
            SolrQueryService solrQueryService,
            JdbcTemplate jdbcTemplate) {
        this.tsvFileMastheadTemplateResource = tsvFileMastheadTemplateResource;
        this.csvWriterFactory = csvWriterFactory;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = new CoexpressedGenesService(new CoexpressedGenesDao(jdbcTemplate));
    }

    BaselineProfilesWriterService create(BaselineProfileStreamFactory inputStreamFactory){
        return new BaselineProfilesWriterService(
                inputStreamFactory,
                new ProfilesWriter<>(new BaselineProfileStreamFilters(), new BaselineProfilesTSVWriter(csvWriterFactory, tsvFileMastheadTemplateResource)),
                solrQueryService,
                coexpressedGenesService);
    }
}
