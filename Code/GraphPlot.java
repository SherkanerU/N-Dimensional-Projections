import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class GraphPlot extends Canvas
{
	private PointGraph graph;
	private canvasPoint[] points;
	private final int CIRCLE_SIZE = 10;

	/**********************************************
					Constructors
	***********************************************/
	public GraphPlot(PointGraph g)										//initializes with internal graph state set to the given graph
	{
		if (g.dimension() != 2)
		{
			throw new IllegalArgumentException("cannot plot non 2 dimensional graphs");
		}

		graph = g;
	}

	/***********************************************
				Inheritance Overrides
	***********************************************/
	public void paint(Graphics g)
	{
		points = verticesToCanvas(graph);
		plotPoints(g);
		plotEdges(g);
	}

	public void update(Graphics g)
	{
		points = verticesToCanvas(graph);
		plotPoints(g);
		plotEdges(g);
	}


	/***********************************************
						Mutators
	************************************************/

	public void updateGraph(PointGraph g)								//replaces the graph
	{
		if (g.dimension() != 2)
		{
			throw new IllegalArgumentException("cannot plot non 2 dimensional graphs");
		}

		graph = g;
		points = verticesToCanvas(g);
	}

	/**********************************************
					Paint Methods
	***********************************************/
	public void plotPoints(Graphics g)
	{
		for (int i = 0; i < points.length; i++)
		{
			g.fillOval(points[i].getX() -CIRCLE_SIZE/2, points[i].getY() - CIRCLE_SIZE/2, CIRCLE_SIZE, CIRCLE_SIZE);
		}
	}

	public void plotEdges(Graphics g)
	{
		for (int i = 0; i < points.length; i ++)
		{
			int thisX = points[i].getX();
			int thisY = points[i].getY();

			for (Integer j : graph.adjTo(i))
			{
				int otherX = points[j].getX();
				int otherY = points[j].getY();

				g.drawLine(thisX,thisY, otherX, otherY);
			}
		}
	}


	/**********************************************
					Helper Methods
	***********************************************/
	private canvasPoint[] verticesToCanvas(PointGraph g)				//converts the points to a canvas points
	{
		Point[] toConvert = g.vertices();
		canvasPoint[] toReturn = new canvasPoint[toConvert.length];

		for (int i = 0; i < toConvert.length; i++)
		{
			canvasPoint p = new canvasPoint(pointConversion(toConvert[i]));
			toReturn[i] = p;
		}

		return toReturn;
	}

	private int[] pointConversion(Point p)								//represents a given point in terms of the current canvas size
	{
		int dim = p.getMax();											//dimension of the point
		int adjustedDim = 2*dim;										//to shift the point over so all is positive

		double pX = (p.getCord(0) + (double)dim);
		double pY = (p.getCord(1) + (double)dim);

		double pXratio = pX/(double)adjustedDim;
		double pYratio = pY/(double)adjustedDim;

		double pXCanvas = pXratio*(double)getWidth();
		double pYCanvas = pYratio*(double)getHeight();

		int[] ret = {(int)pXCanvas, (int)pYCanvas};

		//System.out.println("The current height and width are x = " + getWidth() + " and with y = " + getHeight());
		//System.out.println("Made point with x = " + pXCanvas + " and with y = " + pYCanvas);

		return ret;
	}

}