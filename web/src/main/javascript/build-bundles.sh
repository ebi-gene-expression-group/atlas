#!/usr/bin/env bash

export http_proxy=http://www-proxy.ebi.ac.uk:3128
export https_proxy=$http_proxy

export NVM_DIR="/nfs/ma/home/ma-svc/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm

all_packages=(anatomogram cell-differential display-levels-button faceted-search heatmap-anatomogram heatmap-baseline-cell-variance help-tooltips legend number-format)
bundled_packages=(anatomogram faceted-search heatmap-anatomogram)

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

# rm -rf node_modules
# npm install

npm run $1
