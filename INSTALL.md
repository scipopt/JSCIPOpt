Building JSCIPOpt on Linux
==========================


You need to download the following software packages to use/extend the Java interface:

 - SCIP optimization suite
 - Java JDK
 - C compiler
 - CMake
 - SWIG (optional)

The following steps need to be done before compiling the Java interface.

1) Create a shared library of the [SCIP optimization suite](http://scip.zib.de/#download) by executing

    make SHARED=true scipoptlib

in the SCIP optimization suite directory. Afterwards, you will find the library (*.so) in the ./lib directory of the
optimization suite.

2) Create a symbolic link in JSCIPOpt to the library compiled in 1) and to the source directory of SCIP (in the
optimization suite)

    mkdir -p lib;
    cd lib;
    ln -s <SCIP source directory> scipinc
    ln -s <SCIP opt suite directory>/lib/<scip opt library> libscipopt.so

3a) Building JSCIPOpt on Linux.

Compile the interface by executing the following commands:

 - mkdir build
 - cd build
 - cmake .. [-DCMAKE_BUILD_TYPE=<"Debug" or "Release", default: "Release">]
 - make

Execute the examples via

 - export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./
 - java -cp scip.jar:examples.jar <"Linear" or "Quadratic" or "Read">

3b) Building JSCIPOpt on Windows.

Compile the interface by executing the following commands:

 - mkdir build
 - cd build
 - cmake .. -G "Visual Studio 14 2015 Win64"
 - cmake --build . --config <"Release" or "Debug">

Execute the examples via

 - export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./
 - PATH=$PATH:../lib:./<"Release" or "Debug"> java -cp "scip.jar;examples.jar" <"Linear" or "Quadratic" or "Read">
