#! /usr/bin/env sh

. /build/config.sh

apt-get remove --purge -y ${BUILD_PKGS} $(apt-mark showauto)

apt-get install -y ${RUNTIME_PKGS}

rm -rf /var/lib/apt/lists/*
