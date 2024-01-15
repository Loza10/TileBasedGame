/**
 * Save.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 28, 2022
 * Updated: May 13, 2022
 *
 * Save lets the user save the current configuration of their maze game,
 * and enter a name for this file. This is done through 
 * JOptionPane.showInputDialog().
 */

package fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import tile.AllTiles;

public class Save {

  /**
   * Empty Save class constructor
   */
  public Save() {
  }



  /**
   * Prompts user for file name to save to. If no filename is provided, the
   * function returns. The function then attempts to call saveFile()
   * <p>
   * Catches InvalidPathException, which calls invalid() and then calls itself
   * <p>
   * This function is static
   *
   * @param currentTiles AllTiles object
   * @return void
   */
  public static void saver(AllTiles currentTiles) {

    String file = (String) JOptionPane.showInputDialog(null,
        "Please input the file name", "Save File", JOptionPane.PLAIN_MESSAGE);

    if (file == null) {
      return;
    }

    file = "input/" + file;

    try {
      saveFile(file, currentTiles);

      // redundant, should be caught by try/catch
//      if (Files.isWritable(Paths.get(file))) {
//        Files.
//        // file is valid, so load it
//        
//        
//      } else {
//        // file not valid, so start again...
//        
//        invalid();
//        saver(currentTiles);
//        
//      }

    } catch (InvalidPathException e) {
      // invalid path, so start again

      invalid();
      saver(currentTiles);

    } catch (IOException e) {
      // TODO: handle IOException thrown from Load class?
      // or just return?
    }

  }



  /**
   * If file already exist prompts user for override. If yes, call
   * Mze.tilesToByte() and call Files.write on data returned If no, then call
   * saver() and return
   * <p>
   * This function is static
   *
   * @param file         string that contains file path
   * @param currentTiles AllTiles Object
   * @return void
   */
  public static void saveFile(String file, AllTiles currentTiles)
      throws IOException {

    Object[] options = { "Yes", "No", "Cancel" };

    // 0 - Yes
    // 1 - No
    // 2 - Cancel
    if (Files.exists(Paths.get(file))) {

      int choice = JOptionPane.showOptionDialog(null,
          "This file already exists. Would you like to overwrite it?",
          "File Options", JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

      if (choice == 1) {
        // Don't overwrite file, ask for filename again
        saver(currentTiles);
        return;
      }

    }

    byte[] data = Mze.tilesToByte(currentTiles);
    Files.write(Paths.get(file), data);
  }



  /**
   * Displays an error message indicating incorrect file path
   * <p>
   * This function is static
   *
   * @return void
   */
  private static void invalid() {
    // throw up error message

    JOptionPane.showMessageDialog(null, "Incorrect File Path", "File IO Error",
        JOptionPane.ERROR_MESSAGE);

  }

}
