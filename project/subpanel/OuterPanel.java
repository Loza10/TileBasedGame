/**
 * OuterPanel.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 1, 2022
 * Updated: May 13, 2022
 *
 * OuterPanel initializes the 16 JPanels that the outer tile holding
 * areas will be kept in, as well as handling tiles being returned to their
 * original spots during a reset.
 */

package subpanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import appearance.Palette;
import board.OuterSlot;
import listener.SlotMouseListener;
import tile.AllTiles;
import tile.Tile;

public class OuterPanel extends SubPanel {

  JPanel[] outerSlots;
  AllTiles allTiles;

  int      side;



  public OuterPanel(AllTiles allTiles, int side) {
    super();

    this.side              = side;

    this.slotMouseListener = null;

    this.setBackground(Palette.background);
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    this.allTiles   = allTiles;
    this.outerSlots = new OuterSlot[8];
    this.gbl        = new GridBagLayout();
    this.gbc        = new GridBagConstraints();
    this.setLayout(gbl);

    for (int row = 0; row < 8; row++) {

      // GridBag Layout
      GridBagLayout gblTile = new GridBagLayout();
      gbc.gridx       = 0;
      gbc.gridy       = row * 2;

      // set up all outerTiles
      outerSlots[row] = new OuterSlot(100);
      outerSlots[row].setLayout(gblTile);

      int  currentId   = row + (8 * (side));

      Tile currentTile = this.allTiles.getTile(currentId);
      // if file couldn't be loaded then maze tiles won't be placed
      if (currentTile != null) {
        // set tile on outerTile
        this.allTiles.attachSlot(currentId, (OuterSlot) outerSlots[row]);
        this.allTiles.setTileOnSlot(currentId, (OuterSlot) outerSlots[row]);
      }

      this.add(outerSlots[row], gbc);

      this.allTiles.setIds();

      // spacer between outerTiles
      gbc.gridx = 0;
      gbc.gridy = row * 2 + 1;
      JPanel spacer = new JPanel();
      spacer.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
      spacer.setBackground(Palette.background);
      this.add(spacer, gbc);
    }
  }



  public synchronized void addMouseListener(
      SlotMouseListener slotMouseListener) {
    
    this.slotMouseListener = slotMouseListener;
    for (int i = 0; i < 8; i++) {
      outerSlots[i].addMouseListener(slotMouseListener);
    }
  }

}
