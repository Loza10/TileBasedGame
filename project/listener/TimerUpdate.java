/**
 * TimerUpdate.java
 * @author Cody, Faith, Zakk
 * Date: May 13, 2022
 * Updated: May 13, 2022
 *
 * TimerUpdate helps GameTimer keep the timer updated every second.
 */

package listener;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;

public class TimerUpdate extends ActionAdapter {
  
  public long time;
  JLabel label;

  
  public TimerUpdate(long time, JLabel label) {
    this.time = time;
    this.label = label;
  }
  
  public void actionPerformed(ActionEvent e) {
    time += 1000;
    this.label.setText(
        String.format("%02d", (time / 3600000)) + ":"
            + String.format("%02d", (time / 60000) % 60)
            + ":"
            + String.format("%02d", (time / 1000) % 60));
  }
  

}