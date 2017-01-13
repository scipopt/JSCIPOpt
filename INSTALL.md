==========================
Building JSCIPOpt on Linux
==========================

You need to download the following software packages to use/extend the Java interface:

 - SCIP optimization suite
 - Java JDK
 - C compiler
 - SWIG (optional)

The following steps need to be done before compiling the Java interface.

1) Create a shared library of the [SCIP optimization suite](http://scip.zib.de/#download) by executing

    make SHARED=true scipoptlib

in the SCIP optimization suite directory.
Afterwards, you will find the library (*.so) in the ./lib directory of the optimization suite.

2) Create a symbolic link in JSCIPOpt to the library compiled in 1), to the include directory of Java JDK and to the source directory of
SCIP (in the optimization suite)

    mkdir -p lib;
    cd lib;
    ln -s <Java JDK include directory> javainc
    ln -s <SCIP source directory> scipinc
    ln -s <SCIP opt suite directory>/lib/<scip opt library> libscipopt.so

3a) This step is only necessary if you have modified src/scipjni.i. If this is the case call

    make swig SWIG=<path to the SWIG binary>

3b) Call 'make' in the main directory of JSCIPOpt. You can also specify some additional flags used during compilation
and linking of the C library with

    make USRCFLAGS=... USRLDFLAGS=...

where

    USRCFLAGS   = flags passed when compiling src/scipjni_wrap.c
    USRLDFLAGS  = flags passed when generating the C library


The previous steps should have created a shared library libjscip.so and a tar archive scip.jar in the lib directory of
JSCIPOpt.


================================
Compiling and using the examples
================================

After building the Java interface the compilation of the examples works as follows.

1) Compile all examples via

    cd examples
    javac -cp ../lib/scip.jar *.java

2) Run the examples with the run.sh script, e.g.,

    ./run.sh Read <instance file> <settings file (optional)>
    ./run.sh Linear
    ./run.sh Quadratic


============================
Building JSCIPOpt on Windows
============================

We recommend to use the latest Visual Studio compiler in MinGW to build the Java interface. The following steps are
similar to the above described steps but contain some necessary changes.

1) Use the `make dll` target in SCIP (not the SCIP optimization suite) to create a shared library. This library can also
be downloaded from <http://scip.zib.de>. Put the library (containing a `*.lib` and `*.dll` file) in the lib directory of JSCIPOpt.

**NOTE: Do not modify the name of the library!**


2) The same as above but instead of `ln -s` use `cp -r`.

3a) The same as above.

3b) The make command changes to

    make COMP=msvc ARCH={x86,x86_64} SCIPOPTLIB=<library from step 1>

On a 64bit system this command should look like

    make COMP=msvc ARCH=x86_64 SCIPOPTLIB=libscip-3.2.1.mingw.x86_64.msvc.opt.spx.lib


NOTE: To run one of the examples you need to change the line

    System.loadLibrary("jscip");

to

    System.loadLibrary("libjscip");


============================
Building JSCIPOpt with CMake
============================

--------
 Linux
--------
   mkdir build
   cd build
   cmake ..
   cmake --build . --config <Release or Debug>
   java -cp scip.jar:examples.jar <"Linear" or "Quadratic" or "Read">

---------
 WINDOWS
---------
   mkdir build
   cd build
   cmake .. -G "Visual Studio 14 2015 Win64"
   cmake --build . --config <"Release" or "Debug">
   PATH=$PATH:../lib:./<"Release" or "Debug"> java -cp "scip.jar;examples.jar" <"Linear" or "Quadratic" or "Read">