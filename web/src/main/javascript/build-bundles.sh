#!/usr/bin/env bash

if [ $# -ne 1 ] || [[ $1 -ne "ci" && $1 -ne "prod" ]] ; then
        echo "Builds JavaScript modules and their dependencies (images such as PNG and CSS files)"
        echo "into bundles with Webpack and places them in the webapp/resources/js-bundles directory."
        echo
        echo "The script runs in two phases:"
        echo "1. Remove our packages from node_modules and run “npm install”, ensuring the latest"
        echo "   version is installed."
        echo "2. Run “npm run {ci|prod}”, which cleans the target directory and generates the bundle"
        echo "   files. See package.json for the different parameters passed to Webpack."
        echo
        echo "Usage: $0 {ci|prod}"
        exit;
fi

export http_proxy=http://www-proxy.ebi.ac.uk:3128
export https_proxy=$http_proxy

export NVM_DIR="/nfs/ma/home/ma-svc/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm

all_packages=(anatomogram atlas-feedback cell-differential contrast-tooltips display-levels-button heatmap-baseline-cell-variance help-tooltips legend number-format expression-atlas-anatomogram expression-atlas-heatmap expression-atlas-heatmap-highcharts faceted-search)
bundled_packages=(expression-atlas-anatomogram expression-atlas-heatmap expression-atlas-heatmap-highcharts faceted-search)

for dir in ${bundled_packages[*]}
do
    cd $dir
    for module in ${all_packages[*]}
    do
        rm -rf node_modules/$module
    done
    npm install
    cd ..
done

npm install

npm run $1
