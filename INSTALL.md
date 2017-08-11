Building JSCIPOpt on Linux
==========================

You need to download the following software packages to use/extend the Java interface:

 - SCIP Optimization Suite
 - Java JDK
 - C compiler
 - CMake
 - SWIG (optional)

The following steps need to be done before compiling the Java interface.

1) Create a shared library of the [SCIP Optimization Suite](http://scip.zib.de/#download) by executing

 - mkdir build
 - cd build
 - cmake ..
 - make

in the SCIP Optimization Suite directory. This should have created all necessary libraries.

2a) Building JSCIPOpt on Linux.

Compile the interface by executing the following commands:

 - mkdir build
 - cd build
 - cmake .. -DSCIP_DIR=<absolute path to SCIP Optimization Suite>/build
 - make

Execute the examples via
 - java -cp scip.jar:examples.jar <"Linear" or "Quadratic" or "Read">

2b) Building JSCIPOpt on Windows.

Compile the interface by executing the following commands:

 - mkdir build
 - cd build
 - cmake .. -G "Visual Studio 14 2015 Win64" -DSCIP_DIR=<absolute path to SCIP Optimization Suite>/build
 - cmake --build . --config <"Release" or "Debug">

Execute the examples via

 - export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./
 - PATH=$PATH:../lib:./<"Release" or "Debug"> java -cp "scip.jar;examples.jar" <"Linear" or "Quadratic" or "Read">
