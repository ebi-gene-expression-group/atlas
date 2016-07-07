package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import java.beans.PropertyEditorSupport;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.strip;

public class OldGeneQueryPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        String geneQuery = TagEditorConverter.tagsToQueryString(text);

        List<String> terms = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(geneQuery);

        setValue(OldGeneQuery.create(removeSurroundingQuotes(terms)));
    }

    static ImmutableList<String> removeSurroundingQuotes(List<String> strings) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (String s : strings) {
            builder.add(strip(s, "\""));

        }

        return builder.build();
    }

    @Override
    public String getAsText() {
        return ((OldGeneQuery) this.getValue()).asUrlQueryParameter();
    }

}
