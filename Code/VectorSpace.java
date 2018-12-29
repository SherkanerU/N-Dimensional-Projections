
public class VectorSpace
{
	private Vector[] basis;

	//decide how far away from 0 we want the determinant of a 
	//system to be!
	private final double DET_TOL = 0;

	/******************************************
					Constructors
	*******************************************/
	public VectorSpace(Vector[] b)
	{
		if (!checkIndependance (b))
		{
			throw new IllegalArgumentException("cannot have dependant vectors as basis");
		}
		else
		{
			System.out.println("accepted system");
		}

		basis = b;
	}

	/*************************************************
						Mutators
	*************************************************/
	public void normalizeBasis()
	{
		basis = normalizeSet(this.basis());
	}
	public void orthogonalizeBasis()
	{
		basis = orthogonalizeSet(this.basis());
	}
	public void GramShmidtBasis()
	{
		basis = GramShmidt(this.basis());
	}


	/***********************************************
					   Operations
	***********************************************/
	public boolean checkIndependance(Vector[] vectors)
	{
		validateDimensions(vectors);

		Matrix A = new Matrix(vectors, "columns");
		Matrix B = new Matrix(vectors, "rows");

		Matrix BA = B.multiply(A);

		return Math.abs(BA.determinant()) >= DET_TOL;
	}

	public Vector[] normalizeSet(Vector[] vectors)
	{
		validateDimensions(vectors);

		Vector[] norms = new Vector[vectors.length];

		for (int i = 0; i < vectors.length; i++)
		{
			norms[i] = vectors[i].normalize();
		}

		return norms;
	}

	public Vector[] orthogonalizeSet(Vector[] vectors)
	{
		validateIndependance(vectors);

		Vector[] ortho = new Vector[vectors.length];

		for (int i = 0; i < vectors.length; i++)
		{
			Vector current = vectors[i];

			//initialize an accumulation vector to 0
			Vector accum = new Vector((double)0, vectors[0].getDimension());

			for (int j = 0; j < i; j++)
			{
				accum = accum.add(ortho[j].projection(current));
			}

			ortho[i] = current.subtract(accum);
		}

		return ortho;
	}

	public Vector[] GramShmidt(Vector[] vectors)
	{
		Vector[] ortho = orthogonalizeSet(vectors);
		Vector[] orthoNormal = normalizeSet(ortho);

		return orthoNormal;
	}

	public Matrix projectionMatrix(Vector[] vectors)
	{
		validateIndependance(vectors);

		double[][] matrix = new double[vectors.length][vectors.length];

		for (int i = 0; i < vectors.length; i++)
		{
			Vector current = vectors[i];

			for (int j = 0; j < vectors.length; j++)
			{
				matrix[i][j] = current.innerProduct(vectors[j]); 
			}
		}

		return new Matrix(matrix);
	}

	//calculates the coordinates of a point's projection relative 
	//to a the current basis vectors!
	public double[] projCords(Vector a)
	{
		checkPoint(a);

		//generate the appropriate column vector
		double[] column = new double[this.dimension()];
		for(int i = 0; i < this.dimension(); i++)
		{
			column[i] = basis[i].innerProduct(a);
		}
		Matrix columnMat = new Matrix(column, "column");

		Matrix invProj = this.projectionMatrix();
		invProj = invProj.inverse();

		Matrix result = invProj.multiply(columnMat);

		return result.column(0);
	}

	//gives the vector given by the array of coordinates
	public Vector vectorCords(double[] coordinates)
	{
		if (coordinates.length != this.dimension())
		{
			throw new IllegalArgumentException("passed illegal coordinate array");
		}

		Vector result = new Vector ((double) 0, dimensionOfAmbientSpace());

		for (int i = 0; i < coordinates.length; i++)
		{
			result = result.add((basis[i].scalarMul(coordinates[i])));
		}

		return result;
	}

	public boolean isMemberofSpace(Vector a)
	{
		//if it is a member of the space then the projection
		//and the original vector are the same.
		Vector projection = vectorCords(projCords(a));

		return projection.equals(a);
	}

	//gives a vector which is the dimension of the subspace passed
	//and is the projection of the vector passed!
	//will perforrm gram shmidt first!
	public Vector getRelativeCoordinates(Vector[] b, Vector p)
	{
		validateIndependance(b);

		Vector[] bas = GramShmidt(b);
		VectorSpace space = new VectorSpace(bas);

		double[] ret = projCords(p);

		return new Vector(ret);
	}

	/***********************************************
						Getters
	************************************************/
	public Vector[] basis(){return basis;}

	public Matrix projectionMatrix()
	{
		return projectionMatrix(this.basis());
	}

	//the dimension of the space
	public int dimensionOfAmbientSpace(){return basis[0].getDimension();}

	//dimension of the subspace defined by basis
	public int dimension(){return basis.length;}

	/************************************************
						Setters
	************************************************/
	public void setBasis(Vector[] vectors)
	{
		validateIndependance(vectors);

		basis = vectors;
	}

	/************************************************
				Validation and Helpers
	************************************************/
	private void validateDimensions(Vector[] a)
	{
		int len = a[0].getDimension();

		for (int i = 0; i < a.length; i++)
		{
			if (len != a[i].getDimension())
			{
				throw new IllegalArgumentException("different vector dimensions");
			}
		}
	}

	private void validateIndependance(Vector[] vectors)
	{
		if (!checkIndependance(vectors))
		{
			throw new IllegalArgumentException("passed dependant vector system");
		}
	}

	private void checkPoint(Point p)
	{
		if (p.getDimension() != this.dimensionOfAmbientSpace())
		{
			throw new IllegalArgumentException("point not of same dimension as ambient space");
		}
	}

	/****************************************************
					  String and Misc
	****************************************************/
	public String toString()
	{
		String ret = "Vectors in Basis: \n";
		for (int i = 0; i < basis.length; i++)
		{
			ret += "" + i + ": " + basis[i].toString() +"\n";
		}

		ret += "Dot Products: \n";
		for (int i = 0; i < basis.length; i++)
		{
			ret += "vector " + i + " has dot prods: \n";
			for (int j = 0; j < basis.length; j++ )
			{
				ret += "" + i + " with " + j +" : " + basis[i].innerProduct(basis[j]) + "\n"; 
			}
		}

		return ret;
	}		
}