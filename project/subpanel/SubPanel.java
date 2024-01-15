/**
 * SubPanel.java
 * @author Cody, Faith, Patrick
 * Date: Mar 4, 2022
 * Updated: May 13, 2022
 *
 * SubPanel sets up GridBagLayout, GridBagConstraints, and 
 * SlotMouseListener objects to be used by BoardPanel and OuterPanel.
 */

package subpanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import listener.SlotMouseListener;
import tile.Tile;

public abstract class SubPanel extends JPanel {

  protected GridBagLayout      gbl;
  protected GridBagConstraints gbc;
  protected SlotMouseListener  slotMouseListener;
  protected Tile[]             tiles;



  public SubPanel() {
    super();
  }
  
  
  public Tile[] getTiles() {
    return this.tiles;
  }
  
  
  
  public void setTiles(Tile[] tiles) {
    this.tiles = tiles;
  }

}
