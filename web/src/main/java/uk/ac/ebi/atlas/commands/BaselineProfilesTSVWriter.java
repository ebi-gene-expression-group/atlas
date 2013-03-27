package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.utils.NumberUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.SortedSet;

@Named("geneProfileWriter")
@Scope("prototype")
public class BaselineProfilesTSVWriter extends GeneProfilesTSVWriter<BaselineProfile, Factor> {

    @Inject
    protected BaselineProfilesTSVWriter(NumberUtils numberUtils, GeneNamesProvider geneNamesProvider) {
        super(numberUtils, geneNamesProvider);
    }

    @Override
    protected List<String> buildColumnNames(SortedSet<Factor> conditions) {
        SortedSet<String> values = Factor.getValues(conditions);
        return Lists.newArrayList(values);
    }
}
