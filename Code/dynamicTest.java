import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class dynamicTest extends Canvas
{
	private int globalI = 0;
	
	public dynamicTest()
	{

	}

	public void paint(Graphics g) {
        g.fillOval(100, 100, 200 , 200);
    }

    public void update(Graphics g)
     {
     	globalI += 5;
        g.fillOval(100, 100, 200 + 10*globalI , 200);
        System.out.println(getWidth() + "  " + getHeight());
    }
}