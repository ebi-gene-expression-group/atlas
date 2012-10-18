function initHeatmapDisplayValueToggle(){

    $("#display-levels").button()
               .toggle(function() {
                            $(this).button('option', 'label','display colours');
                            $("div[data-color]").parent('td').each(function(){
                                //this.style["background-color"]="white";
                                this.style["font-size"]="9px";
                                $(this).find('div').attr('style','background-color:white;margin:4px;padding:2px;');
                            });
                        },
                        function() {
                            $(this).button('option', 'label','display levels');
                            $("div[data-color]").parent('td').each(function(){
                                var dataColor = $(this).find('div').attr("data-color");
                                //this.style["background-color"]=dataColor;
                                this.style["font-size"]="1px";
                                $(this).find('div').attr('style','');
                            });
                        });

}