/**
 * ActionAdapter.java
 * @author Cody, Faith, Patrick
 * Date: Mar 2, 2022
 * Updated: May 2, 2022
 *
 * ActionAdapter is an abstract class that implements the ActionListener
 * interface and allows MenuActionListener to handle JButton actions. 
 */

package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ActionAdapter implements ActionListener {

  protected ActionAdapter() {
  }



  public void actionPerformed (ActionEvent e) {
  }

}
