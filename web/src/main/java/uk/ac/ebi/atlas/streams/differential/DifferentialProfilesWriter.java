package uk.ac.ebi.atlas.streams.differential;

import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.download.GeneProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.streams.IterableObjectInputStream;
import uk.ac.ebi.atlas.streams.RankProfiles;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class DifferentialProfilesWriter<T extends DifferentialProfile> {

    private static final Logger LOGGER = Logger.getLogger(DifferentialProfilesWriter.class);

    private DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder;
    private GeneProfilesTSVWriter<T, Contrast> tsvWriter;

    public DifferentialProfilesWriter(DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder, GeneProfilesTSVWriter<T, Contrast> tsvWriter) {
        this.pipelineBuilder = pipelineBuilder;
        this.tsvWriter = tsvWriter;
    }

    public long write(PrintWriter outputWriter, ObjectInputStream<T> inputStream, DifferentialProfileStreamOptions options, Set<Contrast> orderedContrasts)  {

        tsvWriter.setResponseWriter(outputWriter);

        try (ObjectInputStream<T> source = inputStream) {

            Iterable<T> profiles = new IterableObjectInputStream<>(source);

            Iterable<T> profilesPipeline = pipelineBuilder.build(profiles, options);

            return tsvWriter.write(profilesPipeline, orderedContrasts);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
