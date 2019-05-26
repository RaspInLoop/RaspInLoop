#! /usr/bin/env sh

. /build/config.sh

apt-get update && apt-get install -y ${BUILD_PKGS}
