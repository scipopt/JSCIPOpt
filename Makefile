#@file    Makefile
#@brief   Makefile for Java interface for the SCIP Optimization Suite
#@author  Benjamin Mueller

#-----------------------------------------------------------------------------
# load default settings and detect host architecture
#-----------------------------------------------------------------------------
include make/make.project

JAVAINC	=	$(LIBDIR)/javainc
SCIPINC	=	$(LIBDIR)/scipinc

SCIPOPTLIB	=	scipopt
JNILIB		=	libjscip
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
FLAGS		+=	-I$(JAVAINC) -I$(SCIPINC)

ifeq ($(COMP),msvc)
	LDFLAGS	+= -dll
else
	CFLAGS	+=	-fpic
	LDFLAGS	+= -shared
endif

JAVAC_d	=	-d
JARFLAGS =	cf

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
		$(CC) $(CC_c) $(SRCDIR)/$(JNISRC) $(FLAGS) $(CFLAGS) $(CC_o)$(OBJDIR)/$(JNIOBJ)
		@echo "-> generating library $(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT)"
		$(LINKCC) $(LINKCC_o)$(LIBDIR)/$(JNILIB).$(SHAREDLIBEXT) $(LDFLAGS) $(OBJDIR)/$(JNIOBJ) $(LINKCC_L)$(LIBDIR) $(LINKCC_l)$(SCIPOPTLIB)
		@echo "-> compiling all java files"
		$(JAVAC) $(JAVAC_d) $(CLASSDIR) $(JAVASRCDIR)/*.java

# generate jar file containing all class files
.PHONY: jar
jar: | library $(CLASSDIR)
		@echo "-> generate $(LIBDIR)/$(SCIPJAR)"
      # todo: creating the jar from the main directory adds $(CLASSDIR) to the package name; how can we avoid this?
		cd $(CLASSDIR); $(JAR) $(JARFLAGS) ../$(LIBDIR)/$(SCIPJAR) $(PACKAGENAME)/*.class; cd -

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
		@-mkdir -p $(CLASSDIR)/$(PACKAGENAME)
