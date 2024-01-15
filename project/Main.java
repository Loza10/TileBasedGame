
/**
 * Main.java
 * @author Kim Buckner, Cody, Faith, Patrick, Zakk
 * Date: Jan 13, 2019
 * Updated: May 13, 2022
 *
 * Main is the starting point of the program that initializes GameWindow and
 * sets the size of the window.
 */

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import appearance.Palette;
import board.GameWindow;

public class Main {

  public static void main(String[] args) {

    // Prevent OS window rescaling
    System.setProperty("sun.java2d.uiScale", "1.0");

    // This is the play area
    GameWindow game = new GameWindow("Group F aMaze");

    // window size of play area
    game.setSize(new Dimension(900, 1000));
    game.setMinimumSize(new Dimension(900, 1000));

    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Use colors that are viewable on ALL DEVICES, Stay away from yellows, do
    // NOT use black or white.
    game.getContentPane().setBackground(Palette.background);
    game.setUp();

    game.setVisible(true);

    // You will HAVE to read some documentation and catch exceptions so get used
    // to it.

    try {
      // The 4 that are installed on Linux here
      // May have to test on Windows boxes to see what is there.
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      // This is the "Java" or CrossPlatform version and the default
      // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      // Linux only
      // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
      // really old style Motif
      // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    } catch (UnsupportedLookAndFeelException e) {
      // handle possible exception
    } catch (ClassNotFoundException e) {
      // handle possible exception
    } catch (InstantiationException e) {
      // handle possible exception
    } catch (IllegalAccessException e) {
      // handle possible exception
    }

  }

};
