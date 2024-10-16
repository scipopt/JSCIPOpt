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
find_package (JNI REQUIRED JVM)
find_package (Java REQUIRED)
include (UseJava)

#
# list of all java sources
#
set(SWIG_JAVA_SOURCES
  ${PROJECT_SOURCE_DIR}/java/jscip/ObjMessagehdlr.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIPJNI.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIPJNIJNI.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Retcode.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Vartype.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_ParamEmphasis.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Objsense.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_VerbLevel.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Status.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SCIP_Stage.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_char.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_double.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_int.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_long_long.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_unsigned_int.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_FILE.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_char.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP_SOL.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_p_SCIP_VAR.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_CONS.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_SOL.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_VAR.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_HEUR.java
  ${PROJECT_SOURCE_DIR}/java/jscip/SWIGTYPE_p_SCIP_Messagehdlr.java
  )

set(JAVA_SOURCES
  ${SWIG_JAVA_SOURCES}
  ${PROJECT_SOURCE_DIR}/java/jscip/Constraint.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Scip.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Variable.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Constraint.java
  ${PROJECT_SOURCE_DIR}/java/jscip/Solution.java
  ${PROJECT_SOURCE_DIR}/java/jscip/MessageHandler.java
  )

#
# find SWIG
#
find_package (SWIG)
if (SWIG_FOUND)
  add_custom_command(OUTPUT ${PROJECT_SOURCE_DIR}/src/scipjni_wrap.cxx ${SWIG_JAVA_SOURCES}
    COMMAND ${SWIG_EXECUTABLE} -package jscip -c++ -java -outdir ${PROJECT_SOURCE_DIR}/java/jscip/ ${PROJECT_SOURCE_DIR}/src/scipjni.i
    DEPENDS ${PROJECT_SOURCE_DIR}/src/scipjni.i)
endif()

#
# find SCIP library and add include directories
#
if (SCIP_DIR)
  # The user explicitly passed a SCIP_DIR, so only look there and not in some
  # random other path that may contain a binary-incompatible version.
  find_package(SCIP "8.0.0" REQUIRED CONFIG NO_DEFAULT_PATH PATHS ${SCIP_DIR})
else()
  find_package(SCIP "8.0.0" REQUIRED CONFIG)
endif()
include_directories (${JNI_INCLUDE_DIRS} ${SCIP_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/src)

#
# check whether SCIP is a static library (in which case it is safe to use macros)
#
get_target_property(LIBSCIP_TARGET_TYPE libscip TYPE)
message("libscip is a ${LIBSCIP_TARGET_TYPE}.")
if (LIBSCIP_TARGET_TYPE STREQUAL STATIC_LIBRARY)
  add_definitions(-DHAVE_STATIC_LIBSCIP=1)
endif()

#
# link directories
#
link_directories (${PROJECT_SOURCE_DIR}/lib)

#
# build JSCIPOpt library
#
add_library (jscip SHARED ${PROJECT_SOURCE_DIR}/src/scipjni_wrap.cxx)
set_target_properties(jscip PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${PROJECT_BINARY_DIR}/${CMAKE_BUILD_TYPE})

#
# add dependencies of other libraries
#
target_link_libraries (jscip ${SCIP_LIBRARIES})

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
