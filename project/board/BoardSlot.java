/**
 * BoardSlot.java
 * @author Cody, Faith, Patrick
 * Date: Mar 4, 2022
 * Updated: May 13, 2022
 *
 * BoardSlot creates the tile areas on the gameboard with the proper color
 * and size.
 */

package board;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import appearance.Palette;

public class BoardSlot extends Slot {

  // Default constructor
  public BoardSlot(int size) {
    super(size, Palette.boardTileBackground);

//    super.setBorder(BorderFactory.createLineBorder(Palette.boardTileGrid));
    super.setPreferredSize(new Dimension(super.size, super.size));

  }

}
