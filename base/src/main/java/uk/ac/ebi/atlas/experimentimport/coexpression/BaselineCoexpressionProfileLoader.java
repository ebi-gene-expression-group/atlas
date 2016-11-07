package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.NoSuchFileException;

@Named
public class BaselineCoexpressionProfileLoader {

    // @Transactional kicks in Spring’s AOP capabilities, which in turn proxies the target class and must have a null constructor for CGLIB-based proxies or
    // implement an interface for JDK-based proxies (e.g. AnalyticsLoader). Therefore we can’t use final or inject beans via the constructor
    // http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/aop-api.html#aop-pfb-proxy-types
    // http://blog.codeleak.pl/2014/07/spring-4-cglib-based-proxy-classes-with-no-default-ctor.html
    // http://forum.spring.io/forum/spring-projects/aop/4971-transaction-proxy-on-class-with-no-default-constructor

    private BaselineCoexpressionProfileDAO baselineCoexpressionProfileDAO;
    private BaselineCoexpressionProfileInputStreamFactory baselineCoexpressionProfileInputStreamFactory;

    @Inject
    public void setBaselineCoexpressionProfileDAO(BaselineCoexpressionProfileDAO baselineCoexpressionProfileDAO) {
        this.baselineCoexpressionProfileDAO = baselineCoexpressionProfileDAO;
    }

    @Inject
    public void setBaselineCoexpressionProfileInputStreamFactory(BaselineCoexpressionProfileInputStreamFactory baselineCoexpressionProfileInputStreamFactory) {
        this.baselineCoexpressionProfileInputStreamFactory = baselineCoexpressionProfileInputStreamFactory;
    }

    @Transactional
    public int loadBaselineCoexpressionsProfile(String experimentAccession) {
        try {
            BaselineCoexpressionProfileInputStream baselineCoexpressionProfileInputStream =
                    baselineCoexpressionProfileInputStreamFactory.create(experimentAccession);
            return baselineCoexpressionProfileDAO.loadCoexpressionsProfile(experimentAccession, baselineCoexpressionProfileInputStream);
        } catch (NoSuchFileException e){
            //it doesn't make sense to calculate coexpressions for all experiments and we allow the file to be missing
            return 0;
        }
    }

    @Transactional
    public int deleteCoexpressionsProfile(String accession) {
        return baselineCoexpressionProfileDAO.deleteCoexpressionsProfile(accession);
    }

}
