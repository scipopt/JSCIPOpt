#@file    CMakeLists.txt
#@brief   CMake file for Java interface for the SCIP Optimization Suite
#@author  Robert Gottwald, Benjamin Mueller

cmake_minimum_required (VERSION 2.8)
project (JSCIPOpt)

# per default compile a release version
if (NOT CMAKE_BUILD_TYPE)
  set (CMAKE_BUILD_TYPE "Release")
endif()

#
# find JNI and java
#
find_package (JNI REQUIRED)
find_package (Java REQUIRED)
include (UseJava)

#
# list of all java sourcse
#
set(SWIG_JAVA_SOURCES
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIPJNI.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIPJNIJNI.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Retcode.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Vartype.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_ParamEmphasis.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_double.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_FILE.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP_SOL.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP_VAR.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_CONS.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_SOL.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_VAR.java
  )

set(JAVA_SOURCES
  ${SWIG_JAVA_SOURCES}
  ${PROJECT_SOURCE_DIR}/java/jscip/Constraint.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Scip.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Variable.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Constraint.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Solution.java
  )

#
# find SWIG
#
find_package (SWIG)
if (SWIG_FOUND)
  add_custom_command(OUTPUT ${PROJECT_SOURCE_DIR}/src/scipjni_wrap.c ${SWIG_JAVA_SOURCES}
    COMMAND ${SWIG_EXECUTABLE} -package jscip -java ${PROJECT_SOURCE_DIR}/src/scipjni.i
    COMMAND mv ${PROJECT_SOURCE_DIR}/src/*.java ${PROJECT_SOURCE_DIR}/java/jscip/
    DEPENDS ${PROJECT_SOURCE_DIR}/src/scipjni.i)
endif()

#
# find SCIP library and add include directories
#
find_package(SCIP REQUIRED CONFIG)
include_directories (${JNI_INCLUDE_DIRS} ${SCIP_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/src)

#
# link directories
#
link_directories (${PROJECT_SOURCE_DIR}/lib)

#
# build JSCIPOpt library
#
add_library (jscip SHARED ${PROJECT_SOURCE_DIR}/src/scipjni_wrap.c)
set_target_properties(jscip PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${PROJECT_BINARY_DIR}/${CMAKE_BUILD_TYPE})

#
# add dependencies of other libraries
#
target_link_libraries (jscip ${SCIP_LIBRARIES} ${JNI_LIBRARIES})

#
# generate jar
#
add_jar (scipjar
        SOURCES ${JAVA_SOURCES}
        OUTPUT_NAME scip
        OUTPUT_DIR ${PROJECT_BINARY_DIR}/${CMAKE_BUILD_TYPE}
        )

#
# generate jar containing all examples
#
FILE (GLOB example_sources ${PROJECT_SOURCE_DIR}/examples/*.java)

add_jar (examples
        SOURCES ${example_sources}
        INCLUDE_JARS scipjar
        OUTPUT_DIR ${PROJECT_BINARY_DIR}/${CMAKE_BUILD_TYPE}
        )
