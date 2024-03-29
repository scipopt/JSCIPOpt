/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package jscip;

public class ObjMessagehdlr {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ObjMessagehdlr(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ObjMessagehdlr obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        SCIPJNIJNI.delete_ObjMessagehdlr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    SCIPJNIJNI.ObjMessagehdlr_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    SCIPJNIJNI.ObjMessagehdlr_change_ownership(this, swigCPtr, true);
  }

  public long getScip_bufferedoutput_() {
    return SCIPJNIJNI.ObjMessagehdlr_scip_bufferedoutput__get(swigCPtr, this);
  }

  public ObjMessagehdlr(long bufferedoutput) {
    this(SCIPJNIJNI.new_ObjMessagehdlr(bufferedoutput), true);
    SCIPJNIJNI.ObjMessagehdlr_director_connect(this, swigCPtr, true, true);
  }

  public void scip_error(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
    if (getClass() == ObjMessagehdlr.class) SCIPJNIJNI.ObjMessagehdlr_scip_error(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg); else SCIPJNIJNI.ObjMessagehdlr_scip_errorSwigExplicitObjMessagehdlr(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg);
  }

  public void scip_warning(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
    if (getClass() == ObjMessagehdlr.class) SCIPJNIJNI.ObjMessagehdlr_scip_warning(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg); else SCIPJNIJNI.ObjMessagehdlr_scip_warningSwigExplicitObjMessagehdlr(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg);
  }

  public void scip_dialog(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
    if (getClass() == ObjMessagehdlr.class) SCIPJNIJNI.ObjMessagehdlr_scip_dialog(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg); else SCIPJNIJNI.ObjMessagehdlr_scip_dialogSwigExplicitObjMessagehdlr(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg);
  }

  public void scip_info(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr, SWIGTYPE_p_FILE file, String msg) {
    if (getClass() == ObjMessagehdlr.class) SCIPJNIJNI.ObjMessagehdlr_scip_info(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg); else SCIPJNIJNI.ObjMessagehdlr_scip_infoSwigExplicitObjMessagehdlr(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr), SWIGTYPE_p_FILE.getCPtr(file), msg);
  }

  public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP_Messagehdlr messagehdlr) {
    return SCIP_Retcode.swigToEnum((getClass() == ObjMessagehdlr.class) ? SCIPJNIJNI.ObjMessagehdlr_scip_free(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr)) : SCIPJNIJNI.ObjMessagehdlr_scip_freeSwigExplicitObjMessagehdlr(swigCPtr, this, SWIGTYPE_p_SCIP_Messagehdlr.getCPtr(messagehdlr)));
  }

}
