var pubmedMinedBioentitiesModule = (function($, europepmcUrl) {
    "use strict";

    var ids = [];

    function fetchPubmedMinedBioentities(pubmedId, callback) {

        var restURL = europepmcUrl.replace("%pubmedId%", pubmedId);
        ids = [];
        $.ajax({
            dataType: "json",
            url: restURL,
            async: false,
            success: parseEuropePMCJSONResponse
        }).done(function() {
            callback(null, ids);
        })
            .fail(function(jqHXR) {
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

} (jQuery, europepmcUrl));