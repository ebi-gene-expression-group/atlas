require('./../../build-util/testdom.js')('<html><body></body></html>');
var chai = require('chai');
var expect = chai.expect;
var Heatmap = require('./../../src/heatmap/heatmap.jsx');
var TestUtils = require('react-addons').TestUtils;
var $ = require('jquery');

describe("heatmap", function () {

    it("should render", function () {

        var heatmap = TestUtils.renderIntoDocument(Heatmap());

        var div = TestUtils.findRenderedDOMComponentWithTag(heatmap, 'div');

        expect(div.textContent).to.eql("Hello React!");
    });

});