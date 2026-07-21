import jscip.*;

/** Example how to inspect LP rows from Java once the first LP was solved. */
public class RowEvent
{
   public static void main(String[] args)
   {
      System.loadLibrary("jscip");

      Scip scip = new Scip();
      scip.create("RowDemo");
      scip.setPresolving(SCIP_ParamSetting.SCIP_PARAMSETTING_OFF, true);
      scip.setHeuristics(SCIP_ParamSetting.SCIP_PARAMSETTING_OFF, true);

      Variable x = scip.createVar("x", 0.0, scip.infinity(), -3.0, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);
      Variable y = scip.createVar("y", 0.0, scip.infinity(), -4.0, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);

      Constraint c1 = scip.createConsLinear("c1", new Variable[]{x, y}, new double[]{1.0, 1.0}, -scip.infinity(), 10.0);
      Constraint c2 = scip.createConsLinear("c2", new Variable[]{x, y}, new double[]{3.0, 1.0}, -scip.infinity(), 15.0);
      scip.addCons(c1);
      scip.addCons(c2);

      new EventHandler(scip, "inspect-rows", "prints LP rows after the first LP solve",
            EventMask.FIRST_LP_SOLVED) {
         private boolean printed = false;

         @Override
         protected void execute(Event event)
         {
            if( printed )
               return;

            printed = true;

            Row[] rows = scip.getLPRows();
            System.out.println(getName() + ": lp rows=" + rows.length);
            for( Row row : rows )
               System.out.println("row " + row.getIndex() + ": " + row.getName());
         }
      }.include();

      scip.solve();

      Row c1row = scip.getRowLinear(c1);
      if( c1row != null )
         System.out.println("linear row for " + c1.getName() + ": " + c1row.getName());

      scip.releaseCons(c2);
      scip.releaseCons(c1);
      scip.releaseVar(y);
      scip.releaseVar(x);
      scip.free();
   }
}
