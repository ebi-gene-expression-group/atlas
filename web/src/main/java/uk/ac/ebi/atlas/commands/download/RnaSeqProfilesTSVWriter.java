package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named("differentialProfileWriter")
@Scope("prototype")
public class RnaSeqProfilesTSVWriter extends DifferentialProfilesTSVWriter<RnaSeqProfile,DifferentialExpression> {

    private RnaSeqRequestContext requestContext;

    @Inject
    public void setRequestContext(RnaSeqRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public RnaSeqRequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    protected List<String> getExpressionDataLabels(){
        return Lists.newArrayList("p-value", "log2foldchange");
    }


    @Override
    protected List<Double> getExpressionLevelData(DifferentialExpression expression){
        return Lists.newArrayList(expression.getLevel(), expression.getFoldChange());
    }

}
