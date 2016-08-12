#!/bin/bash
PATH=$PATH:../lib LD_LIBRARY_PATH=$LD_LIBRARY_PATH:../lib java -cp ".:../lib/scip.jar" ${@:2,1}
