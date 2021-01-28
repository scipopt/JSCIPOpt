package jscip;

/** Class representing a single SCIP instance (equivalent of SCIP).*/
public class Scip
{
   private SWIGTYPE_p_SCIP _scipptr; /** pointer address class created by SWIG */

   /* helper function to check a SCIP retcode */
   private static void CHECK_RETCODE(SCIP_Retcode retcode)
   {
      assert(retcode == SCIP_Retcode.SCIP_OKAY);
   }

   /** default constructor */
   public Scip()
   {
      _scipptr = null;
   }

   /** creates the C data for a SCIP; it also creates the problem and incudes all default plug-ins */
   public void create(String probname)
   {
      assert(_scipptr == null);
      _scipptr = SCIPJNI.createSCIP();
      CHECK_RETCODE( SCIPJNI.SCIPcreateProbBasic(_scipptr, probname) );
      CHECK_RETCODE( SCIPJNI.SCIPincludeDefaultPlugins(_scipptr) );
   }

   /** frees the C data of a SCIP; this function has to be called after create() has been called */
   public void free()
   {
      assert(_scipptr != null);
      SCIPJNI.freeSCIP(_scipptr);
   }

   /** wraps SCIPsolve() */
   public void solve()
   {
      CHECK_RETCODE( SCIPJNI.SCIPsolve(_scipptr) );
   }

   /** wraps SCIPsolveConcurrent() */
   public void solveConcurrent()
   {
      CHECK_RETCODE( SCIPJNI.SCIPsolveConcurrent(_scipptr) );
   }

   /** wraps SCIPsetMessagehdlrQuiet() */
   public void hideOutput(boolean quite)
   {
      if( quite )
         SCIPJNI.SCIPsetMessagehdlrQuiet(_scipptr, 1);
      else
         SCIPJNI.SCIPsetMessagehdlrQuiet(_scipptr, 0);
   }

   /** wraps SCIPreadProb() */
   public void readProb(String filename)
   {
      CHECK_RETCODE( SCIPJNI.SCIPreadProb(_scipptr, filename, null) );
   }

   /** wraps SCIPreadParams() */
   public void readParams(String filename)
   {
      CHECK_RETCODE( SCIPJNI.SCIPreadParams(_scipptr, filename) );
   }

   /** wraps SCIPsetBoolParam() */
   public void setBoolParam(String name, boolean value)
   {
      if( value )
         CHECK_RETCODE( SCIPJNI.SCIPsetBoolParam(_scipptr, name, 1) );
      else
         CHECK_RETCODE( SCIPJNI.SCIPsetBoolParam(_scipptr, name, 0) );
   }

   /** wraps SCIPsetIntParam() */
   public void setIntParam(String name, int value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetIntParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetLongintParam() */
   public void setLongintParam(String name, int value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetLongintParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetRealParam() */
   public void setRealParam(String name, double value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetRealParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetCharParam() */
   public void setCharParam(String name, char value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetCharParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetStringParam() */
   public void setStringParam(String name, String value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetStringParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetEmphasis() */
   public void setEmphasis(SCIP_ParamEmphasis paramEmphasis, boolean quite)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetEmphasis(_scipptr, paramEmphasis, quite ? 1 : 0) );
   }

   /** wraps SCIPwriteOrigProblem() */
   public void writeOrigProblem(String filename)
   {
      CHECK_RETCODE( SCIPJNI.SCIPwriteOrigProblem(_scipptr, filename, null, 0) );
   }

   /** wraps SCIPwriteTransProblem() */
   public void writeTransProblem(String filename)
   {
      CHECK_RETCODE( SCIPJNI.SCIPwriteTransProblem(_scipptr, filename, null, 0 ));
   }

   /** wraps SCIPprintStatistics() */
   public void printStatistics()
   {
      CHECK_RETCODE( SCIPJNI.SCIPprintStatistics(_scipptr, null) );
   }

   /** wraps SCIPprintBestSol() */
   public void printBestSol(boolean printzeros)
   {
      if( printzeros )
         CHECK_RETCODE( SCIPJNI.SCIPprintBestSol(_scipptr, null, 1) );
      else
         CHECK_RETCODE( SCIPJNI.SCIPprintBestSol(_scipptr, null, 0) );
   }

   /** wraps SCIPaddVar() */
   public Variable createVar(String name, double lb, double ub, double obj, SCIP_Vartype vartype)
   {
      Variable var =  new Variable(SCIPJNI.createVar(_scipptr, name, lb, ub, obj, vartype));
      assert(var.getPtr() != null);
      CHECK_RETCODE( SCIPJNI.SCIPaddVar(_scipptr, var.getPtr()) );
      return var;
   }

   /** wraps SCIPchgVarObj() */
   public void changeVarObj(Variable var, double obj)
   {
      assert(var.getPtr() != null);
      CHECK_RETCODE( SCIPJNI.SCIPchgVarObj(_scipptr, var.getPtr(), obj));
   }

   /** wraps SCIPreleaseVar() */
   public void releaseVar(Variable var)
   {
      assert(var.getPtr() != null);
      SCIPJNI.releaseVar(_scipptr, var.getPtr());
      var.setPtr(null);
   }

   /** wraps SCIPgetNVars() */
   public int getNVars()
   {
      return SCIPJNI.SCIPgetNVars(_scipptr);
   }

   /** wraps SCIPgetVars() */
   public Variable[] getVars()
   {
      int nvars = getNVars();
      Variable[] vars = new Variable[nvars];
      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.SCIPgetVars(_scipptr);

      for( int i = 0; i < nvars; ++i )
      {
         vars[i] = new Variable(SCIPJNI.SCIP_VAR_array_getitem(varsptr, i));
      }
      return vars;
   }

   /** wraps SCIPcreateConsBasicLinear(); note that the function need to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsLinear(String name, Variable[] vars, double[] vals, double lhs, double rhs)
   {
      int nvars;

      assert(vars.length == vals.length);
      assert(lhs <= rhs);

      nvars = vars.length;

      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);
      SWIGTYPE_p_double valsptr = SCIPJNI.new_double_array(nvars);

      for( int i = 0; i < nvars; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         SCIPJNI.double_array_setitem(valsptr, i, vals[i]);
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicLinear(_scipptr, name, nvars, varsptr, valsptr, lhs, rhs);
      assert(consptr != null);

      SCIPJNI.delete_double_array(valsptr);
      SCIPJNI.delete_SCIP_VAR_array(varsptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicQuadratic(); note that the function need to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsQuadratic(String name, Variable[] quadvars1, Variable[] quadvars2, double[] quadcoefs, Variable[] linvars, double[] lincoefs, double lhs, double rhs)
   {
      assert(lhs <= rhs);

      SWIGTYPE_p_p_SCIP_VAR quadvars1ptr = null;
      SWIGTYPE_p_p_SCIP_VAR quadvars2ptr = null;
      SWIGTYPE_p_double quadcoefsptr = null;
      SWIGTYPE_p_p_SCIP_VAR linvarsptr = null;
      SWIGTYPE_p_double lincoefsptr = null;

      int nlinvars = 0;
      int nquadvars = 0;

      // copy linear variables
      if( linvars != null )
      {
         nlinvars = linvars.length;

         assert(nlinvars == lincoefs.length);
         linvarsptr = SCIPJNI.new_SCIP_VAR_array(nlinvars);
         lincoefsptr = SCIPJNI.new_double_array(nlinvars);

         for( int i = 0; i < nlinvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(linvarsptr, i, linvars[i].getPtr());
            SCIPJNI.double_array_setitem(lincoefsptr, i, lincoefs[i]);
         }
      }

      // copy quadratic terms
      if( quadvars1 != null )
      {
         nquadvars = quadvars1.length;

         assert(quadvars2 != null && quadcoefs != null);
         assert(quadvars2.length == nquadvars);
         assert(quadcoefs.length == nquadvars);

         quadvars1ptr = SCIPJNI.new_SCIP_VAR_array(nquadvars);
         quadvars2ptr = SCIPJNI.new_SCIP_VAR_array(nquadvars);
         quadcoefsptr = SCIPJNI.new_double_array(nquadvars);

         for( int i = 0; i < nquadvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(quadvars1ptr, i, quadvars1[i].getPtr());
            SCIPJNI.SCIP_VAR_array_setitem(quadvars2ptr, i, quadvars2[i].getPtr());
            SCIPJNI.double_array_setitem(quadcoefsptr, i, quadcoefs[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicQuadratic(_scipptr, name, nlinvars, linvarsptr, lincoefsptr, nquadvars, quadvars1ptr, quadvars2ptr, quadcoefsptr, lhs, rhs);
      assert(consptr != null);

      if( quadvars1ptr != null )
      {
         SCIPJNI.delete_double_array(quadcoefsptr);
         SCIPJNI.delete_SCIP_VAR_array(quadvars2ptr);
         SCIPJNI.delete_SCIP_VAR_array(quadvars1ptr);

      }

      if( linvarsptr != null )
      {
         SCIPJNI.delete_double_array(lincoefsptr);
         SCIPJNI.delete_SCIP_VAR_array(linvarsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSuperindicator() */
   public Constraint createConsBasicSuperIndicator(String name, Variable binVar, Constraint slackCons)
   {
      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSuperIndicator(_scipptr, name, binVar.getPtr(), slackCons.getPtr());
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** wraps SCIPaddCons() */
   public void addCons(Constraint cons)
   {
      assert(cons.getPtr() != null);
      CHECK_RETCODE( SCIPJNI.SCIPaddCons(_scipptr, cons.getPtr()) );
   }

   /** wraps releaseCons() */
   public void releaseCons(Constraint cons)
   {
      assert(cons.getPtr() != null);

      SCIPJNI.releaseCons(_scipptr, cons.getPtr());
      cons.setPtr(null);
   }

   /** wraps SCIPgetNSols() */
   public int getNSols()
   {
      return SCIPJNI.SCIPgetNSols(_scipptr);
   }

   /** wraps SCIPgetBestSol(); returns null if SCIP did not find a solution */
   public Solution getBestSol()
   {
      if( getNSols() == 0 )
         return null;

      SWIGTYPE_p_SCIP_SOL solptr = SCIPJNI.SCIPgetBestSol(_scipptr);
      assert(solptr != null);

      return new Solution(solptr);
   }

   /** wraps SCIPgetSols(); returns null if SCIP did not find a solution */
   public Solution[] getSols()
   {
      if( getNSols() == 0 )
         return null;

      SWIGTYPE_p_p_SCIP_SOL solsptr = SCIPJNI.SCIPgetSols(_scipptr);
      Solution[] sols =  new Solution[getNSols()];

      for( int i = 0; i < getNSols(); ++i )
      {
         sols[i] = new Solution(SCIPJNI.SCIP_SOL_array_getitem(solsptr, i));
      }

      return sols;
   }

   /** wraps SCIPgetSolVal() */
   public double getSolVal(Solution sol, Variable var)
   {
      assert(sol != null && sol.getPtr() != null);
      assert(var != null && var.getPtr() != null);

      return SCIPJNI.SCIPgetSolVal(_scipptr, sol.getPtr(), var.getPtr());
   }

   /** wraps SCIPgetSolOrigObj() */
   public double getSolOrigObj(Solution sol)
   {
      assert(sol != null && sol.getPtr() != null);
      return SCIPJNI.SCIPgetSolOrigObj(_scipptr, sol.getPtr());
   }

   /** wraps SCIPinfinity() */
   public double infinity()
   {
      return SCIPJNI.SCIPinfinity(_scipptr);
   }

   /** wraps SCIPepsilon() */
   public double epsilon()
   {
      return SCIPJNI.SCIPepsilon(_scipptr);
   }

   /** wraps SCIPfeastol() */
   public double feastol()
   {
      return SCIPJNI.SCIPfeastol(_scipptr);
   }

   /** wraps SCIPsetObjsense() */
   public void setMaximize()
   {
      SCIPJNI.SCIPsetObjsense(_scipptr, SCIP_Objsense.SCIP_OBJSENSE_MAXIMIZE);
   }

   /** wraps SCIPsetObjsense(); minimize is the default */
   public void setMinimize() {
      SCIPJNI.SCIPsetObjsense(_scipptr, SCIP_Objsense.SCIP_OBJSENSE_MINIMIZE);
   }

   /** wraps SCIPgetObjsense() */
   public boolean maximization()
   {
      return SCIPJNI.SCIPgetObjsense(_scipptr) == SCIP_Objsense.SCIP_OBJSENSE_MAXIMIZE;
   }

   /** wraps SCIPgetObjsense() */
   public boolean minimization()
   {
      return SCIPJNI.SCIPgetObjsense(_scipptr) == SCIP_Objsense.SCIP_OBJSENSE_MINIMIZE;
   }

   /** wraps SCIPchgVarBranchPriority() */
   public void chgVarBranchPriority(Variable var, int branchpriority)
   {
      assert(var != null && var.getPtr() != null);
      CHECK_RETCODE( SCIPJNI.SCIPchgVarBranchPriority(_scipptr, var.getPtr(), branchpriority) );
   }

   /** wraps SCIPcreateSol() */
   public Solution createSol()
   {
      SWIGTYPE_p_p_SCIP_SOL solptr = SCIPJNI.new_SCIP_SOL_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPcreateSol(_scipptr, solptr, null) );
      Solution sol = new Solution(SCIPJNI.SCIP_SOL_array_getitem(solptr, 0));
      SCIPJNI.delete_SCIP_SOL_array(solptr);
      return sol;
   }

   /** wraps SCIPsetSolVal() */
   public void setSolVal(Solution sol, Variable var, double val)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetSolVal(_scipptr, sol.getPtr(), var.getPtr(), val) );
   }

   /** wraps SCIPsetSolVals() */
   public void setSolVals(Solution sol, Variable[] vars, double[] vals)
   {
      int nvars = vars.length;
      assert(vars.length == vals.length);

      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);
      SWIGTYPE_p_double valsptr = SCIPJNI.new_double_array(nvars);

      for( int i = 0; i < nvars; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         SCIPJNI.double_array_setitem(valsptr, i, vals[i]);
      }

      CHECK_RETCODE( SCIPJNI.SCIPsetSolVals(_scipptr, sol.getPtr(), nvars, varsptr, valsptr) );

      SCIPJNI.delete_double_array(valsptr);
      SCIPJNI.delete_SCIP_VAR_array(varsptr);
   }

   /** wraps SCIPaddSolFree() */
   public boolean addSolFree(Solution sol)
   {
      SWIGTYPE_p_p_SCIP_SOL arr = SCIPJNI.new_SCIP_SOL_array(1);
      SWIGTYPE_p_unsigned_int stored = SCIPJNI.new_unsigned_int_array(1);
      SCIPJNI.SCIP_SOL_array_setitem(arr, 0, sol.getPtr());

      // add and free solution
      CHECK_RETCODE( SCIPJNI.SCIPaddSolFree(_scipptr, arr, stored) );

      // get success value and free memory
      boolean succ = SCIPJNI.unsigned_int_array_getitem(stored, 0) != 0;
      SCIPJNI.delete_unsigned_int_array(stored);
      SCIPJNI.delete_SCIP_SOL_array(arr);
      return succ;
   }

   /** wraps SCIPgetGap() */
   public double getGap()
   {
      return SCIPJNI.SCIPgetGap(_scipptr);
   }
}
