/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.collections.TransactionRunner;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.berkeley.ObjectValueTransactionWorker;
import uk.ac.ebi.atlas.commons.berkeley.StringValueTransactionWorker;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneannotation.biomart.BioMartGeneNameStream;

import javax.inject.Inject;
import javax.inject.Named;

@Named("geneAnnotationLoader")
public class GeneAnnotationLoader {

    private static final Logger logger = Logger.getLogger(GeneAnnotationLoader.class);

    private TransactionRunner transactionRunner;

    private BioMartGeneNameStream.Builder geneNameStreamBuilder;

    private AnnotationEnvironment annotationEnvironment;

    @Inject
    public GeneAnnotationLoader(AnnotationEnvironment annotationEnvironment, BioMartGeneNameStream.Builder geneNameStreamBuilder) {
        this.annotationEnvironment = annotationEnvironment;
        this.geneNameStreamBuilder = geneNameStreamBuilder;
    }

    private void turnOffReadonly() {
        this.annotationEnvironment.close();
        this.annotationEnvironment.initBerkeleyDatabase(false);
    }

    private void turnOnReadOnly() {
        this.annotationEnvironment.close();
        this.annotationEnvironment.initBerkeleyReadonly();
    }


    protected void loadAnnotations(ObjectInputStream<String[]> annotationsInputStream,
                                   ObjectValueTransactionWorker transactionWorker) {

        String[] row;

        transactionRunner = annotationEnvironment.getTransactionRunner();

        try {
            while ((row = annotationsInputStream.readNext()) != null) {
                transactionRunner.run(transactionWorker.setRow(row));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Exception while loading annotations.", e);
        }
    }

    public void loadGeneNames(String organism) {

        try (ObjectInputStream<String[]> annotationsInputStream = geneNameStreamBuilder.create(organism)) {

            turnOffReadonly();

            ObjectValueTransactionWorker<String> transactionWorker =
                    new StringValueTransactionWorker(annotationEnvironment.geneNames());


            loadAnnotations(annotationsInputStream, transactionWorker);

        } catch (Exception e) {

            throw new IllegalStateException("Error while writing gene name DB: " + e.getMessage());

        } finally {
            turnOnReadOnly();
        }
    }


}
