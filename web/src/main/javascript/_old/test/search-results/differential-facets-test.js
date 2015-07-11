require('./../../build-util/testdom.js')('<html><body></body></html>');
var chai = require('chai');
var expect = chai.expect;
var Facets = require('./../../src/search-results/facets.jsx');
var TestUtils = require('react-addons').TestUtils;
var $ = require('jquery');

describe("differential-facets", function () {

    it("should render facets", function () {

        var facetsData = {
            "kingdom": [{"name": "ensembl", "value": "ensembl"}, {"name": "plants", "value": "plants"}],
            "species": [{"name": "mus musculus", "value": "mus musculus"}, {
                "name": "arabidopsis thaliana",
                "value": "arabidopsis thaliana"
            }, {"name": "homo sapiens", "value": "homo sapiens"}],
            "experimentType": [{
                "name": "microarray_1colour_mrna_differential",
                "value": "microarray_1colour_mrna_differential"
            }, {
                "name": "rnaseq_mrna_differential",
                "value": "rnaseq_mrna_differential"
            }, {"name": "microarray_2colour_mrna_differential", "value": "microarray_2colour_mrna_differential"}],
            "factors": [{"name": "compound", "value": "compound"}, {
                "name": "genotype",
                "value": "genotype"
            }, {"name": "organism part", "value": "organism part"}, {
                "name": "disease",
                "value": "disease"
            }, {"name": "culture condition", "value": "culture condition"}],
            "numReplicates": [{"name": "3", "value": "3"}, {"name": "5", "value": "5"}, {
                "name": "4",
                "value": "4"
            }, {"name": "6", "value": "6"}],
            "regulation": [{"name": "DOWN", "value": "DOWN"}, {"name": "UP", "value": "UP"}]
        };

        var facets = TestUtils.renderIntoDocument(Facets({facets: facetsData}));

        var facetsTopLevelText =  $(facets.getDOMNode()).children('li').children('span').map(function (index, el) { return el.textContent; });

        expect(facetsTopLevelText.toArray()).to.eql(["kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"]);
    });

});