function initHeatmapDisplayValueToggle(){

    function showValues(button){
        $(button).button('option','label','hide levels');
        $("div[data-color]").each(function(){
            $(this).attr('style','font-size:9px;background-color:white;margin:4px;padding:2px;');
        });
        $(".gradient-level").attr("style",'');
    }

    function hideValues(button){
        $(button).button('option','label','display levels');
        $("div[data-color]").each(function(){
            $(this).attr('style','font-size:1px');
        });
        $(".gradient-level").attr("style",'color:white');
    }

    $("#display-levels").button()
               .toggle(function() {
                            showValues(this);
                            $("#prefForm #displayLevels").val("true");
                        },
                        function() {
                            hideValues(this);
                            $("#prefForm #displayLevels").val("false");
                        });

    if ($("#prefForm #displayLevels").val() == "true"){
        $("#display-levels").click();
    }


    $("#heatmap-table td:has(div[data-color])").toggle(function() {
            $(this).find("div").attr('style','font-size:9px;background-color:white;margin:4px;padding:2px;');
        },
        function() {
            $(this).find("div").attr('style','font-size:1px');

        });

    $('#download-profiles').tooltip({content: "Download query results"});



}