#! /usr/bin/env sh

git clone https://openmodelica.org/git-readonly/OpenModelica.git /build/openmodelica || exit 1
cd /build/openmodelica
git checkout tags/v1.11.0 && git submodule update --init --recursive common libraries doc OMCompiler OMShell || exit 1
autoconf && ./configure --prefix=/usr --disable-modelica3d --with-omniORB=/usr && make && make install && pip install OMPython
