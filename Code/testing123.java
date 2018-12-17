public class testing123
{

	public static PointGraph PG = new PointGraph(100);

	public static void main(String[] args)
	{
		double[] weee = {1,2,3};

		Point p1 = new Point (1,1);
		Point p2 = new Point (.50000000001, .5);
		Point p3 = new Point (.5000000001, .5);
		Point p4 = new Point (.25, .25);

		Point[] test = {p1,p2, new Point(weee), p3,p4};

		PG.validatePoints(test);
	}
}