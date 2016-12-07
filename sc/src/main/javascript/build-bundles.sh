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


# export NVM_DIR="/nfs/ma/home/ma-svc/.nvm"
# [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm

atlas_modules=`ls atlas_modules`
atlas_bundles=`ls atlas_bundles`

all_packages=("${sc_modules[@]}" "${sc_bundles[@]}")

for dir in sc_bundles/*
do
    pushd . > /dev/null
    cd $dir
        for module in ${all_packages[*]}
        do
            rm -rf node_modules/sc-atlas-$module
        done
    npm install
    popd > /dev/null
done

for module in ${all_packages[*]}
do
    rm -rf node_modules/sc-atlas-$module
done

npm install

npm run $1
