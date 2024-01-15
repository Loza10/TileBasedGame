/**
 * GameWindow.java
 * @author Kim Buckner, Cody, Faith, Patrick, Zakk
 * Date: Jan 13, 2019
 * Updated: May 13, 2022
 *
 * GameWindow draws the game window using BoardPanel, MenuPanel, and 
 * OuterPanel. It initializes the setup of each upon a new game.
 */

package board;

import appearance.Palette;
import fileio.Mze;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.MenuActionListener;
import listener.SlotMouseListener;
import tile.AllTiles;
import subpanel.BoardPanel;
import subpanel.MenuPanel;
import subpanel.OuterPanel;
import fileio.Load;

public class GameWindow extends JFrame {

  /**
   * because it is a serializable object, need this or javac complains <b>a
   * lot</b>, the ID can be any integer.
   */
  public static final long   SERIAL_VERSION_UID = 1;

  /**
   * Main JPanel
   */
  private JPanel             panel;
  private GridBagLayout      gbl;
  private GridBagConstraints gbcMain;
  private SlotMouseListener  slotMouseListener;
  private AllTiles           allTiles;

  /**
   * Sub-Panels
   */
  private BoardPanel         board;
  private MenuPanel          menu;
  private OuterPanel         outerPanelLeft;
  private OuterPanel         outerPanelRight;

  /**
   * Filler Sub-Panels
   */
  private JPanel             fillMenu;
  private JPanel             fillBoardLeft;
  private JPanel             fillBoardRight;
//  public static JPanel       timePanel;
//  public static JLabel       timeCount;

  /**
   * FileIO
   */
  public static Mze          mze;

  /**
   * The control buttons that will part of the Game Window left, right, middle.
   */
  private JButton            buttonFile         = new JButton("File");
  private JButton            buttonReset        = new JButton("Reset");
  private JButton            buttonQuit         = new JButton("Quit");
  private JButton            buttons[]          = { buttonFile, buttonReset,
      buttonQuit };

  /**
   * Size control
   */
  private int                tileSize           = 100;

  /**
   * Game timer to keep track of time
   */
//  public static GameTimer    timer;



  /**
   * Constructor sets the window name using super(), changes the layout, which
   * you really need to read up on, and maybe you can see why I chose this one.
   *
   * @param s string which is the name of the window.
   */
  public GameWindow(String s) {
    super(s);

    gbl   = new GridBagLayout();
    panel = new JPanel();
    panel.setLayout(gbl);
    panel.setBackground(Palette.BLUE);

    this.add(panel);

  }



  /**
   * Establishes the initial board.
   */
  public void setUp() {

    gbcMain        = new GridBagConstraints();
    gbcMain.anchor = GridBagConstraints.CENTER;

    allTiles       = Load.loadDefault(allTiles);


    if (allTiles.isValid()) {
      this.setUpTiles();
    }

    this.setUpMenu();

    this.setUpBoardPanel();
    this.setUpOuterPanels();

    allTiles.setIds();

    this.setUpFillerPanels();
    this.setUpButtons();

    return;
  }



  public void setUpTiles() {

    slotMouseListener = new SlotMouseListener(allTiles);
    allTiles.addMouseListener(slotMouseListener);

    if (!allTiles.isValid()) {

      // TODO:
      // handle this error/situation

    } else if (!allTiles.isModified()) {
//      System.out.println("randomize");
      allTiles.randomize();
    }

  }



  /**
   * Establishes the top menu JPanel.
   */
  public void setUpMenu() {

    menu      = new MenuPanel(buttons);

//    timePanel = new JPanel();

    menu.setBackground(Palette.GREEN);
    menu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    gbcMain.gridx = 2;
    gbcMain.gridy = 0;
    panel.add(menu, gbcMain);

    // Sets up panel for Timer.
//    timePanel = new JPanel();
//    timeCount = new JLabel("Not started yet!");
//    timeCount.setFont(new Font("Dialog",Font.PLAIN,25));

//    timePanel.setBackground(Palette.GREEN);
//    timePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    gbcMain.gridx = 2;
    gbcMain.gridy = 1;
//    timePanel.add(timeCount);
    panel.add(allTiles.getTimer().getPanel(), gbcMain);
  }



  public void setUpButtons() {
    buttonQuit.setActionCommand("exit");
    buttonQuit.addActionListener(
        new MenuActionListener(outerPanelLeft, outerPanelRight, allTiles));

    buttonReset.setActionCommand("reset");
    buttonReset.addActionListener(
        new MenuActionListener(outerPanelLeft, outerPanelRight, allTiles));

    buttonFile.setActionCommand("file");
    buttonFile.addActionListener(
        new MenuActionListener(outerPanelLeft, outerPanelRight, allTiles));
  }



  /**
   * Establishes the OuterPanel JPanels and for tiles.
   */
  public void setUpOuterPanels() {

    outerPanelLeft = new OuterPanel(allTiles, 0);
    outerPanelLeft.addMouseListener(slotMouseListener);
    gbcMain.gridx      = 0;
    gbcMain.gridy      = 0;
    gbcMain.gridheight = 3;
    panel.add(outerPanelLeft, gbcMain);

    outerPanelRight = new OuterPanel(allTiles, 1);
    outerPanelRight.addMouseListener(slotMouseListener);
    gbcMain.gridx      = 4;
    gbcMain.gridy      = 0;
    gbcMain.gridheight = 3;
    panel.add(outerPanelRight, gbcMain);
  }



  /**
   * Establishes the game board JPanel.
   */
  private void setUpBoardPanel() {
    board = new BoardPanel(tileSize, allTiles);
    board.addMouseListener(slotMouseListener);

    gbcMain.gridx = 2;
    gbcMain.gridy = 2;
    panel.add(board, gbcMain);
  }



  /**
   * Establishes all filler panels for dynamic resizing of window.
   */
  private void setUpFillerPanels() {

    fillMenu       = new JPanel();
    fillBoardLeft  = new JPanel();
    fillBoardRight = new JPanel();

    fillMenu.addMouseListener(slotMouseListener);
    fillBoardLeft.addMouseListener(slotMouseListener);
    fillBoardRight.addMouseListener(slotMouseListener);

    // filler below menu
    fillMenu.setBackground(Palette.background);
    gbcMain.gridx   = 2;
    gbcMain.gridy   = 1;
    gbcMain.weighty = 1.0;
    panel.add(fillMenu, gbcMain);

    // filler area JPanel between left tiles and board
    fillBoardLeft.setBackground(Palette.background);
    gbcMain.gridx   = 1;
    gbcMain.gridy   = 2;
    gbcMain.weightx = 1.0;
    panel.add(fillBoardLeft, gbcMain);

    // filler area JPanel between board and right tiles
    fillBoardRight.setBackground(Palette.background);
    gbcMain.gridx   = 3;
    gbcMain.gridy   = 2;
    gbcMain.weightx = 1.0;
    panel.add(fillBoardRight, gbcMain);
  }

}
