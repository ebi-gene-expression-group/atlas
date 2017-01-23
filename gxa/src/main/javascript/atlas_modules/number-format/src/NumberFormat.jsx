import React from 'react';

const formatBaselineExpression = expressionLevel => {
    const numberExpressionLevel = +expressionLevel;
    return (
        numberExpressionLevel >= 100000 || numberExpressionLevel < 0.1 ?
            formatScientificNotation(expressionLevel, 1) :
            `${numberExpressionLevel}`
    );
};

// expects number in the format #E# and displays exponent in superscript
const formatScientificNotation = (value, accuracy) => {
    const scientificNotationString= (+value).toExponential(accuracy || 4);

    const formatParts = scientificNotationString.split(/[Ee]/);
    if (formatParts.length === 1) {
        return (
            <span>{value}</span>
        );
    }

    const mantissa = formatParts[0].replace(/([^\.])0+$/,"$1");
    const exponent = formatParts[1].replace("+","");
    if (+exponent === 0) {
        return <span>{mantissa}</span>;
    } else {
        return (
            <span>
                {(mantissa !== `1`) ? `${mantissa} × ` : ``}10<span style={{verticalAlign: `super`}}>{exponent}</span>
            </span>
        );
    }
};

export default {
    baselineExpression: formatBaselineExpression,
    scientificNotation: formatScientificNotation
};
