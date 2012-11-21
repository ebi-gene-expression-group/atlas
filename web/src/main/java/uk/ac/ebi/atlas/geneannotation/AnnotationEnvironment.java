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

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.concurrent.ConcurrentMap;

@Named("annotationEnvironment")
public class AnnotationEnvironment {

    private static final Logger logger = Logger.getLogger(AnnotationEnvironment.class);

    private static final String GENES_DB = "genes.db";
    private Environment environment;

    private Database geneNameDatabase;

    private String environmentLocation;

    @Inject
    public AnnotationEnvironment(@Value("#{configuration['genename.bdb.location']}") String environmentLocation) {
        this.environmentLocation = environmentLocation;
    }

    @PostConstruct
    public void setup() {
        setupEnvironment();
        setupGeneNameDatabase();

    }

    private void setupEnvironment() {
        EnvironmentConfig envConfig = new EnvironmentConfig();

        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
        File envHome = new File(environmentLocation);
        if (!envHome.exists()) {
            envHome.mkdirs();
        }
        environment = new Environment(envHome, envConfig);
    }

    public void setupGeneNameDatabase() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);

        geneNameDatabase = environment.openDatabase(null, GENES_DB, dbConfig);
    }

    @Bean(name="geneNames")
    public ConcurrentMap<String, String> geneNames() {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);

        return new StoredMap<String, String>(geneNameDatabase, keyBinding, dataBinding, true);
    }

    public TransactionRunner getTransactionRunner() {
        return new TransactionRunner(environment);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Database getGeneNameDatabase() {
        return geneNameDatabase;
    }

    @PreDestroy
    public void close() {
        geneNameDatabase.close();
        environment.close();
        logger.info("<close> gene name database successfully closed");
    }
}
