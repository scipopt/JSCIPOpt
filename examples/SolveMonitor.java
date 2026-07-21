import jscip.*;

/** Example how to monitor solve progress from Java events. */
public class SolveMonitor
{
   private static String eventLabel(long eventType)
   {
      if( eventType == EventMask.FIRST_LP_SOLVED )
         return "FIRST_LP_SOLVED";
      if( eventType == EventMask.LP_SOLVED )
         return "LP_SOLVED";
      if( eventType == EventMask.NODE_FOCUSED )
         return "NODE_FOCUSED";
      if( eventType == EventMask.NODE_FEASIBLE )
         return "NODE_FEASIBLE";
      if( eventType == EventMask.NODE_INFEASIBLE )
         return "NODE_INFEASIBLE";
      if( eventType == EventMask.NODE_BRANCHED )
         return "NODE_BRANCHED";
      if( eventType == EventMask.POOR_SOL_FOUND )
         return "POOR_SOL_FOUND";
      if( eventType == EventMask.BEST_SOL_FOUND )
         return "BEST_SOL_FOUND";
      return Long.toString(eventType);
   }

   private static String fmt(double value)
   {
      if( Double.isNaN(value) )
         return "nan";
      if( Math.abs(value) >= 1.0e19 )
         return "n/a";
      if( Double.isInfinite(value) )
         return value > 0.0 ? "+inf" : "-inf";
      return String.format("%.6f", value);
   }

   private static String snapshot(String label, Scip model)
   {
      Solution bestsol = model.getBestSol();
      double bestKnownObj = bestsol == null ? Double.NaN : model.getSolOrigObj(bestsol);
      double bestPossibleObj = model.getDualbound();

      return label
         + " stage=" + model.getStage()
         + " status=" + model.getStatus()
         + " time=" + fmt(model.getSolvingTime())
         + " gap=" + fmt(model.getGap())
         + " bestKnownObj=" + fmt(bestKnownObj)
         + " bestPossibleObj=" + fmt(bestPossibleObj)
         + " lpRows=" + model.getNLPRows()
         + " nSols=" + model.getNSols();
   }

   public static void main(String[] args)
   {
      System.loadLibrary("jscip");

      Scip scip = new Scip();
      scip.create("SolveMonitorDemo");

      Variable x = scip.createVar("x", 0.0, 10.0, 3.0, SCIP_Vartype.SCIP_VARTYPE_INTEGER);
      Variable y = scip.createVar("y", 0.0, 10.0, 4.0, SCIP_Vartype.SCIP_VARTYPE_INTEGER);

      Constraint c1 = scip.createConsLinear("c1", new Variable[]{x, y}, new double[]{2.0, 1.0}, -scip.infinity(), 100.0);
      Constraint c2 = scip.createConsLinear("c2", new Variable[]{x, y}, new double[]{1.0, 2.0}, -scip.infinity(), 80.0);
      scip.addCons(c1);
      scip.addCons(c2);
      scip.releaseCons(c2);
      scip.releaseCons(c1);

      new EventHandler(scip, "solve-monitor", "prints key solve state values",
            EventMask.LP_EVENT | EventMask.NODE_EVENT | EventMask.SOL_EVENT) {
         private int counter = 0;
         private String lastSnapshot = null;

         @Override
         protected void execute(Event event)
         {
            counter++;
            String payload = snapshot(eventLabel(event.getType()), scip);

            if( payload.equals(lastSnapshot) )
               return;

            lastSnapshot = payload;
            System.out.println("event[" + counter + "] " + payload);
         }
      }.include();

      scip.solve();
      System.out.println("final " + snapshot("FINAL", scip));
      System.out.flush();

      scip.releaseVar(y);
      scip.releaseVar(x);
      scip.free();
   }
}
