
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

#find atlas_bundles -maxdepth 2 -name '*lock*' | xargs rm
for dir in atlas_bundles/*
do
    pushd . > /dev/null
    echo "Upgrading $dir:"
    cd $dir
    yarn install
    popd > /dev/null
done

yarn install
yarn run $1
