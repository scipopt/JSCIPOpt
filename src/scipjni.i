/* File: scipjni.i */
%module(directors="1") SCIPJNI

// generate directors for all classes that have virtual methods
%feature("director");

%{
   #include "scip/scip.h"
   #include "scip/scipdefplugins.h"
   #include "objscip/objmessagehdlr.h"
   #include "objscip/objexprhdlr.h"
   #include "symmetry/struct_symmetry.h"

   /* if libscip is a shared library, ensure we use function calls instead of
      macros, for better binary compatibility across SCIP versions */
   #ifndef HAVE_STATIC_LIBSCIP
   #ifdef SCIPinfinity
   #undef SCIPinfinity
   #endif

   #ifdef SCIPgetStage
   #undef SCIPgetStage
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

   #ifdef SCIPexprGetData
   #undef SCIPexprGetData
   #endif

   #ifdef SCIPexprSetData
   #undef SCIPexprSetData
   #endif

   #ifdef SCIPexprGetNChildren
   #undef SCIPexprGetNChildren
   #endif

   #ifdef SCIPexprGetChildren
   #undef SCIPexprGetChildren
   #endif

   #ifdef SCIPexprGetEvalValue
   #undef SCIPexprGetEvalValue
   #endif

   #ifdef SCIPexprGetDot
   #undef SCIPexprGetDot
   #endif

   #ifdef SCIPexprGetActivity
   #undef SCIPexprGetActivity
   #endif

   #ifdef SCIPexprIsIntegral
   #undef SCIPexprIsIntegral
   #endif

   #ifdef SCIPconsGetName
   #undef SCIPconsGetName
   #endif

   #ifdef SCIPcaptureExpr
   #undef SCIPcaptureExpr
   #endif

   #ifdef SCIPevalExprActivity
   #undef SCIPevalExprActivity
   #endif

   #ifdef SCIPcompareExpr
   #undef SCIPcompareExpr
   #endif

   #ifdef SCIPfindExprhdlr
   #undef SCIPfindExprhdlr
   #endif
   #endif /* ndef HAVE_STATIC_LIBSCIP */
%}

/* function pointer types */
%{
typedef SCIP_DECL_EXPR_OWNERCREATE(SCIP_EXPR_OWNERCREATE);
typedef SCIP_DECL_EXPR_INTEVALVAR(SCIP_EXPR_INTEVALVAR);
%}
/* declare opaque types for the function pointers */
struct SCIP_EXPR_OWNERCREATE;
struct SCIP_EXPR_INTEVALVAR;

%{
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

   /* assist function to create a general expression (for use by expression handlers only) */
   SCIP_EXPR* createExpr(SCIP* scip, SCIP_EXPRHDLR* exprhdlr, SCIP_EXPRDATA* exprdata, int nchildren, SCIP_EXPR** children, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExpr(scip, &expr, exprhdlr, exprdata, nchildren, children, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create an abs expression */
   SCIP_EXPR* createExprAbs(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprAbs(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create an entropy expression */
   SCIP_EXPR* createExprEntropy(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprEntropy(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create an exp expression */
   SCIP_EXPR* createExprExp(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprExp(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a log (ln) expression */
   SCIP_EXPR* createExprLog(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprLog(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a pow expression */
   SCIP_EXPR* createExprPow(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprPow(scip, &expr, child, exponent, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a signpower expression */
   SCIP_EXPR* createExprSignpower(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSignpower(scip, &expr, child, exponent, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a product expression */
   SCIP_EXPR* createExprProduct(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real coefficient, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprProduct(scip, &expr, nchildren, children, coefficient, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a sum expression */
   SCIP_EXPR* createExprSum(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real* coefficients, SCIP_Real constant, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSum(scip, &expr, nchildren, children, coefficients, constant, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a sin expression */
   SCIP_EXPR* createExprSin(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprSin(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a cos expression */
   SCIP_EXPR* createExprCos(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprCos(scip, &expr, child, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a (constant) value expression */
   SCIP_EXPR* createExprValue(SCIP* scip, SCIP_Real value, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprValue(scip, &expr, value, ownercreate, ownercreatedata) );
      return expr;
   }

   /* assist function to create a var(iable) expression */
   SCIP_EXPR* createExprVar(SCIP* scip, SCIP_VAR *var, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata)
   {
      SCIP_EXPR* expr;

      SCIP_CALL_ABORT( SCIPcreateExprVar(scip, &expr, var, ownercreate, ownercreatedata) );
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

   /* assist function to create a superindicator constraint */
   SCIP_CONS* createConsBasicSuperindicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSuperindicator(scip, &cons, name, binvar, slackcons) );

      return cons;
   }

   /* assist function to create a nonlinear constraint */
   SCIP_CONS* createConsBasicNonlinear(SCIP* scip, const char* name, SCIP_EXPR* expr, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicNonlinear(scip, &cons, name, expr, lhs, rhs) );

      return cons;
   }

   /* assist function to create a pseudoboolean constraint */
   SCIP_CONS* createConsBasicPseudoboolean(SCIP* scip, const char* name, SCIP_VAR** linvars, int nlinvars, SCIP_Real* linvals, SCIP_VAR*** terms, int nterms, int* ntermvars, SCIP_Real* termvals, SCIP_VAR* indvar, SCIP_Real weight, SCIP_Bool issoftcons, SCIP_VAR* intvar, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicPseudoboolean(scip, &cons, name, linvars, nlinvars, linvals, terms, nterms, ntermvars, termvals, indvar, weight, issoftcons, intvar, lhs, rhs) );

      return cons;
   }

   /* assist function to create a set partitioning constraint */
   SCIP_CONS* createConsBasicSetpart(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSetpart(scip, &cons, name, nvars, vars) );

      return cons;
   }

   /* assist function to create a set packing constraint */
   SCIP_CONS* createConsBasicSetpack(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSetpack(scip, &cons, name, nvars, vars) );

      return cons;
   }

   /* assist function to create a set covering constraint */
   SCIP_CONS* createConsBasicSetcover(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSetcover(scip, &cons, name, nvars, vars) );

      return cons;
   }

   /* assist function to create a second-order cone constraint */
   SCIP_CONS* createConsBasicSOC(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* coefs, SCIP_Real* offsets, SCIP_Real constant, SCIP_VAR* rhsvar, SCIP_Real rhscoeff, SCIP_Real rhsoffset)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSOCNonlinear(scip, &cons, name, nvars, vars, coefs, offsets, constant, rhsvar, rhscoeff, rhsoffset) );

      return cons;
   }

   /* assist function to create a signpower constraint */
   SCIP_CONS* createConsBasicSignpower(SCIP* scip, const char* name, SCIP_VAR* x, SCIP_VAR* z, SCIP_Real exponent, SCIP_Real xoffset, SCIP_Real zcoef, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSignpowerNonlinear(scip, &cons, name, x, z, exponent, xoffset, zcoef, lhs, rhs) );

      return cons;
   }

   /* assist function to create an and constraint */
   SCIP_CONS* createConsBasicAnd(SCIP* scip, const char* name, SCIP_VAR* resvar, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicAnd(scip, &cons, name, resvar, nvars, vars) );

      return cons;
   }

   /* assist function to create an or constraint */
   SCIP_CONS* createConsBasicOr(SCIP* scip, const char* name, SCIP_VAR* resvar, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicOr(scip, &cons, name, resvar, nvars, vars) );

      return cons;
   }

   /* assist function to create a bound disjunction constraint */
   SCIP_CONS* createConsBasicBounddisjunction(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_BOUNDTYPE* boundtypes, SCIP_Real* bounds)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicBounddisjunction(scip, &cons, name, nvars, vars, boundtypes, bounds) );

      return cons;
   }

   /* assist function to create a redundant bound disjunction constraint */
   SCIP_CONS* createConsBasicBounddisjunctionRedundant(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_BOUNDTYPE* boundtypes, SCIP_Real* bounds)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicBounddisjunctionRedundant(scip, &cons, name, nvars, vars, boundtypes, bounds) );

      return cons;
   }

   /* assist function to create a cardinality constraint */
   SCIP_CONS* createConsBasicCardinality(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, int cardval, SCIP_VAR** indvars, SCIP_Real* weights)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicCardinality(scip, &cons, name, nvars, vars, cardval, indvars, weights) );

      return cons;
   }

   /* assist function to create a conjunction constraint */
   SCIP_CONS* createConsBasicConjunction(SCIP* scip, const char* name, int nconss, SCIP_CONS** conss)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicConjunction(scip, &cons, name, nconss, conss) );

      return cons;
   }

   /* assist function to create a disjunction constraint */
   SCIP_CONS* createConsBasicDisjunction(SCIP* scip, const char* name, int nconss, SCIP_CONS** conss, SCIP_CONS* relaxcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicDisjunction(scip, &cons, name, nconss, conss, relaxcons) );

      return cons;
   }

   /* assist function to create a cumulative constraint */
   SCIP_CONS* createConsBasicCumulative(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, int* durations, int* demands, int capacity)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicCumulative(scip, &cons, name, nvars, vars, durations, demands, capacity) );

      return cons;
   }

   /* assist function to create an indicator constraint */
   SCIP_CONS* createConsBasicIndicator(SCIP* scip, const char* name, SCIP_VAR* binvar, int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicIndicator(scip, &cons, name, binvar, nvars, vars, vals, rhs) );

      return cons;
   }

   /* assist function to create an indicator constraint with given linear
      constraint and slack variable*/
   SCIP_CONS* createConsBasicIndicatorLinCons(SCIP* scip, const char* name, SCIP_VAR* binvar, SCIP_CONS* lincons, SCIP_VAR* slackvar)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicIndicatorLinCons(scip, &cons, name, binvar, lincons, slackvar) );

      return cons;
   }

   /* assist function to create a knapsack constraint */
   SCIP_CONS* createConsBasicKnapsack(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Longint* weights, SCIP_Longint capacity)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicKnapsack(scip, &cons, name, nvars, vars, weights, capacity) );

      return cons;
   }

   /* assist function to create a linking constraint */
   SCIP_CONS* createConsBasicLinking(SCIP* scip, const char* name, SCIP_VAR* linkvar, SCIP_VAR** binvars, SCIP_Real* vals, int nbinvars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicLinking(scip, &cons, name, linkvar, binvars, vals, nbinvars) );

      return cons;
   }

   /* assist function to create a logicor constraint */
   SCIP_CONS* createConsBasicLogicor(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicLogicor(scip, &cons, name, nvars, vars) );

      return cons;
   }

   /* assist function to create an orbisack constraint */
   SCIP_CONS* createConsBasicOrbisack(SCIP* scip, const char* name, SCIP_VAR** vars1, SCIP_VAR** vars2, int nrows, SCIP_Bool ispporbisack, SCIP_Bool isparttype, SCIP_Bool ismodelcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicOrbisack(scip, &cons, name, vars1, vars2, nrows, ispporbisack, isparttype, ismodelcons) );

      return cons;
   }

   /* assist function to create an orbitope constraint */
   SCIP_CONS* createConsBasicOrbitope(SCIP* scip, const char* name, SCIP_VAR*** vars, SCIP_ORBITOPETYPE orbitopetype, int nspcons, int nblocks, SCIP_Bool usedynamicprop, SCIP_Bool resolveprop, SCIP_Bool ismodelcons, SCIP_Bool mayinteract)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicOrbitope(scip, &cons, name, vars, orbitopetype, nspcons, nblocks, usedynamicprop, resolveprop, ismodelcons, mayinteract) );

      return cons;
   }

   /* assist function to create an SOS1 constraint */
   SCIP_CONS* createConsBasicSOS1(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* weights)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSOS1(scip, &cons, name, nvars, vars, weights) );

      return cons;
   }

   /* assist function to create an SOS2 constraint */
   SCIP_CONS* createConsBasicSOS2(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* weights)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSOS2(scip, &cons, name, nvars, vars, weights) );

      return cons;
   }

   /* assist function to create a symresack constraint */
   SCIP_CONS* createConsBasicSymresack(SCIP* scip, const char* name, int* perm, SCIP_VAR** vars, int nvars, SCIP_Bool ismodelcons)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicSymresack(scip, &cons, name, perm, vars, nvars, ismodelcons) );

      return cons;
   }

   /* assist function to create a variable bound constraint */
   SCIP_CONS* createConsBasicVarbound(SCIP* scip, const char* name, SCIP_VAR* var, SCIP_VAR* vbdvar, SCIP_Real vbdcoef, SCIP_Real lhs, SCIP_Real rhs)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicVarbound(scip, &cons, name, var, vbdvar, vbdcoef, lhs, rhs) );

      return cons;
   }

   /* assist function to create an xor constraint */
   SCIP_CONS* createConsBasicXor(SCIP* scip, const char* name, SCIP_Bool rhs, int nvars, SCIP_VAR** vars)
   {
      SCIP_CONS* cons;

      SCIP_CALL_ABORT( SCIPcreateConsBasicXor(scip, &cons, name, rhs, nvars, vars) );

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

   /* assist function to create and include an expression handler */
   SCIP_EXPRHDLR* includeObjExprhdlr(SCIP* scip, scip::ObjExprhdlr* objexprhdlr, SCIP_Bool deleteobject)
   {
      SCIP_EXPRHDLR* exprhdlr;

      SCIP_CALL_ABORT( SCIPincludeObjExprhdlr(scip, objexprhdlr, deleteobject, &exprhdlr) );

      return exprhdlr;
   }

   /* assist function to invoke a SCIP_EXPR_INTEVALVAR callback */
   SCIP_INTERVAL invokeExprIntevalvar(SCIP_EXPR_INTEVALVAR* intevalvar, SCIP* scip, SCIP_VAR* var, void* intevalvardata)
   {
      return (*intevalvar)(scip, var, intevalvardata);
   }

   /* assist function to allocate a SYM_EXPRDATA */
   SYM_EXPRDATA* allocSymDataExpr(SCIP* scip, int nconstants, int ncoefficients)
   {
      SYM_EXPRDATA* symdata;
      if (SCIPallocBlockMemory(scip, &symdata) != SCIP_OKAY) {
         return NULL;
      }
      symdata->nconstants = nconstants;
      symdata->ncoefficients = ncoefficients;
      if (nconstants > 0) {
         if (SCIPallocBlockMemoryArray(scip, &symdata->constants, nconstants) != SCIP_OKAY) {
            SCIPfreeBlockMemory(scip, &symdata);
            return NULL;
         }
      }
      if (ncoefficients > 0) {
         if (SCIPallocBlockMemoryArray(scip, &symdata->coefficients, ncoefficients) != SCIP_OKAY) {
            SCIPfreeBlockMemoryArrayNull(scip, &symdata->constants, nconstants);
            SCIPfreeBlockMemory(scip, &symdata);
            return NULL;
         }
         if (SCIPallocBlockMemoryArray(scip, &symdata->children, ncoefficients) != SCIP_OKAY) {
            SCIPfreeBlockMemoryArrayNull(scip, &symdata->coefficients, ncoefficients);            SCIPfreeBlockMemoryArrayNull(scip, &symdata->constants, nconstants);
            SCIPfreeBlockMemory(scip, &symdata);
            return NULL;
         }
      }
      return symdata;
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
%array_functions( SCIP_INTERVAL, SCIP_INTERVAL_array )
%array_functions( SCIP_BOUNDTYPE, SCIP_BoundType_array )
%array_functions( SCIP_EXPRCURV, SCIP_EXPRCURV_array )
%array_functions( SCIP_MONOTONE, SCIP_MONOTONE_array )
%array_functions( char*, String_array )
%array_functions( SCIP_VAR*, SCIP_VAR_array )
%array_functions( SCIP_EXPR*, SCIP_EXPR_array )
%array_functions( SCIP_CONS*, SCIP_CONS_array )
%array_functions( SCIP_SOL*, SCIP_SOL_array )
%array_functions( SCIP_EXPRDATA*, SCIP_EXPRDATA_array )
%array_functions( SYM_EXPRDATA*, SYM_EXPRDATA_array )
%apply SWIGTYPE * {char *char_array_array_getitem};
%apply SWIGTYPE * {char *value};
%typemap(freearg, noblock=1) char *value {}
%array_functions( char*, char_array_array )
%apply char * {char *value};
%array_functions( double*, double_array_array )
%array_functions( SCIP_VAR**, SCIP_VAR_array_array )

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

/** SCIP_BoundType enum */
enum SCIP_BoundType
{
   SCIP_BOUNDTYPE_LOWER = 0,
   SCIP_BOUNDTYPE_UPPER = 1
};
typedef enum SCIP_BoundType SCIP_BOUNDTYPE;

/** SCIP_OrbitopeType enum */
enum SCIP_OrbitopeType
{
   SCIP_ORBITOPETYPE_FULL         = 0,
   SCIP_ORBITOPETYPE_PARTITIONING = 1,
   SCIP_ORBITOPETYPE_PACKING      = 2
};
typedef enum SCIP_OrbitopeType SCIP_ORBITOPETYPE;

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

/* from scip_numerics.h */
%apply SWIGTYPE * {const char* stringptr};
%typemap(freearg, noblock=1) const char* stringptr {}
SCIP_Bool      SCIPparseReal(SCIP* scip, const char* stringptr, SCIP_Real* value, char** endptr);

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

/* from scip_var.h */
void           SCIPcaptureVar(SCIP* scip, SCIP_VAR* var);

/* from scip_expr.h */
void           SCIPcaptureExpr(SCIP_EXPR* expr);
SCIP_RETCODE   SCIPevalExprActivity(SCIP* scip, SCIP_EXPR* expr);
int            SCIPcompareExpr(SCIP* scip, SCIP_EXPR* expr1, SCIP_EXPR* expr2);
SCIP_RETCODE   SCIPparseExpr(SCIP* scip, SCIP_EXPR** expr, const char* stringptr, const char** finalpos, SCIP_EXPR_OWNERCREATE* ownercreate, void* ownercreatedata);
SCIP_EXPRHDLR* SCIPfindExprhdlr(SCIP* scip, const char* name);

/* from scip_cons.h */
void           SCIPcaptureCons(SCIP* scip, SCIP_CONS* cons);

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

/* from objmessagehdlr.h */
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
%typemap(in, noblock=1) (const char *formatstr, ...) {
   $1 = const_cast<char *>("%s"); /* Fix format string to %s */
   if ($input) { /* Get string argument */
     $2 = (char *)jenv->GetStringUTFChars($input, 0);
     if (!$2) return $null;
   }
};
%typemap(freearg, noblock=1) (const char *formatstr, ...) {
   if ($2) jenv->ReleaseStringUTFChars($input, (const char *)$2);
}
void SCIPinfoMessage(SCIP* scip, FILE* file, const char* formatstr, ...);

/* from objcloneable.h */
namespace scip
{
class ObjCloneable
{
public:
   virtual ~ObjCloneable() {}

   /** assignment of polymorphic classes causes slicing and is therefore disabled. */
   ObjCloneable& operator=(const ObjCloneable& o) = delete;

   /** assignment of polymorphic classes causes slicing and is therefore disabled. */
   ObjCloneable& operator=(ObjCloneable&& o) = delete;

   virtual ObjCloneable* clone(SCIP* scip) const;
   virtual SCIP_Bool iscloneable(void) const;
};
} /* namespace scip */

/* from intervalarith.h */
struct SCIP_Interval
{
   SCIP_Real             inf;
   SCIP_Real             sup;
};
typedef struct SCIP_Interval SCIP_INTERVAL;

/* from type_expr.h */
%{
struct SCIP_ExprData {
   jobject jobj;
};
%}
%typemap(memberin) jobject {
   if ($self) {
      if ($1) jenv->DeleteGlobalRef($1);
      $1 = $input;
      if ($1) $1 = jenv->NewGlobalRef($1);
   }
}
typedef struct SCIP_ExprData {
   jobject jobj;
} SCIP_EXPRDATA;
typedef struct SCIP_Exprhdlr SCIP_EXPRHDLR;
typedef struct SCIP_ExprhdlrData SCIP_EXPRHDLRDATA;
typedef unsigned int SCIP_EXPRITER_STAGE;
typedef enum
{
   SCIP_EXPRCURV_UNKNOWN    = 0,
   SCIP_EXPRCURV_CONVEX     = 1,
   SCIP_EXPRCURV_CONCAVE    = 2,
   SCIP_EXPRCURV_LINEAR     = SCIP_EXPRCURV_CONVEX | SCIP_EXPRCURV_CONCAVE
} SCIP_EXPRCURV;
typedef enum
{
   SCIP_MONOTONE_UNKNOWN      = 0,
   SCIP_MONOTONE_INC          = 1,
   SCIP_MONOTONE_DEC          = 2,
   SCIP_MONOTONE_CONST        = SCIP_MONOTONE_INC | SCIP_MONOTONE_DEC
} SCIP_MONOTONE;
const unsigned int SCIP_EXPRITER_ENTEREXPR;
const unsigned int SCIP_EXPRITER_VISITINGCHILD;
const unsigned int SCIP_EXPRITER_VISITEDCHILD;
const unsigned int SCIP_EXPRITER_LEAVEEXPR;
const unsigned int SCIP_EXPRITER_ALLSTAGES;
const int SCIP_EXPR_MAXINITESTIMATES;

/* from pub_expr.h */
SCIP_EXPRDATA* SCIPexprGetData(SCIP_EXPR* expr);
void           SCIPexprSetData(SCIP_EXPR* expr, SCIP_EXPRDATA* data);
int            SCIPexprGetNChildren(SCIP_EXPR* expr);
SCIP_EXPR**    SCIPexprGetChildren(SCIP_EXPR* expr);
SCIP_Real      SCIPexprGetEvalValue(SCIP_EXPR* expr);
SCIP_Real      SCIPexprGetDot(SCIP_EXPR* expr);
SCIP_INTERVAL  SCIPexprGetActivity(SCIP_EXPR* expr);
SCIP_Bool      SCIPexprIsIntegral(SCIP_EXPR* expr);

/* from struct_symmetry.h */
typedef struct SYM_ExprData
{
   SCIP_Real*            constants;
   int                   nconstants;
   SCIP_Real*            coefficients;
   int                   ncoefficients;
   SCIP_EXPR**           children;
} SYM_EXPRDATA;

/* from objexprhdlr.h */
namespace scip
{

class ObjExprhdlr : public ObjCloneable
{
public:
   ObjExprhdlr(SCIP* scip, const char* name, const char* desc,
               unsigned int precedence,
               SCIP_Bool has_copydata,
               SCIP_Bool has_freedata,
               SCIP_Bool has_simplify,
               SCIP_Bool has_compare,
               SCIP_Bool has_print,
               SCIP_Bool has_parse,
               SCIP_Bool has_bwdiff,
               SCIP_Bool has_fwdiff,
               SCIP_Bool has_bwfwdiff,
               SCIP_Bool has_inteval,
               SCIP_Bool has_estimate,
               SCIP_Bool has_initestimates,
               SCIP_Bool has_reverseprop,
               SCIP_Bool has_hash,
               SCIP_Bool has_curvature,
               SCIP_Bool has_monotonicity,
               SCIP_Bool has_integrality,
               SCIP_Bool has_getsymdata);
   ObjExprhdlr(const ObjExprhdlr& o);
   virtual ~ObjExprhdlr();

   /** assignment of polymorphic classes causes slicing and is therefore disabled. */
   ObjExprhdlr& operator=(const ObjExprhdlr& o) = delete;

   /** assignment of polymorphic classes causes slicing and is therefore disabled. */
   ObjExprhdlr& operator=(ObjExprhdlr&& o) = delete;

   virtual SCIP_RETCODE scip_freehdlr(SCIP* scip, SCIP_EXPRHDLR* exprhdlr, SCIP_EXPRHDLRDATA** exprhdlrdata);
   virtual SCIP_RETCODE scip_eval(SCIP* scip, SCIP_EXPR* expr, SCIP_Real* val, SCIP_SOL*  sol) = 0;
   virtual SCIP_RETCODE scip_copydata(SCIP* targetscip, SCIP_EXPRHDLR*  targetexprhdlr, SCIP_EXPRDATA** targetexprdata, SCIP* sourcescip, SCIP_EXPR* sourceexpr);
   virtual SCIP_RETCODE scip_freedata(SCIP* scip, SCIP_EXPR* expr);
   virtual SCIP_RETCODE scip_simplify(SCIP* scip, SCIP_EXPR* expr, SCIP_EXPR** simplifiedexpr, SCIP_EXPR_OWNERCREATE* ownercreate, void* ownercreatedata);
   virtual int scip_compare(SCIP* scip, SCIP_EXPR* expr1, SCIP_EXPR* expr2);
   virtual SCIP_RETCODE scip_print(SCIP* scip, SCIP_EXPR* expr, SCIP_EXPRITER_STAGE stage, int currentchild, unsigned int parentprecedence, FILE* file);
   virtual SCIP_RETCODE scip_parse(SCIP* scip, SCIP_EXPRHDLR* exprhdlr, const char* stringptr, const char** endstring, SCIP_EXPR** expr, SCIP_Bool* success, SCIP_EXPR_OWNERCREATE* ownercreate, void* ownercreatedata);
   virtual SCIP_RETCODE scip_bwdiff (SCIP* scip, SCIP_EXPR* expr, int childidx, SCIP_Real* val);
   virtual SCIP_RETCODE scip_fwdiff(SCIP* scip, SCIP_EXPR* expr, SCIP_Real* dot, SCIP_SOL*  direction);
   virtual SCIP_RETCODE scip_bwfwdiff(SCIP* scip, SCIP_EXPR* expr, int childidx, SCIP_Real* bardot, SCIP_SOL*  direction);
   virtual SCIP_RETCODE scip_inteval(SCIP* scip, SCIP_EXPR* expr, SCIP_INTERVAL* interval, SCIP_EXPR_INTEVALVAR *intevalvar, void* intevalvardata);
   virtual SCIP_RETCODE scip_estimate(SCIP* scip, SCIP_EXPR* expr, SCIP_INTERVAL* localbounds, SCIP_INTERVAL* globalbounds, SCIP_Real* refpoint, SCIP_Bool overestimate, SCIP_Real targetvalue, SCIP_Real* coefs, SCIP_Real* constant, SCIP_Bool* islocal, SCIP_Bool* success, SCIP_Bool* branchcand);
   virtual SCIP_RETCODE scip_initestimates(SCIP* scip, SCIP_EXPR* expr, SCIP_INTERVAL* bounds, SCIP_Bool overestimate, SCIP_Real* coefs[SCIP_EXPR_MAXINITESTIMATES], SCIP_Real constant[SCIP_EXPR_MAXINITESTIMATES], int* nreturned);
   virtual SCIP_RETCODE scip_reverseprop(SCIP* scip, SCIP_EXPR* expr, SCIP_INTERVAL  bounds, SCIP_INTERVAL* childrenbounds, SCIP_Bool* infeasible);
   virtual SCIP_RETCODE scip_hash(SCIP* scip, SCIP_EXPR* expr, unsigned int* hashkey, unsigned int* childrenhashes);
   virtual SCIP_RETCODE scip_curvature(SCIP* scip, SCIP_EXPR* expr, SCIP_EXPRCURV  exprcurvature, SCIP_Bool* success, SCIP_EXPRCURV* childcurv);
   virtual SCIP_RETCODE scip_monotonicity(SCIP* scip, SCIP_EXPR* expr, int childidx, SCIP_MONOTONE* result);
   virtual SCIP_RETCODE scip_integrality(SCIP* scip, SCIP_EXPR* expr, SCIP_Bool* integrality);
   virtual SCIP_RETCODE scip_getsymdata(SCIP* scip, SCIP_EXPR* expr, SYM_EXPRDATA** symdata);
};

} /* namespace scip */

scip::ObjExprhdlr* SCIPfindObjExprhdlr(SCIP* scip, const char* name);
scip::ObjExprhdlr* SCIPgetObjExprhdlr(SCIP* scip, SCIP_EXPRHDLR* exprhdlr);

/* assist functions */
SCIP*          createSCIP();
void           freeSCIP(SCIP* scip);
SCIP_VAR*      createVar(SCIP* scip, const char* name, SCIP_Real lb, SCIP_Real ub, SCIP_Real obj, SCIP_VARTYPE vartype);
void           releaseVar(SCIP* scip, SCIP_VAR* var);
SCIP_EXPR*     createExpr(SCIP* scip, SCIP_EXPRHDLR* exprhdlr, SCIP_EXPRDATA* exprdata, int nchildren, SCIP_EXPR** children, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprAbs(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprEntropy(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprExp(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprLog(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprPow(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprSignpower(SCIP* scip, SCIP_EXPR* child, SCIP_Real exponent, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprProduct(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real coefficient, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprSum(SCIP* scip, int nchildren, SCIP_EXPR** children, SCIP_Real* coefficients, SCIP_Real constant, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprSin(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprCos(SCIP* scip, SCIP_EXPR* child, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprValue(SCIP* scip, SCIP_Real value, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
SCIP_EXPR*     createExprVar(SCIP* scip, SCIP_VAR *var, SCIP_EXPR_OWNERCREATE* ownercreate, void *ownercreatedata);
void           releaseExpr(SCIP* scip, SCIP_EXPR* expr);
SCIP_CONS*     createConsBasicLinear(SCIP* scip, const char* name , int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicQuadratic(SCIP* scip, const char* name, int nlinvars, SCIP_VAR** linvars, SCIP_Real* lincoefs, int nquadvars, SCIP_VAR** quadvars1, SCIP_VAR** quadvars2, SCIP_Real* quadcoefs, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicNonlinear(SCIP* scip, const char* name, SCIP_EXPR* expr, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicSuperindicator(SCIP *scip, const char *name, SCIP_VAR *binvar, SCIP_CONS *slackcons);
SCIP_CONS*     createConsBasicPseudoboolean(SCIP* scip, const char* name, SCIP_VAR** linvars, int nlinvars, SCIP_Real* linvals, SCIP_VAR*** terms, int nterms, int* ntermvars, SCIP_Real* termvals, SCIP_VAR* indvar, SCIP_Real weight, SCIP_Bool issoftcons, SCIP_VAR* intvar, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicSetpart(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicSetpack(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicSetcover(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicSOC(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* coefs, SCIP_Real* offsets, SCIP_Real constant, SCIP_VAR* rhsvar, SCIP_Real rhscoeff, SCIP_Real rhsoffset);
SCIP_CONS*     createConsBasicSignpower(SCIP* scip, const char* name, SCIP_VAR* x, SCIP_VAR* z, SCIP_Real exponent, SCIP_Real xoffset, SCIP_Real zcoef, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicAnd(SCIP* scip, const char* name, SCIP_VAR* resvar, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicOr(SCIP* scip, const char* name, SCIP_VAR* resvar, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicBounddisjunction(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_BOUNDTYPE* boundtypes, SCIP_Real* bounds);
SCIP_CONS*     createConsBasicBounddisjunctionRedundant(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_BOUNDTYPE* boundtypes, SCIP_Real* bounds);
SCIP_CONS*     createConsBasicCardinality(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, int cardval, SCIP_VAR** indvars, SCIP_Real* weights);
SCIP_CONS*     createConsBasicConjunction(SCIP* scip, const char* name, int nconss, SCIP_CONS** conss);
SCIP_CONS*     createConsBasicDisjunction(SCIP* scip, const char* name, int nconss, SCIP_CONS** conss, SCIP_CONS* relaxcons);
SCIP_CONS*     createConsBasicCumulative(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, int* durations, int* demands, int capacity);
SCIP_CONS*     createConsBasicIndicator(SCIP* scip, const char* name, SCIP_VAR* binvar, int nvars, SCIP_VAR** vars, SCIP_Real* vals, SCIP_Real rhs);
SCIP_CONS*     createConsBasicIndicatorLinCons(SCIP* scip, const char* name, SCIP_VAR* binvar, SCIP_CONS* lincons, SCIP_VAR* slackvar);
SCIP_CONS*     createConsBasicKnapsack(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Longint* weights, SCIP_Longint capacity);
SCIP_CONS*     createConsBasicLinking(SCIP* scip, const char* name, SCIP_VAR* linkvar, SCIP_VAR** binvars, SCIP_Real* vals, int nbinvars);
SCIP_CONS*     createConsBasicLogicor(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars);
SCIP_CONS*     createConsBasicOrbisack(SCIP* scip, const char* name, SCIP_VAR** vars1, SCIP_VAR** vars2, int nrows, SCIP_Bool ispporbisack, SCIP_Bool isparttype, SCIP_Bool ismodelcons);
SCIP_CONS*     createConsBasicOrbitope(SCIP* scip, const char* name, SCIP_VAR*** vars, SCIP_ORBITOPETYPE orbitopetype, int nspcons, int nblocks, SCIP_Bool usedynamicprop, SCIP_Bool resolveprop, SCIP_Bool ismodelcons, SCIP_Bool mayinteract);
SCIP_CONS*     createConsBasicSOS1(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* weights);
SCIP_CONS*     createConsBasicSOS2(SCIP* scip, const char* name, int nvars, SCIP_VAR** vars, SCIP_Real* weights);
SCIP_CONS*     createConsBasicSymresack(SCIP* scip, const char* name, int* perm, SCIP_VAR** vars, int nvars, SCIP_Bool ismodelcons);
SCIP_CONS*     createConsBasicVarbound(SCIP* scip, const char* name, SCIP_VAR* var, SCIP_VAR* vbdvar, SCIP_Real vbdcoef, SCIP_Real lhs, SCIP_Real rhs);
SCIP_CONS*     createConsBasicXor(SCIP* scip, const char* name, SCIP_Bool rhs, int nvars, SCIP_VAR** vars);
void           releaseCons(SCIP* scip, SCIP_CONS* cons);
SCIP_MESSAGEHDLR* createObjMessagehdlr(scip::ObjMessagehdlr* objmessagehdlr, SCIP_Bool deleteobject);
SCIP_EXPRHDLR* includeObjExprhdlr(SCIP* scip, scip::ObjExprhdlr* objexprhdlr, SCIP_Bool deleteobject);
SCIP_INTERVAL  invokeExprIntevalvar(SCIP_EXPR_INTEVALVAR* intevalvar, SCIP* scip, SCIP_VAR* var, void* intevalvardata);
SYM_EXPRDATA*  allocSymDataExpr(SCIP* scip, int nconstants, int ncoefficients);
