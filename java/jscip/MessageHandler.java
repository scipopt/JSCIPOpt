package jscip;

/** Class representing a message handler (equivalent of SCIP_Messagehdlr). */
public abstract class MessageHandler
{
   private SWIGTYPE_p_SCIP_Messagehdlr _messagehdlrptr; /** pointer address class created by SWIG */

   /** default constructor to construct a Java message handler */
   protected MessageHandler()
   {
      ObjMessagehdlr objmessagehdlr = new ObjMessagehdlr(1) {
         @Override
         public void scip_error(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
            error(msg);
         }

         @Override
         public void scip_warning(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
            warning(msg);
         }

         @Override
         public void scip_dialog(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
            dialog(msg);
         }

         @Override
         public void scip_info(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
            info(msg);
         }

         @Override
         public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr) {
            try {
               close();
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }
      };
      objmessagehdlr.swigReleaseOwnership();
      _messagehdlrptr = SCIPJNI.createObjMessagehdlr(objmessagehdlr, 1);
   }

   /** constructor to wrap a native message handler
    *  (must not be called directly, construct a Wrapper instead) */
   private MessageHandler(SWIGTYPE_p_SCIP_Messagehdlr messagehdlrptr)
   {
      _messagehdlrptr = messagehdlrptr;
   }

   /** returns SWIG object type representing a SCIP_Messagehdlr pointer */
   public SWIGTYPE_p_SCIP_Messagehdlr getPtr()
   {
      return _messagehdlrptr;
   }

   /** returns SWIG object type representing a SCIP_Messagehdlr pointer */
   public static SWIGTYPE_p_SCIP_Messagehdlr getPtr(MessageHandler obj) {
      return obj != null ? obj.getPtr() : new SWIGTYPE_p_SCIP_Messagehdlr();
   }

   protected abstract void error(String msg);

   protected abstract void warning(String msg);

   protected abstract void dialog(String msg);

   protected abstract void info(String msg);

   protected void close() throws Exception {
      // do nothing by default
   }

   /** returns a String representation */
   public String toString()
   {
      return "message handler of type " + getClass().getName();
   }

   /** Class wrapping an existing native SCIP_Messagehdlr. */
   public static final class Wrapper extends MessageHandler {
      /** constructor from a native SCIP_Messagehdlr pointer */
      public Wrapper(SWIGTYPE_p_SCIP_Messagehdlr messagehdlrptr) {
         super(messagehdlrptr);
      }

      @Override
      protected final void error(String msg) {
         // This method will never be reached from native C/C++ code.
         throw new UnsupportedOperationException(
               "MessageHandler.Wrapper.error should never be called");
      }

      @Override
      protected final void warning(String msg) {
         // This method will never be reached from native C/C++ code.
         throw new UnsupportedOperationException(
               "MessageHandler.Wrapper.warning should never be called");
      }

      @Override
      protected final void dialog(String msg) {
         // This method will never be reached from native C/C++ code.
         throw new UnsupportedOperationException(
               "MessageHandler.Wrapper.dialog should never be called");
      }

      @Override
      protected final void info(String msg) {
         // This method will never be reached from native C/C++ code.
         throw new UnsupportedOperationException(
               "MessageHandler.Wrapper.info should never be called");
      }
   }
}
