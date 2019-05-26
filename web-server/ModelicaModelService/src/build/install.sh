#! /usr/bin/env sh
apt update
apt install -y wget lsb-release
for deb in deb deb-src; do echo "$deb http://build.openmodelica.org/apt `lsb_release -cs` nightly"; done | tee /etc/apt/sources.list.d/openmodelica.list
wget -q http://build.openmodelica.org/apt/openmodelica.asc -O- | apt-key add - 
apt update
apt install -y default-jre-headless
# apt install -y openmodelica
# apt install -y omlib-.*