/**
 * Slot.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 4, 2022
 * Updated: May 13, 2022
 *
 * Slot is the template both BoardSlot and OuterSlot derive from. It contains 
 * all getters and setters for a Tile's size, color, and id properties. It
 * also handles setting and removing a Tile.
 */

package board;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import appearance.Palette;
import listener.SlotMouseListener;
import subpanel.SubPanel;
import tile.Tile;

public class Slot extends JPanel {

  protected int               size;
  protected Palette           color;
  public Tile              tile;
  public int               id;                   // slot permanent id number
  protected SubPanel          location;
  protected SlotMouseListener slotMouseListener;
  protected boolean board;

  
  
  
  public Slot() {
    this.setBoard(false);
  }
  
  
  
  public Slot(int size, Palette color) {
    this.setBoard(false);
    
    this.slotMouseListener = null;
    this.size              = size;
    this.color             = color;

    super.setBackground(color);
  }
  
  
  public void showBackground() {
    if(!this.isBoard()) {
      return;
    }
    
    JPanel background = new JPanel();
    background.setBackground(Palette.boardTileBackground);
    background.setSize(new Dimension(100, 100));
    background.setBorder(BorderFactory.createLineBorder(Palette.boardTileGrid));
    background.setPreferredSize(new Dimension(100, 100));
    this.add(background);
  }
  
  
  public void hideBackground() {
    
    if(!this.isBoard()) {
      return;
    }
    
    this.removeAll();
  }
  


  public boolean isEmpty() {
    return this.tile == null;
  }

  

  // setters
  public void setColor (Palette newColor) {
    this.color = newColor;
    super.setBackground(color);
  }



  public void setId (int id) {
    this.id = id;
  }



  public void setSize (int newSize) {
    this.size = newSize;
//    super.setBorder(BorderFactory.createEmptyBorder(this.size, this.size,
//        this.size, this.size));
  }
  
  
  
  public void setBoard(boolean state) {
    this.board = state;
  }

  

  // getters
  


  public int getId() {
    return this.id;
  }



  public Tile getTile() {
    return tile;
  }
  
  
  public boolean isBoard() {
    return this.board;
  }

  

  // listeners
  public synchronized void addMouseListener (
      SlotMouseListener slotMouseListener) {
    this.slotMouseListener = slotMouseListener;
  }

}
