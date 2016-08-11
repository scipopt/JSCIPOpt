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
# detect host architecture
#-----------------------------------------------------------------------------
include make/make.detecthost

BASE		=	$(OSTYPE).$(ARCH).$(COMP).$(OPT)
SRCDIR	=	src
OBJDIR	=	obj
LIBDIR	=	lib
JAVAINC	=	$(LIBDIR)/javainc
SCIPINC	=	$(LIBDIR)/scipinc

SCIPOPTLIB	=	scipopt
JNILIB		=	libjni
SCIPJAR		=	scip.jar

#-----------------------------------------------------------------------------
# swig specific parameters
#-----------------------------------------------------------------------------
SWIG     = swig
SCIPJNIINTERFACE	=	scipjni.i

#-----------------------------------------------------------------------------
# Java stettings
#-----------------------------------------------------------------------------
JAVAC		=	javac
JAVAH		=	javah
JAR		=	jar

CLASSDIR	=	classes
JAVASRCDIR	=	java

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
CC		=	gcc
CC_c		=	-c # the trailing space is important
CC_o		=	-o # the trailing space is important
LINKCC		=	gcc
LINKCC_L	=	-L
LINKCC_l	=	-l
LINKCC_o	=	-o # the trailing space is important
LINKRPATH	=	-Wl,-rpath,
SHAREDLIBEXT	=	so

CFLAGS	=	-fpic
LDFLAGS	= --shared
FLAGS		=	-I$(JAVAINC) -I$(SCIPINC)

JAVAC_d	=	-d
JARFLAGS =	cf

#-----------------------------------------------------------------------------
# include additional make files
#-----------------------------------------------------------------------------
-include make/make.$(BASE)

.PHONY: all
all:  swig library jar

# create shared C library
.PHONY: library
library: | $(OBJDIR) $(LIBDIR) $(CLASSDIR)
		@echo "-> compiling $(OBJDIR)/$(JNIOBJ)"
		@$(CC) $(CC_c) $(SRCDIR)/$(JNISRC) $(FLAGS) $(CFLAGS) $(CC_o) $(OBJDIR)/$(JNIOBJ)
		@echo "-> generating library $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)"
		@$(LD) $(LDFLAGS) $(LINKCC_L)$(LIBDIR) -l$(SCIPOPTLIB) $(OBJDIR)/$(JNIOBJ) $(LINKCC_o) $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)
		@echo "-> compiling all java files"
		@$(JAVAC) $(JAVAC_d) $(CLASSDIR) $(JAVASRCDIR)/*.java

# generate jar file containing all class files
.PHONY: jar
jar: | library $(JAVAOBJ)
		@echo "-> generate $(LIBDIR)/$(SCIPJAR)"
		@$(JAR) $(JARFLAGS) $(LIBDIR)/$(SCIPJAR) $(CLASSDIR)/*.class

# generates JNI interface with SWIG
.PHONY: swig
swig:
		@echo "-> generate interface for $(SRCDIR)/$(SCIPJNIINTERFACE)";
		@$(SWIG) -java $(SRCDIR)/$(SCIPJNIINTERFACE)
	   @mv $(SRCDIR)/*.java $(JAVASRCDIR)/

.PHONY: clean
clean: | $(LIBDIR) $(OBJDIR)
		@echo "-> remove library $(LIBDIR)/$(SCIPJAR)"
		@-rm -f $(LIBDIR)/$(SCIPJAR)
		@echo "-> remove library $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)"
		@-rm -f $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)
		@echo "-> remove object file $(OBJDIR)/$(JNIOBJ)"
		@-rm -f $(OBJDIR)/$(JNIOBJ)
		@echo "-> remove class files in ./$(CLASSDIR)"
		@-rm -f $(CLASSDIR)/*.class

$(OBJDIR):
		@-mkdir -p $(OBJDIR)

$(LIBDIR):
		@-mkdir -p $(LIBDIR)

$(CLASSDIR):
		@-mkdir -p $(CLASSDIR)
