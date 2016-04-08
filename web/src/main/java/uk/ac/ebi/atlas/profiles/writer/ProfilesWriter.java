package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class ProfilesWriter<P extends Profile, K, O extends ProfileStreamOptions<K>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilesWriter.class);

    private ProfileStreamPipelineBuilder<P, O, K> pipelineBuilder;
    private GeneProfilesTSVWriter<P, K, O> tsvWriter;

    public ProfilesWriter(ProfileStreamPipelineBuilder<P, O, K> pipelineBuilder, GeneProfilesTSVWriter<P, K, O>
            tsvWriter) {
        this.pipelineBuilder = pipelineBuilder;
        this.tsvWriter = tsvWriter;
    }

    public long write(PrintWriter outputWriter, ObjectInputStream<P> inputStream, O options, Set<K> conditions,
                      Optional<GeneQueryResponse> geneQueryResponse)  {

        tsvWriter.setResponseWriter(outputWriter);

        try (ObjectInputStream<P> source = inputStream) {

            Iterable<P> profiles = new IterableObjectInputStream<>(source);

            Iterable<P> profilesPipeline = pipelineBuilder.build(profiles, options,geneQueryResponse);

            return tsvWriter.write(profilesPipeline, conditions, options, geneQueryResponse.isPresent());

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
