"use strict";

//var sinon = require('sinon');
var expect = require('chai').expect;
require('testdom')('', {$ : 'jquery', jquery_ui: 'jquery-ui'});

if (typeof $ === "undefined") {
    var $ = require('jquery');
}

require('jquery-ui');

describe("HelpTooltipsModule <div id=\"help\" data-help-loc=\"#gradient-differential\">", function() {

    before(function() {
        $.support.cors = true;

        $('<div id="help" data-help-loc="#gradient-differential"></div>').appendTo("body");
        var HelpTooltipsModuleInit = require("../src/HelpTooltips.js");
        HelpTooltipsModuleInit("http://localhost:8080/gxa", "experiment", document.getElementById("help"));
    });

    after(function() {
        $("#help").remove();
    });

    it("shows a question mark", function() {
        expect($("#help").text()).to.equal("?");
    });

    // TODO use sinon to fake server: http://stackoverflow.com/questions/29945046/stubbing-jquery-ajax-in-node-environment-jquery-2-x
    it("shows amd hides the tooltip when the mouse enters and then moves away", function(done) {
        $("#help a").trigger("mouseenter");
        setTimeout(function() {
            expect($(".gxaHelpTooltip").length).to.equal(1);
            $("#help a").trigger("mouseout");
            setTimeout(function() {
                expect($(".gxaHelpTooltip").length).to.equal(0);
                done();
            }, 500);
        }, 500);
    });

});

