/**
 * FlashRedListener.java
 * @author Cody, Faith, Patrick
 * Date: April 13, 2022
 * Updated: May 2, 2022
 *
 * FlashRedListener listens for if an invalid swap move is made, and gives Tile
 * flashing functionality in this event. 
 */

package listener;

import java.awt.event.ActionEvent;
import tile.Tile;

public class FlashRedListener extends ActionAdapter {
  Tile tile;



  public FlashRedListener(Tile tile) {
    this.tile = tile;
  }



  public void actionPerformed(ActionEvent evt) {
    this.tile.errorColor();
  }
}
