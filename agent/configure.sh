#!/bin/sh
# $1 is the directy name of libevent
# ./configure.sh libevent-2.1.8-stable

CUR_PATH=$(pwd)

cd ./$1
./configure -prefix=$CUR_PATH/libevent
make clean
make 
make install
cd ../

