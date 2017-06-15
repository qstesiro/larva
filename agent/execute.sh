#!/bin/sh

make clean
make
if [ -f "libagent.so" ]; then  
   ./java/java.sh
fi

