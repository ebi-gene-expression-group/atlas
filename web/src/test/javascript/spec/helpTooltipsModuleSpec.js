/*global describe, beforeEach, it, expect*/
/*global $,helpTooltipsModule:false*/

describe("HelpTooltipsModule", function () {

    "use strict";

    helpTooltipsModule.init("experiment");

    beforeEach(function () {
    });

    describe("buildHelpAnchor should return an anchor with", function () {

        var anchor = helpTooltipsModule.buildHelpAnchor();

        it("title set to #", function () {
            expect($(anchor).attr("href")).toBe("#");
        });

        it("text set to ?", function () {
            expect($(anchor).text()).toBe("?");
        });

        it("class set to help-icon", function () {
            expect($(anchor).attr("class")).toBe("help-icon");
        });

    });

    describe("getHelpFileName should ", function () {

        it("return prepend and append the correct prefix and suffix to the given input", function () {
            expect(helpTooltipsModule.getHelpFileName("experiment")).toBe("help-tooltips.experiment-page.html");
        });

    });

});