#!/usr/bin/env bash

if [ $# -ne 1 ] || [[ $1 -ne "ci" && $1 -ne "prod" ]] ; then
        echo "Builds JavaScript modules and their dependencies (images such as PNG and CSS files)"
        echo "into bundles with Webpack and places them in the webapp/resources/js-bundles directory."
        echo
        echo "The script runs in two phases:"
        echo "1. Run “yarn upgrade”, ensuring the packages tagged with \"latest\" are updated."
        echo "2. Run “yarn run {ci|prod}”, which cleans the target directory and generates the bundle"
        echo "   files. See package.json for the different parameters passed to Webpack."
        echo
        echo "Usage: $0 {ci|prod}"
        exit;
fi

for dir in bundles/*
do
    pushd . > /dev/null
    echo "Upgrading $dir:"
    cd $dir
    yarn upgrade
    popd > /dev/null
done

yarn upgrade
yarn run $1
