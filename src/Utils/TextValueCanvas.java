
package Utils;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;



public class TextValueCanvas
    extends Canvas 
{
    private String f_text;
    
    private int f_value;
    
    private Color f_color_text;
    
    private Color f_color_value;
    
    
    // The size of the padding above and below the text/value.
    private static final float s_padding_proportion_horizontal = 0.1f;
    
    // The size of the padding to the left and right of the text/value.
    private static final float s_padding_proportion_vertical = 0.1f;
    
    private static final Color s_colour_back_default = Color.WHITE;
    
    private static final Color s_colour_text_default = Color.BLACK;
    
    private static final Color s_colour_value_default = new Color(200, 200, 200);
    
    private static final long serialVersionUID = 1L;
    
    

    public TextValueCanvas(String a_text , int a_value, int a_width, int a_height, 
                           Color a_color_back, Color a_color_text, Color a_color_value)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(a_color_back);
        f_color_text = a_color_text;
        f_color_value = a_color_value;

        // Set text and value.
        f_text = a_text;
        f_value = a_value;
    }
    
    public TextValueCanvas(String a_text , int a_value, int a_width, int a_height)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(s_colour_back_default);
        f_color_text = s_colour_text_default;
        f_color_value = s_colour_value_default;

        // Set text and value.
        f_text = a_text;
        f_value = a_value;
    }

    public void SetValue(int aValue)
    {
        f_value = aValue;
        
        super.repaint();
    }
    
    public void Increment() 
    {
        ++f_value;
        
        super.repaint();
    }
    
    public void Decrement() 
    {
        --f_value;
        
        super.repaint();
    }
    
    public int GetValue()
    {
        return f_value;
    }
    
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void update(Graphics g)
    {
        //Graphics gg = DoubleBuffer.getGraphics(this);
        
        /* Ideas
             * The text and value should be on the same line (for simplicitly).
             * There should be a semicolon directly after the text, followed by a gap.
             * The font should be as big as possible, which is of course limited by the canvas' width and height. Start
               with the maximum height, then decrease by 1 until the text fits.
        */
        
        // Draw the background.
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // The text should be appended by a semicolon and a space.
        String l_text_string = f_text + ": ";
        
        // The string form of the value.
        String l_text_value = String.valueOf(f_value);
        
        // The full text to output.
        String l_text_full = l_text_string + l_text_value;
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( super.getPreferredSize().width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( super.getPreferredSize().height * s_padding_proportion_vertical );
        
        // The text's maximum width.
        int l_width_max = (int)( super.getPreferredSize().width - 2 * l_padding_horizontal );
        
        // The font's maximum height.
        int l_height_max = (int)( super.getPreferredSize().height - 2 * l_padding_vertical );

        // Print the text upon the canvas at the appropriate font.
        for (int height = l_height_max; height > 1; --height)
        {
            g.setFont(new Font("Arial", Font.BOLD, height));             
            
            FontMetrics l_font_metrics = g.getFontMetrics();
            
            // The highest height of a character of the current font.
            int l_height_text = l_font_metrics.getAscent();
            
            // Check if the height is too high.
            if (l_height_text > l_height_max)
            { continue; }
            
            // The width of l_text_full under the current font.
            int l_width_text_full = l_font_metrics.stringWidth(l_text_full);
            
            // Check if the width is to wide.
            if (l_width_text_full > l_width_max)
            { continue; }
            
            // The width of l_text_full under the current font.
            int l_width_text_string = l_font_metrics.stringWidth(l_text_string);
            
            // X and Y coordinate of the text l_text_string.
            int l_x = l_padding_horizontal;
            int l_y = l_padding_vertical + l_height_text;
            
            // Set the colour for l_text_string.
            g.setColor(f_color_text);
            
            // Draw l_text_string.
            g.drawString(l_text_string, l_x, l_y);
            
            // The x-coordinate of l_text_value.
            l_x += l_width_text_string;
            
            // Set the colour for l_text_value.
            g.setColor(f_color_value);
            
            // Draw l_text_string.
            g.drawString(l_text_value, l_x, l_y);
            
            break;
        }
        
        //DoubleBuffer.switchBuffer(g);
    }
    
}
