package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Op {
    LIST("Show a description of that experiment"),
    LOG("Show the history of processing for that experiment"),
    STATUS("Show current processing status, inferred recorded from start and finish times"),
    CLEAR_LOG("Clear the operation history"),
    UPDATE_PRIVATE("Set experiment as private in the database and delete from analytics index"),
    UPDATE_PUBLIC("Set experiment as public in the database and update the design"),
    UPDATE_DESIGN_ONLY("Assume the experiment is public. Then update the design. Expect serialize to follow"),
    IMPORT("Parse and validate the configuration.xml file. Delete the experiment if already present, " +
            "then use the configuration to load the analytics into database. Then update the design," +
            " and add the experiment to database."),
    IMPORT_PUBLIC("Same as import but make experiment searchable and visible without an access key"),
    DELETE("Delete the analytics from database, if public delete from conditions and analytics index, and remove the " +
            "accession from experiment list in the database. Remove any remaining kryo files"),
    SERIALIZE("Write out the kryo files and make a baseline experiment load much faster"),
    COEXPRESSION_IMPORT("Assume there is no coexpression data in the database. Then load coexpressions"),
    COEXPRESSION_UPDATE("Delete coexpressions from database and then load coexpressions"),
    COEXPRESSION_DELETE("Delete coexpressions from database"),
    ANALYTICS_IMPORT("Fetch file content, extract bioentity identifiers to serve as keywords, " +
            "and upload to Solr analytics index. If you skip this op, searching on the main page will not reach " +
            "the identifiers from the experiment, but searching on the experiment page will still work."),
    ANALYTICS_DELETE("Tell Solr to delete all data with this experiment accession"),
    CACHE_READ("Read the attributes of the experiment from cache, trying to load it if it is absent"),
    CACHE_REMOVE("Delete from cache if present"),
    CHECK("Do the same checks that are done on experiment import - required files are present, their headers match configuration.xml, configuration.xml and condensed-sdrf match each other"),
    POPULATE_MARKER_GENES("Add random data for marker genes to the DB (the experiment doesn’t need to exist)"),
    DELETE_MARKER_GENES("Delete marker genes data from DB"),
    DELETE_ALL_MARKER_GENES("Delete all marker genes data from DB (will ignore any experiment accession)");



    static ImmutableMap<String, ImmutableList<Op>> synonyms = ImmutableMap.of(
            "UPDATE_DESIGN",ImmutableList.of(UPDATE_DESIGN_ONLY,SERIALIZE),
            "UPDATE", ImmutableList.of(UPDATE_PRIVATE), // Deprecated June 2016
            "LOAD_PUBLIC", ImmutableList.of(IMPORT_PUBLIC,COEXPRESSION_UPDATE,SERIALIZE,ANALYTICS_IMPORT),
            "LOAD", ImmutableList.of(IMPORT,COEXPRESSION_UPDATE,SERIALIZE),
            "DOUBLE_CHECK", ImmutableList.of(CACHE_REMOVE,CHECK,CACHE_READ)
    );

    static Map<String, String> JARGON_ELABORATED = ImmutableMap.of(
            "update the design", "Parse the SDRF file to get factors, characteristics per assay, and other " +
                    "information needed for the experiment design page, then write out the design file." +
                    " For a public experiment," +
                    " update the conditions index with the ontology terms extracted from the design",
            "the analytics", "The experiment results, whose format varies per experiment type. e.g. for baseline " +
                    "experiments they are expression value per gene and assay",
            "the kryo files", "Expression data files that have been expanded into serialized Java objects, which the " +
                    "server can retrieve without doing the parsing work",
            "load coexpressions", "Parse the coexpressions.tsv.gz file, then for each gene pick top 100 genes with " +
                    "expression pattern most similar in that experiment. Then save them in database as a blob."

    );

    private final String description;

    Op(String description) {
        this.description = description;
    }

    public String getDescription() {
        String result = description;
        for (String pieceOfJargon : JARGON_ELABORATED.keySet()) {
            result = result.replace(pieceOfJargon,
                    String.format("*%s*", pieceOfJargon));
        }
        return result;
    }

    public static List<Op> opsForParameter(String parameter) throws IllegalArgumentException {
        return opsForUppercasedParameter(parameter.toUpperCase());
    }

    private static List<Op> opsForUppercasedParameter(String parameter) throws IllegalArgumentException {
        List<Op> result = new ArrayList<>();
        for (String s : parameter.split(",")) {
            List<Op> opsForShortcutName = synonyms.get(s);
            if (opsForShortcutName == null || opsForShortcutName.isEmpty()) {
                result.add(Op.valueOf(s));
            } else {
                result.addAll(opsForShortcutName);
            }
        }
        return result;
    }


}