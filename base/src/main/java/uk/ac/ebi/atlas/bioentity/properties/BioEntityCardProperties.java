package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static uk.ac.ebi.atlas.solr.BioentityPropertyName.*;

public class BioEntityCardProperties {
    // As long as all species in species-properties.json are properly annotated and both geneome browser and gene info
    // come from the same place we won’t need a fallback URL such as http://www.ensemblgenomes.org/id-gene/{0} and save
    // the user one click

    private static final Map<BioentityPropertyName, Function<Species, String>> PROPERTY_LINK_MAPPER =
            ImmutableMap.<BioentityPropertyName, Function<Species, String>>builder()
                    .put(ENSGENE, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Gene/Summary?g={0}")
                    .put(WBPSGENE, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Gene/Summary?g={0}")
                    .put(ENSTRANSCRIPT, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Transcript/Summary?t={0}")
                    .put(WBPSTRANSCRIPT, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Transcript/Summary?t={0}")
                    .put(ENSPROTEIN, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Transcript/ProteinSummary?t={0}")
                    .put(WBPSPROTEIN, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Transcript/ProteinSummary?t={0}")
                    .put(ENSFAMILY_DESCRIPTION, species -> species.isUnknown() ? "" : species.getGenomeBrowsers().asList().get(0).get("url") + "/Gene/Family?g={1}")
                    .put(PATHWAYID,
                            species -> species.isPlant() ?
                            "http://plantreactome.gramene.org/content/detail/{0}" :
                            "https://reactome.org/content/detail/{0}")

                    .put(GO, species -> "https://www.ebi.ac.uk/ols/ontologies/go/terms?iri=http://purl.obolibrary.org/obo/{0}")
                    .put(GOTERM, species -> "https://www.ebi.ac.uk/ols/search?q={0}&ontology=go")
                    .put(PO, species -> "https://www.ebi.ac.uk/ols/ontologies/po/terms?iri=http://purl.obolibrary.org/obo/{0}")
                    .put(POTERM, species -> "https://www.ebi.ac.uk/ols/search?q={0}&ontology=po")
                    .put(INTERPRO, species -> "https://www.ebi.ac.uk/interpro/entry/{0}")
                    .put(INTERPROTERM, species -> "https://www.ebi.ac.uk/interpro/search?q={0}")
                    .put(MGI_ID, species -> "http://www.informatics.jax.org/marker/{0}")
                    .put(MGI_DESCRIPTION, species -> "http://www.informatics.jax.org/searchtool/Search.do?query={0}")
                    .put(ENTREZGENE, species -> "https://www.ncbi.nlm.nih.gov/gene?term={0}")
                    .put(UNIPROT, species -> "https://www.uniprot.org/uniprot/{0}")
                    .put(ORTHOLOG, species -> "/gxa/genes/{0}")
                    .put(GENE_BIOTYPE, species -> "http://www.ensembl.org/Help/Glossary?id=275")
                    .put(MIRBASE_ACCESSION, species -> "http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc={0}")
            .build();

    public static final ImmutableList<BioentityPropertyName> BIOENTITY_PROPERTY_NAMES = ImmutableList.of(
            DESCRIPTION,
            SYMBOL,
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
            MIRBASE_SEQUENCE);

    public static String getUrlTemplate(BioentityPropertyName propertyName, Species species) {
        return PROPERTY_LINK_MAPPER.getOrDefault(propertyName, s -> "").apply(species);
    }

    static Set<BioentityPropertyName> linkedPropertynames() {
        return PROPERTY_LINK_MAPPER.keySet();
    }
}
