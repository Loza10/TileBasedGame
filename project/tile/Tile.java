/**
 * Tile.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Feb 27, 2022
 * Updated: May 13, 2022
 *
 * All 16 tiles in the game are objects of the Tile class. Tile has getters 
 * and setters for a Tile's associated id number, home, and starting position.
 * It also handles the highlighting of a Tile based on its selection status,
 * the redrawing of tiles when they are rotated, and the flashing of tiles on
 * an incorrect move.
 */

package tile;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;

import appearance.Palette;
import board.Slot;
import listener.SlotMouseListener;
import listener.FlashRedListener;
import listener.FlashWhiteListener;

public class Tile extends JPanel {

 
  
  public int               id;                     // solved location
  public int               position;               // current location
  private int              positionReset;          // reset location
  public Slot              slot;                   // pointer to current slot tile is on
  public Slot              slotReset;
  
  
  private int               size;
  private ArrayList<Line2D> lines;
  private BasicStroke       width            = new BasicStroke((float) 3);

  // Transformation matrices for 90 degree rotations
  // Do note that Swing uses an inverted y-axis, so these
  // transformations reflect that
  private int               angleCurrent;
  private int               angleReset;
  private final float[][]   clockwise        = { { 0, 1 }, { -1, 0 } };

  private SlotMouseListener slotMouseListener;
  private ActionListener    flashWhite;
  private ActionListener    flashRed;

  private Timer             timerWhite;
  private Timer             timerRed;
  


  /*
   * 
   * Constructors
   * 
   */
  
  
  
  public Tile() {
    super();
    
    this.lines = new ArrayList<Line2D>();
    this.size = 100;
    
    super.setPreferredSize(new Dimension(this.size, this.size));
    super.setBackground(Palette.boardTileBackground);
    
    super.paint(getGraphics());
  }


  

  public Tile(int position, ArrayList<Line2D> lines) {
    super();
    
    this.position   = position;
    this.lines = lines;
    this.size  = 100;   

    super.setPreferredSize(new Dimension(this.size, this.size));
    super.setBackground(Palette.boardTileBackground);
    
    this.lines = new ArrayList<Line2D>();
    for (int i = 0; i < lines.size(); i++) {
      Line2D nextLine = lines.get(i);
      this.lines.add(nextLine);
    }

    super.paint(getGraphics());
  }

  
  
  
  public void reset(Slot slot) {
    
    // reset to positionReset
    
    // I would like to do this
    // Need a way to initialize slotReset:
//    this.move(this.slotReset);
    
    // Instead:
    this.move(slot);
    
    // reset angle
    this.rotateToReset();
    
  }
  
  
  
  
  public void move(Slot target) {
    
    // Remove existing tile from target slot
    if(target != null) {
      target.hideBackground();
      
      Tile newSlotsOldTile = target.tile;
      if(newSlotsOldTile != null) {
        newSlotsOldTile.slot = null;
      }
      
      target.tile = null;
    }

    // Remove current tile from old slot
    Slot oldSlot = this.slot;
    
    if(oldSlot != null) {
      oldSlot.tile = null;
      oldSlot.remove(this);
      oldSlot.showBackground();
      
      oldSlot.repaint();
      oldSlot = null;
    }
    
    // Set down current tile
    this.slot = target;
    this.slot.tile = this;
    this.position = target.id;
    target.add(this);
    
    // Repaint current tile/
    this.unselect();
    this.repaint();
  }
  
  
  /////
  
  
  
  /*
   * 
   * getters
   * 
   */


  
  public int getId() {
    return this.id;
  }
  
  
  

  /**
   * 
   * @return the originally set slot location of the tile.
   */
  public Slot getSlot() {
    return this.slot;
  }
  
  
  

  /**
   * 
   * @return the position on the board associated with the tile.
   */
  public int getPosition() {
    return this.position;
  }
  
  
  
  public int getPositionReset() {
    return this.positionReset;
  }
  
  
  
  
  /**
   * 
   * @return the present angle of the tile relative to tiles home in
   * 90 degree increments
   */
  public int getCurrentAngle() {
    return this.angleCurrent;
  }
  
  
  
  
  public int getAngleReset() {
    return this.angleReset;
  }
  
  
  
  
  /**
   * 
   * @return an array of Line2D that contains tiles lines
   */
  public ArrayList<Line2D> getLines() {
    return this.lines;
  }
  

  
  
  /*
   * 
   * setters
   * 
   */

  
  
  
  public void setId(int id) {
    this.id = id;
  }
  
  
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  
  
  public void setPositionReset(int position) {
    this.positionReset = position;
  }


  
  
  public void setCurrentAngle(int angle) {
    this.angleCurrent = angle;
  }
  

  
  
  public void setAngleReset(int angle) {
    this.angleReset = angle;
  }
  
  
  
  public void setLines(ArrayList<Line2D> lines) {
    this.lines = lines;
  }
  
  
  
  
  /*
   * 
   * Rotation Handling
   * 
   */

  
  
  
  public void rotateToReset() {
    
    this.rotateToAngle(this.angleReset);
  }



  
  public void rotateToAngle(int angle) {

    int angleDelta = (angle - this.angleCurrent + 4) % 4;

    for (int count = 0; count < angleDelta; count++) {
      this.rotateClockwise();
    }

//    this.angleCurrent %= 4;
    
  }


  

  public void rotateClockwise() {
    

    Matrix transformation = new Matrix(clockwise);
    this.lines = rotate(transformation);

    super.paint(getGraphics());

    this.angleCurrent += 4;
    this.angleCurrent  = (this.angleCurrent + 1) % 4;
  }
  
  

  private ArrayList<Line2D> rotate(Matrix transformation) {

    ArrayList<Line2D> results = new ArrayList<Line2D>();

    for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
      
      Line2D rotatedLine = this.lines.get(lineIndex);
      results.add(this.rotateLine(rotatedLine, transformation));

    }

    return results;
  }



  
  private Line2D rotateLine(Line2D line, Matrix transformation) {
    
    float origin = 50;

    // convert to float
    // subtract 49 to center all coordinates around origin
    // for matrix rotation about origin
    float  x1     = (float) line.getX1() - origin;
    float  y1     = (float) line.getY1() - origin;
    float  x2     = (float) line.getX2() - origin;
    float  y2     = (float) line.getY2() - origin;

    Vector v1     = new Vector(new float[] { x1, y1 });
    Vector v2     = new Vector(new float[] { x2, y2 });

    Vector b1     = transformation.product(v1);
    Vector b2     = transformation.product(v2);

    // convert transformed vector back to float x,y points
    // add back 49 so no longer centered on origin
    float  tx1    = b1.getElement(0) + origin;
    float  ty1    = b1.getElement(1) + origin;
    float  tx2    = b2.getElement(0) + origin;
    float  ty2    = b2.getElement(1) + origin;

    Line2D result = new Line2D.Float(tx1, ty1, tx2, ty2);
    return result;
  }
  
  
  

  /*
   * 
   * listeners and mouse handling
   * 
   */



  /**
   * 
   * Sets the slotMouseListener for the tile.
   * 
   * @param slotMouseListener
   */
  public void addActionListener(SlotMouseListener slotMouseListener) {
    this.slotMouseListener = slotMouseListener;
  }



  /**
   * 
   * Highlights tile.
   * 
   */
  public void select() {
    super.setBackground(Palette.ORANGE);
  }



  /**
   * 
   * Un-highlights tile.
   * 
   */
  public void unselect() {
    super.setBackground(Palette.WHITE);
  }



  /**
   * 
   * Highlights tile with error color
   * 
   */
  public void errorColor() {
    super.setBackground(Palette.RED);
  }



  /**
   * 
   * Flashes tile to indicate errors.
   * 
   */
  public void errorSelect() {
    this.flashRed   = new FlashRedListener(this);
    this.flashWhite = new FlashWhiteListener(this);
    int delay = 0;
    for (int i = 0; i < 2; i++) {
      delay           = i * 500;
      this.timerWhite = new Timer(delay, flashWhite);
      this.timerWhite.setRepeats(false);
      this.timerWhite.start();
      this.timerRed = new Timer(delay + 250, flashRed);
      this.timerRed.setRepeats(false);
      this.timerRed.start();
    }
    this.timerWhite = new Timer(1000, flashWhite);
    this.timerWhite.setRepeats(false);
    this.timerWhite.start();
  }

  
  
  
  /*
   * 
   * Drawing and painting
   * 
   */


  

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    draw(g);
  }



  /**
   * 
   * Draws all Line2D lines array on the tile.
   * 
   * @param graphics
   */
  private void draw(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics;
    graphics2d.setStroke(width);
    for (int lineIndex = 0; lineIndex < this.lines.size(); lineIndex++) {
      graphics2d.draw(lines.get(lineIndex));
    }
  }

}
