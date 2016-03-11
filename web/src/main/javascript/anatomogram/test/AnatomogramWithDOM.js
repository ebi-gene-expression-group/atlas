"use strict";

var expect = require('chai').expect;
var React = require('react');
var ReactTestUtils = require("react-addons-test-utils")
require('testdom')('');


describe("Anatomogram", function() {

    var Anatomogram;
    var subject;

    beforeEach(function() {
        Anatomogram = require('../src/Anatomogram.jsx');
        subject = React.createElement(SimpleComponent);
    });

    it("is a React element", function() {
        expect(ReactTestUtils.isElement(subject)).to.equal(true);
    });

    it("is a React element of type Anatomogram", function() {
        expect(ReactTestUtils.isElementOfType(subject, Anatomogram)).to.equal(true);
    });

    it("is a DOM component", function() {
        expect(ReactTestUtils.isDOMComponent(subject)).to.equal(false);
    });

});

