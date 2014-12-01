var pubmedMinedBioentitiesModule = (function($, europepmcUrl) {
    "use strict";

    function fetchPubmedMinedBioentities(pubmedId, callback) {


        callback(["id1", "id2"]);
    }

    return {
        fetchPubmedMinedBioentities: fetchPubmedMinedBioentities
    };
} (jQuery, europepmcUrl));