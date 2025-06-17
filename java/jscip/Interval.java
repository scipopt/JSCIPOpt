package jscip;

/** Value class representing a single interval (equivalent of SCIP_INTERVAL). */
public class Interval
{
   public double inf;
   public double sup;

   /** default constructor */
   public Interval(double inf, double sup)
   {
      this.inf = inf;
      this.sup = sup;
   }

   /** constructs an empty interval */
   public Interval()
   {
      this(1., -1.);
   }

   /** conversion from a SCIP_Interval SWIG object */
   Interval(SCIP_Interval intervalptr)
   {
      this(intervalptr.getInf(), intervalptr.getSup());
   }

   /** conversion to a SCIP_Interval SWIG object */
   SCIP_Interval getPtr()
   {
      SCIP_Interval intervalptr = new SCIP_Interval();
      assignToPtr(intervalptr);
      return intervalptr;
   }

   /** assignment to an existing SCIP_Interval SWIG object */
   void assignToPtr(SCIP_Interval intervalptr)
   {
      intervalptr.setInf(inf);
      intervalptr.setSup(sup);
   }

   /** returns whether this interval is empty */
   public boolean isEmpty()
   {
      return inf > sup;
   }

   /** makes this interval empty */
   public void setEmpty()
   {
      inf = 1.;
      sup = -1.;
   }

   /** returns a String representation */
   public String toString()
   {
      return "[" + inf + "," + sup + "]";
   }
}
