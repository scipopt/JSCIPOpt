package jscip;

/** Minimal wrapper for a SCIP_NODE pointer. */
public final class Node
{
   private final SWIGTYPE_p_SCIP_Node _nodeptr;

   public Node(SWIGTYPE_p_SCIP_Node nodeptr)
   {
      _nodeptr = nodeptr;
   }

   public SWIGTYPE_p_SCIP_Node getPtr()
   {
      return _nodeptr;
   }
}
