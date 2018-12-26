
public class Matrix 
{
	private double[][] matrix;

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

	/***********************************************
					  Arithmetic
	***********************************************/
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

	public Matrix multiply (double num)
	{
		double[][] mul = new double[this.rows()][this.columns()];

		for (int i = 0; i < this.rows(); i++)
		{
			mul[i] = mulConstant(this.matrix()[i], num);
		}

		return new Matrix(mul);
	}

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
	public Matrix removeRowCol(Matrix m, int j, int i)
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