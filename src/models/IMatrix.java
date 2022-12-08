package models;

/**
 *
 * @author Familia
 */
public interface IMatrix {

	void producto(MatrixModel other);

	void escalar(double escala);									

	void escalarAt(int variableIndex, double escala);				

	void escalar(double[] escalas);						  

	void rotacion(double angulo);							

	void traslacion(double[] ts);							  

	void traslacionAt(int variableIndex, double t);

	boolean addPoint(double[] point);

	boolean setValueAt(int row, int column, double value);

	double get(int row, int column);
}
