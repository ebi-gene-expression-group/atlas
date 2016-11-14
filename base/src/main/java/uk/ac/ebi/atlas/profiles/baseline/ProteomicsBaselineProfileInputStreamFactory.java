package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfileInputStreamFactory extends BaselineProfileInputStreamFactory {

    @Inject
    public ProteomicsBaselineProfileInputStreamFactory(DataFileHub dataFileHub,
                                                       ExpressionsRowDeserializerProteomicsBaselineBuilder expressionsRowDeserializerProteomicsBaselineBuilder,
                                                       ExpressionsRowRawDeserializerBaselineBuilder expressionsRowRawDeserializerBaselineBuilder,
                                                       KryoReaderFactory kryoReaderFactory) {
        super(dataFileHub,expressionsRowDeserializerProteomicsBaselineBuilder,
                expressionsRowRawDeserializerBaselineBuilder,
                kryoReaderFactory);

    }

}
