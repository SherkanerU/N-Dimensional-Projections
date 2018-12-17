import java.util.*;

public class PointGraph												//this graph will maintain
{
	private final int V; 											//number of vertices
	private int E;													//number of edges
	private final int PRIME;										//prime around 1000 for hashing purposes

	private ArrayList<Point>[] adjacentTo;							//adjacentTo[i] denotes the list of point vertices which share an edge with i
	private Point[] vertices;										//the points which are being represented in this graph

	public PointGraph (Point[] points)
	{
		vertices = new Point[points.length];						//initialize the point array as the size of the one passed
		for (int i = 0; i < points.length; i++) 					//copy the points over
		{
			vertices[i] = points[i];
		}

		this.V = points.length;										//setting up more instance data
		this.E = 0;

		adjacentTo = new ArrayList<Point>[points.length];
	}

	public PointGraph (int V)
	{
		this.V = V;													//setting up some instance data that relies only on V
		this.E = 0;
		vertices = new Point[V];
	}

	public PointGraph (PointGraph g)								//construct a graph using another graph
	{
		this.V = g.V();
	}

	public int V(){return this.V;}									//get num vertices
	public int E(){return this.E;}									//get num edges
	public Point[] vertices(){return vertices;}						//get vertex array

	public Iterable<Point> adjTo(int v){return adjacentTo[v];}		//get the points connected to v

	public void addEdge

	//this method tries to find repeated points within an array by filtering them by the sums of their vertexes
	public void validatePoint(Point[] points)								//checks to ensure points are not repeated 
	{
		//im being paranoid about speed here so this will get a little complicated
		boolean[] flags = new int[997]								//its prime beacause hash!!
		int[] sums = new int[point.length];							//store the sums of the points

		Arrays.fill(flags, Boolean.FALSE);

		for (int i = 0; i < points.length; i++)						//set up the flags array
		{
			int sum = points[i].cordSumInt();

			sums[i] = sum;

			int index = sum % PRIME;
			flags[index] += 1;
		}


	}
}