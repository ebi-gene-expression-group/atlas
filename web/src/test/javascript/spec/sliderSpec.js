/*global describe, beforeEach, it, expect*/
/*global MagnifiedScale:false*/

describe("Bar Chart magnified scale", function () {

    "use strict";

    var magnifiedScale = new MagnifiedScale();

    beforeEach(function () {
    });

    describe("toString(value) method", function () {
        it("should convert long values into magnified strings, using K and M to indicate thousands and millions", function () {

            expect(magnifiedScale.toString(0)).toEqual("0");
            expect(magnifiedScale.toString(11)).toEqual("11");
            expect(magnifiedScale.toString(123)).toEqual("123");
            expect(magnifiedScale.toString(2342)).toEqual("2.342K");
            expect(magnifiedScale.toString(2000)).toEqual("2K");
            expect(magnifiedScale.toString(34534)).toEqual("34.534K");
            expect(magnifiedScale.toString(34000)).toEqual("34K");
            expect(magnifiedScale.toString(234454)).toEqual("234.454K");
            expect(magnifiedScale.toString(5000000)).toEqual("5M");
            expect(magnifiedScale.toString(23000000)).toEqual("23M");

        });
    });

    describe("getNearestScaledValue(value) method", function () {
        it("should 'round' to magnified scale any value smaller than 1", function () {

            expect(magnifiedScale.getNearestScaleValue(0)).toEqual("0");
            expect(magnifiedScale.getNearestScaleValue(0.001)).toEqual("0.0");
            expect(magnifiedScale.getNearestScaleValue(0.099)).toEqual("0.1");
            expect(magnifiedScale.getNearestScaleValue(0.1)).toEqual("0.1");
            expect(magnifiedScale.getNearestScaleValue(0.11)).toEqual("0.1");
            expect(magnifiedScale.getNearestScaleValue(0.11111)).toEqual("0.1");
            expect(magnifiedScale.getNearestScaleValue(0.19934)).toEqual("0.2");

        });

        it("should 'floor' to magnified scale any value greater than 1", function () {

            expect(magnifiedScale.getNearestScaleValue(4.1)).toEqual("4");
            expect(magnifiedScale.getNearestScaleValue(4.77)).toEqual("4");
            expect(magnifiedScale.getNearestScaleValue(8.017)).toEqual("8");
            expect(magnifiedScale.getNearestScaleValue(1231)).toEqual("1000");
            expect(magnifiedScale.getNearestScaleValue(380)).toEqual("300");
            expect(magnifiedScale.getNearestScaleValue(48342)).toEqual("40000");

        });

    });

});