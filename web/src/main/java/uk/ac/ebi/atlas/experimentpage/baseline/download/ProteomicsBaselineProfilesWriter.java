package uk.ac.ebi.atlas.experimentpage.baseline.download;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfilesWriter extends BaselineProfilesWriter {

    @Inject
    public ProteomicsBaselineProfilesWriter(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                            BaselineProfilesTSVWriter tsvWriter,
                                            ProteomicsBaselineProfileInputStreamFactory proteomicsInputStreamFactory,
                                            LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, tsvWriter, proteomicsInputStreamFactory, loadGeneIdsIntoRequestContext);
    }
}
