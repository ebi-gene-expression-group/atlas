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
import com.sleepycat.je.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.concurrent.ConcurrentMap;

//ToDo Separate database creation and update from database readonly access.

@Named("annotationEnvironment")
@Scope("singleton")
public class AnnotationEnvironment {

    private static final Logger logger = Logger.getLogger(AnnotationEnvironment.class);

    private static final String GENES_DB = "genes.db";

    private static final String DESIGN_ELEMENTS_DB = "de.db";

    private File environmentDirectory;

    private Environment environment;

    private Database geneNameDatabase;

    private Database designElementDatabase;

    @Inject
    public AnnotationEnvironment(@Value("#{configuration['genename.bdb.location']}") String environmentLocation) {
        environmentDirectory = new File(environmentLocation);
        if (!environmentDirectory.exists()) {
            boolean result = environmentDirectory.mkdirs();
            if (!result) {
                throw new IllegalStateException("Cannot create directory for BDB in " + environmentLocation);
            }
        }
    }

    @PostConstruct
    public void initBerkeleyReadonly() {
        initBerkeleyDatabase(true);
    }

    public void initBerkeleyDatabase(boolean readonly) {
        try {
            setupEnvironment(readonly);
            setupGeneNameDatabase(readonly);
            setupDesignElementDatabase(readonly);
        } catch (EnvironmentNotFoundException e) {
            initBerkeleyDatabase(false);
            close();
            initBerkeleyDatabase(readonly);
        }

    }

    private EnvironmentConfig createEnvironmentConfig(boolean readOnly) {
        EnvironmentConfig envConfig = new EnvironmentConfig();

        envConfig.setTransactional(!readOnly);
        envConfig.setAllowCreate(!readOnly);
        envConfig.setReadOnly(readOnly);
        envConfig.setLocking(!readOnly);
        return envConfig;
    }

    private void setupEnvironment(boolean readonly) {
        environment = new Environment(environmentDirectory, createEnvironmentConfig(readonly));
    }

    public void setupGeneNameDatabase(boolean readonly) {
        geneNameDatabase = setupDatabase(readonly, GENES_DB);
    }

    public void setupDesignElementDatabase(boolean readonly) {
        designElementDatabase = setupDatabase(readonly, DESIGN_ELEMENTS_DB);
    }

    public Database setupDatabase(boolean readonly, String dbName) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(!readonly);
        dbConfig.setReadOnly(readonly);
        return environment.openDatabase(null, dbName, dbConfig);
    }


    //@Bean(name="geneNames") injecting StoredMap is too fragile, clients may cache it and it may become invalid if underlying database is closed and reopen at runtime
    public ConcurrentMap<String, String> geneNames() {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);
        return new StoredMap<>(geneNameDatabase, keyBinding, dataBinding, true);
    }

    public ConcurrentMap<String, String> geneDesignElementsToGeneNames() {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);
        return new StoredMap<>(designElementDatabase, keyBinding, dataBinding, true);
    }

    public TransactionRunner getTransactionRunner() {
        return new TransactionRunner(environment);
    }

//    public Environment getEnvironment() {
//        return environment;
//    }
//
//    public Database getGeneNameDatabase() {
//        return geneNameDatabase;
//    }

    @PreDestroy
    public void close() {
        geneNameDatabase.close();
        environment.close();
        logger.info("<close> gene name database successfully closed");
    }
}
