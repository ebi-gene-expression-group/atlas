"use strict";

var expect = require('chai').expect;
var React = require('react/addons');
require('testdom')('');

describe("SimpleComponent", function() {

    //var SimpleComponent = require('../src/SimpleComponent.jsx');

    describe("#render()", function() {
        it("should render on the page", function() {

            var div = document.createElement('div');
            div.setAttribute("id", "blah");
            div.innerHTML = "<p>You shouldn&rsquo;t be able to see this</p>";
            document.body.appendChild(div);
            //console.log("document = " + document.documentElement.innerHTML);

            //var instance = React.addons.TestUtils.renderIntoDocument(React.createElement(SimpleComponent));
            //console.log(div.firstChild.innerHTML);
            console.log("document = " + document.documentElement.innerHTML);
        });
    });
});


describe("Array", function() {
    describe("#indexOf()", function () {
        it("should return -1 when the value is not present", function () {
            expect([1,2,3].indexOf(5)).to.equal(-1);
            expect([1,2,3].indexOf(2)).to.equal(1);
        });
    });
});

