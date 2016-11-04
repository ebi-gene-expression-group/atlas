// ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
// used to launch the ensembl genome browser for tracks
/**
 * @param {string} species
 */
function toEnsemblSpecies(species) {
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
        const words = str.split(` `);
        return (words.length <= 2) ? str : `${words[0]} ${words[1]}`;
    }

    return capitaliseFirstLetter(firstTwoWords(species).replace(` `, `_`).toLowerCase());
}

function getEnsemblHost(ensemblDB) {
    var ensemblHost = ``;

    if (ensemblDB === ) {
        ensemblHost = `http://plants.ensembl.org/`;
    } else if (ensemblDB === `fungi`) {
        ensemblHost = `http://fungi.ensembl.org/`;
    } else if (ensemblDB === `metazoa`) {
        ensemblHost = `http://metazoa.ensembl.org/`;
    } else if (ensemblDB === `ensembl`) {
        ensemblHost = `http://www.ensembl.org/`;
    }

    return ensemblHost;
}

const genomeBrowserHosts = [
    {
        ensembl: `plants`,
        hosts: [`http://plants.ensembl.org/${ensemblSpecies}/Location/View?db=core;g=${geneId};`, `http://ensembl.gramene.org/`]
    },
    {
        ensembl: `fungi`,
        hosts: [`http://fungi.ensembl.org/`]
    },
    {
        ensembl: `metazoa`,
        hosts: [`http://metazoa.ensembl.org/`]
    },
    {
        ensembl: `ensembl`,
        host: [`http://www.ensembl.org/`]
    },
    {
        ensembl: `wbps`,
        hosts: [`http://plants.ensembl.org/`]
    },
]


function getGrameneHost() {
    return `http://ensembl.gramene.org/`;
}

//*------------------------------------------------------------------*

exports.toEnsemblSpecies = toEnsemblSpecies;
exports.getEnsemblHost = getEnsemblHost;
exports.getGrameneHost = getGrameneHost;