

public class PointsWithSpace
{
	private PointGraph points;
	private VectorSpace space;

	/****************************************
				   Constructor
	*****************************************/

	public PointsWithSpace(PointGraph p, VectorSpace s)
	{
		if (p.vertices()[0].getDimension() != s.basis().getDimension())
		{
			throw new IllegalArgumentException("point collection and vector space not of same ambient dimension");
		}

		points = p;
		space = s;
	}


	/********************************************
					   Mutators
	********************************************/
	//projects the graph onto the given space
	//updates the graph with the new projected
	//points
	public void projectGraph()
	{
		//first we need to turn the points of the graph
		//into vectors for projection
		Vector[] toProject = new Vector[points.vertices().length];

		//turn the points in the graph into vectors because
		//i didnt think out the point graph well...
		for (int i = 0; i < toProject.length; i++)
		{
			toProject[i] = new Vector(points.vertices()[i].cords());
		}

		//Point[] projections = new Point[toProject.length];

		for(int i = 0; i < projections.length; i++)
		{
			double[] projCords = space.projCords(toProject[i])
			Point point = (Point) space.vectorCords(projCords);

			points.setVertex(i, point);
		}
	}

	//reduce the space by 1 dimension of thej 
	//type specified
	public void reduceSpace(String type)
	{
		space = space.reducedDimension(type);
	}

	/*****************************************************
							Getters
	*****************************************************/
	public int dimension()
	{
		int dim = space.dimensionOfAmbientSpace();
		if (dim != points.vertices()[].getDimension())
		{
			throw new IllegalStateException("space and point graph not of same ambient dimension");
		}

		return dim;
	}
	public int spaceDimension()
	{
		return space.dimension();
	}
	public PointGraph points()
	{
		return points;
	}
	public VectorSpace space()
	{
		return space;
	}
}