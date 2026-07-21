package jscip;

/** Class representing an event handler (equivalent of SCIP_Eventhdlr). */
public abstract class EventHandler
{
   protected final Scip scip;

   private final String _name;
   private final String _desc;
   private final long _eventmask;
   private final ObjEventhdlr _objeventhdlr;
   private SWIGTYPE_p_SCIP_Eventhdlr _eventhdlrptr;
   private boolean _includeattempted;
   private boolean _autocatchregistered;

   /** constructs a Java event handler that automatically catches eventmask */
   protected EventHandler(Scip scipobj, String name, String desc, long eventmask)
   {
      if( scipobj == null || scipobj.getPtr() == null )
         throw new IllegalArgumentException("scip must be created before constructing an event handler");
      if( name == null || desc == null )
         throw new NullPointerException("event handler name and description must not be null");

      scip = scipobj;
      _name = name;
      _desc = desc;
      _eventmask = eventmask;
      _eventhdlrptr = null;
      _includeattempted = false;
      _autocatchregistered = false;
      _objeventhdlr = createObjEventhdlr();
   }

   /** constructs a Java event handler whose events are registered manually */
   protected EventHandler(Scip scipobj, String name, String desc)
   {
      this(scipobj, name, desc, EventMask.DISABLED);
   }

   /** constructs a wrapper around an existing native event handler */
   private EventHandler(SWIGTYPE_p_SCIP_Eventhdlr eventhdlrptr)
   {
      if( eventhdlrptr == null )
         throw new NullPointerException("event handler pointer must not be null");

      scip = null;
      _name = null;
      _desc = null;
      _eventmask = EventMask.DISABLED;
      _objeventhdlr = null;
      _eventhdlrptr = eventhdlrptr;
      _includeattempted = true;
      _autocatchregistered = false;
   }

   private ObjEventhdlr createObjEventhdlr()
   {
      return new ObjEventhdlr(scip.getPtr(), _name, _desc) {
         @Override
         public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
         {
            bind(eventhdlr);
            SCIP_Retcode result = invokeFree();

            try
            {
               EventHandler.this.close();
            }
            catch( Exception e )
            {
               reportException(e);
               result = SCIP_Retcode.SCIP_ERROR;
            }
            finally
            {
               _autocatchregistered = false;
               _eventhdlrptr = null;
               scip.releaseEventHandler(EventHandler.this);
            }

            return result;
         }

         @Override
         public SCIP_Retcode scip_init(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
         {
            bind(eventhdlr);
            _autocatchregistered = false;

            if( _eventmask != EventMask.DISABLED )
            {
               SCIP_Retcode retcode = SCIPJNI.SCIPcatchEvent(scipptr, _eventmask, eventhdlr, null, null);
               if( retcode != SCIP_Retcode.SCIP_OKAY )
                  return retcode;
               _autocatchregistered = true;
            }

            try
            {
               EventHandler.this.init();
               return SCIP_Retcode.SCIP_OKAY;
            }
            catch( Exception e )
            {
               reportException(e);
               SCIP_Retcode cleanup = dropAutomaticEvent(scipptr, eventhdlr);
               return cleanup == SCIP_Retcode.SCIP_OKAY ? SCIP_Retcode.SCIP_ERROR : cleanup;
            }
         }

         @Override
         public SCIP_Retcode scip_exit(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
         {
            bind(eventhdlr);
            SCIP_Retcode result = SCIP_Retcode.SCIP_OKAY;

            try
            {
               EventHandler.this.exit();
            }
            catch( Exception e )
            {
               reportException(e);
               result = SCIP_Retcode.SCIP_ERROR;
            }

            SCIP_Retcode cleanup = dropAutomaticEvent(scipptr, eventhdlr);
            return cleanup == SCIP_Retcode.SCIP_OKAY ? result : cleanup;
         }

         @Override
         public SCIP_Retcode scip_initsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
         {
            bind(eventhdlr);
            try
            {
               EventHandler.this.initsol();
               return SCIP_Retcode.SCIP_OKAY;
            }
            catch( Exception e )
            {
               reportException(e);
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_exitsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
         {
            bind(eventhdlr);
            try
            {
               EventHandler.this.exitsol();
               return SCIP_Retcode.SCIP_OKAY;
            }
            catch( Exception e )
            {
               reportException(e);
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_delete(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr, SWIGTYPE_p_p_SCIP_EventData eventdata)
         {
            bind(eventhdlr);
            try
            {
               EventHandler.this.delete();
               return SCIP_Retcode.SCIP_OKAY;
            }
            catch( Exception e )
            {
               reportException(e);
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_exec(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr, SWIGTYPE_p_SCIP_Event event, SWIGTYPE_p_SCIP_EventData eventdata)
         {
            bind(eventhdlr);
            try
            {
               EventHandler.this.execute(new Event(event));
               return SCIP_Retcode.SCIP_OKAY;
            }
            catch( Exception e )
            {
               reportException(e);
               return SCIP_Retcode.SCIP_ERROR;
            }
         }
      };
   }

   private SCIP_Retcode invokeFree()
   {
      try
      {
         free();
         return SCIP_Retcode.SCIP_OKAY;
      }
      catch( Exception e )
      {
         reportException(e);
         return SCIP_Retcode.SCIP_ERROR;
      }
   }

   private SCIP_Retcode dropAutomaticEvent(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
   {
      if( !_autocatchregistered )
         return SCIP_Retcode.SCIP_OKAY;

      _autocatchregistered = false;
      return SCIPJNI.SCIPdropEvent(scipptr, _eventmask, eventhdlr, null, -1);
   }

   private void bind(SWIGTYPE_p_SCIP_Eventhdlr eventhdlr)
   {
      if( _eventhdlrptr == null )
         _eventhdlrptr = eventhdlr;
   }

   private static void reportException(Exception e)
   {
      e.printStackTrace();
   }

   /** includes this Java event handler in its SCIP instance */
   public final void include()
   {
      if( _objeventhdlr == null )
         throw new UnsupportedOperationException("Cannot include an EventHandler.Wrapper");
      if( _includeattempted )
         throw new IllegalStateException("EventHandler already included");
      if( scip.getPtr() == null )
         throw new IllegalStateException("SCIP instance has already been freed");

      SCIP_Retcode retcode;
      try
      {
         _includeattempted = true;
         _objeventhdlr.swigReleaseOwnership();
         retcode = SCIPJNI.SCIPincludeObjEventhdlr(scip.getPtr(), _objeventhdlr, 1);
      }
      catch( RuntimeException | Error e )
      {
         restoreAfterFailedInclude();
         throw e;
      }
      if( retcode != SCIP_Retcode.SCIP_OKAY )
      {
         restoreAfterFailedInclude();
         throw new IllegalStateException("SCIP call failed with retcode " + retcode);
      }

      scip.retainEventHandler(this);
      _eventhdlrptr = SCIPJNI.SCIPfindEventhdlr(scip.getPtr(), _name);
      if( _eventhdlrptr == null )
         throw new IllegalStateException("SCIP did not return the included event handler");
   }

   private void restoreAfterFailedInclude()
   {
      try
      {
         _objeventhdlr.swigTakeOwnership();
      }
      finally
      {
         _includeattempted = false;
      }
   }

   /** returns the event mask automatically registered during initialization */
   public final long getEventMask()
   {
      return _eventmask;
   }

   /** returns this event handler's name */
   public final String getName()
   {
      return _eventhdlrptr == null ? _name : SCIPJNI.SCIPeventhdlrGetName(_eventhdlrptr);
   }

   /** returns the SWIG object representing the SCIP_EVENTHDLR pointer */
   public final SWIGTYPE_p_SCIP_Eventhdlr getPtr()
   {
      if( _eventhdlrptr == null )
         throw new IllegalStateException("include() must be called first");
      return _eventhdlrptr;
   }

   /** returns the SWIG object representing an event handler pointer */
   public static SWIGTYPE_p_SCIP_Eventhdlr getPtr(EventHandler obj)
   {
      if( obj == null )
         throw new NullPointerException("event handler must not be null");
      return obj.getPtr();
   }

   /** returns the backing C++ object, or null for a native wrapper */
   public final ObjEventhdlr getObjEventhdlr()
   {
      return _objeventhdlr;
   }

   protected void init() throws Exception
   {
   }

   protected void exit() throws Exception
   {
   }

   protected void initsol() throws Exception
   {
   }

   protected void exitsol() throws Exception
   {
   }

   protected void delete() throws Exception
   {
   }

   protected void free() throws Exception
   {
   }

   protected void close() throws Exception
   {
   }

   protected abstract void execute(Event event) throws Exception;

   @Override
   public String toString()
   {
      return getName();
   }

   /** Class wrapping an existing native SCIP event handler. */
   public static final class Wrapper extends EventHandler
   {
      public Wrapper(SWIGTYPE_p_SCIP_Eventhdlr eventhdlrptr)
      {
         super(eventhdlrptr);
      }

      @Override
      protected void execute(Event event)
      {
      }
   }
}
