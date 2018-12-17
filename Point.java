public class Point
{
	double[] cords;

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

	//returns the dimensions of the point, 
	//ie how many coordinates
	public int getDimension()
	{
		return cords.length;
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
}
