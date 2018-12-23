

public class canvasPoint
{
	private int x;
	private int y;

	public canvasPoint(int[] init)
	{
		if (init.length != 2){throw new IllegalArgumentException("dont give me more than 2 cords");}

		x = init[0];
		y = init[1];
	}

	public canvasPoint(int one, int two)
	{
		x = one;
		y = two;
	}


	public int getX(){return x;}
	public int getY(){return y;}

	public boolean equals(canvasPoint p)
	{
		return x == p.getX() && y == p.getY();
	}
}