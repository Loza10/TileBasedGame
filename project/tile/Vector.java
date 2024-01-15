/**
 * Vector.java
 * @author Cody, Faith, Patrick, Zakk
 * Date: April 11, 2022
 * Updated: May 13, 2022
 *
 * Vector acts as a library for vectors, which are used for tile rotation. 
 * A vector may be created, and there are getters and setters for one's
 * elements and size.
 */

package tile;

public class Vector {

  private float[] elements;
  private int     size = 0;



  public Vector(float[] elements) {
    this.setElements(elements);
  }



  public Vector(int size) {
    this.setElements(new float[size]);
  }



  public float[] getElements() {
    return this.elements;
  }



  public float getElement(int index) {
    return this.elements[index];
  }



  public int getSize() {
    return this.size;
  }



  private void setElement(int index, float value) {

    if (index >= this.getSize()) {
      return;
    }
    this.elements[index] = value;
  }



  public void setElements(float[] elements) {
    this.elements = elements;
    this.setSize(elements.length);
  }



  private void setSize(int size) {
    this.size = size;
  }



  public void fill(int value) {
    for (int i = 0; i < this.getSize(); i++) {
      this.setElement(i, value);
    }
  }



  public Vector add(Vector vector) {

    if (this.getSize() != elements.length) {
      return null;
    }
    Vector result = new Vector(this.getSize());
    for (int i = 0; i < this.getSize(); i++) {
      result.setElement(i, this.elements[i] + vector.getElement(i));
    }
    return result;
  }



  public Vector scale(float scalar) {
    Vector result = new Vector(this.getSize());
    for (int i = 0; i < this.getSize(); i++) {
      result.setElement(i, this.getElement(i) * scalar);
    }
    return result;
  }
}
