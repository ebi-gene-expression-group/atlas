expect = require('chai').expect;

describe('strings', function() {
    it("concatenates", function() {
        expect("foo" + "bar").to.eql("foobar");
    });
});