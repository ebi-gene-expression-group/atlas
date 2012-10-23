function initSearchForm(homePageURL) {

    $(".chzn-select").chosen();

    $("#submit-button").button();

    $("#reset-button").button().click(function (event) {
        location.replace(homePageURL);
    })

}