#!/bin/bash

source "bin/init/env.sh"

git submodule update --init 'openvpn/*'
rm -rf openvpn/build/outputs/aar
./gradlew openvpn:assembleRelease || exit 1
mkdir -p app/libs
cp openvpn/build/outputs/aar/* app/libs
