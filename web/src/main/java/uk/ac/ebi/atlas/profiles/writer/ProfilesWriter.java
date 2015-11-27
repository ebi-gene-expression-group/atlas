package uk.ac.ebi.atlas.profiles.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class ProfilesWriter<P extends Profile, K, O extends ProfileStreamOptions> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilesWriter.class);

    private ProfileStreamPipelineBuilder<P, O> pipelineBuilder;
    private GeneProfilesTSVWriter<P, K, O> tsvWriter;

    public ProfilesWriter(ProfileStreamPipelineBuilder<P, O> pipelineBuilder, GeneProfilesTSVWriter<P, K, O> tsvWriter) {
        this.pipelineBuilder = pipelineBuilder;
        this.tsvWriter = tsvWriter;
    }

    public long write(PrintWriter outputWriter, ObjectInputStream<P> inputStream, O options, Set<K> conditions)  {

        tsvWriter.setResponseWriter(outputWriter);

        try (ObjectInputStream<P> source = inputStream) {

            Iterable<P> profiles = new IterableObjectInputStream<>(source);

            Iterable<P> profilesPipeline = pipelineBuilder.build(profiles, options);

            return tsvWriter.write(profilesPipeline, conditions, options);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
