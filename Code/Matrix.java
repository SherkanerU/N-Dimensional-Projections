
public class Matrix 
{
	private double[][] matrix;

	/*********************************************
					Constructors
	**********************************************/

	//initialize a matrix from a two dimensional array
	public Matrix(double[][] rows)
	{
		validateDimensions(rows);
		matrix = rows;
	}

	//turns an array into either a column or row vector dependant on the type variable
	public Matrix (double[] array, String type )
	{
		if (type.equals("row"))
		{
			double [][] set = new double[1][array.length];
			set[0] = array;

			matrix = set;
		}
		else if (type.equals("column"))
		{
			double [][] set = new double[array.length][1];

			for (int i = 0; i < array.length; i++)
			{
				set[i][0] = array[i];
			}

			matrix = set;
		}
		else
		{
			throw new IllegalArgumentException("type not passed correctly: either row or column accepted");
		}
	}

	//initializes this list of vectors/points as a matrix, either by 
	//column or by row depending on type
	public Matrix (Point[] vectors, String type)
	{
		if (type.equals("rows"))
		{
			double[][] rows = new double[vectors.length][vectors[0].getDimension()];
			for (int i = 0; i < vectors.length; i++)
			{
				rows[i] = vectors[i].cords();
			}

			validateDimensions(rows);

			matrix = rows;
		}
		else if (type.equals("columns"))
		{
			double[][] rows = new double[vectors.length][vectors[0].getDimension()];
			for (int i = 0; i < vectors.length; i++)
			{
				rows[i] = vectors[i].cords();
			}

			validateDimensions(rows);

			Matrix trans = new Matrix(rows);
			trans = trans.transpose();
			matrix = trans.matrix();
		}
		else
		{
			throw new IllegalArgumentException("specifiy either rows or columns");
		}
	}

	/***********************************************
					  Arithmetic
	***********************************************/
	//does addition row wise!
	//uses one of the array addition helpers
	public Matrix add (Matrix other)
	{
		validateDimensionsAddition(this, other);

		double[][] sum = new double[this.rows()][this.columns()];

		for (int i = 0 ; i < this.rows(); i++)
		{
			sum[i] = addArrays(this.matrix()[i], other.matrix()[i]); 
		}

		return new Matrix(sum);
	}

	//multiplies a matrix by a constant
	//uses the constant array mul helper
	public Matrix multiply (double num)
	{
		double[][] mul = new double[this.rows()][this.columns()];

		for (int i = 0; i < this.rows(); i++)
		{
			mul[i] = mulConstant(this.matrix()[i], num);
		}

		return new Matrix(mul);
	}

	//multiplies two matricies together
	//uses inner product helper by skimming rows of this and multiplying 
	//them by columns of other to calculate the individual index values
	public Matrix multiply (Matrix other)
	{
		validateDimensionsMul(this, other);

		double[][] product = new double[this.rows()][other.columns()];

		for (int i = 0; i < other.columns(); i++)
		{
			double[] currentColumn = other.column(i);

			for (int j = 0; j < this.rows(); j++)
			{
				double[] currentRow = this.row(j);

				product[j][i] = innerProduct(currentColumn,currentRow);
			}
		}

		return new Matrix(product);
	}

	//uses the recursive helper immedietly!
	public double determinant()
	{
		return determinant(this);
	}

	//swaps rows with columns
	//uses get column helper
	public Matrix transpose()
	{
		double[][] trans = new double[this.columns()][this.rows()];

		for (int i = 0; i < trans.length; i++)
		{
			trans[i] = this.column(i);
		}

		return new Matrix(trans);
	}

	//calculates the matrix of minors by iterating over each value 
	//and taking the determinant of the matrix with the current
	//row and column taken away
	public Matrix minors()
	{
		double[][] minors = new double[this.rows()][this.columns()];

		for (int i = 0; i < this.rows(); i++)
		{
			for(int j = 0; j < this.columns(); j++)
			{
				minors[i][j] = determinant(minor(this, i, j));
			}
		}

		return new Matrix(minors);
	}

	//calcs adjoint matrix by generating the cofactor matrix from the minors matrix
	//then transposes and returns
	public Matrix adjoint()
	{
		Matrix minors = this.minors();
		minors = minors.cofactor();
		minors = minors.transpose();
		return minors;
	}

	//uses adjoint and determinant to invert the matrix
	public Matrix inverse()
	{
		double det = this.determinant();
		if (det == 0)
		{
			throw new IllegalArgumentException("determinant is 0, no inverse");
		}

		return (this.adjoint()).multiply(1/det);
	}

	/**********************************************
					Recursive Helpers
	**********************************************/

	//works using expansion across the top row!
	//is recursive, has base case of 2 and 1
	//recursive case calculates the matrix removed from 
	//this row and column and takes its determinant
	private double determinant(Matrix m)
	{
		validateSqure(m);
		if (m.rows() == 1)
		{
			return m.matrix()[0][0];
		}
		else if (m.rows() == 2)
		{
			double[][] mat = m.matrix();
			return mat[0][0]*mat[1][1] - mat[0][1]*mat[1][0];
		}
		else
		{	
			//top row!
			double[] expandRow = m.matrix()[0];

			double det = 0;

			for (int i = 0; i < m.columns(); i++)
			{
				det += expandRow[i] * Math.pow(-1,i) * determinant(minor(m,0,i));
			}

			return det;
		}
	}


	/***********************************************
				   Arithmetic Helpers
	************************************************/
	private double[] addArrays(double[] a, double[] b)
	{
		validateDimensions(a,b);
		double[] sum = new double[a.length];

		for (int i = 0; i < a.length; i++)
		{
			sum[i] = a[i] + b[i];
		}

		return sum;
	}
	private double[] mulConstant(double[] a, double b)
	{
		double[] sum = new double[a.length];
		for (int i = 0; i < a.length; i ++)
		{
			sum[i] = a[i]*b;
		}
		return sum;
	}
	private double innerProduct(double[] a, double[] b)
	{
		validateDimensions(a,b);

		double sum = 0;

		for (int i  = 0; i < a.length; i++)
		{
			sum += a[i]*b[i];
		}

		return sum;
	}
	//calculates the matrix resultant from removing row j and column i
	public Matrix minor(Matrix m, int j, int i)
	{
		if (m.rows() <= 1 || this.columns() <= 1)
		{
			throw new IllegalArgumentException("cannot remove from a matrix with either rows = 1 or columns = 1");
		}

		double[][] result = new double[m.rows() - 1][m.columns() -1];
 
 		int rowCount = 0;
 		int columnCount = 0;
		for (int p = 0; p < m.rows(); p++)
		{
			if (p != j)
			{
				columnCount = 0;
				for (int q = 0; q < m.columns(); q++)
				{
					if (q != i)
					{
						result[rowCount][columnCount] = m.matrix()[p][q];
						columnCount++;
					}
				}
				rowCount++;
			}
		}

		return new Matrix(result);
	}

 	//overlays the proper negative signs
 	private Matrix cofactor(Matrix m)
 	{
 		double[][] factor = new double[m.rows()][m.columns()];

 		for (int i = 0; i < m.rows(); i++)
 		{
 			for (int j = 0; j < m.columns(); j++)
 			{
 				factor[i][j] = Math.pow(-1, i+j) * m.matrix()[i][j];
 			}
 		}

 		return new Matrix(factor);
 	}

 	public Matrix cofactor()
 	{
 		return cofactor(this);
 	}

	/***********************************************
						Getters
	************************************************/
	public int rows(){return matrix.length;}
	public int columns(){return matrix[0].length;}
	public double[][] matrix(){return matrix;}
	public double[] row(int i)
	{
		if (i < 0 || i >= this.rows())
		{
			throw new IllegalArgumentException("invalid row number!!");
		}
		return matrix[i];
	}
	public double[] column(int i)
	{
		double[] column = new double[this.rows()];

		for (int j = 0; j < this.rows(); j++)
		{
			column[j] = matrix[j][i];
		}

		return column;
	}

	/***********************************************
				Validation and Helpers
	************************************************/
	private void validateSqure(Matrix m)
	{
		if (m.rows() != m.columns())
		{
			throw new IllegalArgumentException("non square matrix");
		}
	}

	//ensure that the matrix has uniform columns
	private void validateDimensions(double[][] m)
	{
		int len = m[0].length;
		for (int i = 0; i < m.length; i++)
		{
			if(m[i].length != len)
			{
				throw new IllegalArgumentException("unequal column counts");
			}
		}
	}

	private void validateDimensions(double[] a, double[] b)
	{
		if (a.length != b.length)
		{
			throw new IllegalArgumentException("unequal arrays lengths");
		}
	}

	private void validateDimensionsAddition(Matrix a, Matrix b)
	{
		if (a.columns() != b.columns() || a.rows() != b.rows())
		{
			throw new IllegalArgumentException("invalid dimensions for addition");
		}
	}

	private void validateDimensionsMul(Matrix a, Matrix b)
	{
		if (a.columns() != b.rows() || a.rows() != b.columns())
		{
			throw new IllegalArgumentException("invalid dimensions for multiplication");
		}
	}


	/*********************************************
						Misc
	**********************************************/

	//returns rows by string
	private String rowString(int i)
	{
		String r = "";
		double[] currentRow = matrix[i];

		for (int j = 0; j < currentRow.length; j++)
		{
			r += currentRow[j] + "     ";
		}
		return r;
	}
	//return the matrix as a string
	public String toString()
	{
		String m = "";
		for (int i = 0; i < matrix.length; i++)
		{
			m += this.rowString(i) + "\n";
		}
		return m;
	}
}