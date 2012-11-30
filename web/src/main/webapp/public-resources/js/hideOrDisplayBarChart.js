function hideOrDisplayBarChart(){

    function hideGeneDistribution(img, isFast) {
        $('#gene-distribution').hide(isFast ? null : 'slow');
        $("#display-chart").tooltip({content:"Display gene distribution"});
    }

    function displayGeneDistribution(img, isFast) {
        $('#gene-distribution').show(isFast ? null : 'slow');
        $("#display-chart").tooltip({content:"Hide gene distribution"});
    }

    function hideOrDisplayGeneDistribution(isFast) {
        var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();
        if (isDisplayEnabled == "true") {
            displayGeneDistribution(this, isFast);
        } else {
            hideGeneDistribution(this, isFast);
        }

    }

    $("#chart-button").button().click(function () {

        var isDisplayEnabled = $("#prefForm #displayGeneDistribution").val();
        if (isDisplayEnabled == "true") {
            $("#prefForm #displayGeneDistribution").val("false");
        } else {
            $("#prefForm #displayGeneDistribution").val("true");
        }

        hideOrDisplayGeneDistribution(false);

        return false;
    }).tooltip();

    hideOrDisplayGeneDistribution(true);

}