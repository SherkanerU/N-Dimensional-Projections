import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class GraphPlot extends Canvas
{
	private PointGraph graph;
	private canvasPoint[] points;

	/**********************************************
					Constructors
	***********************************************/
	public GraphPlot(PointGraph g)										//initializes with internal graph state set to the given graph
	{
		if (g.dimension() != 2){throw new IllegalArgumentException("cannot plot non 2 dimensional graphs");}
		graph = g;
		points = verticesToCanvas(g);
	}



	/**********************************************
					Helper Methods
	***********************************************/
	private canvasPoint[] verticesToCanvas(PointGraph g)				//converts the points to a canvas points
	{
		Point[] toConvert = g.vertices;
		canvasPoint[] toReturn = new canvasPoint[toConvert.length];

		for (int i = 0; i < toConvert.length; i++)
		{
			canvasPoint p = new canvasPoint(pointConversion(toConvert[i]));
			toReturn[i] = p;
		}
	}

	private int[] pointConversion(Point p)								//represents a given point in terms of the current canvas size
	{
		int dim = p.getMax();											//dimension of the point
		int adjustedDim = 2*dim;										//to shift the point over so all is positive

		double pX = (p.getCord(0) + dim)
		double pY = (p.getCord(1) + dim)

		double pXratio = pX/adjustedDim;
		double pYratio = pY/adjustedDim;

		int pXcanvas = (int)(pXratio*getWidth());
		int pYCanvas = (int)(pYRatio*getWidth());

		int[] ret = {pXcanvas, pYCanvas};
		return ret;
	}

}