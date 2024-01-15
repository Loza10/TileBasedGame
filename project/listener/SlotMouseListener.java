/**
 * SlotMouseListener.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 4, 2022
 * Updated: May 13, 2022
 *
 * SlotMouseListener handles click actions of tiles. It detects which type of
 * Tile was clicked, handles the moving of tiles, and sets them to be 
 * highlighted or not based on their selection status.
 */

package listener;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import appearance.Palette;
import board.GameWindow;
import board.Slot;
import tile.AllTiles;
import tile.Tile;

public class SlotMouseListener extends MouseAdapter {

  Tile     tileSelected;
  AllTiles allTiles;



  public SlotMouseListener(AllTiles allTiles) {
    super();
    this.tileSelected = null;
    this.allTiles     = allTiles;
  }



  @Override
  public void mousePressed(MouseEvent e) {

    boolean isTileClicked      = e.getComponent().getClass()
        .getName() == "tile.Tile";
    boolean isTileRightClicked = isTileClicked
        && e.getButton() == MouseEvent.BUTTON3;
    boolean isSlotClicked      = e.getComponent().getClass()
        .getName() == "board.BoardSlot"
        || e.getComponent().getClass().getName() == "board.OuterSlot";
    boolean isFillerClicked    = e.getComponent().getClass()
        .getName() == "javax.swing.JPanel";

    if (isTileRightClicked) {

      if (tileSelected != (Tile) e.getComponent()) {
        // if another tile already selected, unselect it before rotating
        // new tile
        this.unselectTile();
      }

      this.rotateTile(e);
    } else if (isTileClicked) {
      this.selectTile(e);
    } else if (isSlotClicked) {
      this.slotClicked(e);
    } else if (isFillerClicked) {
      this.fillerClicked(e);
    }

  }



  private void unselectTile() {
    // unselect currently selected tile

    if (tileSelected == null) {
      return;
    }

    tileSelected.unselect();
    tileSelected = null;
  }



  private void rotateTile(MouseEvent e) {

    Tile tile = (Tile) e.getComponent();
    tile.rotateClockwise();
    tile.repaint();

    allTiles.setModified(true);
    if (allTiles.isFirstMoveMade() == false) {
      allTiles.setFirstMove(true);
      allTiles.getTimer().start();
//      GameWindow.timer.start();
    }
  }



  private void selectTile(MouseEvent e) {

    Tile tileNext = (Tile) e.getComponent();

    if (this.tileSelected == null) {
      // select new tile
      this.tileSelected = tileNext;
      this.tileSelected.select();
    } else if (tileSelected != (Tile) e.getComponent()) {
      // new tile clicked, unselect old tile, leave nothing selected
      tileSelected.errorSelect();
      tileSelected = null;
    } else {
      // tile already selected is the tile that was clicked
      this.unselectTile();
    }
  }



  private void slotClicked(MouseEvent e) {
    Slot slot = (Slot) e.getComponent();

    if (tileSelected != null) {
      
   // move tile to empty slot
      this.tileSelected.move(slot);
      tileSelected = null;
      allTiles.setModified(true);
      if (allTiles.isFirstMoveMade() == false) {
        allTiles.setFirstMove(true);
        allTiles.getTimer().start();
      }

    } else {
//      slot.showBackground();
    }
  }



  private void fillerClicked(MouseEvent e) {
    if (tileSelected != null) {
      // filler clicked, unselect tile
      tileSelected.unselect();
      tileSelected = null;
    }
  }

}
