/* File: scipjni.i */
%module(directors="1") SCIPJNI

// generate directors for all classes that have virtual methods
%feature("director");

%{
   #include "scip/scip.h"
   #include "scip/scipdefplugins.h"
   #include "objscip/objmessagehdlr.h"

   /* if libscip is a shared library, ensure we use function calls instead of
      macros, for better binary compatibility across SCIP versions */
   #ifndef HAVE_STATIC_LIBSCIP
   #ifdef SCIPinfinity
   #undef SCIPinfinity
   #endif

   #ifdef BMScheckEmptyMemory
   #undef BMScheckEmptyMemory
   #endif
   #define BMScheckEmptyMemory() BMScheckEmptyMemory_call()

   #ifdef BMSgetMemoryUsed
   #undef BMSgetMemoryUsed
   #endif
   #define BMSgetMemoryUsed() BMSgetMemoryUsed_call()

   #ifdef SCIPvarGetName
   #undef SCIPvarGetName
   #endif

   #ifdef SCIPvarGetType
   #undef SCIPvarGetType
   #endif

   #ifdef SCIPvarGetLbLocal
   #undef SCIPvarGetLbLocal
   #endif

   #ifdef SCIPvarGetUbLocal
   #undef SCIPvarGetUbLocal
   #endif

   #ifdef SCIPvarGetLbGlobal
   #undef SCIPvarGetLbGlobal
   #endif

   #ifdef SCIPvarGetUbGlobal
   #undef SCIPvarGetUbGlobal
   #endif

   #ifdef SCIPvarGetObj
   #undef SCIPvarGetObj
   #endif

   #ifdef SCIPvarGetBranchPriority
   #undef SCIPvarGetBranchPriority
   #endif

   #ifdef SCIPsolGetDepth
   #undef SCIPsolGetDepth
   #endif

   #ifdef SCIPsolGetIndex
   #undef SCIPsolGetIndex
   #endif

   #ifdef SCIPconsGetName
   #undef SCIPconsGetName
   #endif
   #endif /* ndef HAVE_STATIC_LIBSCIP */

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

   /* assist function to create an abs expression */
   SCIP_EXPR* createExprAbs(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprAbs(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create an entropy expression */
   SCIP_EXPR* createExprEntropy(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprEntropy(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create an exp expression */
   SCIP_EXPR* createExprExp(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprExp(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create a log (ln) expression */
   SCIP_EXPR* createExprLog(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprLog(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create a pow expression */
   SCIP_EXPR* createExprPow(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprPow(scip, &expr, child, exponent, NULL, NULL) );
      return expr;
   }

   /* assist function to create a signpower expression */
   SCIP_EXPR* createExprSignpower(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSignpower(scip, &expr, child, exponent, NULL, NULL) );
      return expr;
   }

   /* assist function to create a product expression */
   SCIP_EXPR* createExprProduct(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real coefficient)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprProduct(scip, &expr, nchildren, children, coefficient, NULL, NULL) );
      return expr;
   }

   /* assist function to create a sum expression */
   SCIP_EXPR* createExprSum(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real* coefficients, SCIP_Real constant)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSum(scip, &expr, nchildren, children, coefficients, constant, NULL, NULL) );
      return expr;
   }

   /* assist function to create a sin expression */
   SCIP_EXPR* createExprSin(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSin(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create a cos expression */
   SCIP_EXPR* createExprCos(SCIP* scip, SCIP_EXPR* child)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprCos(scip, &expr, child, NULL, NULL) );
      return expr;
   }

   /* assist function to create a (constant) value expression */
   SCIP_EXPR* createExprValue(SCIP* scip, SCIP_Real value)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprValue(scip, &expr, value, NULL, NULL) );
      return expr;
   }

   /* assist function to create a var(iable) expression */
   SCIP_EXPR* createExprVar(SCIP* scip, SCIP_VAR *var)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprVar(scip, &expr, var, NULL, NULL) );
      return expr;
   }

   /* assist function to release an expression */
   void releaseExpr(SCIP* scip, SCIP_EXPR* expr)
   {
      SCIP_CALL_ABORT( SCIPreleaseExpr(scip, &expr) );
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

      SCIP_CALL_ABORT( SCIPcreateConsBasicQuadraticNonlinear(scip, &cons, name, nlinvars, linvars, lincoefs, nquadvars, quadvars1, quadvars2, quadcoefs, lhs, rhs) );

      return cons;
   }

   /* assist function to create a super indicator constraint */
   SCIP_CONS* createConsBasicSuperIndicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSuperindicator(scip, &cons, name, binvar, slackcons) );

      return cons;
   }

   /* assist function to create a nonlinear constraint */
   SCIP_CONS* createConsBasicNonlinear(SCIP* scip, const char* name, SCIP_EXPR*            expr, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicNonlinear(scip, &cons, name, expr, lhs, rhs) );

      return cons;
   }

   /* assist function to release a constraint */
   void releaseCons(SCIP* scip, SCIP_CONS* cons)
   {
      SCIP_CALL_ABORT( SCIPreleaseCons(scip, &cons) );
   }

   /* assist function to create a message handler */
   SCIP_MESSAGEHDLR* createObjMessagehdlr(scip::ObjMessagehdlr* objmessagehdlr, SCIP_Bool deleteobject)
   {
      SCIP_MESSAGEHDLR* messagehdlr;

      SCIP_CALL_ABORT( SCIPcreateObjMessagehdlr(&messagehdlr, objmessagehdlr, deleteobject) );

      return messagehdlr;
   }
%}

/* use SWIG internal arrays */
%include carrays.i
/* char *ary is a pointer to a char, not a string, so override the map */
%apply SWIGTYPE * {char *new_char_array(int)};
%apply SWIGTYPE * {char *ary};
%typemap(freearg, noblock=1) char *ary {}
%array_functions( char, char_array )
%array_functions( double, double_array )
%array_functions( int, int_array )
%array_functions( long long, long_long_array )
%array_functions( unsigned int, unsigned_int_array )
%array_functions( char*, String_array )
%array_functions( SCIP_VAR*, SCIP_VAR_array )
%array_functions( SCIP_EXPR*, SCIP_EXPR_array )
%array_functions( SCIP_SOL*, SCIP_SOL_array )

 /* some defines from def.h */
#define SCIP_Real double
#define SCIP_Longint long long int
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

/** SCIP VerbLevel enum */
enum SCIP_VerbLevel
{
   SCIP_VERBLEVEL_NONE    = 0,
   SCIP_VERBLEVEL_DIALOG  = 1,
   SCIP_VERBLEVEL_MINIMAL = 2,
   SCIP_VERBLEVEL_NORMAL  = 3,
   SCIP_VERBLEVEL_HIGH    = 4,
   SCIP_VERBLEVEL_FULL    = 5
};
typedef enum SCIP_VerbLevel SCIP_VERBLEVEL;

/** objective sense */
enum SCIP_Objsense
{
   SCIP_OBJSENSE_MAXIMIZE = -1,           /**< maximize objective function */
   SCIP_OBJSENSE_MINIMIZE = +1            /**< minimize objective function */
};
typedef enum SCIP_Objsense SCIP_OBJSENSE;

/** SCIP Status enum */
enum SCIP_Status
{
   SCIP_STATUS_UNKNOWN        =  0,
   SCIP_STATUS_USERINTERRUPT  =  1,
   SCIP_STATUS_NODELIMIT      =  2,
   SCIP_STATUS_TOTALNODELIMIT =  3,
   SCIP_STATUS_STALLNODELIMIT =  4,
   SCIP_STATUS_TIMELIMIT      =  5,
   SCIP_STATUS_MEMLIMIT       =  6,
   SCIP_STATUS_GAPLIMIT       =  7,
   SCIP_STATUS_SOLLIMIT       =  8,
   SCIP_STATUS_BESTSOLLIMIT   =  9,
   SCIP_STATUS_RESTARTLIMIT   = 10,
   SCIP_STATUS_OPTIMAL        = 11,
   SCIP_STATUS_INFEASIBLE     = 12,
   SCIP_STATUS_UNBOUNDED      = 13,
   SCIP_STATUS_INFORUNBD      = 14,
   SCIP_STATUS_TERMINATE      = 15
};
typedef enum SCIP_Status SCIP_STATUS;

/** SCIP operation stage */
enum SCIP_Stage
{
   SCIP_STAGE_INIT         =  0,
   SCIP_STAGE_PROBLEM      =  1,
   SCIP_STAGE_TRANSFORMING =  2,
   SCIP_STAGE_TRANSFORMED  =  3,
   SCIP_STAGE_INITPRESOLVE =  4,
   SCIP_STAGE_PRESOLVING   =  5,
   SCIP_STAGE_EXITPRESOLVE =  6,
   SCIP_STAGE_PRESOLVED    =  7,
   SCIP_STAGE_INITSOLVE    =  8,
   SCIP_STAGE_SOLVING      =  9,
   SCIP_STAGE_SOLVED       = 10,
   SCIP_STAGE_EXITSOLVE    = 11,
   SCIP_STAGE_FREETRANS    = 12,
   SCIP_STAGE_FREE         = 13
};
typedef enum SCIP_Stage SCIP_STAGE;

/* from pub_misc.h */
SCIP_Real      SCIPcalcMachineEpsilon();

/* from scip.h*/
SCIP_RETCODE   SCIPcreate(SCIP** scip);
SCIP_RETCODE   SCIPreadProb(SCIP* scip, const char* filename, const char* extension);
SCIP_RETCODE   SCIPreadParams(SCIP* scip, const char* filename);
SCIP_RETCODE   SCIPcreateProbBasic(SCIP* scip, const char* probname);
SCIP_RETCODE   SCIPincludeDefaultPlugins(SCIP* scip);
SCIP_RETCODE   SCIPsolve(SCIP* scip);
SCIP_RETCODE   SCIPsolveConcurrent(SCIP* scip);
SCIP_RETCODE   SCIPinterruptSolve(SCIP* scip);
SCIP_Bool      SCIPisSolveInterrupted(SCIP* scip);
SCIP_RETCODE   SCIPaddVar(SCIP* scip, SCIP_VAR* var);
int            SCIPgetNVars(SCIP* scip);
SCIP_VAR**     SCIPgetVars(SCIP* scip);
int            SCIPgetNOrigVars(SCIP* scip);
SCIP_VAR**     SCIPgetOrigVars(SCIP* scip);
SCIP_RETCODE   SCIPaddCons(SCIP* scip, SCIP_CONS* cons);
SCIP_RETCODE   SCIPwriteOrigProblem(SCIP* scip, const char* filename, const char* extension, SCIP_Bool genericnames);
SCIP_RETCODE   SCIPwriteTransProblem(SCIP* scip, const char* filename, const char* extension, SCIP_Bool genericnames);
SCIP_RETCODE   SCIPprintStatistics(SCIP* scip, FILE* file);
SCIP_RETCODE   SCIPprintBestSol(SCIP* scip, FILE* file, SCIP_Bool printzeros);
void           SCIPsetMessagehdlrQuiet(SCIP* scip, SCIP_Bool quite);
SCIP_STATUS    SCIPgetStatus(SCIP* scip);
SCIP_STAGE     SCIPgetStage(SCIP* scip);
SCIP_SOL**     SCIPgetSols(SCIP* scip);
int            SCIPgetNSols(SCIP* scip);
SCIP_SOL*      SCIPgetBestSol(SCIP* scip);
SCIP_Real      SCIPgetSolVal(SCIP* scip, SCIP_SOL* sol, SCIP_VAR* var);
SCIP_Real      SCIPgetSolOrigObj(SCIP* scip, SCIP_SOL* sol);
SCIP_Real      SCIPinfinity(SCIP* scip);
SCIP_Real      SCIPepsilon(SCIP* scip);
SCIP_Real      SCIPfeastol(SCIP* scip);
SCIP_RETCODE   SCIPgetBoolParam(SCIP* scip, const char* name, SCIP_Bool* p_value);
SCIP_RETCODE   SCIPgetIntParam(SCIP* scip, const char* name, int* p_value);
SCIP_RETCODE   SCIPgetLongintParam(SCIP* scip, const char* name, SCIP_Longint* p_value);
SCIP_RETCODE   SCIPgetRealParam(SCIP* scip, const char* name, SCIP_Real* p_value);
/* char *p_value is a pointer to a char, not a string, so override the map */
%apply SWIGTYPE * {char *p_value};
%typemap(freearg, noblock=1) char *p_value {}
SCIP_RETCODE   SCIPgetCharParam(SCIP* scip, const char* name, char* p_value);
SCIP_RETCODE   SCIPgetStringParam(SCIP* scip, const char* name, char** p_value);
SCIP_RETCODE   SCIPsetBoolParam(SCIP* scip, const char* name, SCIP_Bool value);
SCIP_RETCODE   SCIPsetIntParam(SCIP* scip, const char* name, int value);
SCIP_RETCODE   SCIPsetLongintParam(SCIP* scip, const char* name, SCIP_Longint value);
SCIP_RETCODE   SCIPsetRealParam(SCIP* scip, const char* name, SCIP_Real value);
SCIP_RETCODE   SCIPsetCharParam(SCIP* scip, const char* name, char value);
SCIP_RETCODE   SCIPsetStringParam(SCIP* scip, const char* name, const char* value);
SCIP_RETCODE   SCIPsetEmphasis(SCIP* scip, SCIP_PARAMEMPHASIS paramemphasis, SCIP_Bool quiet);
SCIP_RETCODE   SCIPsetObjsense(SCIP* scip, SCIP_OBJSENSE objsense);
SCIP_OBJSENSE  SCIPgetObjsense(SCIP* scip);
SCIP_Real      SCIPgetGap(SCIP* scip);
SCIP_RETCODE   SCIPchgVarObj(SCIP* scip, SCIP_VAR* var, SCIP_Real obj);

/* from scip_var.h */
SCIP_RETCODE   SCIPchgVarBranchPriority(SCIP* scip, SCIP_VAR* var, int branchpriority);

/* from scip_sol.h */
SCIP_RETCODE   SCIPcreateSol(SCIP* scip, SCIP_SOL** sol, SCIP_HEUR* heur);
SCIP_RETCODE   SCIPcreatePartialSol(SCIP* scip, SCIP_SOL** sol, SCIP_HEUR* heur);
SCIP_RETCODE   SCIPsetSolVal(SCIP* scip, SCIP_SOL* sol, SCIP_VAR* var, SCIP_Real val);
SCIP_RETCODE   SCIPsetSolVals(SCIP* scip, SCIP_SOL* sol, int nvars, SCIP_VAR** vars, SCIP_Real* val);
SCIP_RETCODE   SCIPaddSolFree(SCIP* scip, SCIP_SOL** sol, SCIP_Bool *stored);

/* from scip_solvingstats.h */
SCIP_Real      SCIPgetPrimalbound(SCIP* scip);
SCIP_Real      SCIPgetDualbound(SCIP* scip);

/* from scip_timing.h */
SCIP_Real      SCIPgetSolvingTime(SCIP* scip);

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
int            SCIPvarGetBranchPriority(SCIP_VAR* var);

/* from pub_sol.h */
int            SCIPsolGetDepth(SCIP_SOL* sol);
int            SCIPsolGetIndex(SCIP_SOL* sol);

/* from pub_cons.h */
const char*    SCIPconsGetName(SCIP_CONS* cons);

/* from type_message.h */
typedef struct SCIP_Messagehdlr SCIP_MESSAGEHDLR;

/* from obj_message.h */
namespace scip
{
class ObjMessagehdlr
{
public:
   const SCIP_Bool scip_bufferedoutput_;

   explicit ObjMessagehdlr(SCIP_Bool bufferedoutput);
   virtual ~ObjMessagehdlr();
   virtual void scip_error(SCIP_MESSAGEHDLR* messagehdlr, FILE* file, const char* msg);
   virtual void scip_warning(SCIP_MESSAGEHDLR* messagehdlr, FILE* file, const char* msg);
   virtual void scip_dialog(SCIP_MESSAGEHDLR* messagehdlr, FILE* file, const char* msg);
   virtual void scip_info(SCIP_MESSAGEHDLR* messagehdlr, FILE* file, const char* msg);
   virtual SCIP_RETCODE scip_free(SCIP_MESSAGEHDLR* messagehdlr);
};

} /* namespace scip */

scip::ObjMessagehdlr* SCIPgetObjMessagehdlr(SCIP_MESSAGEHDLR* messagehdlr);
void           SCIPsetStaticErrorPrintingMessagehdlr(SCIP_MESSAGEHDLR* messagehdlr);

/* from scip_message.h */
SCIP_RETCODE   SCIPsetMessagehdlr(SCIP* scip, SCIP_MESSAGEHDLR* messagehdlr);
SCIP_MESSAGEHDLR* SCIPgetMessagehdlr(SCIP* scip);
void           SCIPsetMessagehdlrLogfile(SCIP* scip, const char* filename);
void           SCIPsetMessagehdlrQuiet(SCIP* scip, SCIP_Bool quiet);
SCIP_VERBLEVEL SCIPgetVerbLevel(SCIP* scip);

/* assist functions */
SCIP*          createSCIP();
void           freeSCIP(SCIP* scip);
SCIP_VAR*      createVar(SCIP* scip, const char* name, SCIP_Real lb, SCIP_Real ub, SCIP_Real obj, SCIP_VARTYPE vartype);
void           releaseVar(SCIP* scip, SCIP_VAR* var);
SCIP_EXPR*     createExprAbs(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprEntropy(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprExp(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprLog(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprPow(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent);
SCIP_EXPR*     createExprSignpower(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent);
SCIP_EXPR*     createExprProduct(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real coefficient);
SCIP_EXPR*     createExprSum(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real* coefficients, SCIP_Real constant);
SCIP_EXPR*     createExprSin(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprCos(SCIP* scip, SCIP_EXPR* child);
SCIP_EXPR*     createExprValue(SCIP* scip, SCIP_Real value);
SCIP_EXPR*     createExprVar(SCIP* scip, SCIP_VAR *var);
void           releaseExpr(SCIP* scip, SCIP_EXPR* expr);
SCIP_CONS*     createConsBasicLinear(SCIP* scip, const char* name , int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicQuadratic(SCIP* scip, const char* name, int nlinvars, SCIP_VAR** linvars, SCIP_Real* lincoefs, int nquadvars, SCIP_VAR** quadvars1, SCIP_VAR** quadvars2, SCIP_Real* quadcoefs, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicNonlinear(SCIP* scip, const char* name, SCIP_EXPR*            expr, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicSuperIndicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons);
void           releaseCons(SCIP* scip, SCIP_CONS* cons);
SCIP_MESSAGEHDLR* createObjMessagehdlr(scip::ObjMessagehdlr* objmessagehdlr, SCIP_Bool deleteobject);
