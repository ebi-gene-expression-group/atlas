/*global describe, beforeEach, it, expect*/
/*global hello*/

describe("jasmine", function () {

    "use strict";

    beforeEach(function () {
    });

    it("says hello to you", function () {
        expect(hello()).toEqual("hello");

    });

});