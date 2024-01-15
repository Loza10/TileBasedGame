/**
 * FlashWhiteListener.java
 * @author Cody, Faith, Patrick
 * Date: April 13, 2022
 * Updated: May 2, 2022
 *
 * FlashWhiteListener listens for if an invalid swap move is made, and gives Tile
 * the functionality of returning to white after turning red in this event. 
 */

package listener;

import java.awt.event.ActionEvent;
import tile.Tile;

public class FlashWhiteListener extends ActionAdapter {
  Tile tile;



  public FlashWhiteListener(Tile tile) {
    this.tile = tile;
  }



  public void actionPerformed(ActionEvent evt) {
    this.tile.unselect();
  }
}
