
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
				System.out.println( "accum[j]: " + accum);
			}

			ortho[i] = current.subtract(accum);
			System.out.println( "ortho[i]: " + ortho[i]);
		}

		return ortho;
	}

	public Vector[] GramShmidt(Vector[] vectors)
	{
		Vector[] ortho = orthogonalizeSet(vectors);
		Vector[] orthoNormal = normalizeSet(ortho);

		return orthoNormal;
	}


	/***********************************************
						Getters
	************************************************/
	public Vector[] basis(){return basis;}

	/************************************************
						Setters
	************************************************/
	public boolean setBasis()
	{
		return false;
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