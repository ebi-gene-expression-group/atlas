package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.*;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import static org.restlet.engine.util.StringUtils.isNullOrEmpty;
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.*;

public class BioEntityCardProperties {

    private static final Multimap<BioentityPropertyName, String> linkTemplates = ImmutableMultimap.<BioentityPropertyName, String>builder()
            .putAll(PATHWAYID, "https://reactome.org/content/detail/{0}", "http://plantreactome.gramene.org/content/detail/{0}")
            .putAll(ORTHOLOG, "/gxa/genes/{0}")
            .putAll(GOTERM,"http://www.ebi.ac.uk/ols/search?q={0}")
            .putAll(GO,"http://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/{0}")
            .putAll(POTERM,"http://www.ebi.ac.uk/ols/search?q={0}")
            .putAll(PO,"http://www.ebi.ac.uk/ols/ontologies/po/terms?iri=http://purl.obolibrary.org/obo/{0}")
            .putAll(INTERPROTERM,"http://www.ebi.ac.uk/interpro/search?q={0}")
            .putAll(INTERPRO,"http://www.ebi.ac.uk/interpro/entry/{0}")
            .putAll(ENSFAMILY_DESCRIPTION,"http://www.ensembl.org/{1}/Search/Results?q={2};facet_feature_type=Protein%20Family")
            .putAll(ENSGENE,"http://www.ensemblgenomes.org/id-gene/{0}")
            .putAll(ENSTRANSCRIPT,"http://www.ensemblgenomes.org/id/{0}")
            .putAll(ENSPROTEIN,"http://www.ensemblgenomes.org/id/{0}")
            .putAll(MGI_DESCRIPTION,"http://www.informatics.jax.org/searchtool/Search.do?query={0}")
            .putAll(ENTREZGENE,"http://www.ncbi.nlm.nih.gov/sites/entrez?db=gene&term={0}")
            .putAll(UNIPROT,"http://www.uniprot.org/uniprot/{0}")
            .putAll(MGI_ID,"http://www.emouseatlas.org/emagewebapp/pages/emage_general_query_result.jsf?genes={0}")
            .putAll(GENE_BIOTYPE,"http://www.ensembl.org/Help/Glossary?id=275")
            .putAll(MIRBASE_ACCESSION,"http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc={0}")
            .putAll(WBPSPROTEIN,"http://parasite.wormbase.org/id/{0}")
            .putAll(WBPSGENE,"http://parasite.wormbase.org/id/{0}")
            .putAll(WBPSTRANSCRIPT,"http://parasite.wormbase.org/id/{0}")
            .build();

    public static final ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
            DESCRIPTION, SYMBOL, //not displayed in the table but useful for other things
            SYNONYM,
            ORTHOLOG,
            GO,
            PO,
            INTERPRO,
            ENSFAMILY_DESCRIPTION,
            ENSGENE,
            ENSTRANSCRIPT,
            ENSPROTEIN,
            WBPSGENE,
            WBPSTRANSCRIPT,
            WBPSPROTEIN,
            ENTREZGENE,
            UNIPROT,
            MGI_ID,
            MGI_DESCRIPTION,
            GENE_BIOTYPE,
            DESIGN_ELEMENT,
            PATHWAYID,
            MIRBASE_ID,
            MIRBASE_ACCESSION,
            MIRBASE_SEQUENCE
    );

    //in case of PATHWAYID the plant url is stored at second index of multivalue list because of which "linkTemplates.get(propertyName).get(1);" is returned

    public static String getUrlTemplate(BioentityPropertyName propertyName, String kingdom){

        if(isNullOrEmpty(kingdom)){
            return "";
        }
        else if(kingdom.equalsIgnoreCase("animals") || kingdom.equalsIgnoreCase("fungi")) {
            return Iterables.getFirst(linkTemplates.get(propertyName),"");
        }
        else{
            return Iterables.get(linkTemplates.get(propertyName),1,"");
        }
    }

    public static String getUrlTemplate(BioentityPropertyName propertyName){
            return Iterables.getFirst(linkTemplates.get(propertyName),"");
    }

    public static Multimap<BioentityPropertyName, String> getAllUrlTemplates() {
        return linkTemplates;
    }
}
