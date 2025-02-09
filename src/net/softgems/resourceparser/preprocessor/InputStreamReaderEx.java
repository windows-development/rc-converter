/*
 * @(#)InputStreamReader.java 1.43 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Portions created by Mike Lischke are released under the MIT license.
 * See LICENSE file for more info.
 */

package net.softgems.resourceparser.preprocessor;

import java.io.*;
import java.nio.charset.*;

import sun.nio.cs.StreamDecoder;



/**
 * InputStreamReaderEx is a rewrite of the Java InputStreamReader class that allows to 
 * switch the character set to decode the input at any time.
 */

/**
 * An InputStreamReader is a bridge from byte streams to character streams: It
 * reads bytes and decodes them into characters using a specified {@link
 * java.nio.charset.Charset <code>charset</code>}.  The charset that it uses
 * may be specified by name or may be given explicitly, or the platform's
 * default charset may be accepted.
 *
 * <p> Each invocation of one of an InputStreamReader's read() methods may
 * cause one or more bytes to be read from the underlying byte-input stream.
 * To enable the efficient conversion of bytes to characters, more bytes may
 * be read ahead from the underlying stream than are necessary to satisfy the
 * current read operation.
 *
 * <p> For top efficiency, consider wrapping an InputStreamReader within a
 * BufferedReader.  For example:
 *
 * <pre>
 * BufferedReader in
 *   = new BufferedReader(new InputStreamReader(System.in));
 * </pre>
 *
 * @see BufferedReader
 * @see InputStream
 * @see java.nio.charset.Charset
 *
 * @version     1.43, 03/01/23
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class InputStreamReaderEx extends Reader
{
  private InputStream input;
  private StreamDecoder sd;

  //------------------------------------------------------------------------------------------------
  
  /**
   * Create an InputStreamReader that uses the default charset.
   *
   * @param in An InputStream
   */
  public InputStreamReaderEx(InputStream in)
  {
    super(in);
    input = in;
    try
    {
      sd = StreamDecoder.forInputStreamReader(input, this, (String)null); // ## check lock object
    }
    catch (UnsupportedEncodingException e)
    {
      // The default encoding should always be available
      throw new Error(e);
    }
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Create an InputStreamReader that uses the given charset. </p>
   *
   * @param  in       An InputStream
   * @param  cs       A charset
   *
   * @since 1.4
   * @spec JSR-51
   */
  public InputStreamReaderEx(InputStream in, Charset cs)
  {
    super(in);
    input = in;
    if (cs == null)
      throw new NullPointerException("charset");
    sd = StreamDecoder.forInputStreamReader(input, this, cs);
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Create an InputStreamReader that uses the given charset decoder.  </p>
   *
   * @param  in       An InputStream
   * @param  dec      A charset decoder
   *
   * @since 1.4
   * @spec JSR-51
   */
  public InputStreamReaderEx(InputStream in, CharsetDecoder dec)
  {
    super(in);
    input = in;
    if (dec == null)
      throw new NullPointerException("charset decoder");
    sd = StreamDecoder.forInputStreamReader(input, this, dec);
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Create an InputStreamReader that uses the named charset.
   *
   * @param in An InputStream
   *
   * @param  charsetName
   *         The name of a supported
   *         {@link java.nio.charset.Charset </code>charset<code>}
   *
   * @exception  UnsupportedEncodingException
   *             If the named charset is not supported
   */
  public InputStreamReaderEx(InputStream in, String charsetName) throws UnsupportedEncodingException
  {
    super(in);
    input = in;
    if (charsetName == null)
      throw new NullPointerException("charsetName");
    sd = StreamDecoder.forInputStreamReader(input, this, charsetName);
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Close the stream.
   *
   * @exception  IOException  If an I/O error occurs
   */
  public void close() throws IOException
  {
    sd.close();
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Return the name of the character encoding being used by this stream.
   *
   * <p> If the encoding has an historical name then that name is returned;
   * otherwise the encoding's canonical name is returned.
   *
   * <p> If this instance was created with the {@link
   * #InputStreamReader(InputStream, String)} constructor then the returned
   * name, being unique for the encoding, may differ from the name passed to
   * the constructor.  This method may return <code>null</code> if the stream
   * has been closed. </p>
   *
   * @return The historical name of this encoding, or possibly
   *         <code>null</code> if the stream has been closed
   *
   * @see java.nio.charset.Charset
   *
   * @revised 1.4
   * @spec JSR-51
   */
  public String getEncoding()
  {
    return sd.getEncoding();
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Read a single character.
   *
   * @return The character read, or -1 if the end of the stream has been
   *         reached
   *
   * @exception  IOException  If an I/O error occurs
   */
  public int read() throws IOException
  {
    return sd.read();
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Read characters into a portion of an array.
   *
   * @param      cbuf     Destination buffer
   * @param      offset   Offset at which to start storing characters
   * @param      length   Maximum number of characters to read
   *
   * @return     The number of characters read, or -1 if the end of the 
   *             stream has been reached
   *
   * @exception  IOException  If an I/O error occurs
   */
  public int read(char cbuf[], int offset, int length) throws IOException
  {
    return sd.read(cbuf, offset, length);
  }

  //------------------------------------------------------------------------------------------------
  
  /**
   * Tell whether this stream is ready to be read.  An InputStreamReader is
   * ready if its input buffer is not empty, or if bytes are available to be
   * read from the underlying byte stream.
   *
   * @exception  IOException  If an I/O error occurs
   */
  public boolean ready() throws IOException
  {
    return sd.ready();
  }
  
  //------------------------------------------------------------------------------------------------
  
  public void setCharset(Charset cs)
  {
    sd = StreamDecoder.forInputStreamReader(input, this, cs);
  }
  
  //------------------------------------------------------------------------------------------------
  
  public void setCharset(CharsetDecoder dec)
  {
    sd = StreamDecoder.forInputStreamReader(input, this, dec);
  }

  //------------------------------------------------------------------------------------------------

  public void setCharset(String charsetName) throws UnsupportedEncodingException
  {
    sd = StreamDecoder.forInputStreamReader(input, this, charsetName);
  }

  //------------------------------------------------------------------------------------------------
  
}
