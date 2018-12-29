

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
		Vector[] toProject = new Vector[points.]
	}
}