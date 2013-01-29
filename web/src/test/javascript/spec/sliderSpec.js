/*global describe, beforeEach, it, expect*/
/*global MagnifiedScale:false*/

describe("Bar Chart magnified scale", function () {

    "use strict";

    var subject = new MagnifiedScale();

    beforeEach(function () {
    });

    it("by default will be configured for a scale with one fractional digit", function () {

        expect(subject.fractionalDigits).toBe(1);

    });

    describe("toString(value) method", function () {
        it("should convert long values into magnified strings, using K and M to indicate thousands and millions", function () {

            expect(subject.toString(0)).toEqual("0");
            expect(subject.toString(11)).toEqual("11");
            expect(subject.toString(123)).toEqual("123");
            expect(subject.toString(2342)).toEqual("2.342K");
            expect(subject.toString(2000)).toEqual("2K");
            expect(subject.toString(34534)).toEqual("34.534K");
            expect(subject.toString(34000)).toEqual("34K");
            expect(subject.toString(234454)).toEqual("234.454K");
            expect(subject.toString(5000000)).toEqual("5M");
            expect(subject.toString(23000000)).toEqual("23M");

        });
    });

    describe("getNearestScaledValue(value) method", function () {
        it("should 'round' to magnified scale any value smaller than 1", function () {

            expect(subject.getNearestScaleValue(0)).toEqual("0");
            expect(subject.getNearestScaleValue(0.001)).toEqual("0.0");
            expect(subject.getNearestScaleValue(0.099)).toEqual("0.1");
            expect(subject.getNearestScaleValue(0.1)).toEqual("0.1");
            expect(subject.getNearestScaleValue(0.11)).toEqual("0.1");
            expect(subject.getNearestScaleValue(0.11111)).toEqual("0.1");
            expect(subject.getNearestScaleValue(0.19934)).toEqual("0.2");

        });

        it("should 'floor' to magnified scale any value greater than 1", function () {

            expect(subject.getNearestScaleValue(4.1)).toEqual("4");
            expect(subject.getNearestScaleValue(4.77)).toEqual("4");
            expect(subject.getNearestScaleValue(8.017)).toEqual("8");
            expect(subject.getNearestScaleValue(1231)).toEqual("1000");
            expect(subject.getNearestScaleValue(380)).toEqual("300");
            expect(subject.getNearestScaleValue(48342)).toEqual("40000");

        });

    });

    describe("getNthScaleValue(atPosition) method", function () {
        it("should return the scale value for the given position", function () {

            expect(subject.getNthScaleValue(0)).toEqual("0");
            expect(subject.getNthScaleValue(1)).toEqual("0.1");
            expect(subject.getNthScaleValue(10)).toEqual("1");
            expect(subject.getNthScaleValue(30)).toEqual("300");
            expect(subject.getNthScaleValue(40)).toEqual("4000");

        });

    });

});