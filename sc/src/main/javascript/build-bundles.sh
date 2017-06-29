#!/usr/bin/env bash

if [ $# -ne 1 ] || [[ $1 -ne "ci" && $1 -ne "prod" ]] ; then
        echo "Bundles ES packages with Webpack and places them in the webapp/resources/js-bundles and"
        echo "webapp/versioned-resources/js-bundles directories."
        echo
        echo "The script runs in two phases:"
        echo "1. Runs “npm update” in each bundle to install the latest version of our published packages."
        echo "2. Runs “npm run {ci|prod}”, which cleans the target directory and generates the bundles."
        echo "   See webpack.config.js for the different parameters passed to Webpack."
        echo
        echo "Usage: $0 {ci|prod}"
        exit;
fi

for dir in bundles/*
do
    pushd . > /dev/null
    cd $dir
    npm update
    popd > /dev/null
done

npm install
npm run $1
