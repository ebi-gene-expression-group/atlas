const React = require(`react`);
const validate = require(`react-prop-types-check`);


const PointPropType = React.PropTypes.shape({
    x: React.PropTypes.number.isRequired,
    y: React.PropTypes.number.isRequired,
    value: React.PropTypes.number.isRequired,
    info: React.PropTypes.object.isRequired
});


const PointsInDataSeriesPropType = React.PropTypes.arrayOf(
    React.PropTypes.arrayOf(PointPropType)
);


const DataSeriesProps = {
    info: React.PropTypes.shape({
        colour: React.PropTypes.string.isRequired,
        name: React.PropTypes.string.isRequired
    }),
    data: React.PropTypes.arrayOf(PointPropType).isRequired
};


const ValidateDataSeries = dataSeries => {
    dataSeries.forEach(series => {
        validate(series, DataSeriesProps);
    });
};


const AxisCategoriesPropType =
    React.PropTypes.arrayOf(
        React.PropTypes.shape({
            id: React.PropTypes.string, // ontology ID can be missing for x axis
            label: React.PropTypes.string.isRequired,
            info: React.PropTypes.shape({
                trackId:React.PropTypes.string,
                tooltip: React.PropTypes.object
            }).isRequired
        })
    ).isRequired;


const OrderingsPropType = (props, propName) => {
  const orderings = props[propName];

  const isPermutation = (arr) =>
      [].concat(arr)
      .sort((a, b) => a - b)
      .map((el, ix) => el === ix)
      .reduce((l, r) => l && r);

  if (!orderings.hasOwnProperty(`Default`)) {
      return new Error(`Default ordering missing`);
  }

  Object.keys(orderings).forEach(orderingName => {
      const ordering = orderings[orderingName];

      if (!isPermutation(ordering.columns)) {
          return new Error(`Column ordering invalid in ${orderingName}`);
      }
      if (!isPermutation(ordering.rows)) {
          return new Error(`Row ordering invalid in ${orderingName}`);
      }
  });
};


const HeatmapDataPropType = (props, propName) => {
    const heatmapData = props[propName];
    const possiblyError = ValidateDataSeries(heatmapData.dataSeries);
    if (possiblyError !== undefined) {
        return possiblyError;
    }

    const width = heatmapData.xAxisCategories.length;
    const height = heatmapData.yAxisCategories.length;

    for (let i = 0; i < heatmapData.dataSeries.length; i++) {
        for (let j = 0; j < heatmapData.dataSeries[i].data.length; j++) {
            const point = heatmapData.dataSeries[i].data[j];
            const x = point.x;
            const y = point.y;
            if (x < 0 || y < 0 || x >= width || y >= height) {
                return new Error(`Point with coordinates outside range: ${x}, ${y}`);
            }
        }
    }
};


const LoadResultPropType = React.PropTypes.shape({
    heatmapConfig: React.PropTypes.object.isRequired,
    colorAxis : React.PropTypes.object,
    orderings: OrderingsPropType,
    heatmapData : HeatmapDataPropType
});


const FormatterPropType = (props, propName) => {
    const f = props[propName];
    if (f === undefined) {
        return new Error(`${propName} formatter missing`);
    } else if (typeof f !== `function` || f.name !== `Formatter`) {
        return new Error(`${propName} formatter not correctly created. See the main method of TooltipFormatter.jsx.`);
    }
};


const OrderingsPropTypes = React.PropTypes.shape({
    available: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
    selected: React.PropTypes.string.isRequired,
}).isRequired;

const FilterPropTypes = React.PropTypes.arrayOf(React.PropTypes.shape({
        name: React.PropTypes.string.isRequired,
        values: React.PropTypes.arrayOf(React.PropTypes.string).isRequired
    })
).isRequired;

const FilterSelectionPropTypes = React.PropTypes.shape({
    name: React.PropTypes.string.isRequired,
    selected: React.PropTypes.arrayOf(React.PropTypes.bool).isRequired
}).isRequired;

module.exports = {
    validateDataSeries : ValidateDataSeries,
    PointsInDataSeries : PointsInDataSeriesPropType,
    Point: PointPropType,
    HeatmapData : HeatmapDataPropType,
    LoadResult: LoadResultPropType,
    AxisCategories : AxisCategoriesPropType,
    Formatter : FormatterPropType,
    Orderings: OrderingsPropTypes,
    Filter: FilterPropTypes,
    FilterSelection: FilterSelectionPropTypes
};
