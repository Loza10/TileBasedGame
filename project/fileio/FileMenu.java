/**
 * FileMenu.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 28, 2022
 * Updated: May 13, 2022
 *
 * FileMenu creates the popup menu for the Load and Save options once a click 
 * of File is detected in MenuActionListener, and invokes Load or Save 
 * methods appropriately based on the option chosen.
 */

package fileio;

import javax.swing.JOptionPane;
import tile.AllTiles;

public class FileMenu {
  
  /**
   * Empty constructor for FileMenu
   */
  public FileMenu() {
  }



  /**
   * Prompts the user to enter a choice after the <i>FILE</i> button is clicked.
   * <p>
   * User choices: Load, Save, Cancel
   * <p>
   * If user chooses load, the <b>loader</b> function from the load class will be
   * called.
   * <p>
   * If the user chooses save, the <b>saver</b> function from the save class will be
   * called
   * <p>
   * This function is static
   * 
   * @param currentTiles an object of the AllTiles class
   * @return returns void
   */
  public static void choose(AllTiles currentTiles) {

    Object[] options = { "Load", "Save", "Cancel" };

    // 0 - Load
    // 1 - Save
    // 2 - Cancel
    int      choice  = JOptionPane.showInternalOptionDialog(null,
        "Would you like to load or save a file?", "File Options",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
        options, options[2]);

    if (choice == 0) {
      Load.loader(currentTiles);
    }
    else if (choice == 1) {
      Save.saver(currentTiles);
    }
    // if choice == 3 cancel: just return

  }



  /**
   * Handles the quit option in the menu bar
   * <p>
   * This function is static
   * 
   * @param allTiles an object of the AllTiles class
   * @return returns void
   */
  public static void quit(AllTiles allTiles) {
    // create "quit/save/cancel" menu popup

    Object[] options = { "Exit", "Save", "Cancel" };

    // 0 - Exit
    // 1 - Save
    // 2 - Cancel
    int      choice  = JOptionPane.showOptionDialog(null,
        "Would you like to save before qutting?", "File Options",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
        options, options[2]);

    if (choice == 0) {
      System.exit(0);
    }
    else if (choice == 1) {
      Save.saver(allTiles);
      System.exit(0);
    }

  }

}
