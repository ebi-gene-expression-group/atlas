// The algorithm is described here: https://www.pivotaltracker.com/story/show/133523275/comments/154718111

const validateDataSeries = require('../PropTypes.js').validateDataSeries;
const Colour = require("color");

const highlightColour = c =>
    c.light() ?
        c.clone().lighten(0.5) :
        c.clone().saturate(0.3).darken(0.5);

const dataClassesFromSeries = dataSeries => {
    validateDataSeries(dataSeries);
    let xs =
        dataSeries
            .map(series =>
                series.data.length === 0 && series.info.name === "Below cutoff" ?
                    ({
                        data: [{value: 0.0}],
                        colour: series.info.colour
                    }) :
                    ({
                        data: series.data,
                        colour: series.info.colour
                    })
            )
            .filter(series => series.data.length>0)
            .map((series,ix,self) => {
                const theseSeriesValuesSorted = series.data.map(point => point.value);
                theseSeriesValuesSorted.sort((l,r) => l-r);

                return {
                    min: theseSeriesValuesSorted[0],
                    minColour: ix === 0 ? highlightColour(Colour(self[ix].colour)): Colour(self[ix].colour).mix(Colour(self[ix-1].colour)),
                    max: theseSeriesValuesSorted[theseSeriesValuesSorted.length-1],
                    maxColour: ix === self.length-1 ? highlightColour(Colour(self[ix].colour)): Colour(self[ix].colour).mix(Colour(self[ix+1].colour)),
                    median: theseSeriesValuesSorted[Math.floor(series.data.length/2)],
                    medianColour: Colour(self[ix].colour),
                    sortedValues: theseSeriesValuesSorted
                }
            });

    const needToSplit = x =>
        x.sortedValues.length > 3 &&
        x.sortedValues[0] !== x.sortedValues[x.sortedValues.length-1] &&
        x.minColour.rgbString() !== x.maxColour.rgbString();

    const splitInHalf = x =>
        [
            {
                min:x.min,
                minColour: x.minColour,
                max:x.median,
                maxColour: x.medianColour,
                median: x.sortedValues[Math.floor(x.sortedValues.length/4)],
                medianColour: x.minColour.clone().mix(x.medianColour),
                sortedValues: x.sortedValues.slice(0, Math.floor(x.sortedValues.length/2))
            },
            {
                min:x.median,
                minColour: x.medianColour,
                max:x.max,
                maxColour: x.maxColour,
                median: x.sortedValues[Math.floor(3* x.sortedValues.length/4)],
                medianColour: x.medianColour.clone().mix(x.maxColour),
                sortedValues: x.sortedValues.slice(Math.floor(x.sortedValues.length/2))
            }
        ];

    let l = Number.MIN_VALUE;
    let L = xs.length;
    while (l<L) {
        xs = xs.reduce((acc, x) => {
            if(needToSplit(x)){
                return acc.concat(splitInHalf(x));
            } else {
                return acc.concat(x);
            }
        }, []);

        l = L;
        L = xs.length;
    }

    return xs.map(x => ({
        from: x.min,
        to: x.max,
        color: x.medianColour.hexString()
    }))
};

const getColorAxisFromDataSeries = (config, dataSeries) =>
  (config.isExperimentPage ?
      {
          dataClasses: dataClassesFromSeries(dataSeries)
      } :
      null
  );

module.exports = getColorAxisFromDataSeries;
