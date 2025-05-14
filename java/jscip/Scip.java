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

   /** wraps SCIPinterruptSolve() */
   public void interruptSolve()
   {
      CHECK_RETCODE( SCIPJNI.SCIPinterruptSolve(_scipptr) );
   }

   /** wraps SCIPisSolveInterrupted() */
   public boolean isSolveInterrupted()
   {
      return SCIPJNI.SCIPisSolveInterrupted(_scipptr) != 0;
   }

   /** wraps SCIPgetStatus() */
   public SCIP_Status getStatus()
   {
      return SCIPJNI.SCIPgetStatus(_scipptr);
   }

   /** wraps SCIPgetStage() */
   public SCIP_Stage getStage()
   {
      return SCIPJNI.SCIPgetStage(_scipptr);
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

   /** wraps SCIPgetBoolParam() */
   public boolean getBoolParam(String name)
   {
      SWIGTYPE_p_unsigned_int value = SCIPJNI.new_unsigned_int_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetBoolParam(_scipptr, name, value) );
      long ret = SCIPJNI.unsigned_int_array_getitem(value, 0);
      SCIPJNI.delete_unsigned_int_array(value);
      return (ret != 0);
   }

   /** wraps SCIPgetIntParam() */
   public int getIntParam(String name)
   {
      SWIGTYPE_p_int value = SCIPJNI.new_int_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetIntParam(_scipptr, name, value) );
      int ret = SCIPJNI.int_array_getitem(value, 0);
      SCIPJNI.delete_int_array(value);
      return ret;
   }

   /** wraps SCIPgetLongintParam() */
   public long getLongintParam(String name)
   {
      SWIGTYPE_p_long_long value = SCIPJNI.new_long_long_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetLongintParam(_scipptr, name, value) );
      long ret = SCIPJNI.long_long_array_getitem(value, 0);
      SCIPJNI.delete_long_long_array(value);
      return ret;
   }

   /** wraps SCIPgetRealParam() */
   public double getRealParam(String name)
   {
      SWIGTYPE_p_double value = SCIPJNI.new_double_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetRealParam(_scipptr, name, value) );
      double ret = SCIPJNI.double_array_getitem(value, 0);
      SCIPJNI.delete_double_array(value);
      return ret;
   }

   /** wraps SCIPgetCharParam() */
   public char getCharParam(String name)
   {
      SWIGTYPE_p_char value = SCIPJNI.new_char_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetCharParam(_scipptr, name, value) );
      char ret = SCIPJNI.char_array_getitem(value, 0);
      SCIPJNI.delete_char_array(value);
      return ret;
   }

   /** wraps SCIPgetStringParam() */
   public String getStringParam(String name)
   {
      SWIGTYPE_p_p_char value = SCIPJNI.new_String_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPgetStringParam(_scipptr, name, value) );
      String ret = SCIPJNI.String_array_getitem(value, 0);
      SCIPJNI.delete_String_array(value);
      return ret;
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
   public void setLongintParam(String name, long value)
   {
      CHECK_RETCODE( SCIPJNI.SCIPsetLongintParam(_scipptr, name, value) );
   }

   /** wraps SCIPsetLongintParam(). Overload to preserve binary
       compatibility. */
   public void setLongintParam(String name, int value)
   {
      setLongintParam(name, (long) value);
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

   /** wraps SCIPgetNOrigVars() */
   public int getNOrigVars()
   {
      return SCIPJNI.SCIPgetNOrigVars(_scipptr);
   }

   /** wraps SCIPgetOrigVars() */
   public Variable[] getOrigVars()
   {
      int nvars = getNOrigVars();
      Variable[] vars = new Variable[nvars];
      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.SCIPgetOrigVars(_scipptr);

      for( int i = 0; i < nvars; ++i )
      {
         vars[i] = new Variable(SCIPJNI.SCIP_VAR_array_getitem(varsptr, i));
      }
      return vars;
   }

   /** wraps SCIPcreateExprAbs() */
   public Expression createExprAbs(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprAbs(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprEntropy() */
   public Expression createExprEntropy(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprEntropy(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprExp() */
   public Expression createExprExp(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprExp(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprLog() (ln) */
   public Expression createExprLog(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprLog(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprPow() */
   public Expression createExprPow(Expression child, double exponent)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprPow(_scipptr, child.getPtr(), exponent);
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprSignpower() */
   public Expression createExprSignpower(Expression child, double exponent)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprSignpower(_scipptr, child.getPtr(), exponent);
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprProduct(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Expression createExprProduct(Expression[] children, double coefficient)
   {
      assert(children != null);
      int nchildren = children.length;

      SWIGTYPE_p_p_SCIP_EXPR childrenptr = SCIPJNI.new_SCIP_EXPR_array(nchildren);

      for( int i = 0; i < nchildren; ++i )
      {
         SCIPJNI.SCIP_EXPR_array_setitem(childrenptr, i, children[i].getPtr());
      }

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprProduct(_scipptr, nchildren, childrenptr, coefficient);
      assert(exprptr != null);

      SCIPJNI.delete_SCIP_EXPR_array(childrenptr);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprSum(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Expression createExprSum(Expression[] children, double[] coefficients, double constant)
   {
      assert(children != null);
      assert(coefficients == null || children.length == coefficients.length);
      int nchildren = children.length;

      SWIGTYPE_p_p_SCIP_EXPR childrenptr = SCIPJNI.new_SCIP_EXPR_array(nchildren);
      SWIGTYPE_p_double coefficientsptr = coefficients != null ? SCIPJNI.new_double_array(nchildren) : null;

      for( int i = 0; i < nchildren; ++i )
      {
         SCIPJNI.SCIP_EXPR_array_setitem(childrenptr, i, children[i].getPtr());
         if (coefficients != null) {
            SCIPJNI.double_array_setitem(coefficientsptr, i, coefficients[i]);
         }
      }

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprSum(_scipptr, nchildren, childrenptr, coefficientsptr, constant);
      assert(exprptr != null);

      SCIPJNI.delete_SCIP_EXPR_array(childrenptr);
      if (coefficientsptr != null) {
         SCIPJNI.delete_double_array(coefficientsptr);
      }

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprSin() */
   public Expression createExprSin(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprSin(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprCos() */
   public Expression createExprCos(Expression child)
   {
      assert(child != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprCos(_scipptr, child.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprValue() */
   public Expression createExprValue(double value)
   {
      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprValue(_scipptr, value);
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPcreateExprVar() */
   public Expression createExprVar(Variable var)
   {
      assert(var != null);

      SWIGTYPE_p_SCIP_EXPR exprptr = SCIPJNI.createExprVar(_scipptr, var.getPtr());
      assert(exprptr != null);

      return new Expression(exprptr);
   }

   /** wraps SCIPreleaseExpr() */
   public void releaseExpr(Expression expr)
   {
      assert(expr.getPtr() != null);
      SCIPJNI.releaseExpr(_scipptr, expr.getPtr());
      expr.setPtr(null);
   }

   /** wraps SCIPcreateConsBasicLinear(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
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

   /** wraps SCIPcreateConsBasicQuadraticNonlinear(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
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
   public Constraint createConsSuperindicator(String name, Variable binvar, Constraint slackcons)
   {
      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSuperindicator(_scipptr, name, binvar.getPtr(), slackcons.getPtr());
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** deprecated old spelling of createConsSuperindicator (we do not include
       "Basic" in the Java names - if anybody wants to add the full version, it
       can be an overload with the same name -, and the spelling should be
       Superindicator, not SuperIndicator) */
   public Constraint createConsBasicSuperIndicator(String name, Variable binvar, Constraint slackcons)
   {
       return createConsSuperindicator(name, binvar, slackcons);
   }

   /** wraps SCIPcreateConsBasicNonlinear() */
   public Constraint createConsNonlinear(String name, Expression expr, double lhs, double rhs)
   {
      assert(expr != null);
      assert(lhs <= rhs);

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicNonlinear(_scipptr, name, expr.getPtr(), lhs, rhs);
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicPseudoboolean(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsPseudoboolean(String name, Variable[] linvars, double[] linvals, Variable[][] terms, double[] termvals, Variable indvar, double weight, boolean issoftcons, double lhs, double rhs)
   {
      assert(lhs <= rhs);
      assert(((linvars == null || linvars.length == 0) && (linvals == null || linvals.length == 0)) || (linvars != null && linvals != null && linvars.length == linvals.length));
      assert(((terms == null || terms.length == 0) && (termvals == null || termvals.length == 0)) || (terms != null && termvals != null && terms.length == termvals.length));
      assert(issoftcons == (indvar != null));

      int nlinvars = 0;
      SWIGTYPE_p_p_SCIP_VAR linvarsptr = null;
      SWIGTYPE_p_double linvalsptr = null;
      int nterms = 0;
      SWIGTYPE_p_p_p_SCIP_VAR termsptr = null;
      SWIGTYPE_p_int ntermvarsptr = null;
      SWIGTYPE_p_double termvalsptr = null;

      // copy linear variables
      if( linvars != null && linvals != null )
      {
         nlinvars = linvars.length;

         linvarsptr = SCIPJNI.new_SCIP_VAR_array(nlinvars);
         linvalsptr = SCIPJNI.new_double_array(nlinvars);

         for( int i = 0; i < nlinvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(linvarsptr, i, linvars[i].getPtr());
            SCIPJNI.double_array_setitem(linvalsptr, i, linvals[i]);
         }
      }

      // copy nonlinear terms
      if( terms != null && termvals != null )
      {
         nterms = terms.length;

         termsptr = SCIPJNI.new_SCIP_VAR_array_array(nterms);
         ntermvarsptr = SCIPJNI.new_int_array(nterms);
         termvalsptr = SCIPJNI.new_double_array(nterms);

         for( int i = 0; i < nterms; ++i )
         {
            Variable[] termvars = terms[i];
            int ntermvars = (termvars != null) ? termvars.length : 0;
            SWIGTYPE_p_p_SCIP_VAR termvarsptr = null;
            if (ntermvars != 0) {
               termvarsptr = SCIPJNI.new_SCIP_VAR_array(ntermvars);
               for( int j = 0; j < ntermvars; ++j )
               {
                  SCIPJNI.SCIP_VAR_array_setitem(termvarsptr, j, termvars[j].getPtr());
               }
            }
            SCIPJNI.SCIP_VAR_array_array_setitem(termsptr, i, termvarsptr);
            SCIPJNI.double_array_setitem(termvalsptr, i, termvals[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicPseudoboolean(_scipptr, name, linvarsptr, nlinvars, linvalsptr, termsptr, nterms, ntermvarsptr, termvalsptr, (indvar != null) ? indvar.getPtr() : null, weight, issoftcons ? 1 : 0, null, lhs, rhs);
      assert(consptr != null);

      if( termsptr != null )
      {
         SCIPJNI.delete_double_array(termvalsptr);
         SCIPJNI.delete_int_array(ntermvarsptr);
         for( int i = 0; i < nterms; ++i )
         {
            SWIGTYPE_p_p_SCIP_VAR termvarsptr = SCIPJNI.SCIP_VAR_array_array_getitem(termsptr, i);
            if( termvarsptr != null )
            {
               SCIPJNI.delete_SCIP_VAR_array(termvarsptr);
            }
         }
         SCIPJNI.delete_SCIP_VAR_array_array(termsptr);

      }

      if( linvarsptr != null )
      {
         SCIPJNI.delete_double_array(linvalsptr);
         SCIPJNI.delete_SCIP_VAR_array(linvarsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSetpart(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsSetpart(String name, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSetpart(_scipptr, name, nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSetpack(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsSetpack(String name, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSetpack(_scipptr, name, nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSetcover(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsSetcover(String name, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSetcover(_scipptr, name, nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSOCNonlinear(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsSOC(String name, Variable[] vars, double[] coefs, double[] offsets, double constant, Variable rhsvar, double rhscoeff, double rhsoffset)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_double coefsptr = null;
      SWIGTYPE_p_double offsetsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy coefficients
      assert(coefs == null || coefs.length == nvars);
      if( coefs != null )
      {
         coefsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(coefsptr, i, coefs[i]);
         }
      }

      // copy offsets
      assert(offsets == null || offsets.length == nvars);
      if( offsets != null )
      {
         offsetsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(offsetsptr, i, offsets[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSOC(_scipptr, name, nvars, varsptr, coefsptr, offsetsptr, constant, rhsvar.getPtr(), rhscoeff, rhsoffset);
      assert(consptr != null);

      if ( offsetsptr != null ) {
         SCIPJNI.delete_double_array(offsetsptr);
      }
      if ( coefsptr != null ) {
         SCIPJNI.delete_double_array(coefsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSignpowerNonlinear() */
   public Constraint createConsSignpower(String name, Variable x, Variable z, double exponent, double xoffset, double zcoef, double lhs, double rhs)
   {
      assert(lhs <= rhs);

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSignpower(_scipptr, name, x.getPtr(), z.getPtr(), exponent, xoffset, zcoef, lhs, rhs);
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicAnd(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsAnd(String name, Variable resvar, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicAnd(_scipptr, name, resvar.getPtr(), nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicOr(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsOr(String name, Variable resvar, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicOr(_scipptr, name, resvar.getPtr(), nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicBounddisjunction(Redundant)(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsBounddisjunction(String name, Variable[] vars, SCIP_BoundType[] boundtypes, double[] bounds, boolean allowRedundant)
   {
      assert(vars != null && boundtypes != null && bounds != null && boundtypes.length == vars.length && bounds.length == vars.length);

      int nvars = vars.length;
      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);
      SWIGTYPE_p_SCIP_BoundType boundtypesptr = SCIPJNI.new_SCIP_BoundType_array(nvars);
      SWIGTYPE_p_double boundsptr = SCIPJNI.new_double_array(nvars);

      // copy arrays
      for( int i = 0; i < nvars; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         SCIPJNI.SCIP_BoundType_array_setitem(boundtypesptr, i, boundtypes[i]);
         SCIPJNI.double_array_setitem(boundsptr, i, bounds[i]);
      }

      SWIGTYPE_p_SCIP_CONS consptr = allowRedundant ? SCIPJNI.createConsBasicBounddisjunctionRedundant(_scipptr, name, nvars, varsptr, boundtypesptr, boundsptr) : SCIPJNI.createConsBasicBounddisjunction(_scipptr, name, nvars, varsptr, boundtypesptr, boundsptr);
      assert(consptr != null);

      SCIPJNI.delete_double_array(boundsptr);
      SCIPJNI.delete_SCIP_BoundType_array(boundtypesptr);
      SCIPJNI.delete_SCIP_VAR_array(varsptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicCardinality(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsCardinality(String name, Variable[] vars, int cardval, Variable[] indvars, double[] weights)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_p_SCIP_VAR indvarsptr = null;
      SWIGTYPE_p_double weightsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy indicator variables
      assert(indvars == null || indvars.length == nvars);
      if( indvars != null )
      {
         indvarsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(indvarsptr, i, indvars[i].getPtr());
         }
      }

      // copy weights
      assert(weights == null || weights.length == nvars);
      if( weights != null )
      {
         weightsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(weightsptr, i, weights[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicCardinality(_scipptr, name, nvars, varsptr, cardval, indvarsptr, weightsptr);
      assert(consptr != null);

      if ( weightsptr != null ) {
         SCIPJNI.delete_double_array(weightsptr);
      }
      if ( indvarsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(indvarsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicConjunction(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsConjunction(String name, Constraint[] conss)
   {
      assert(conss != null);

      // copy constraints
      int nconss = conss.length;
      SWIGTYPE_p_p_SCIP_CONS conssptr = SCIPJNI.new_SCIP_CONS_array(nconss);

      for( int i = 0; i < nconss; ++i )
      {
         SCIPJNI.SCIP_CONS_array_setitem(conssptr, i, conss[i].getPtr());
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicConjunction(_scipptr, name, nconss, conssptr);
      assert(consptr != null);

      SCIPJNI.delete_SCIP_CONS_array(conssptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicDisjunction(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsDisjunction(String name, Constraint[] conss, Constraint relaxcons)
   {
      assert(conss != null);

      // copy constraints
      int nconss = conss.length;
      SWIGTYPE_p_p_SCIP_CONS conssptr = SCIPJNI.new_SCIP_CONS_array(nconss);

      for( int i = 0; i < nconss; ++i )
      {
         SCIPJNI.SCIP_CONS_array_setitem(conssptr, i, conss[i].getPtr());
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicDisjunction(_scipptr, name, nconss, conssptr, relaxcons != null ? relaxcons.getPtr() : null);
      assert(consptr != null);

      SCIPJNI.delete_SCIP_CONS_array(conssptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicCumulative(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsCumulative(String name, Variable[] vars, int[] durations, int[] demands, int capacity)
   {
      assert(vars != null && durations != null && demands != null && vars.length > 0 && durations.length == vars.length && demands.length == vars.length);
      assert(capacity >= 0);

      int nvars = vars.length;
      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);
      SWIGTYPE_p_int durationsptr = SCIPJNI.new_int_array(nvars);
      SWIGTYPE_p_int demandsptr = SCIPJNI.new_int_array(nvars);

      // copy arrays
      for( int i = 0; i < nvars; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         SCIPJNI.int_array_setitem(durationsptr, i, durations[i]);
         SCIPJNI.int_array_setitem(demandsptr, i, demands[i]);
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicCumulative(_scipptr, name, nvars, varsptr, durationsptr, demandsptr, capacity);
      assert(consptr != null);

      SCIPJNI.delete_int_array(demandsptr);
      SCIPJNI.delete_int_array(durationsptr);
      SCIPJNI.delete_SCIP_VAR_array(varsptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicIndicator(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsIndicator(String name, Variable binvar, Variable[] vars, double[] vals, double rhs)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_double valsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy values
      assert(((vals != null) ? vals.length : 0) == nvars);
      if( vals != null )
      {
         valsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(valsptr, i, vals[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicIndicator(_scipptr, name, binvar != null ? binvar.getPtr() : null, nvars, varsptr, valsptr, rhs);
      assert(consptr != null);

      if ( valsptr != null ) {
         SCIPJNI.delete_double_array(valsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicIndicatorLinCons() */
   public Constraint createConsIndicator(String name, Variable binvar, Constraint lincons, Variable slackvar)
   {
      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicIndicatorLinCons(_scipptr, name, binvar != null ? binvar.getPtr() : null, lincons.getPtr(), slackvar.getPtr());
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicKnapsack(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsKnapsack(String name, Variable[] vars, long[] weights, long capacity)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_long_long weightsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy weights
      assert(((weights != null) ? weights.length : 0) == nvars);
      if( weights != null )
      {
         weightsptr = SCIPJNI.new_long_long_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.long_long_array_setitem(weightsptr, i, weights[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicKnapsack(_scipptr, name, nvars, varsptr, weightsptr, capacity);
      assert(consptr != null);

      if ( weightsptr != null ) {
         SCIPJNI.delete_long_long_array(weightsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicLinking(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsLinking(String name, Variable linkvar, Variable[] binvars, double[] vals)
   {
      int nbinvars = 0;
      SWIGTYPE_p_p_SCIP_VAR binvarsptr = null;
      SWIGTYPE_p_double valsptr = null;

      // copy variables
      if( binvars != null )
      {
         nbinvars = binvars.length;
         binvarsptr = SCIPJNI.new_SCIP_VAR_array(nbinvars);

         for( int i = 0; i < nbinvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(binvarsptr, i, binvars[i].getPtr());
         }
      }

      // copy values
      assert(((vals != null) ? vals.length : 0) == nbinvars);
      if( vals != null )
      {
         valsptr = SCIPJNI.new_double_array(nbinvars);

         for( int i = 0; i < nbinvars; ++i )
         {
            SCIPJNI.double_array_setitem(valsptr, i, vals[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicLinking(_scipptr, name, linkvar.getPtr(), binvarsptr, valsptr, nbinvars);
      assert(consptr != null);

      if ( valsptr != null ) {
         SCIPJNI.delete_double_array(valsptr);
      }
      if ( binvarsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(binvarsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicLogicor(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsLogicor(String name, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicLogicor(_scipptr, name, nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicOrbisack(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsOrbisack(String name, Variable[] vars1, Variable[] vars2, boolean ispporbisack, boolean isparttype, boolean ismodelcons)
   {
      assert(vars1 != null && vars2 != null && vars1.length > 0 && vars2.length == vars1.length);

      int nrows = vars1.length;
      SWIGTYPE_p_p_SCIP_VAR vars1ptr = SCIPJNI.new_SCIP_VAR_array(nrows);
      SWIGTYPE_p_p_SCIP_VAR vars2ptr = SCIPJNI.new_SCIP_VAR_array(nrows);

      // copy arrays
      for( int i = 0; i < nrows; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(vars1ptr, i, vars1[i].getPtr());
         SCIPJNI.SCIP_VAR_array_setitem(vars2ptr, i, vars2[i].getPtr());
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicOrbisack(_scipptr, name, vars1ptr, vars2ptr, nrows, ispporbisack ? 1 : 0, isparttype ? 1 : 0, ismodelcons ? 1 : 0);
      assert(consptr != null);

      SCIPJNI.delete_SCIP_VAR_array(vars2ptr);
      SCIPJNI.delete_SCIP_VAR_array(vars1ptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicOrbitope(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsOrbitope(String name, Variable[][] vars, SCIP_OrbitopeType orbitopetype, boolean usedynamicprop, boolean resolveprop, boolean ismodelcons, boolean mayinteract)
   {
      assert(vars != null && vars.length > 0 && vars[0] != null && vars[0].length != 0);

      int nspcons = vars.length;
      int nblocks = vars[0].length;
      SWIGTYPE_p_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array_array(nspcons);

      // copy variables
      for( int i = 0; i < nspcons; ++i )
      {
         Variable[] spconsvars = vars[i];
         assert(spconsvars != null && spconsvars.length == nblocks);
         SWIGTYPE_p_p_SCIP_VAR spconsvarsptr = SCIPJNI.new_SCIP_VAR_array(nblocks);
         for( int j = 0; j < nblocks; ++j )
         {
            SCIPJNI.SCIP_VAR_array_setitem(spconsvarsptr, j, spconsvars[j].getPtr());
         }
         SCIPJNI.SCIP_VAR_array_array_setitem(varsptr, i, spconsvarsptr);
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicOrbitope(_scipptr, name, varsptr, orbitopetype, nspcons, nblocks, usedynamicprop ? 1 : 0, resolveprop ? 1 : 0, ismodelcons ? 1 : 0, mayinteract ? 1 : 0);
      assert(consptr != null);

      for( int i = 0; i < nspcons; ++i )
      {
         SCIPJNI.delete_SCIP_VAR_array( SCIPJNI.SCIP_VAR_array_array_getitem(varsptr, i));
      }
      SCIPJNI.delete_SCIP_VAR_array_array(varsptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSOS1(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsSOS1(String name, Variable[] vars, double[] weights)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_double weightsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy weights
      assert(weights == null || weights.length == nvars);
      if( weights != null && weights.length > 0 )
      {
         weightsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(weightsptr, i, weights[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSOS1(_scipptr, name, nvars, varsptr, weightsptr);
      assert(consptr != null);

      if ( weightsptr != null ) {
         SCIPJNI.delete_double_array(weightsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSOS2(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsSOS2(String name, Variable[] vars, double[] weights)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;
      SWIGTYPE_p_double weightsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      // copy weights
      assert(weights == null || weights.length == nvars);
      if( weights != null && weights.length > 0 )
      {
         weightsptr = SCIPJNI.new_double_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.double_array_setitem(weightsptr, i, weights[i]);
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSOS2(_scipptr, name, nvars, varsptr, weightsptr);
      assert(consptr != null);

      if ( weightsptr != null ) {
         SCIPJNI.delete_double_array(weightsptr);
      }
      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicSymresack(); note that the function needs to copy the content of the arrays into arrays which are passed to native interface */
   public Constraint createConsSymresack(String name, int[] perm, Variable[] vars, boolean ismodelcons)
   {
      assert(vars != null && perm != null && vars.length > 0 && perm.length == vars.length);

      int nvars = vars.length;
      SWIGTYPE_p_p_SCIP_VAR varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);
      SWIGTYPE_p_int permptr = SCIPJNI.new_int_array(nvars);

      // copy arrays
      for( int i = 0; i < nvars; ++i )
      {
         SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         SCIPJNI.int_array_setitem(permptr, i, perm[i]);
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicSymresack(_scipptr, name, permptr, varsptr, nvars, ismodelcons ? 1 : 0);
      assert(consptr != null);

      SCIPJNI.delete_int_array(permptr);
      SCIPJNI.delete_SCIP_VAR_array(varsptr);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicVarbound() */
   public Constraint createConsVarbound(String name, Variable boundedvar, Variable vbdvar, double vbdcoef, double lhs, double rhs)
   {
      assert(lhs <= rhs);

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicVarbound(_scipptr, name, boundedvar.getPtr(), vbdvar.getPtr(), vbdcoef, lhs, rhs);
      assert(consptr != null);

      return new Constraint(consptr);
   }

   /** wraps SCIPcreateConsBasicXor(); note that the function needs to copy the content of the array into an array which is passed to native interface */
   public Constraint createConsXor(String name, boolean rhs, Variable[] vars)
   {
      int nvars = 0;
      SWIGTYPE_p_p_SCIP_VAR varsptr = null;

      // copy variables
      if( vars != null )
      {
         nvars = vars.length;
         varsptr = SCIPJNI.new_SCIP_VAR_array(nvars);

         for( int i = 0; i < nvars; ++i )
         {
            SCIPJNI.SCIP_VAR_array_setitem(varsptr, i, vars[i].getPtr());
         }
      }

      SWIGTYPE_p_SCIP_CONS consptr = SCIPJNI.createConsBasicXor(_scipptr, name, rhs ? 1 : 0, nvars, varsptr);
      assert(consptr != null);

      if ( varsptr != null ) {
         SCIPJNI.delete_SCIP_VAR_array(varsptr);
      }

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

   /** wraps SCIPcreatePartialSol() */
   public Solution createPartialSol()
   {
      SWIGTYPE_p_p_SCIP_SOL solptr = SCIPJNI.new_SCIP_SOL_array(1);
      CHECK_RETCODE( SCIPJNI.SCIPcreatePartialSol(_scipptr, solptr, null) );
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

   /** wraps SCIPgetPrimalbound() */
   public double getPrimalbound()
   {
      return SCIPJNI.SCIPgetPrimalbound(_scipptr);
   }

   /** wraps SCIPgetDualbound() */
   public double getDualbound()
   {
      return SCIPJNI.SCIPgetDualbound(_scipptr);
   }

   /** wraps SCIPgetSolvingTime() */
   public double getSolvingTime()
   {
      return SCIPJNI.SCIPgetSolvingTime(_scipptr);
   }

   /** wraps SCIPgetGap() */
   public double getGap()
   {
      return SCIPJNI.SCIPgetGap(_scipptr);
   }

   /** wraps SCIPsetStaticErrorPrintingMessagehdlr() */
   public static void setStaticErrorPrintingMessagehdlr(MessageHandler messagehdlr) {
      SCIPJNI.SCIPsetStaticErrorPrintingMessagehdlr(MessageHandler.getPtr(messagehdlr));
   }

   /** wraps SCIPsetMessagehdlr() */
   public void setMessagehdlr(MessageHandler messagehdlr) {
      CHECK_RETCODE( SCIPJNI.SCIPsetMessagehdlr(_scipptr, MessageHandler.getPtr(messagehdlr)) );
   }

   /** wraps SCIPgetMessagehdlr() */
   public MessageHandler getMessagehdlr() {
      SWIGTYPE_p_SCIP_Messagehdlr ptr = SCIPJNI.SCIPgetMessagehdlr(_scipptr);
      return (ptr == null) ? null : new MessageHandler.Wrapper(ptr);
   }

   /** wraps SCIPsetMessagehdlrLogfile() */
   public void setMessagehdlrLogfile(String filename) {
      SCIPJNI.SCIPsetMessagehdlrLogfile(_scipptr, filename);
   }

   /** wraps SCIPgetVerbLevel() */
   public SCIP_VerbLevel getVerbLevel() {
      return SCIPJNI.SCIPgetVerbLevel(_scipptr);
   }
}
