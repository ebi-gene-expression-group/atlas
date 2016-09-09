"use strict";
//Taken from ensemblUtils.js and EnsemblLauncher.jsx in the old heatmap
//*------------------------------------------------------------------*

// ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
// used to launch the ensembl genome browser for tracks
/**
 * @param {string} species
 */
var toEnsemblSpecies = function (species) {
    /**
     * @param {string} str
     */
    function capitaliseFirstLetter(str)
    {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }

    /**
     * @param {string} str
     */
    function firstTwoWords(str) {
        var words = str.split(" ");
        return (words.length <= 2) ? str : words[0] + " " + words[1];
    }

    return capitaliseFirstLetter(firstTwoWords(species).replace(" ", "_").toLowerCase());
};


var _ensemblTrackURL= function (isBaseline, experimentAccession, atlasBaseUrl, species, baseURL, selectedColumnId, selectedGeneId) {
    var ensemblSpecies = toEnsemblSpecies(species);
    var atlasTrackBaseURLWithTrackFileHeader =
        atlasBaseUrl
        + "/experiments/" + experimentAccession
        + "/tracks/" + experimentAccession + "." + selectedColumnId;
    var contigViewBottom =
        "contigviewbottom=url:" + atlasTrackBaseURLWithTrackFileHeader
        + (isBaseline ? ".genes.expressions.bedGraph" : ".genes.log2foldchange.bedGraph");
    var tiling =
        isBaseline
        ? ""
        : "=tiling,url:" + atlasTrackBaseURLWithTrackFileHeader + ".genes.pval.bedGraph=pvalue;";
    return baseURL + ensemblSpecies +
        "/Location/View?g=" + selectedGeneId
        + ";db=core;"
        + contigViewBottom + tiling
        + ";format=BEDGRAPH";
};


var getHost = function (ensemblDB) {
    var ensemblHost = "";
    if (ensemblDB === "plants") {
        ensemblHost = "https://ensembl.gramene.org/";
    } else if (ensemblDB === "fungi") {
        ensemblHost = "https://fungi.ensembl.org/";
    } else if (ensemblDB === "metazoa") {
        ensemblHost = "https://metazoa.ensembl.org/";
    } else if (ensemblDB === "ensembl") {
        ensemblHost = "https://www.ensembl.org/";
    }
    return ensemblHost;
}

module.exports = function(config){
  return _ensemblTrackURL(
    !config.isDifferential,
    config.experimentAccession,
    config.atlasBaseURL,
    config.species,
    getHost(config.ensemblDB),
    "__x__",
    "__y__");
}
