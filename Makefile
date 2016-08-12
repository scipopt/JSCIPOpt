#* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
#*                                                                           *
#*                  This file is part of the program and library             *
#*         SCIP --- Solving Constraint Integer Programs                      *
#*                                                                           *
#*    Copyright (C) 2002-2016 Konrad-Zuse-Zentrum                            *
#*                            fuer Informationstechnik Berlin                *
#*                                                                           *
#*  SCIP is distributed under the terms of the ZIB Academic Licence.         *
#*                                                                           *
#*  You should have received a copy of the ZIB Academic License              *
#*  along with SCIP; see the file COPYING. If not email to scip@zib.de.      *
#*                                                                           *
#* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

#@file    Makefile
#@brief   Makefile for Java Native Interface
#@author  Benjamin Mueller

#-----------------------------------------------------------------------------
# load default settings and detect host architecture
#-----------------------------------------------------------------------------
include make/make.project

JAVAINC	=	$(LIBDIR)/javainc
SCIPINC	=	$(LIBDIR)/scipinc

SCIPOPTLIB	=	scipopt
JNILIB		=	libjni
SCIPJAR		=	scip.jar
PACKAGENAME	=	jscip

#-----------------------------------------------------------------------------
# swig specific parameters
#-----------------------------------------------------------------------------
SWIG     = swig
SWIGFLAGS = -package $(PACKAGENAME) -java
SWIGSRC	=	scipjni.i

#-----------------------------------------------------------------------------
# Java stettings
#-----------------------------------------------------------------------------
JAVAC		=	javac
JAVAH		=	javah
JAR		=	jar

CLASSDIR	=	classes
JAVASRCDIR	=	java/$(PACKAGENAME)

#-----------------------------------------------------------------------------
# Force certain variable to specific values
#-----------------------------------------------------------------------------
override SHARED	=	true

#-----------------------------------------------------------------------------
# SCIP JNI Library
#-----------------------------------------------------------------------------
JNISRC	=	scipjni_wrap.c
JNIOBJ	=	$(JNISRC:.c=.o)

#-----------------------------------------------------------------------------
# compiler and linker parameters
#-----------------------------------------------------------------------------
CFLAGS	+=	-fpic
FLAGS		+=	-I$(JAVAINC) -I$(SCIPINC)
LDFLAGS	= --shared

JAVAC_d	=	-d
JARFLAGS =	cf
JARSRCFILES	=	$(subst $(CLASSDIR)/, -C $(CLASSDIR) ,$(shell find $(CLASSDIR)/$(PACKAGENAME) -name '*.class'))

#-----------------------------------------------------------------------------
# include additional make files
#-----------------------------------------------------------------------------
-include make.$(BASE)

.PHONY: all
all:  library jar

ifeq ($(VERBOSE),false)
.SILENT:	library jar swig clean
MAKE		+= -s
endif

# create shared C library
.PHONY: library
library: | $(OBJDIR) $(LIBDIR) $(CLASSDIR)
		@echo "-> compiling $(OBJDIR)/$(JNIOBJ)"
		$(CC) $(CC_c) $(SRCDIR)/$(JNISRC) $(FLAGS) $(CFLAGS) $(CC_o) $(OBJDIR)/$(JNIOBJ)
		@echo "-> generating library $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)"
		$(LD) $(LDFLAGS) $(LINKCC_L)$(LIBDIR) -l$(SCIPOPTLIB) $(OBJDIR)/$(JNIOBJ) $(LINKCC_o) $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)
		@echo "-> compiling all java files"
		$(JAVAC) $(JAVAC_d) $(CLASSDIR) $(JAVASRCDIR)/*.java

# generate jar file containing all class files
.PHONY: jar
jar: | library $(JAVAOBJ)
		@echo "-> generate $(LIBDIR)/$(SCIPJAR)"
		$(JAR) $(JARFLAGS) $(LIBDIR)/$(SCIPJAR) $(JARSRCFILES)

# generates JNI interface with SWIG
.PHONY: swig
swig:
		@echo "-> generate interface for $(SRCDIR)/$(SWIGSRC)";
		$(SWIG) $(SWIGFLAGS) $(SRCDIR)/$(SWIGSRC)
	   mv $(SRCDIR)/*.java $(JAVASRCDIR)/

.PHONY: clean
clean: | $(LIBDIR) $(OBJDIR)
		@echo "-> remove library $(LIBDIR)/$(SCIPJAR)"
		-rm -f $(LIBDIR)/$(SCIPJAR)
		@echo "-> remove library $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)"
		-rm -f $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)
		@echo "-> remove object file $(OBJDIR)/$(JNIOBJ)"
		-rm -f $(OBJDIR)/$(JNIOBJ)
		@echo "-> remove class files in ./$(CLASSDIR)"
		-rm -rf $(CLASSDIR)/*

$(OBJDIR):
		@-mkdir -p $(OBJDIR)

$(LIBDIR):
		@-mkdir -p $(LIBDIR)

$(CLASSDIR):
		@-mkdir -p $(CLASSDIR)
