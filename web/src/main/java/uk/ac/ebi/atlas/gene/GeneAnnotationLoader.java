package uk.ac.ebi.atlas.gene;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.utils.biomart.BioMartGeneNameStream;

import javax.inject.Inject;
import javax.inject.Named;

@Named("geneAnnotationLoader")
@Scope("prototype")
public class GeneAnnotationLoader {


    private TransactionRunner transactionRunner;

    private Database database;

    private BioMartGeneNameStream.Builder geneNameStreamBuilder;

    @Inject
    public GeneAnnotationLoader(Database database, BioMartGeneNameStream.Builder geneNameStreamBuilder) {
        this.database = database;
        this.geneNameStreamBuilder = geneNameStreamBuilder;
        transactionRunner = new TransactionRunner(database.getEnvironment());
    }


    public void loadAnnotations(ObjectInputStream<String[]> annotationsInputStream,
                                GeneNameTransactionWorker transactionWorker) {

        String[] line;

        while ((line = annotationsInputStream.readNext()) != null) {
            try {
                transactionRunner.run(transactionWorker.setLine(line));
            } catch (DatabaseException e) {
                System.err.println("AccessExample: " + e);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void loadGeneNames() {
        ObjectInputStream<String[]> objectInputStream = geneNameStreamBuilder.create();
        GeneNameTransactionWorker transactionWorker = new GeneNameTransactionWorker(initGeneNameMap());

        loadAnnotations(objectInputStream, transactionWorker);
    }


    //ToDo: use one from configuration
    private StoredMap<String, String> initGeneNameMap() {
        TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        TupleBinding<String> dataBinding = TupleBinding.getPrimitiveBinding(String.class);

        StoredMap<String, String> storedMap = new StoredMap<String, String>
                (database, keyBinding, dataBinding, true);

        return storedMap;
    }

    private static class GeneNameTransactionWorker implements TransactionWorker {

        private String[] line;

        private StoredMap<String, String> map;

        private GeneNameTransactionWorker(StoredMap<String, String> map) {
            this.map = map;
        }

        public GeneNameTransactionWorker setLine(String[] line) {
            this.line = line;
            return this;
        }

        @Override
        public void doWork() throws Exception {

            try {
                if (!map.containsKey(line[0]))
                    map.put(line[0], line[1]);
                else
                    System.out.println("Key " + line[0] + ": " + line[1] +
                            " already exists.");
            } catch (Exception e) {
                System.err.println("doWork: " + e);
            }
        }
    }
}
