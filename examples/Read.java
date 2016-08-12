import jscip.*;

/** Example how to read an instance and solve it with the SCIP Java interface. */
public class Read
{
   public static void main(String args[])
   {
      if( args.length == 0 || args.length > 2 )
      {
         System.out.println("usage: java Read <input file> <settings file (optional)>");
         return;
      }

      // load generated C-library
      System.loadLibrary("jscip");

      Scip scip = new Scip();

      // set up data structures of SCIP
      scip.create(args[0]);

      // read problem
      scip.readProb(args[0]);

      // read settings
      if( args.length == 2 )
         scip.readParams(args[1]);

      // solve problem
      scip.solve();

      // print statistics and the best found solution (only non-zeros)
      scip.printStatistics();
      scip.printBestSol(false);

      // free SCIP
      scip.free();
    }
}
