function initHelpTooltip() {
    $("div[data-help-loc]").tooltip();
    $("div[data-help-loc]").bind('mouseover', function () {
        var selectedElement = this;

        $('#tooltip').load('resources/atlas-help.html ' + $(selectedElement).attr('data-help-loc'), function () {

            $(selectedElement).tooltip({content: $('#tooltip').text()});

        });

    });

}