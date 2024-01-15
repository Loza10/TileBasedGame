/**
 * Palette.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 11, 2022
 * Updated: May 13, 2022
 *
 * Palette sets all the color constants for the game and assigns them to 
 * their respective components.
 */

package appearance;

import java.awt.Color;

public class Palette extends Color {

  // Custom colors

  public static final Palette blue                = new Palette(130, 175, 180);
  public static final Palette BLUE                = blue;

  public static final Palette green               = new Palette(145, 200, 150);
  public static final Palette GREEN               = green;

  public static final Palette red                 = new Palette(170, 95, 100);
  public static final Palette RED                 = red;

  public static final Palette purple              = new Palette(80, 85, 160);
  public static final Palette PURPLE              = purple;

  public static final Palette orange              = new Palette(186, 121, 89);
  public static final Palette ORANGE              = orange;

  public static final Palette white               = new Palette(255, 255, 255);
  public static final Palette WHITE               = white;

  public static final Palette black               = new Palette(0, 0, 0);
  public static final Palette BLACK               = black;

  // Color Scheme mapping

  public static final Palette background          = Palette.blue;

  public static final Palette menu                = Palette.GREEN;

  public static final Palette mazeBackground      = Palette.WHITE;
  public static final Palette mazeForeground      = Palette.BLACK;
  public static final Palette mazeSelected        = Palette.ORANGE;

  public static final Palette boardTileBackground = Palette.GREEN;
  public static final Palette boardTileGrid       = Palette.BLACK;

  public static final Palette outerTileBackground = Palette.PURPLE;



  public Palette(int r, int g, int b) {
    super(r, g, b, 255);
  }

}
