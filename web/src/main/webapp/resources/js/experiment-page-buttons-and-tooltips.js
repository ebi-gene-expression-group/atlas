function initExperimentPageButtonsAndTooltips() {

    $('#display-ae').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#display-analysis-methods').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#display-experiment-design').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#display-experiment').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#display-qc-report').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#display-fastqc-report').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#clustering-pdf').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-raw')) $('#download-raw').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-normalized')) $('#download-normalized').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-logFold')) $('#download-logFold').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-analytics')) $('#download-analytics').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-expressions')) $('#download-expressions').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    if (undefined !== $('#download-expressions-modal')) $('#download-expressions-modal').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#download-r').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#download-r-modal').button().tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#goto-ae').tooltip({tooltipClass: "gxaHelpTooltip"});
    $('#goto-experiment').tooltip({tooltipClass: "gxaHelpTooltip"});
    $("#goto-experiment-name").tooltip({tooltipClass: "gxaHelpTooltip"});
    $('.array-design').tooltip({tooltipClass: "gxaHelpTooltip"});
    $('.pubmed-id').tooltip({tooltipClass: "gxaHelpTooltip"});
}