
package SortingVisualiser;

import java.awt.Color;

//import Utils.Compare;



public class BarCanvas
    extends RectangleCanvas
{
    private static final long serialVersionUID = 1L;

    private int f_value;
    
    
    public static int s_pixels_per_unit = 1;
    
    public static int s_width = 1;
    
    
    
    public BarCanvas(int a_value, Color a_colour)
    {
        super(s_width, a_value * s_pixels_per_unit, a_colour);
        
        f_value = a_value;
    }
    
    
    
    public int GetValue()
    {
        return f_value;
    }
    
    public void SetValue(int a_value)
    {
        f_value = a_value;
        
        super.SetDimension(s_width, f_value * s_pixels_per_unit);
        
        //super.revalidate();
        
        //repaint();
    }

}
