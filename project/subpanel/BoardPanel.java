/**
 * BoardPanel.java
 * @author Cody, Faith, Patrick
 * Date: Mar 1, 2022
 * Updated: May 13, 2022
 *
 * BoardPanel initializes the 16 tile spaces on the gameboard, keeping
 * track of them with Points.
 */

package subpanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import appearance.Palette;
import board.BoardSlot;
import listener.SlotMouseListener;
import tile.AllTiles;

public class BoardPanel extends SubPanel {

  private JPanel[] boardTiles;
  private AllTiles allTiles;
  private Point    center = new Point();



  public BoardPanel(int size, AllTiles allTiles) {
    super();

    this.allTiles          = allTiles;
    this.slotMouseListener = null;

    this.boardTiles        = new BoardSlot[16];

    this.gbl               = new GridBagLayout();
    this.gbc               = new GridBagConstraints();
    this.setLayout(gbl);

    int current = 0;
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {

        this.gbc.gridx = col;
        this.gbc.gridy = row;

        this.center.setLocation(col, row);

        BoardSlot boardSlot = new BoardSlot(size);
        this.boardTiles[current] = boardSlot;
        
        GridLayout gblBoardSlot = new GridLayout();
        boardSlot.setLayout(gblBoardSlot);
        boardSlot.setBoard(true);
        boardSlot.showBackground();
        
        this.allTiles.attachSlot(current + 16, (BoardSlot) boardSlot);

        this.add(boardTiles[current], this.gbc);
        current++;
      }
    }
  }



  public synchronized void addMouseListener(
      SlotMouseListener slotMouseListener) {
    
    this.slotMouseListener = slotMouseListener;
    for (int i = 0; i < 16; i++) {
      boardTiles[i].addMouseListener(this.slotMouseListener);
    }
  }

}
