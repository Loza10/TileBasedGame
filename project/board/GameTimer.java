/**
 * GameTimer.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: May 3, 2022
 * Updated: May 13, 2022
 *
 * GameTimer creates and displays a timer above the options menu that shows
 * the total time the user has taken to solve the maze so far. It also resets
 * the timer to its original value upon a reset.
 */

package board;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import appearance.Palette;
import listener.TimerUpdate;

public class GameTimer implements ActionListener {
  
  long  totalTime = 0;
  
  JPanel panel;
  public JLabel label;
    
  Timer timer;
  
  TimerUpdate update;


  // Constructor for default time = 0
  public GameTimer() {
    
    this.label = new JLabel("Not started yet!");
    this.label.setFont(new Font("Dialog", Font.PLAIN, 25));
    this.label.setText(String.format("%02d", (totalTime / 3600000))
        + ":" + String.format("%02d", (totalTime / 60000) % 60) + ":"
        + String.format("%02d", (totalTime / 1000) % 60));
    
    this.panel = new JPanel();
    this.panel.setBackground(Palette.GREEN);
    this.panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    this.panel.add(label);
    
//    update = new TimerUpdate(totalTime, label);
    timer = new Timer(1000, this);
    
//    timer = new Timer(1000, new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        totalTime += 1000;
//        this.label.setText(
//            String.format("%02d", (totalTime / 3600000)) + ":"
//                + String.format("%02d", (totalTime / 60000) % 60)
//                + ":"
//                + String.format("%02d", (totalTime / 1000) % 60));
//      }
//    });

    // start();
  }



  @Override
  public void actionPerformed(ActionEvent e) {
    totalTime += 1000;
    this.label.setText(
        String.format("%02d", (totalTime / 3600000)) + ":"
            + String.format("%02d", (totalTime / 60000) % 60)
            + ":"
            + String.format("%02d", (totalTime / 1000) % 60));
  }



  // Starts the timer
  public void start() {
    timer.start();
    this.label.setText(String.format("%02d", (totalTime / 3600000))
        + ":" + String.format("%02d", (totalTime / 60000) % 60) + ":"
        + String.format("%02d", (totalTime / 1000) % 60));
  }



  // Stops the timer
  public void stop() {
    timer.stop();
  }



  // Resets the timer
  public void reset() {
    timer.stop();
    totalTime = 0;
    this.label.setText(String.format("%02d", (totalTime / 3600000))
        + ":" + String.format("%02d", (totalTime / 60000) % 60) + ":"
        + String.format("%02d", (totalTime / 1000) % 60));
  }
  
  
  public long getTime() {
    return this.totalTime;
//    return this.totalTime;
  }
  
  public JPanel getPanel() {
    return this.panel;
  }
  
  
  public void setTime(long time) {
    this.totalTime = time;
    this.label.setText(String.format("%02d", (totalTime / 3600000))
        + ":" + String.format("%02d", (totalTime / 60000) % 60) + ":"
        + String.format("%02d", (totalTime / 1000) % 60));
  }
}
