package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import static uk.ac.ebi.atlas.model.baseline.BioentityPropertyName.*;

public class BioEntityCardProperties {

    public static final ImmutableMap<BioentityPropertyName, String> linkTemplates =
        ImmutableMap.<BioentityPropertyName, String>builder()
        .put(ORTHOLOG,
                "/gxa/genes/{0}")
        .put(GOTERM,
                "http://amigo.geneontology.org/cgi-bin/amigo/search.cgi?search_constraint=term&exact_match=yes&action=new-search&search_query={0}")
        .put(GO,
                "http://amigo.geneontology.org/amigo/term/{0}")
        .put(INTERPROTERM,
                "http://www.ebi.ac.uk/interpro/search?q={0}")
        .put(INTERPRO,
                "http://www.ebi.ac.uk/interpro/entry/{0}")
        .put(ENSFAMILY_DESCRIPTION,
                "http://www.ensembl.org/{1}/Search/Results?q={2};facet_feature_type=Protein%20Family" )
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
                " http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc={0}")
        .build();

    public static final ImmutableList<BioentityPropertyName> bioentityPropertyNames = ImmutableList.of(
            BioentityPropertyName.SYNONYM,
            BioentityPropertyName.ORTHOLOG,
            BioentityPropertyName.GO,
            BioentityPropertyName.PO,
            BioentityPropertyName.INTERPRO,
            BioentityPropertyName.ENSFAMILY_DESCRIPTION,
            BioentityPropertyName.ENSGENE,
            BioentityPropertyName.ENSTRANSCRIPT,
            BioentityPropertyName.ENSPROTEIN,
            BioentityPropertyName.ENTREZGENE,
            BioentityPropertyName.UNIPROT,
            BioentityPropertyName.MGI_ID,
            BioentityPropertyName.MGI_DESCRIPTION,
            BioentityPropertyName.GENE_BIOTYPE,
            BioentityPropertyName.DESIGN_ELEMENT,
            BioentityPropertyName.REACTOME,
            BioentityPropertyName.MIRBASE_ID,
            BioentityPropertyName.MIRBASE_ACCESSION,
            BioentityPropertyName.MIRBASE_SEQUENCE
    );

}
