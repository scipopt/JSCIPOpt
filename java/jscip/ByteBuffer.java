package jscip;

/** Class representing a byte buffer for parsing (equivalent of char *). */
public class ByteBuffer
{
   private SWIGTYPE_p_char _charptr; /** pointer address class created by SWIG */
   private final SWIGTYPE_p_char _basecharptr; /** base of offsets */

   /** default constructor */
   public ByteBuffer(SWIGTYPE_p_char charptr)
   {
      assert(charptr != null);
      _charptr = charptr;
      _basecharptr = charptr;
   }

   /** returns SWIG object type representing a byte (C char) pointer */
   public SWIGTYPE_p_char getPtr()
   {
      return _charptr;
   }

   /** updates the byte (C char) pointer */
   public void setPtr(SWIGTYPE_p_char charptr)
   {
      _charptr = charptr;
   }

   /** returns the base (original) pointer */
   public SWIGTYPE_p_char getBasePtr()
   {
      return _basecharptr;
   }

   /** reads the next byte without moving the pointer, as a Java char */
   public char peekChar()
   {
      return SCIPJNI.char_array_getitem(_charptr, 0);
   }

   /** reads the next byte without moving the pointer, as a Java byte */
   public byte peekByte()
   {
      return (byte) peekChar();
   }

   /** advances the pointer by the given offset */
   public void advance(long offset)
   {
      _charptr = new SWIGTYPE_p_char(SWIGTYPE_p_char.getCPtr(_charptr) + offset, false);
   }

   /** reads the next byte as a Java char and increments the pointer by 1 */
   public char takeChar()
   {
      char c = peekChar();
      advance(1);
      return c;
   }

   /** reads the next byte as a Java byte and increments the pointer by 1 */
   public byte takeByte()
   {
      return (byte) takeChar();
   }

   /** computes the current offset, for use, e.g., in the ParseException constructor */
   public long getOffset()
   {
      return SWIGTYPE_p_char.getCPtr(_charptr) - SWIGTYPE_p_char.getCPtr(_basecharptr);
   }
}
