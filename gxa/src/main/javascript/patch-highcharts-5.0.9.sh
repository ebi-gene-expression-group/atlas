#!/usr/bin/env bash

# Replace the line:
# attr(tspan, 'onclick', 'location.href=\"' + span.match(hrefRegex)[1] + '\"');
# with the line:
# attr(tspan, 'onclick', 'onclick=window.open(\"'+ span.match(hrefRegex)[1] +'\",\"_blank\")');
#
# This makes Highcharts open links in the Y axis labels in new tabs.
# A backup copy of highcharts.src.js and highcharts.js is kept with the .bak extension

if [ -d "node_modules/highcharts" ] ; then
    cp node_modules/highcharts/js/highcharts.js node_modules/highcharts/js/highcharts.js.bak
    cp node_modules/highcharts/highcharts.js node_modules/highcharts/highcharts.js.bak
    sed -i .bak 's/attr(tspan, '\''onclick'\'',.*/attr(tspan, '\''onclick'\'', '\''onclick=window.open(\\"'\''+ span.match(hrefRegex)[1] +'\''\\",\\"_blank\\")'\'');/g' node_modules/highcharts/js/highcharts.src.js
    sed -i .bak 's/attr(tspan, '\''onclick'\'',.*/attr(tspan, '\''onclick'\'', '\''onclick=window.open(\\"'\''+ span.match(hrefRegex)[1] +'\''\\",\\"_blank\\")'\'');/g' node_modules/highcharts/highcharts.src.js
    cp node_modules/highcharts/js/highcharts.src.js node_modules/highcharts/js/highcharts.js
    cp node_modules/highcharts/highcharts.src.js node_modules/highcharts/highcharts.js
fi

