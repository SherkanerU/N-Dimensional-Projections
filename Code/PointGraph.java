import java.util.*;

/******************************************************************
this is a class which stores graphs of point objects.  It uses an
adjacency list data structure, as of writing this it only has basic 
storage and verification methods.  It will interface with a graphical
display class in order to plot the points and edges.
*******************************************************************/
public class PointGraph											
{
	private final int V; 											//number of vertices
	private int E;													//number of edges
	private final int PRIME = 997;									//prime around 1000 for hashing purposes

	private ArrayList<Integer>[] adjacentTo;						//adjacentTo[i] denotes the list of point vertices which share an edge with i
	private Point[] vertices;										//the points which are being represented in this graph

	/***************************************************
						Constructors
	***************************************************/

	@SuppressWarnings("unchecked")
	public PointGraph (Point[] points)
	{

		validatePoints(points);

		vertices = new Point[points.length];						//initialize the point array as the size of the one passed
		for (int i = 0; i < points.length; i++) 					//copy the points over
		{
			vertices[i] = points[i];
		}

		this.V = points.length;										//setting up more instance data
		this.E = 0;

		adjacentTo = (ArrayList<Integer>[]) new ArrayList[points.length];
		for (int i = 0; i < V; i ++)
		{
			adjacentTo[i] = new ArrayList<Integer>();
		}
	}

	public PointGraph (int V)
	{
		this.V = V;													//setting up some instance data that relies only on V
		this.E = 0;
		vertices = new Point[V];
	}

	public PointGraph (PointGraph g)								//construct a graph using another graph
	{
		validatePoints(g.vertices());								//ensure a proper graph is passed

		this.V = g.V();												//set up vertex and edge trackers
		this.E = g.E();

		vertices = new Point[g.V()];								//set up point and adjacency list info
		adjacentTo = (ArrayList<Integer>) new ArrayList[g.V()];
		for (int i = 0; i < V; i ++)
		{
			adjacentTo[i] = new ArrayList<Integer>();
		}

		for(int v = 0; v < g.V(), v++)								//copy over points
		{
			double[] copyArry = new double[g.vertex(v).getDimension()]
			for (int i = 0; i < g.vertex(v).getDimension(); i++)
			{
				copyArry[i] = g.vertex(v).getCord(i);
			}
			vertices[i] = new Point(copyArry);
		}

		for (int v = 0; v < V; v++)									//copy over edges!
		{
			ArryaList<Integer> currentAdj = g.adjTo(v);
			for (Integer i: currentAdj)
			{
				adjacentTo[v].add(i);
			}
		}
	}

	/*********************************************
						Setters
	**********************************************/

	public void addEdge(int v, int w)								//adds an edge between vertices[v] and vertices[w]
	{
		validateVertex(v);
		validateVertex(w);

		E++;

		adjacentTo[v].add(w);
		adjacentTo[w].add(v);
	}


	/**********************************************
				       Getters
	**********************************************/

	public int V(){return this.V;}									//get num vertices
	public int E(){return this.E;}									//get num edges
	public Point[] vertices(){return vertices;}						//get vertex array
	public Point vertex(int v) {return vertices[v];}				//the point object being stores as v in vertices[v]
	public int dimension()											//return the dimension of the points in the graph
	{
		validateDimensions(vertices);
		return vertices[0].getDimension();
	}

	public Iterable<Integer> adjTo(int v){return adjacentTo[v];}	//get the points connected to v


	/*************************************************
			 Validation and Checker Methods
	*************************************************/

	public void validateVertex(int v)								//verifies that the specified vertex exists in the graph
	{
		if (v < 0 || v >= V){throw new IllegalArgumentException("the given vertex: " + v + " is not in the graph"); }
	}

	//this method tries to find repeated points within 
	//an array by filtering them by the sums of their vertexes
	public void validatePoints(Point[] points)						//checks to ensure points are not repeated 
	{

		if (points.length == 0){throw new IllegalArgumentException("cannot pass empty point array");}

		int[] sums = new int[points.length];						//store the sums of the points

		for (int i = 0; i < points.length; i++)						//populate sums array
		{
			int sum = points[i].cordSumInt();

			sums[i] = sum;
		}

		//now we need to detect duplicated in the array of sums
		//this is n^2 in the size of the points array
		//might make it faster in the future, who knows
		for (int i = 0; i < sums.length; i ++)
		{
			int targetSum = sums[i];
			for (int j = 1 + i; j < sums.length; j++)
			{
				if (sums[j] == targetSum)
				{
					if (points[i].equals(points[j]))
					{
						throw new IllegalStateException("point " + points[i].toString() + " at: " + i + " and point " + points[j].toString() + "at: " + j + " found to be the same");
					}
				}
			}
		}

		validateDimensions(points);

		System.out.println("the points passed the test! (this should be taken out when finished)");
	}

	private void validateDimensions(Point[] points)					//verifies that the points are of the same dimensions
	{
		int dimension = points[0].getDimension();
		for (int i = 0; i< points.length; i++)
		{
			if (points[i].getDimension() != dimension)
			{
				throw new IllegalStateException("points in passed array found to be of different dimensions");
			}
		}
	}
}