package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.*;

public class BioEntityCardProperties {

    public static final ImmutableMap<BioentityPropertyName, String> linkTemplates =
        ImmutableMap.<BioentityPropertyName, String>builder()
        .put(ORTHOLOG,
                "/gxa/genes/{0}")
        .put(GOTERM,
                "http://www.ebi.ac.uk/ols/search?q={0}")
        .put(GO,
                "http://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/{0}")
        .put(POTERM,
                "http://www.ebi.ac.uk/ols/search?q={0}")
        .put(PO,
                "http://www.ebi.ac.uk/ols/ontologies/po/terms?iri=http://purl.obolibrary.org/obo/{0}")
        .put(INTERPROTERM,
                "http://www.ebi.ac.uk/interpro/search?q={0}")
        .put(INTERPRO,
                "http://www.ebi.ac.uk/interpro/entry/{0}")
        .put(ENSFAMILY_DESCRIPTION,
                "http://www.ensembl.org/{1}/Search/Results?q={2};facet_feature_type=Protein%20Family")
        .put(ENSGENE,
                "http://www.ensemblgenomes.org/id-gene/{0}")
        .put(ENSTRANSCRIPT,
                "http://www.ensemblgenomes.org/id/{0}")
        .put(ENSPROTEIN,
                "http://www.ensemblgenomes.org/id/{0}")
        .put(MGI_DESCRIPTION,
                "http://www.informatics.jax.org/searchtool/Search.do?query={0}")
        .put(ENTREZGENE,
                "http://www.ncbi.nlm.nih.gov/sites/entrez?db=gene&term={0}")
        .put(UNIPROT,
                "http://www.uniprot.org/uniprot/{0}")
        .put(MGI_ID,
                "http://www.emouseatlas.org/emagewebapp/pages/emage_general_query_result.jsf?genes={0}")
        .put(GENE_BIOTYPE,
                "http://www.ensembl.org/Help/Glossary?id=275" /*seems wrong*/)
        .put(REACTOME,
                "http://www.reactome.org/cgi-bin/eventbrowser_st_id?ST_ID={0}")
        .put(MIRBASE_ACCESSION,
                "http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc={0}")
        .put(WBPSGENE,
                "http://parasite.wormbase.org/id/{0}")
        .put(WBPSTRANSCRIPT,
                "http://parasite.wormbase.org/id/{0}")
        .put(WBPSPROTEIN,
                "http://parasite.wormbase.org/id/{0}")
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
            REACTOME,
            MIRBASE_ID,
            MIRBASE_ACCESSION,
            MIRBASE_SEQUENCE
    );

}
