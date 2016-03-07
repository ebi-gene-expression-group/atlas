/*
 * Copyright 2008-2016 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Expression Atlas:
 * https://www.ebi.ac.uk/gxa
 *
 * For further details of the Expression Atlas project, including source code,
 * downloads and documentation, please see:
 * https://github.com/gxa/atlas
 */

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 04/03/2016.
 */

package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class BaselineCoexpressionsProfileLoader {

    // @Transactional kicks in Spring’s AOP capabilities, which in turn proxies the target class and must have a null constructor for CGLIB-based proxies or
    // implement an interface for JDK-based proxies (e.g. AnalyticsLoader). Therefore we can’t use final or inject beans via the constructor
    // http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/aop-api.html#aop-pfb-proxy-types
    // http://blog.codeleak.pl/2014/07/spring-4-cglib-based-proxy-classes-with-no-default-ctor.html
    // http://forum.spring.io/forum/spring-projects/aop/4971-transaction-proxy-on-class-with-no-default-constructor

    private BaselineCoexpressionsProfileDAO baselineCoexpressionsProfileDAO;
    private BaselineCoexpressionsProfileInputStreamFactory baselineCoexpressionsProfileInputStreamFactory;

    @Inject
    public void setBaselineCoexpressionsProfileDAO (BaselineCoexpressionsProfileDAO baselineCoexpressionsProfileDAO) {
        this.baselineCoexpressionsProfileDAO = baselineCoexpressionsProfileDAO;
    }

    @Inject
    public void setBaselineCoexpressionsProfileInputStreamFactory(BaselineCoexpressionsProfileInputStreamFactory baselineCoexpressionsProfileInputStreamFactory) {
        this.baselineCoexpressionsProfileInputStreamFactory = baselineCoexpressionsProfileInputStreamFactory;
    }

    @Transactional
    public void loadBaselineCoexpressionsProfile(String experimentAccession) {
        BaselineCoexpressionsProfileInputStream baselineCoexpressionsProfileInputStream = baselineCoexpressionsProfileInputStreamFactory.create(experimentAccession);
        baselineCoexpressionsProfileDAO.loadCoexpressionsProfile(experimentAccession, baselineCoexpressionsProfileInputStream);
    }

    @Transactional
    public void deleteCoexpressionsProfile(String accession) {
        baselineCoexpressionsProfileDAO.deleteCoexpressionsProfile(accession);
    }

}
