package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfileInputStreamFactory extends BaselineProfileInputStreamFactory {

    @Inject
    public ProteomicsBaselineProfileInputStreamFactory(ProteomicsBaselineExpressionsQueueBuilder baselineExpressionsQueueBuilder,
                                                       CsvReaderFactory csvReaderFactory) {
        super(baselineExpressionsQueueBuilder, csvReaderFactory);
    }
}
