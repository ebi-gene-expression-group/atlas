#!/usr/bin/env bash

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

npm install -g react-tools@0.12.2
npm install -g webpack@1.10.1
