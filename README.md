# Expression Atlas

<img src="https://www.ebi.ac.uk/gxa/resources/images/ExpressionAtlas_logo_web.png" alt="Expression Atlas logo" width="64" height="64" style="vertical-align: bottom">
##### http://www.ebi.ac.uk/gxa/

The Expression Atlas provides information on gene expression patterns under different biological conditions. Gene expression data is re-analysed in-house to detect genes showing interesting baseline and differential expression patterns. [Read more about Expression Atlas](http://www.ebi.ac.uk/gxa/about.html)


### Atlas Widget

Our data can be included as a widget as part of your website.
[Demo](http://www.ebi.ac.uk/gxa/resources/test/widget/highcharts/production/mouse_multiexperiment.html)

##### What you need
You should add the following to your environment:

```
<link rel="stylesheet" type="text/css"
href="http://www.ebi.ac.uk/gxa/resources/css/customized-bootstrap-3.3.5.css"/>
```

If you already use your own flavour of Bootstrap, then you
can remove the styles link tags and the widget will integrate smoothly
in your environment. You could also skip it as we only make a fairly light use of Bootstrap.

Then include our widget and the vendor bundle:
```
<script language="JavaScript" type="text/javascript"
src="http://www.ebi.ac.uk/gxa/resources/js-bundles/vendorCommons.bundle.js"></script>
<script language="JavaScript" type="text/javascript"
src="http://www.ebi.ac.uk/gxa/resources/js-bundles/expressionAtlasHeatmapHighcharts.bundle.js"></script>
```

You could also build us from source - we use webpack/npm.

Tell us about any problems by raising an issue in this repository.

##### Invoking the widget

You need to call the render method on the exposed global variable:
```
expressionAtlasHeatmapHighcharts.render({
    params: "geneQuery=ASPM&species=mus%20musculus",
    isMultiExperiment: true,
    target: "heatmapContainer"
});
```

At the time of writing the docs are over the code: [here](https://github.com/gxa/atlas/blob/master/web/src/main/javascript/expression-atlas-heatmap-highcharts/src/highchartsHeatmapRenderer.js)
