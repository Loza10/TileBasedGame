/**
 * AllTiles.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 30, 2022
 * Updated: May 13, 2022
 *
 * AllTiles handles tile management, including their associated slots.
 */

package tile;

import java.util.Random;

import board.GameTimer;
import board.Slot;
import listener.SlotMouseListener;

public class AllTiles {

  public Tile[]     tiles;

  // all possible slots tile can be attached to
  public Slot[]     slots;
  
  public GameTimer  timer;

  protected int     size;

  // has the user modified the placement/rotation of any tiles?
  protected boolean modified;
  protected boolean firstmove;
  // valid file/mze flag
  protected boolean valid;

  // randomizing position and rotation
  private long      seed   = System.nanoTime();
  private Random    random = new Random(seed);

  /*
   * 
   * constructors
   * 
   */



  /**
   * Create new AllTile instance with blank tiles
   */
  public AllTiles() {
    
    this.setTimer(new GameTimer());
    
    this.tiles = new Tile[16];
    this.slots = new Slot[32];
    this.size  = 16;

    // initialize tile and set tile ids
    for (int i = 0; i < 16; i++) {
      this.tiles[i] = new Tile();
      this.tiles[i].setId(i);
    }

    // initialize slots and set slot ids
    for (int i = 0; i < 32; i++) {
      this.slots[i] = new Slot();
      this.slots[i].setId(i);
    }

    // set state defaults
    this.modified  = false;
    this.valid     = false;
    this.firstmove = false;
  }



  /**
   * 
   * Create new AllTile instance from existing Tile[]
   * 
   * @param tiles existing Tile[]
   */
  public AllTiles(Tile[] tiles) {

    this.tiles = tiles;
    this.size  = tiles.length;

    // set tile ids
    for (int i = 0; i < 16; i++) {
      this.tiles[i].setId(i);
    }

    // initialize slots and set slot ids
    for (int i = 0; i < 32; i++) {
      this.slots[i] = new Slot();
      this.slots[i].setId(i);
    }

    // set state defaults
    this.modified  = false;
    this.valid     = true;
    this.firstmove = false;
  }



  public void reset() {
    for (int i = 0; i < this.size; i++) {
      // This is dirty:

      int positionReset = this.tiles[i].getPositionReset();
      Slot slotReset = this.slots[positionReset];
      slotReset.hideBackground();

      this.tiles[i].reset(slotReset);
    }

  }



  public void removeAllTiles() {
    for (int i = 0; i < 32; i++) {
      Slot slot = this.slots[i];
      if (slot.tile != null) {
        Tile tile = slot.tile;
        slot.tile = null;
//        slot.remove(tile);
        slot.showBackground();
        slot.repaint();
        tile.slot = null;

      }
    }
  }

  /*
   * 
   * getters
   * 
   */



  /**
   * 
   * @return returns all tiles in order of id as Tile[]
   */
  public Tile[] getTiles() {
    return this.tiles;
  }



  /**
   * 
   * @param id of tile
   * @return tile associated with id
   */
  public Tile getTile(int id) {
    return tiles[id];
  }



  /**
   * 
   * @param id of tile
   * @return slot where tile is placed
   */
  public Slot getSlotCurrent(int tileId) {
    return this.tiles[tileId].getSlot();
  }



  /**
   * 
   * @param slotId of index in Slot[]
   * @return slot reference
   */
  public Slot getSlot(int slotId) {
    return this.slots[slotId];
  }
  
  
  public GameTimer getTimer() {
    return this.timer;
  }



  public boolean isModified() {
    return this.modified;
  }



  public boolean isFirstMoveMade() {
    return this.firstmove;
  }



  public boolean isValid() {
    return this.valid;
  }

  /*
   * 
   * setters
   * 
   */



  /**
   * 
   * Attach slot to slot id/index. Only to be used for AllTiles setup!
   * 
   * @param id   of intended slot id/index
   * @param slot to be attached
   */
  public void attachSlot(int id, Slot slot) {
    // only to be used for initial setup of AllTiles
    this.slots[id] = slot;
    this.slots[id].setId(id);
  }



  /**
   * 
   * Set current slot to its id
   * 
   * @param tileId
   * @param slot
   */
  public void setTileOnSlot(int id, Slot slot) {
    Tile tile = tiles[id];
    tile.position = slot.id;
    // taken from another method for cleanup
    // might not need all of these...

    slot.tile = tile;
    tile.slot = slot;
    slot.hideBackground();

    slot.add(tile);
    tile.unselect();
  }



  /**
   * 
   * Set all id's
   * 
   */
  public void setIds() {
    for (int i = 0; i < this.tiles.length; i++) {
      // must use array index as id has not been set
      this.tiles[i].setId(i);
    }
  }



  public void setModified(boolean state) {
    
//    if(state == false) {
//      this.getTimer().setTime(0);
//    }
    
    this.modified = state;
  }
  
  
  public void setTimeToModified() {
    if(this.isModified() == false) {
      this.getTimer().setTime(0);
    }
  }



  public void setFirstMove(boolean state) {
    this.firstmove = state;
  }



  public void setValid(boolean state) {
    
    if(state == false) {
      this.getTimer().setTime(0);
    }
    
    this.valid = state;
  }
  
  
  public void setTimer(GameTimer timer) {
    this.timer = timer;
  }
  
  

  /*
   * 
   * Randomization
   * 
   */



  /**
   * 
   * Randomize the position and rotation of all tiles on outerslots
   * 
   */
  public void randomize() {
    // shuffle positions to prevent specific tiles from
    // always being rotated by the same angle
    this.randomizePositions();
    this.randomizeAngles();
    // produce uniform distribution of rotations
    this.randomizePositions();
  }



  /**
   * 
   * Randomize the position of all tiles on outerslots
   * 
   */
  public void randomizePositions() {
    // random in-place shuffle
    for (int i = 0; i < tiles.length; i++) {
      int  choice = random.nextInt(tiles.length - i);
      Tile temp   = tiles[choice];
      tiles[choice]               = tiles[tiles.length - 1 - i];
      tiles[tiles.length - 1 - i] = temp;
    }
    // ensure reset actually resets to this position
    for (int i = 0; i < tiles.length; i++) {
      tiles[i].setPositionReset(i);
    }
  }



  /**
   * 
   * Randomize the rotation of all tiles
   * 
   */
  public void randomizeAngles() {

    // decides how many get randomized to each angle
    int stop0   = random.nextInt(0, 3);
    int stop90  = random.nextInt(stop0 + 1, 14);
    int stop180 = random.nextInt(stop90 + 1, 15);
    // rotate all but 0 angle tiles
    for (int i = stop0; i < stop90; i++) {
      tiles[i].rotateToAngle(90);
    }
    for (int i = stop90; i < stop180; i++) {
      tiles[i].rotateToAngle(180);
    }
    for (int i = stop180; i < tiles.length; i++) {
      tiles[i].rotateToAngle(270);
    }
    // ensure reset actually resets to this angle
    for (int i = 0; i < tiles.length; i++) {
      tiles[i].setAngleReset(tiles[i].getCurrentAngle());
    }

  }

  /*
   * 
   * Misc
   * 
   */

//  public boolean isSolved() {
//    
//    for(Tile tile : tiles) {
//      if(tile.getId() != (tile.getSlot().getId() + 16)) {
//        return false;
//      }
//    }
//    
//    return true;
//  }



  public synchronized void addMouseListener(
      SlotMouseListener slotMouseListener) {
    // ensure tiles exist
    if (tiles == null) {
      return;
    }
    // push mouseListener to all existing tiles
    for (int i = 0; i < tiles.length; i++) {
      if (this.tiles[i] != null) {
        this.tiles[i].addMouseListener(slotMouseListener);
      }
    }
  }
}
