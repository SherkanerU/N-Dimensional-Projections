

public class Vector extends Point
{
	public Vector(double[] c)
	{
		super(c);
	}

	public double innerProduct(Vector other)
	{
		return arrayInnerProd(this.cords(), other.cords());
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