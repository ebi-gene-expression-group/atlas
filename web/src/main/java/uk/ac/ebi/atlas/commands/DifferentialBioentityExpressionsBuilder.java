package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.DiffExpressionDao;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.conditions.ConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedContrast;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("request")
public class DifferentialBioentityExpressionsBuilder {

    private DiffExpressionDao diffExpressionDao;

    private ConditionsSearchService conditionsSearchService;

    @Inject
    public DifferentialBioentityExpressionsBuilder(DiffExpressionDao diffExpressionDao, ConditionsSearchService conditionsSearchService) {
        this.diffExpressionDao = diffExpressionDao;
        this.conditionsSearchService = conditionsSearchService;
    }

    public DifferentialBioentityExpressions build(String query) {

        checkArgument(StringUtils.isNotBlank(query));

        Collection<IndexedContrast> contrasts = conditionsSearchService.findContrasts(query);
        List<DifferentialBioentityExpression> expressions = diffExpressionDao.getExpressions(contrasts);
        int resultCount = diffExpressionDao.getResultCount(contrasts);

        return new DifferentialBioentityExpressions(expressions, resultCount);

    }
}
