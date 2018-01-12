/* File: scipjni.i */
%module SCIPJNI
%{
   #include "scip/scip.h"
   #include "scip/scipdefplugins.h"

   /* assist function to create a SCIP */
   SCIP* createSCIP()
   {
      SCIP* scip;

      SCIP_CALL_ABORT( SCIPcreate(&scip) );

      return scip;
   }

   /* assist function to free a SCIP */
   void freeSCIP(SCIP* scip)
   {
      SCIP_CALL_ABORT( SCIPfree(&scip) );
   }

   /* assist function to create a variable */
   SCIP_VAR* createVar(SCIP* scip, const char* name, SCIP_Real lb, SCIP_Real ub, SCIP_Real obj, SCIP_VARTYPE vartype)
   {
      SCIP_VAR* var;

      SCIP_CALL_ABORT( SCIPcreateVarBasic(scip, &var, name, lb, ub, obj, vartype) );
      return var;
   }

   /* assist function to free a variable */
   void releaseVar(SCIP* scip, SCIP_VAR* var)
   {
      SCIP_CALL_ABORT( SCIPreleaseVar(scip, &var) );
   }

   /* assist function to create a linear constraint */
   SCIP_CONS* createConsBasicLinear(SCIP* scip, const char* name , int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicLinear(scip, &cons, name, nvars, vars, vals, lhs, rhs) );

      return cons;
   }

   /* assist function to create a quadratic constraint */
   SCIP_CONS* createConsBasicQuadratic(SCIP* scip, const char* name, int nlinvars, SCIP_VAR** linvars, SCIP_Real* lincoefs,\
      int nquadvars, SCIP_VAR** quadvars1, SCIP_VAR** quadvars2, SCIP_Real* quadcoefs, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicQuadratic(scip, &cons, name, nlinvars, linvars, lincoefs, nquadvars, quadvars1, quadvars2, quadcoefs, lhs, rhs) );

      return cons;
   }

   /* assist function to create a super indicator constraint */
   SCIP_CONS* createConsBasicSuperIndicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSuperindicator(scip, &cons, name, binvar, slackcons) );

      return cons;
   }

   /* assist function to release a constraint */
   void releaseCons(SCIP* scip, SCIP_CONS* cons)
   {
      SCIP_CALL_ABORT( SCIPreleaseCons(scip, &cons) );
   }
%}

/* use SWIG internal arrays */
%include carrays.i
%array_functions( double, double_array )
%array_functions( SCIP_VAR*, SCIP_VAR_array )
%array_functions( SCIP_SOL*, SCIP_SOL_array )

 /* some defines from def.h */
#define SCIP_Real double
#define SCIP_Longint long int
#define SCIP_Bool unsigned int

/* SCIP retcode enum */
enum SCIP_Retcode
{
   SCIP_OKAY               =  +1,
   SCIP_ERROR              =   0,
   SCIP_NOMEMORY           =  -1,
   SCIP_READERROR          =  -2,
   SCIP_WRITEERROR         =  -3,
   SCIP_NOFILE             =  -4,
   SCIP_FILECREATEERROR    =  -5,
   SCIP_LPERROR            =  -6,
   SCIP_NOPROBLEM          =  -7,
   SCIP_INVALIDCALL        =  -8,
   SCIP_INVALIDDATA        =  -9,
   SCIP_INVALIDRESULT      = -10,
   SCIP_PLUGINNOTFOUND     = -11,
   SCIP_PARAMETERUNKNOWN   = -12,
   SCIP_PARAMETERWRONGTYPE = -13,
   SCIP_PARAMETERWRONGVAL  = -14,
   SCIP_KEYALREADYEXISTING = -15,
   SCIP_MAXDEPTHLEVEL      = -16,
   SCIP_BRANCHERROR        = -17
};
typedef enum SCIP_Retcode SCIP_RETCODE;

/* SCIP vartype enum */
enum SCIP_Vartype
{
   SCIP_VARTYPE_BINARY     = 0,
   SCIP_VARTYPE_INTEGER    = 1,
   SCIP_VARTYPE_IMPLINT    = 2,
   SCIP_VARTYPE_CONTINUOUS = 3
};
typedef enum SCIP_Vartype SCIP_VARTYPE;

/* SCIP ParamEmphasis enum */
enum SCIP_ParamEmphasis 
{ 
  SCIP_PARAMEMPHASIS_DEFAULT = 0, 
  SCIP_PARAMEMPHASIS_CPSOLVER = 1, 
  SCIP_PARAMEMPHASIS_EASYCIP = 2, 
  SCIP_PARAMEMPHASIS_FEASIBILITY = 3, 
  SCIP_PARAMEMPHASIS_HARDLP = 4, 
  SCIP_PARAMEMPHASIS_OPTIMALITY = 5, 
  SCIP_PARAMEMPHASIS_COUNTER = 6, 
  SCIP_PARAMEMPHASIS_PHASEFEAS = 7, 
  SCIP_PARAMEMPHASIS_PHASEIMPROVE = 8, 
  SCIP_PARAMEMPHASIS_PHASEPROOF = 9 
};
typedef enum SCIP_ParamEmphasis SCIP_PARAMEMPHASIS;

/** objective sense */
enum SCIP_Objsense
{
   SCIP_OBJSENSE_MAXIMIZE = -1,           /**< maximize objective function */
   SCIP_OBJSENSE_MINIMIZE = +1            /**< minimize objective function */
};
typedef enum SCIP_Objsense SCIP_OBJSENSE;

/* from pub_misc.h */
SCIP_Real      SCIPcalcMachineEpsilon();

/* from scip.h*/
SCIP_RETCODE   SCIPcreate(SCIP** scip);
int            SCIPgetNVars(SCIP* scip);
SCIP_RETCODE   SCIPreadProb(SCIP* scip, const char* filename, const char* extension);
SCIP_RETCODE   SCIPreadParams(SCIP* scip, const char* filename);
SCIP_RETCODE   SCIPcreateProbBasic(SCIP* scip, const char* probname);
SCIP_RETCODE   SCIPincludeDefaultPlugins(SCIP* scip);
SCIP_RETCODE   SCIPsolve(SCIP* scip);
SCIP_RETCODE   SCIPaddVar(SCIP* scip, SCIP_VAR* var);
int            SCIPgetNVars(SCIP* scip);
SCIP_VAR**     SCIPgetVars(SCIP* scip);
SCIP_RETCODE   SCIPaddCons(SCIP* scip, SCIP_CONS* cons);
SCIP_RETCODE   SCIPwriteOrigProblem(SCIP* scip, const char* filename, const char* extension, SCIP_Bool genericnames);
SCIP_RETCODE   SCIPwriteTransProblem(SCIP* scip, const char* filename, const char* extension, SCIP_Bool genericnames);
SCIP_RETCODE   SCIPprintStatistics(SCIP* scip, FILE* file);
SCIP_RETCODE   SCIPprintBestSol(SCIP* scip, FILE* file, SCIP_Bool printzeros);
void           SCIPsetMessagehdlrQuiet(SCIP* scip, SCIP_Bool quite);
SCIP_SOL**     SCIPgetSols(SCIP* scip);
int            SCIPgetNSols(SCIP* scip);
SCIP_SOL*      SCIPgetBestSol(SCIP* scip);
SCIP_Real      SCIPgetSolVal(SCIP* scip, SCIP_SOL* sol, SCIP_VAR* var);
SCIP_Real      SCIPgetSolOrigObj(SCIP* scip, SCIP_SOL* sol);
SCIP_Real      SCIPinfinity(SCIP* scip);
SCIP_Real      SCIPepsilon(SCIP* scip);
SCIP_Real      SCIPfeastol(SCIP* scip);
SCIP_RETCODE   SCIPsetBoolParam(SCIP* scip, const char* name, SCIP_Bool value);
SCIP_RETCODE   SCIPsetIntParam(SCIP* scip, const char* name, int value);
SCIP_RETCODE   SCIPsetLongintParam(SCIP* scip, const char* name, SCIP_Longint value);
SCIP_RETCODE   SCIPsetRealParam(SCIP* scip, const char* name, SCIP_Real value);
SCIP_RETCODE   SCIPsetCharParam(SCIP* scip, const char* name, char value);
SCIP_RETCODE   SCIPsetStringParam(SCIP* scip, const char* name, const char* value);
SCIP_RETCODE   SCIPsetEmphasis(SCIP* scip, SCIP_PARAMEMPHASIS paramemphasis, SCIP_Bool quiet);
SCIP_RETCODE   SCIPsetObjsense(SCIP* scip, SCIP_OBJSENSE objsense);
SCIP_OBJSENSE  SCIPgetObjsense(SCIP* scip);

/* from memory.h */
void           BMScheckEmptyMemory();
long long      BMSgetMemoryUsed();

/* from pub_var.h */
const char*    SCIPvarGetName(SCIP_VAR* var);
SCIP_VARTYPE   SCIPvarGetType(SCIP_VAR* var);
SCIP_Real      SCIPvarGetLbLocal(SCIP_VAR* var);
SCIP_Real      SCIPvarGetUbLocal(SCIP_VAR* var);
SCIP_Real      SCIPvarGetLbGlobal(SCIP_VAR* var);
SCIP_Real      SCIPvarGetUbGlobal(SCIP_VAR* var);
SCIP_Real      SCIPvarGetObj(SCIP_VAR* var);

/* from pub_sol.h */
int            SCIPsolGetDepth(SCIP_SOL* sol);
int            SCIPsolGetIndex(SCIP_SOL* sol);

/* from pub_cons.h */
const char*    SCIPconsGetName(SCIP_CONS* cons);

/* assist functions */
SCIP*          createSCIP();
void           freeSCIP(SCIP* scip);
SCIP_VAR*      createVar(SCIP* scip, const char* name, SCIP_Real lb, SCIP_Real ub, SCIP_Real obj, SCIP_VARTYPE vartype);
void           releaseVar(SCIP* scip, SCIP_VAR* var);
SCIP_CONS*     createConsBasicLinear(SCIP* scip, const char* name , int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicQuadratic(SCIP* scip, const char* name, int nlinvars, SCIP_VAR** linvars, SCIP_Real* lincoefs, int nquadvars, SCIP_VAR** quadvars1, SCIP_VAR** quadvars2, SCIP_Real* quadcoefs, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicSuperIndicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons);
void           releaseCons(SCIP* scip, SCIP_CONS* cons);
