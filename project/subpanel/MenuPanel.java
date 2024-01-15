/**
 * MenuPanel.java
 * @author Cody, Faith, Patrick
 * Date: Mar 2, 2022
 * Updated: May 13, 2022
 *
 * MenuPanel initializes the top menu and the file, reset, and quit 
 * buttons.
 */

package subpanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import appearance.Palette;

public class MenuPanel extends JPanel {

  JButton            buttons[];
  GridBagLayout      gbl;
  GridBagConstraints gbc;



  public MenuPanel(JButton[] buttons) {
    super();

    this.setBackground(Palette.menu);
    this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    this.buttons = buttons;

    this.gbl     = new GridBagLayout();
    this.gbc     = new GridBagConstraints();

    this.setLayout(gbl);

    for (int col = 0; col < this.buttons.length; col++) {
      this.gbc.gridx = col;
      this.gbc.gridy = 0;
      this.add(this.buttons[col], this.gbc);
    }
  }

}
