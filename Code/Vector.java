

public class Vector extends Point
{
	public Vector(double[] c)
	{
		super(c);
	}

	//calculate dot/inner product of two vectors
	public double innerProduct(Vector other)
	{
		return arrayInnerProd(this.cords(), other.cords());
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

	/*********************************************
					   Helpers
	*********************************************/
	private double arrayInnerProd(double[] other)
	{
		double[] a = this.cords();
		double[] b = other.cords();

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