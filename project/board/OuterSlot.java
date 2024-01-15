/**
 * OuterSlot.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: Mar 4, 2022
 * Updated: May 13, 2022
 *
 * OuterSlot creates the outer tile holding areas with the proper color
 * and size.
 */

package board;

import java.awt.Dimension;

import appearance.Palette;

public class OuterSlot extends Slot {

  // Default constructor
  public OuterSlot(int size) {
    super(size, Palette.outerTileBackground);

    super.setBorder(null);
    super.setPreferredSize(new Dimension(super.size, super.size));
  }

}
