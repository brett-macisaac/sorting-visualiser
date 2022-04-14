
package SortingVisualiser;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


public class RectangleCanvas
    extends Canvas
{
    private static final long serialVersionUID = 1L;
    
    
    
    public RectangleCanvas(int a_width, int a_height, Color a_colour)
    {
        super();
        
        // Set dimensions.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set color.
        super.setBackground(a_colour);
    }
    
    
    
    public void SetDimension(int a_width, int a_height)
    {
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Revalidate helps to reposition the canvas in its correct position in its parent container (i.e. the JPanel).
        // Don't quite understand it, but it nevertheless fixes the issue.
        super.revalidate();
        repaint();
    }
    
    public void SetColour(Color a_colour)
    {
        super.setBackground(a_colour);
        
        //super.revalidate();
        repaint();
    }
    
    public void paint(Graphics g)
    {
        update(g);
    }

    public void update(Graphics g)
    { 
        // Set the colour.
        g.setColor(super.getBackground());

        // Draw the rectangle.
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
    }
    
}
