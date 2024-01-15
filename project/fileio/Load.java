/**
 * Load.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 28, 2022
 * Updated: May 13, 2022
 *
 * Load lets the user enter the name of a maze file to load, whether it be
 * an original maze file or a modified saved state. This is done through
 * JOptionPane.showInputDialog().
 */

package fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import tile.AllTiles;

public class Load {
  /**
   * Empty constructor for Load class
   */
  public Load() {
  }



  /**
   * Prompts user for name of input file
   * <p>
   * If file is <i>null</i> then no further work is done and currentTiles is returned.
   * If the file is exists, then <b>loadFile()</b> is returned. Else invalidPath is
   * called and the function calls itself.
   * <p>
   * This function is static
   * 
   * @param currentTiles An object of the AllTiles class
   * @return AllTiles object
   */
  public static AllTiles loader(AllTiles currentTiles) {

    try {

      String file = (String) JOptionPane.showInputDialog(null,
          "Please input the file name", "Load File", JOptionPane.PLAIN_MESSAGE);

      if (file == null) {
        return currentTiles;
      }

      file = "input/" + file;

      if (Files.exists(Paths.get(file)) && !file.isEmpty()) {

        // file is valid, so load it
        return loadFile(file, currentTiles);

      }
      else {
        // file not valid, so start again...

        invalidPath();
        return loader(currentTiles);

      }

    } catch (InvalidPathException e) {
      // invalid path, so start again

      invalidPath();
      return loader(currentTiles);

    } catch (Exception e) {
      // TODO: handle IOException thrown from Load class?
      // or just return?
      return currentTiles;
    }

  }



  /**
   * Calls <b>loadFile()</b> on the <i>default.mze</i> file If an IOException is caught then
   * the function will call <b>loader()</b>
   * <p>
   * This function is static
   * 
   * @param currentTiles An object of the AllTiles class
   * @return AllTiles object
   */
  public static AllTiles loadDefault(AllTiles currentTiles) {
    String file = "input/default.mze";

    try {
      return loadFile(file, currentTiles);

    } catch (IOException e) {
      // invalid path

      return loader(currentTiles);
    }
  }



  /**
   * Checks file modification status. If modified, then prompt the user to save.
   * <p>
   * If the user wants to save, <b>saver()</b> will be called. Else if the user cancels
   * the currentTiles will be returned. Otherwise loadTiles will be called.
   * <p>
   * This function is static
   *
   * @param file         string that contains file path
   * @param currentTiles An object of the AllTiles class
   * @return AllTiles object
   * @throws IOException
   */
  private static AllTiles loadFile(String file, AllTiles currentTiles)
      throws IOException {

    // handle case where current maze has been modified
    if (currentTiles != null && currentTiles.isModified()) {

      Object[] options = { "Yes", "No", "Cancel" };

      // 0 - Yes
      // 1 - No
      // 2 - Cancel
      int      choice  = JOptionPane.showOptionDialog(null,
          "Would you like to save your current attempt?", "File Options",
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          options, options[0]);

      if (choice == 0) {
        Save.saver(currentTiles);
      }
      else if (choice == 2) {
        // cancel
        return currentTiles;
      }
      // 0 - Yes will continue here after saving.
      // 1 - No will continue
    }

    return loadTiles(file, currentTiles);
  }



  /**
   * Loads tiles from file
   * <p>
   * This function is static
   * 
   * @param file         string with file path
   * @param currentTiles An object of the AllTiles class
   * @return AllTiles object
   * @throws IOException
   */
  private static AllTiles loadTiles(String file, AllTiles allTiles)
      throws IOException {
    
    Path   path  = Paths.get(file);

    byte[] bytes = Files.readAllBytes(path);

    if (allTiles == null) {
      
      allTiles = Mze.bytesToEmptyTiles(bytes, allTiles);
      
      return allTiles;
    } else  {
      allTiles.getTimer().stop();
    }
    
    allTiles = Mze.bytesToFullTiles(bytes, allTiles);

    return allTiles;
  }



  /**
   * Displays an error message indicating incorrect file path
   * <p>
   * This function is static
   *
   * @return void
   */
  private static void invalidPath() {
    // error pop-up if path is invalid

    JOptionPane.showMessageDialog(null, "Incorrect File Path", "File IO Error",
        JOptionPane.ERROR_MESSAGE);
  }



  /**
   * Displays an error message indicating the file is invalid
   * <p>
   * This function is static
   *
   * @return void
   */
  private static void invalidFile() {
    // error pop-up if file is invalid

    JOptionPane.showMessageDialog(null, "Invalid File", "File IO Error",
        JOptionPane.ERROR_MESSAGE);
  }

}
