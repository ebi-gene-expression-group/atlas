"use strict";

var React = require('react');   // React is called in the transpiled JS files in the return statements

//*------------------------------------------------------------------*

function formatBaselineExpression(expressionLevel) {
    var numberExpressionLevel = +expressionLevel;
    return (
      numberExpressionLevel >= 100000 || numberExpressionLevel < 0.1
      ? formatScientificNotation(expressionLevel, 1)
       : '' + numberExpressionLevel
    );
}

// expects number in the format #E# and displays exponent in superscript
function formatScientificNotation(value,accuracy) {
    var scientificNotationString= (+value).toExponential(accuracy||4);

    var formatParts = scientificNotationString.split(/[Ee]/);

    if (formatParts.length == 1) {
        return (
            <span>{value}</span>
        );
    }

    var mantissa = formatParts[0].replace(/([^\.])0+$/,"$1");
    var exponent = formatParts[1].replace("+","");

    if (+exponent==0) {
        return (
            <span>{mantissa}</span>
        );
    }

    return (
        <span>
            {(mantissa !== "1") ? mantissa + " \u00D7 " : ''}10<span style={{'verticalAlign': 'super'}}>{exponent}</span>
        </span>
    );
}

//*------------------------------------------------------------------*

exports.baselineExpression = formatBaselineExpression;
exports.scientificNotation = formatScientificNotation;
