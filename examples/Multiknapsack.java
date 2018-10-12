import java.util.Random;
import jscip.*;

/**
 *  Two ways two write simple multi-knapsack models with 10 articles and many knapsack constraints.
 *  each article can be assigned at most once. The contribution on the Objective
 *  Function and of weight of the articles of each article are assigned randomly
 *  In the First model, each  knapsack constraint is built manually. It includes 2 knapsacks
 *  In the Second model, each knapsack constraint is built with a for Cycle. It includes 20 knapsacks
 *
 * @author  José Miguel Quesada Perez (jose.quesada@uclouvain.be)
 * @version 1.0
 * @since   2018-10-12
 */
public class Multiknapsack
{
   public static void main(String args[])
      {
         // load generated C-library and set up structures of SCIP
         System.loadLibrary("jscip");
         Scip scip = new Scip();
         scip.create("multiKnapsack");


         // FIRST WAY: Write multi-knapsack by writing each constraint manually
         // declare variables and varCoefficients
         System.out.print("\n***********  FIRST WAY *******************************\n");
         System.out.print("\n***********  2 knapsacks \n");
         Variable[] vars1 = new Variable[10];
         double[]   coef1 = new double[10];
         double[]   coef2 = new double[10];

         Random rand = new Random();

         // create variables and coefficients
         for( int i = 0; i < vars1.length; i++ )
         {

            // variable Xi = 1 if the article  i is selected; 0 otherwise. Since SCIP minimizes by default, the value on
            // the OF is multiplied by -1, so we will maximize.Contribution in the OF random int between 0 and 50
            vars1[i] = scip.createVar("x" + i, 0.0, 1, -(rand.nextInt(50) + 1), SCIP_Vartype.SCIP_VARTYPE_BINARY);

            // coefficients of each article are random between 0 and 25;
            coef1[i] = rand.nextDouble()*25;
            coef2[i] = rand.nextDouble()*25;
         }

         // create and add constraints to SCIP
         // knapsack capacity, random between 50 and 80 ;
         Constraint knapsack1 = scip.createConsLinear("knapsack1", vars1, coef1, 0, 50+rand.nextDouble()*30);
         Constraint knapsack2 = scip.createConsLinear("knapsack2", vars1, coef2, 0, 50+rand.nextDouble()*30);
         scip.addCons(knapsack1);
         scip.addCons(knapsack2);

         // release constraint
         scip.releaseCons(knapsack1);
         scip.releaseCons(knapsack2);

         // set parameters
         scip.setRealParam("limits/time", 100.0);
         scip.setRealParam("limits/memory", 10000.0);
         scip.setLongintParam("limits/totalnodes", 1000);

         // solve problem and print solution
         scip.solve();
         Solution sol = scip.getBestSol();
         System.out.print("\nObjective value " + -scip.getSolOrigObj(sol));
         if( sol != null )
         {
            System.out.println("\nvarValues " );
            for( int i = 0; i < vars1.length; i++ )
            {
               System.out.print("\nx" + (i+1) + " " + scip.getSolVal(sol, vars1[i]));
            }
         }

         // release variables (if not needed anymore) and fee SCIP
         for( int i = 0; i < vars1.length; i++ )
         {
            scip.releaseVar(vars1[i]);
         }

         scip.free();

         // SECOND WAY: Using a for cycle that writes each constraint
         System.out.print("\n***********  FIRST WAY *******************************\n");
         System.out.print("\n***********  20 knapsacks \n");
         scip = new Scip();
         scip.create("multiKnapsack2");

         // declare and create variables and Coefficients
         Variable[] vars = new Variable[10];
         double  [] coefs = new double [10];

         for( int i = 0; i < vars.length; i++ )
         {
            vars[i] = scip.createVar("x" + i, 0.0, 1, -(rand.nextInt(50) + 1), SCIP_Vartype.SCIP_VARTYPE_BINARY);
         }

         // create, add and release constraints
         int nrKnapsacks = 20; // increase this number to add knapsacks
         for( int j = 1; j <= nrKnapsacks; j++ )
         {
            for( int i = 0; i < coefs.length; i++ )
            {
               coefs[i] = rand.nextDouble()*25 +1;
               Constraint knapsack = scip.createConsLinear("knapsack"+j, vars, coefs, 0, 50+rand.nextDouble()*30 +1);
               scip.addCons(knapsack);
               scip.releaseCons(knapsack);
            }
         }

         // solve problem and print solution
         scip.solve();
         sol = scip.getBestSol();
         System.out.print("\nObjective value " + -scip.getSolOrigObj(sol));
         if( sol != null )
         {
            System.out.println("\nvarValues " );
            for( int i = 0; i < vars.length; i++ )
            {
               System.out.print("\nx" + (i+1) + " " + scip.getSolVal(sol, vars[i]));
            }
         }

         // release variables (if not needed anymore) and fee SCIP
         for( int i = 0; i < vars1.length; i++ )
         {
            scip.releaseVar(vars[i]);
         }

         scip.free();
      }
}
