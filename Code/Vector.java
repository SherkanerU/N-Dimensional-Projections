

public class Vector extends Point
{
	//if the dot product is less than this call it 0 
	private final double DOT_TOL = .00000000001;


	/*******************************************
					Constructors
	********************************************/
	public Vector(double[] c)
	{
		super(c);
	}

	public Vector(double num, int len)
	{
		super(num,len);
	}

	/********************************************
				     Mathematics
	*********************************************/

	//calculate dot/inner product of two vectors
	public double innerProduct(Vector other)
	{
		double ret = arrayInnerProd(this.cords(), other.cords());

		if (Math.abs(ret) <= DOT_TOL)
		{
			return 0;
		}
		else
		{
			return ret;
		}
	}

	public Vector scalarMul(double n)
	{
		double[] product;
		product = mulArray(this.cords(), n);

		return new Vector(product);
	}

	//calculates length of the vector
	public double norm()
	{
		return Math.sqrt(this.innerProduct(this));
	}

	//returns unit vector of this vector
	public Vector normalize()
	{
		double[] normArray;
		normArray = mulArray(this.cords(), 1/this.norm());

		return new Vector(normArray);
	}

	//returns the composition or scalar projection of
	//Vector other onto this
	public double comp(Vector other)
	{
		return this.innerProduct(other)/this.norm();
	}

	//returns the projection of Vector other 
	//onto this vector
	public Vector projection(Vector other)
	{
		return (this.normalize()).scalarMul(this.comp(other));
	}

	public Vector add(Vector other)
	{
		double[] sum = addArrays(this.cords(), other.cords());

		return new Vector(sum);
	}

	public Vector subtract(Vector other)
	{
		return this.add(other.scalarMul(-1));
	}

	/*********************************************
					   Helpers
	*********************************************/
	private double arrayInnerProd(double[] a, double[] b)
	{
		validateLengths(a,b);

		double sum = 0;
		for (int i = 0; i < a.length; i++)
		{
			sum += a[i] * b[i];
		}

		return sum;
	}


	private double[] mulArray(double[] a, double n)
	{
		double[] ret = new double[a.length];

		for (int i = 0; i < a.length; i++)
		{
			ret[i] = n*a[i];
		}

		return ret;
	}

	private double[] addArrays(double[] a, double[] b)
	{
		validateLengths(a,b);

		double[] sum = new double[a.length];

		for (int i = 0; i < a.length; i++)
		{
			sum[i] = a[i] + b[i];
		}

		return sum;
	}


	/*********************************************
					  Verification
	**********************************************/
	private void validateLengths(double[] a, double[] b)
	{
		if (a.length != b.length)
		{
			throw new IllegalArgumentException("cannot multiply unlike length arrays");
		}
	}
}