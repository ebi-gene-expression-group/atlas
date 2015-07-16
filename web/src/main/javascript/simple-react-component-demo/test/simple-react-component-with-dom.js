"use strict";

var expect = require('chai').expect;
var React = require('react/addons');
var ReactTestUtils = React.addons.TestUtils;
require('testdom')('');


describe("SimpleComponent", function() {

    var SimpleComponent;
    var subject;

    beforeEach(function() {
        SimpleComponent = require('../src/SimpleComponent.jsx');
        subject = React.createElement(SimpleComponent);
    });

    it("is a React element", function() {
        expect(ReactTestUtils.isElement(subject)).to.equal(true);
    });

    it("is a React element of type SimpleComponent", function() {
        expect(ReactTestUtils.isElementOfType(subject, SimpleComponent)).to.equal(true);
    });

    it("is a DOM component", function() {
        expect(ReactTestUtils.isDOMComponent(subject)).to.equal(false);
    });

    it("should render on the page", function() {
        expect(ReactTestUtils.renderIntoDocument(subject).getDOMNode().innerHTML).to.equal("Hello world, I’m not hot!");
    });
});

