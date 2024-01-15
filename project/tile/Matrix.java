/**
 * Matrix.java
 * @author Cody, Faith, Patrick
 * Date: April 11, 2022
 * Updated: May 13, 2022
 *
 * Matrix acts as a library for simple matrices, or an array of vectors, 
 * which are used for tile rotation.
 */

package tile;

public class Matrix {

  private Vector[] columns;
  public int       rowCount, columnCount;



  public Matrix(float[][] columns) {

    this.rowCount    = columns[0].length;
    this.columnCount = columns.length;
    this.columns     = new Vector[this.columnCount];

    for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
      this.columns[columnIndex] = new Vector(columns[columnIndex]);
    }

  }



  public Vector product(Vector vector) {

    if (this.columnCount != vector.getSize()) {
      return null;
    }

    Vector result = new Vector(this.rowCount);
    result.fill(0);

    for (int i = 0; i < columnCount; i++) {
      float scalar = vector.getElement(i);
      result = result.add(columns[i].scale(scalar));
    }

    return result;
  }

}
