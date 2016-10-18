"use strict";

//*------------------------------------------------------------------*

module.exports = {
    BASELINE: { isBaseline: true, heatmapTooltip: "#heatMapTableCellInfo-baseline" },
    PROTEOMICS_BASELINE: { isBaseline: true, isProteomics: true, heatmapTooltip: "#heatMapTableCellInfo-proteomics" },
    DIFFERENTIAL: { isDifferential: true, heatmapTooltip: "#heatMapTableCellInfo-differential" },
    MULTIEXPERIMENT: { isMultiExperiment: true, heatmapTooltip: "#heatMapTableCellInfo-multiexperiment" }
};
