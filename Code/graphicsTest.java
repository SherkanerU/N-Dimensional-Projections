import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

public class graphicsTest extends Canvas {

	private static  int globalI = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        Canvas canvas = new graphicsTest();

        canvas.setSize(400, 400);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

        for (int i = 0; i <= 10; i++)
        {
        	globalI = i;
        	try
        	{
        		Thread.sleep(100);
        	} 
        	catch (InterruptedException e)
        	{

        	}
        	canvas.repaint();
        }

    }

    public void paint(Graphics g) {
        g.fillOval(100, 100, 200 , 200);
    }

    public void update(Graphics g) {
        g.fillOval(100, 100, 200 + 10*globalI , 200);
        System.out.println(getWidth() + "  " + getHeight());
    }

}

