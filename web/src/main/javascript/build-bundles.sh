#!/usr/bin/env bash

export http_proxy=http://www-proxy.ebi.ac.uk:3128
export https_proxy=$http_proxy

export NVM_DIR="/nfs/ma/home/ma-svc/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm

mkdir -p ../node_modules

for dir in `ls -d */`
do
    if [[ $dir != node_modules/ ]]
    then
        cd $dir
        rm -rf ./node_modules
        ln -sf ../../node_modules ./node_modules
        npm install
        cd ..
    fi
done

rm -rf ./node_modules
ln -sf ../node_modules ./node_modules
npm install

npm run $1
