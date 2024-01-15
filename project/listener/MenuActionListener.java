/**
 * MenuActionListener.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 2, 2022
 * Updated: May 13, 2022
 *
 * MenuActionListener handles clicks of the file, reset, or quit buttons.
 */

package listener;

import java.awt.event.ActionEvent;

import board.GameTimer;
import board.GameWindow;
import fileio.FileMenu;
import subpanel.OuterPanel;
import tile.AllTiles;

public class MenuActionListener extends ActionAdapter {

  OuterPanel outerPanelLeft;
  OuterPanel outerPanelRight;
  AllTiles   allTiles;



  public MenuActionListener(OuterPanel outerPanelLeft,
      OuterPanel outerPanelRight, AllTiles allTiles) {

    this.outerPanelLeft  = outerPanelLeft;
    this.outerPanelRight = outerPanelRight;
    this.allTiles        = allTiles;
  }



  @Override
  public void actionPerformed(ActionEvent e) {

    if ("exit".equals(e.getActionCommand())) {
      if (allTiles.isModified()) {
        FileMenu.quit(allTiles);
      } else {
        System.exit(0);
      }
    }

    if ("reset".equals(e.getActionCommand())) {
      if (!allTiles.isModified()) {
        // skip if still unmodified
        return;
      }
      allTiles.setModified(false);
      allTiles.reset();
      allTiles.setFirstMove(false);
      allTiles.timer.reset();
    }

    if ("file".equals(e.getActionCommand())) {
      FileMenu.choose(allTiles);
    }

  }

}
