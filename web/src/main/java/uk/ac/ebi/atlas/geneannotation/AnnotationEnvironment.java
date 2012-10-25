package uk.ac.ebi.atlas.geneannotation;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;

//@Named("annotationEnvironment")
public class AnnotationEnvironment {

    private static final String GENES_DB = "genes.db";
    private Environment environment;

    private Database geneNameDatabase;

    private String environmentLocation;

    public AnnotationEnvironment(String environmentLocation) {
        this.environmentLocation = environmentLocation;
    }

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

    public StoredMap<String, String> geneNames() {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);

        StoredMap<String, String> storedMap = new StoredMap<String, String>
                (geneNameDatabase, keyBinding, dataBinding, true);

        return storedMap;
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

    public void close() {
        if (geneNameDatabase != null) {
            geneNameDatabase.close();
        }
        if (environment != null) {
            environment.close();
        }
    }
}
