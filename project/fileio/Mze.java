/** 
 * Mze.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 24, 2022
 * Updated: April 29, 2022
 *
 * Mze reads the byte info from a data input file and converts it to integers
 * or floats. It also writes to created files by converting integers and 
 * floats to byte arrays. The handling of default.mze is handled here too.
 */

package fileio;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import board.Slot;
import tile.AllTiles;
import tile.Tile;

public class Mze {

  private int      status;
  private Tile[]   tiles;
  private AllTiles allTiles;



  public Mze() throws IOException {

  }

  /*
   * .mze File Layout:
   * 
   * Header Tile Count Time Played Tile id Tile current position Tile current
   * rotation Line Count X0 Y0 X1 Y1 ... Repeat for all lines ... Repeat for all
   * tiles
   * 
   */



  public static AllTiles bytesToFullTiles(byte[] inputBytes, AllTiles allTiles)
      throws IOException {

    // this won't always work, so find a better way
    // only works when reset for all tiles has different starting slots than
    // loaded maze.
    // will still have problems with slots being dirty from last maze.
    allTiles.removeAllTiles();

//    System.out.println("-----read bytesToFullTilesstart-----");
//    String hex = "";
//    int counter = -3;
//    for (byte i: inputBytes) {
//      hex += String.format("%02X", i);
//     if(counter % 4 == 0) {
//        hex += String.format(" ");
//      }
//      counter++;
//    }
//    System.out.println(hex);
//    System.out.println("-----read bytesToFullTilesstart-----");

    // Header
    byte[] headerBytes = byteSlice(inputBytes, 0, 4);
    int    header      = bytesToInt(headerBytes);

    try {
      allTiles.setModified(isModified(header));
      allTiles.setValid(true);
    } catch (IOException e) {
      allTiles.setValid(false);
      return allTiles;
    }
    
    allTiles.setFirstMove(false);

    // Tile Count
    byte[] sizeBytes = byteSlice(inputBytes, 4, 8);
    int    size      = bytesToInt(sizeBytes);
    
    byte[] timeBytes = byteSlice(inputBytes, 8, 16);
    long   time      = bytesToLong(timeBytes);

    // All Tiles
    int    offset    = 16;
    for (int tileIndex = 0; tileIndex < size; tileIndex++) {

      int    tileStart         = offset;

      // Tile Current Positon
      byte[] tilePositionBytes = byteSlice(inputBytes, tileStart,
          tileStart + 4);
      int    position          = bytesToInt(tilePositionBytes);
      offset += 4;

      // Tile Current Rotation
      byte[] tileRotationBytes = byteSlice(inputBytes, tileStart + 4,
          tileStart + 8);
      int    angle             = bytesToInt(tileRotationBytes);
      offset += 4;

      // Line Count
      byte[] lineCountBytes = byteSlice(inputBytes, tileStart + 8,
          tileStart + 12);
      int    lineCount      = bytesToInt(lineCountBytes);
      offset += 4;

      Tile              tile  = allTiles.getTile(tileIndex);
//      ArrayList<Line2D> lines = tile.getLines();
      ArrayList<Line2D> lines = new ArrayList<Line2D>();
//      if(lineCount  < lines.size()) {
//        
//      }
//      lines.subList(0, lineCount);

      // All Lines in current Tile
      for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {

        int    lineStart = offset;

        // Starting index for all points in current line
        int    x0Start   = lineStart;
        int    y0Start   = x0Start + 4;
        int    x1Start   = y0Start + 4;
        int    y1Start   = x1Start + 4;

        // Isolate bytes for all points in current line
        byte[] x0Bytes   = byteSlice(inputBytes, x0Start, x0Start + 4);
        byte[] y0Bytes   = byteSlice(inputBytes, y0Start, y0Start + 4);
        byte[] x1Bytes   = byteSlice(inputBytes, x1Start, x1Start + 4);
        byte[] y1Bytes   = byteSlice(inputBytes, y1Start, y1Start + 4);

        // Convert all points from bytes to float
        float  x0        = bytesToFloat(x0Bytes);
        float  y0        = bytesToFloat(y0Bytes);
        float  x1        = bytesToFloat(x1Bytes);
        float  y1        = bytesToFloat(y1Bytes);

        if (lineIndex < lines.size()) {
          // Existing space to copy Line2D elements into
          // so copy away:
          Line2D nextLine = lines.get(lineIndex);
          nextLine.setLine(x0, y0, x1, y1);
        }
        else {
          // loading tile has more lines, so expand:
          Line2D nextLine = new Line2D.Float(x0, y0, x1, y1);
          lines.add(nextLine);
        }

        // Increment offset index past current line
        offset += 16;
      }

      tile.setLines(lines);

      tile.setPosition(position);
      tile.setPositionReset(position);
      tile.setId(tileIndex);
      tile.setAngleReset(angle);
//      tile.rotateToAngle(angle);

      Slot currentSlot = allTiles.getSlot(position);
      allTiles.setTileOnSlot(tileIndex, currentSlot);

      tile.repaint();
    }

    allTiles.getTimer().setTime(time);
    allTiles.setTimeToModified();

    return allTiles;
  }



  /**
   * Overrides current allTiles object and stores converted bytes to information
   * stored in allTiles object.
   * <p>
   * This function is static
   * 
   * @param inputBytes array of bytes
   * @param allTiles   AllTiles object
   * @return Alltiles object
   * @throws IOException
   */
  public static AllTiles bytesToEmptyTiles(byte[] inputBytes, AllTiles allTiles)
      throws IOException {

//    System.out.println("-----read bytesToEmptyTilesstart-----");
//    String hex = "";
//    int counter = -3;
//    for (byte i: inputBytes) {
//      hex += String.format("%02X", i);
//     if(counter % 4 == 0) {
//        hex += String.format(" ");
//      }
//      counter++;
//    }
//    System.out.println(hex);
//    System.out.println("-----read bytesToEmptyTilesstart-----");

    allTiles = new AllTiles();

    byte[] headerBytes = byteSlice(inputBytes, 0, 4);
    int    header      = bytesToInt(headerBytes);

    try {
      allTiles.setModified(isModified(header));
      allTiles.setValid(true);
    } catch (IOException e) {
      allTiles.setValid(false);
      return allTiles;
    }
    allTiles.setFirstMove(false);

    // get num tiles
    byte[] sizeBytes = byteSlice(inputBytes, 4, 8);
    int    size      = bytesToInt(sizeBytes);
    
    byte[] timeBytes = byteSlice(inputBytes, 8, 16);
    long   time      = bytesToLong(timeBytes);

    // Iterate thru and add tiles
    int    offset    = 16;
    for (int tileIndex = 0; tileIndex < size; tileIndex++) {

      int    tileStart         = offset;

      // get current tile position
      byte[] tilePositionBytes = byteSlice(inputBytes, tileStart,
          tileStart + 4);
      int    position          = bytesToInt(tilePositionBytes);

      offset += 4;

      // get current tile rotation
      byte[] tileRotationBytes = byteSlice(inputBytes, tileStart + 4,
          tileStart + 8);
      int    angle             = bytesToInt(tileRotationBytes);

      offset += 4;

      // get number of lines for current tile
      byte[] lineCountBytes = byteSlice(inputBytes, tileStart + 8,
          tileStart + 12);
      int    lineCount      = bytesToInt(lineCountBytes);
      offset += 4;

      Tile              tile  = allTiles.getTile(tileIndex);
      ArrayList<Line2D> lines = tile.getLines();

      // get all lines
      for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {

        // Iterate thru and add all lines to tile
        int    lineStart = offset;

        // Starting index for all points in current line
        int    x0Start   = lineStart;
        int    y0Start   = x0Start + 4;
        int    x1Start   = y0Start + 4;
        int    y1Start   = x1Start + 4;

        // Isolate bytes for all points in current line
        byte[] x0Bytes   = byteSlice(inputBytes, x0Start, x0Start + 4);
        byte[] y0Bytes   = byteSlice(inputBytes, y0Start, y0Start + 4);
        byte[] x1Bytes   = byteSlice(inputBytes, x1Start, x1Start + 4);
        byte[] y1Bytes   = byteSlice(inputBytes, y1Start, y1Start + 4);

        // Convert all points from bytes to float
        float  x0        = bytesToFloat(x0Bytes);
        float  y0        = bytesToFloat(y0Bytes);
        float  x1        = bytesToFloat(x1Bytes);
        float  y1        = bytesToFloat(y1Bytes);

        Line2D nextLine  = new Line2D.Float(x0, y0, x1, y1);

        lines.add(nextLine);

        // Increment offset index past current line
        offset += 16;
      }

      tile.setPosition(position);
      tile.setPositionReset(position);
      tile.setId(tileIndex);
      tile.setAngleReset(angle);
//      tile.rotateToAngle(angle);

    }
    
    allTiles.getTimer().setTime(time);
    allTiles.setTimeToModified();

    return allTiles;
  }



  /**
   * Converts tiles to an mze byte[]
   * 
   * @param allTiles
   * @return byte[] in mze file format
   */
  public static byte[] tilesToByte(AllTiles allTiles) throws IOException {

    Tile[] tiles   = allTiles.getTiles();
    byte[] results = {};

    // Header
    int    header  = getModifiedBytes(allTiles.isModified());
    results = appendByteArray(results, intToByte(header));

    // Tile Count
    int tileCount = tiles.length;
    results = appendByteArray(results, intToByte(tileCount));
    
    // GameTimer time
    long time = allTiles.getTimer().getTime();
    results = appendByteArray(results, longToByte(time));

    // All tiles
    for (int tileIndex = 0; tileIndex < tileCount; tileIndex++) {

      Tile tile = tiles[tileIndex];

      // Tile Current Position
      results = appendByteArray(results, intToByte(tile.getPosition()));

      // Tile Current Angle
      int angle = tile.getCurrentAngle();
      results = appendByteArray(results, intToByte(angle));

      ArrayList<Line2D> lines     = tile.getLines();
      int               lineCount = lines.size();
      results = appendByteArray(results, intToByte(lineCount));

      // All lines in current tile
      for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {

        float   x0     = (float) lines.get(lineIndex).getX1();
        float   y0     = (float) lines.get(lineIndex).getY1();
        float   x1     = (float) lines.get(lineIndex).getX2();
        float   y1     = (float) lines.get(lineIndex).getY2();
        float[] points = { x0, y0, x1, y1 };
        results = appendByteArray(results, floatArrayToByte(points));

      }
    }

//    System.out.println("--writeout----");
//     String hex = "";
//     int counter = -3;
//     for (byte i: results) {
//       hex += String.format("%02X", i);
//      if(counter % 4 == 0) {
//         hex += String.format(" ");
//       }
//       counter++;
//     }
//     System.out.println(hex);
//     System.out.println("---writeout-----");
    return results;
  }



  /**
   * Returns boolean determined by the header of an input file
   * <p>
   * This function is protected and static
   *
   * @param header int
   * @return boolean
   * @throws IOException
   */
  protected static boolean isModified(int header) throws IOException {

    // randomize Bytes are 0xca, 0xfe, 0xbe, 0xef
    if (header == -889274641) {
      return false;
    }
    // dont' randomize 0xca 0xfe 0xde 0xed
    else if (header == -889266451) {
      return true;
    }
    else {
      throw new IOException();
    }

  }



  /**
   * returns an integer based on the modification state
   * <p>
   * This function is static and protected
   *
   * @param state boolean
   * @return void
   */
  protected static int getModifiedBytes(boolean state) throws IOException {
    if (state) {
      return -889266451;
    }

    return -889274641;
  }



  /**
   * 
   * Concatenates two byte arrays together.
   * 
   * @param target byte[] front byte array after append
   * @param insert byte[] back byte array after append
   * @return byte[] concatenation of target and insert
   */
  public static byte[] appendByteArray(byte[] target, byte[] insert) throws IOException {

    byte[] result = new byte[target.length + insert.length];

    for (int i = 0; i < target.length; i++) {
      result[i] = target[i];
    }

    for (int i = 0; i < insert.length; i++) {
      result[target.length + i] = insert[i];
    }
    return result;

  }



  /**
   * 
   * @return a Tile array from an <b>.mze</b> file.
   */
  public Tile[] getTiles() throws IOException {
    return tiles;
  }



  public void setTiles(Tile[] tiles) throws IOException {
    this.tiles = tiles;
  }



  public AllTiles getAllTiles() throws IOException {
    return this.allTiles;
  }



  public void setAllTiles(AllTiles allTiles) throws IOException {
    this.allTiles = allTiles;
  }



  /**
   * 
   * @return a int from an <b>.mze</b> file.
   */
  public int getStatus() throws IOException {
    return status;
  }



  /**
   * Slices a byte array starting at the <b>start</b> index up to the </b>
   * index</b> (exclusive).
   * 
   * @param bytes byte[]
   * @param start integer
   * @param stop  integer
   * @return byte[]
   */
  private static byte[] byteSlice(byte[] bytes, int start, int stop) throws IOException {

    try {
      int    length      = stop - start;
      byte[] result      = new byte[length];

      int    resultIndex = 0;
      for (int i = start; i < stop; i++) {
        result[resultIndex] = bytes[i];
        resultIndex++;
      }

      return result;
      
    } catch(ArrayIndexOutOfBoundsException e) {
      throw new IOException();
    }
    
  }



  /**
   * Converts a byte array into an integer.
   * 
   * @param bytes bytes array
   * @return int
   */
  private static int bytesToInt(byte[] bytes) throws IOException  {
    return ByteBuffer.wrap(bytes).getInt();
  }
  
  
  
  private static long bytesToLong(byte[] bytes) throws IOException  {
    return ByteBuffer.wrap(bytes).getLong();
  }



  /**
   * Converts a byte array into a float.
   * 
   * @param bytes byte array
   * @return float
   */
  private static float bytesToFloat(byte[] bytes) throws IOException  {
    return ByteBuffer.wrap(bytes).getFloat();
  }



  /**
   * Converts a integer into a byte array.
   * 
   * @param data int
   * @return byte[]
   */
  private static byte[] intToByte(int data) throws IOException  {
    byte[]     bytes  = new byte[4];
    ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
    buffer.putInt(data);
    return buffer.array();
  }
  
  
  
  private static byte[] longToByte(long data) throws IOException  {
    byte[] bytes = new byte[8];
    ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
    buffer.putLong(data);
    return buffer.array();
  }



  /**
   * Converts a float array into a byte array.
   * 
   * @param data float[}
   * @return byte[]
   */
  private static byte[] floatArrayToByte(float[] data) throws IOException  {
    byte[]     bytes  = new byte[4 * data.length];
    
    ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
    
    for (int i = 0; i < data.length; i++) {
      buffer.putFloat(data[i]);
    }
    return buffer.array();
  }

}
