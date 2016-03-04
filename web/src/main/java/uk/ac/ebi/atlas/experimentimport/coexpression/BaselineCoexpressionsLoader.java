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
public class BaselineCoexpressionsLoader {

    private final BaselineCoexpressionsDAO baselineCoexpressionsDAO;
    private final BaselineCoexpressionsInputStreamFactory baselineCoexpressionsInputStreamFactory;

    @Inject
    public BaselineCoexpressionsLoader(BaselineCoexpressionsDAO baselineCoexpressionsDAO, BaselineCoexpressionsInputStreamFactory baselineCoexpressionsInputStreamFactory) {
        this.baselineCoexpressionsDAO = baselineCoexpressionsDAO;
        this.baselineCoexpressionsInputStreamFactory = baselineCoexpressionsInputStreamFactory;
    }

//    @Transactional
    public void loadBaselineCoexpressions(String experimentAccession) {
        BaselineCoexpressionsInputStream baselineCoexpressionsInputStream = baselineCoexpressionsInputStreamFactory.create(experimentAccession);
        baselineCoexpressionsDAO.loadCoexpressions(experimentAccession, baselineCoexpressionsInputStream);
    }

//    @Transactional
    public void deleteCoexpressions(String accession) {
        baselineCoexpressionsDAO.deleteCoexpressions(accession);
    }

}
