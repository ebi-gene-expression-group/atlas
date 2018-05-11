"use strict";

var pubmedMinedBioentitiesModule = (function($) {
    var europepmcUrl =
      "https://www.ebi.ac.uk/europepmc/webservices/rest/MED/%pubmedId%/textMinedTerms?semantic_type=GENE_PROTEIN&page=1&pageSize=25&format=json";

    var ids = [];

    function fetchPubmedMinedBioentities(pubmedId, callback) {

        var restURL = europepmcUrl.replace("%pubmedId%", pubmedId);
        ids = [];
        $.ajax({
            dataType: "json",
            url: restURL,
            // TODO Is there any reason to have a sync call instead of async?
            async: false,
            success: parseEuropePMCJSONResponse
        }).done(function() {
            callback(null, ids);
        }).fail(function(jqHXR) {
                callback(jqHXR.statusText);
            });
    }

    function parseEuropePMCJSONResponse(json) {
        if (!json.semanticTypeList) {
            return;
        }

        $.each(json.semanticTypeList.semanticType[0].tmSummary, function(index, value) {
            ids.push(value.term);
        });
    }

    return {
        fetchPubmedMinedBioentities: fetchPubmedMinedBioentities
    };

} (jQuery));