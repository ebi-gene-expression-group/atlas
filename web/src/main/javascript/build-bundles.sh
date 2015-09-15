#!/usr/bin/env bash

export NVM_DIR="/nfs/ma/home/ma-svc/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm

mkdir -p ../node_modules

for dir in `ls -d */`
do
    cd $dir
    rm -rf ./node_modules
    ln -sf ../../node_modules ./node_modules
    npm install
    cd ..
done

rm -rf ./node_modules
ln -sf ../node_modules ./node_modules
npm install

npm run $1
