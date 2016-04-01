Expression Atlas Heatmap
========================

Heatmap visualization of baseline, proteomics baseline and differential gene expression experiments for [Expression 
Atlas](http://www.ebi.ac.uk/gxa).

It has four basic visualizations:
* Multiexperiment: collapses several baseline tissue experiments into a single table
* Baseline: displays a single baseline expression experiment
* Proteomics baseline: displays a single proteomics baseline expression experiment
* Differential: displays a single differential expression experiment

Visualizations include, where available, an anatomogram to the left of the table.

For more information visit [http://www.ebi.ac.uk/gxa](http://www.ebi.ac.uk/gxa).

Notes for users
===============
* Notes for users: how to point at our bundles
* API (invoking the heatmap with `window.exposed`)

Notes for devlopers
===================
* Decide what stays into `html`. Should we remove the references and demo pages that point at `wwwdev`? 
* Installation instructions (`npm`, `webpack` copying resources from `dist`)
* Testing instructions (`webpack-dev-server`, directory `html`)