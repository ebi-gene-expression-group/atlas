package uk.ac.ebi.atlas.gene;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.io.File;


@Configuration
public class BDBConfiguration {


    @Bean(/*initMethod = "init",*/ destroyMethod = "close")
    @Inject
    public Database geneDatabase(Environment environment) {

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);

        return environment.openDatabase(null, "genes.db", dbConfig);
    }


    @Bean(/*initMethod = "init",*/ destroyMethod = "close")
    @Value("#{configuration['genename.bdb.location']}")
    public Environment environment(String location) {

        return new Environment(new File(location), environmentConfig());
    }

    @Bean
    public EnvironmentConfig environmentConfig() {
        EnvironmentConfig envConfig = new EnvironmentConfig();

        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);

        return envConfig;

    }

    @Bean
    @Inject
    public StoredMap<String, String> geneNames(Database db) {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);

        StoredMap<String, String> storedMap = new StoredMap<String, String>
                (db, keyBinding, dataBinding, true);

        return storedMap;
    }

}
