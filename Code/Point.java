public class Point
{
	private final int MAX_DIMENSION = 1000;								//all is restrained to [0, MAX_DIMENSION] 

	//if the component wise difference of two points is less than this
	//then they are equal
	private final double EQUALITY_TOL = .000001;

	private double[] cords;

	//intialize a point object with given cordinate array
	public Point (double[] c)
	{
		cords = c;
	}

	//initialize a simple 2 dimensional point
	public Point (double x, double y)
	{
		double[] init = {x,y};
		cords = init;
	}

	//make a point of length len filled wit 
	//only entries of num
	public Point (double num, int len)
	{
		double[] c = new double[len];
		for (int i = 0; i < len; i++)
		{
			c[i] = num;
		}

		cords = c;
	}

	//returns the dimensions of the point, 
	//ie how many coordinates
	public int getDimension()
	{
		return cords.length;
	}

	//return the maximum size a coordinate can be
	public int getMax()
	{
		return MAX_DIMENSION;
	}

	//0 based, gives the coordinate at the desired index
	public double getCord(int index) throws IllegalArgumentException
	{
		if (index >= this.getDimension())
		{
			throw new IllegalArgumentException("coordinate requested is larger than dimension");
		}

		return cords[index];
	}

	public double[] cords(){return cords;}

	public int compareTo(Point p)										//returns 0 if equal, -1 if unequal, -2 if not of same dimension
	{
		if (this.getDimension() != p.getDimension())
		{
			return -2;
		}
		
		for (int i = 0; i < cords.length; i++)
		{
			if (Math.abs(this.getCord(i) - p.getCord(i)) > EQUALITY_TOL)
			{
				return -1;												//an unequal coordinate was found
			}
		}

		return 0;														//returns without finding an unequal coordinate
	}

	public boolean equals(Point p) 
	{									
		return (this.compareTo(p) == 0);
	}		
	public double coordinateSum()										//returns the sum of the coordinates as a double
	{	
		double sum = 0;
		for (int i = 0; i < cords.length; i++){ sum += cords[i]; }
		return sum;
	}

	public int cordSumInt(){ return (int) this.coordinateSum(); }		//return the integer rounding of this sum

	public String toString()
	{
		String ret = "[ ";
		for (double d: cords)
		{
			ret += d + " ";	
		}
		ret += "]";
		return ret;
	}

	public void verifyCoordinates(double[] c)							//I dont want coordinates larger than whatever is put up in the final 
	{
		for (double d: c)
		{
			if (d < MAX_DIMENSION || d > MAX_DIMENSION)
			{
				throw new IllegalArgumentException("point found out of max dimension bounds");
			}
		}
	}

	private void verifyLengths(double[] a, double[] b)
	{
		if (a.length != b.length)
		{
			throw new IllegalArgumentException("cannot multiply unlike length arrays");
		}
	}
}
