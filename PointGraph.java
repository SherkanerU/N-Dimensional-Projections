import java.util.*;

public class PointGraph
{
	private final int V; 				//number of vertices
	private int E;						//number of edges

	private ArrayList[] adjacentTo;		//adjacentTo[i] denotes the list of point vertices which share an edge with i
	private Point[] vertices;			//the points which are being represented in this graph
}