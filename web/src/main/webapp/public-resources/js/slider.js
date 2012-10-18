function initSlider(defaultCutoff){

    // The result should be between 0 (but log(0) is NaN) and max FPKM

    var minv = Math.log(0.001);
    var maxv = Math.log(71047.7);

    // position will be between 0 and 100
    var minp = 0;
    var maxp = 100;

    $("#slider-range-max").slider({
        range:"max",
        min:minp,
        max:maxp,

        value:logposition(defaultCutoff),

        slide:function (event, ui) {
            $("#cutoff").val(logslider(ui.value));
        },
        change:function (event, ui) {
            $("form#prefForm").submit();
        }
    });

    function logslider(position) {

        // calculate adjustment factor
        var scale = (maxv - minv) / (maxp - minp);

        return Number(Math.exp(minv + scale * (position - minp))).toFixed(3);
    }

    function logposition(value) {
        var scale = (maxv - minv) / (maxp - minp);
        return (Math.log(value) - minv) / scale + minp;
    }

}