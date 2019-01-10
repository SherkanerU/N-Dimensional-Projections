import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class GraphPlot extends JPanel 
{
	private PointGraph graph;
	private canvasPoint[] points;
	private final int CIRCLE_SIZE = 10;

	private boolean dynamic = false;									//to handle mouse events

	private Vector[] originalBasis;

	PointGraph originalGraph;



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

		dynamic = true;
	}

	public GraphPlot(PointGraph g, VectorSpace space, String type)
	{
		originalBasis = space.basis();
		originalGraph = new PointGraph(g);

		int dim = space.dimension();

		PointsWithSpace pws = new PointsWithSpace(g,space);

		for (int i = dim; i > 2; i --)
		{
			if (i%2 == 0)
			{
				pws.reduceSpace(type);
			}
			else
			{
				pws.reduceSpace("mix");
			}
			pws.projectGraph();
		}

		graph = pws.withRespectToBasis();

		dynamic = true;

		/*
		addMouseListener(new MouseListener(){
                    public void mouseClicked(MouseEvent e){
                        System.out.println("Mouse was clicked");
                        //repaint();
                    }

                    public void mouseEntered(MouseEvent arg0) {}
                    public void mouseExited(MouseEvent arg0) {}
                    public void mousePressed(MouseEvent arg0) {}
                    public void mouseReleased(MouseEvent arg0) {}
        });
        */

	}

	/***********************************************
				Inheritance Overrides
	***********************************************/
	public void paint(Graphics g)
	{
		if (dynamic)
		{
			points = verticesToCanvas(graph);
			plotPoints(g);
			plotEdges(g);
			dynamic = false;
		}
		else
		{
			super.paintComponent(g);
			update(g);
		}
	}

	public void update(Graphics g)
	{
		System.out.println("hell0");


		VectorSpace space = new VectorSpace(originalBasis);

		space.mixBasis();
		originalBasis = space.basis();

		int dim = space.dimension();

		PointGraph gr = new PointGraph(originalGraph);

		PointsWithSpace pws = new PointsWithSpace(gr,space);

		for (int i = dim; i > 2; i --)
		{
			pws.mixSpace();
			if (i%2 == 0)
			{
				pws.reduceSpace("total mix");
			}
			else
			{
				pws.reduceSpace("mix");
			}
			pws.projectGraph();
		}

		graph = pws.withRespectToBasis();

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

		/*
		double max = -1 * toConvert[0].getMax();
		double min = toConvert[0].getMax();

		for (int i = 0; i < toConvert.length; i++)
		{
			if (max < Math.abs(toConvert[i].getCord(0)))
			{
				max = toConvert[i].getCord(0);
			}
			if (max < Math.abs(toConvert[i].getCord(1)))
			{
				max = toConvert[i].getCord(1);
			}
			if (min > Math.abs(toConvert[i].getCord(0)))
			{
				min = toConvert[i].getCord(0);
			}
			if (min > Math.abs(toConvert[i].getCord(1)))
			{
				min = toConvert[i].getCord(1);
			}
		}
		*/

		//System.out.println("min:" + min);
		//System.out.println("max" + max);

		for (int i = 0; i < toConvert.length; i++)
		{
			canvasPoint p = new canvasPoint(pointConversion(toConvert[i]));
			toReturn[i] = p;
		}

		return toReturn;
	}

	private int[] pointConversion(Point p)									//represents a given point in terms of the current canvas size
	{
		int dim = p.getMax();												//dimension of the point
		int adjustedDim = 2*dim;											//to shift the point over so all is positive

		double pX = (p.getCord(0) + (double)dim);
		double pY = (p.getCord(1) + (double)dim);

		//double pX = Math.abs(p.getCord(0));
		//double pY = Math.abs(p.getCord(1));

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