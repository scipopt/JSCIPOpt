import jscip.*;

/** Example how to register a Java event handler. */
public class Eventhdlr
{
   public static void main(String[] args)
   {
      System.loadLibrary("jscip");

      Scip scip = new Scip();
      scip.create("EventDemo");

      Variable x = scip.createVar("x", 0.0, 10.0, 3.0, SCIP_Vartype.SCIP_VARTYPE_INTEGER);
      Variable y = scip.createVar("y", 0.0, 10.0, 4.0, SCIP_Vartype.SCIP_VARTYPE_INTEGER);

      Constraint c1 = scip.createConsLinear("c1", new Variable[]{x, y}, new double[]{2.0, 1.0}, -scip.infinity(), 100.0);
      Constraint c2 = scip.createConsLinear("c2", new Variable[]{x, y}, new double[]{1.0, 2.0}, -scip.infinity(), 80.0);
      scip.addCons(c1);
      scip.addCons(c2);
      scip.releaseCons(c2);
      scip.releaseCons(c1);

      EventHandler handler = new EventHandler(scip, "count-events", "prints lp, node, and solution events",
            EventMask.LP_EVENT | EventMask.NODE_EVENT | EventMask.SOL_EVENT) {
         private int counter = 0;

         @Override
         protected void execute(Event event)
         {
            counter++;
            System.out.println("event[" + counter + "] from " + getName() + " type=" + event.getType());
         }
      };
      handler.include();

      EventHandler included = scip.findEventHandler("count-events");
      if( included == null || !included.getName().equals(handler.getName()) )
         throw new IllegalStateException("included event handler was not found");

      scip.solve();

      scip.releaseVar(y);
      scip.releaseVar(x);
      scip.free();
   }
}
