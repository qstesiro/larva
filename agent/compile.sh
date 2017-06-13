#!/bin/sh

make

rm -rf ./bin
mkdir bin

if [ -f "libagent.so" ]; then  
   cp libagent.so ./bin
   cp libevent/lib/*.so ./bin   
fi  

make clean

