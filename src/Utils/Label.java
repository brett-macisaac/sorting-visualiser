
package Utils;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;



public class Label
    extends Canvas 
{
    public enum Alignment
    {
        Left,
        Centre
    }
    
    private String f_text;
    
    private Alignment f_align;
    
    
    // The size of the padding above and below the text/value.
    private static final float s_padding_proportion_horizontal = 0.025f;
    
    // The size of the padding to the left and right of the text/value.
    private static final float s_padding_proportion_vertical = 0.025f;
    
    private static final Color s_colour_back_default = Color.BLACK;
    
    private static final Color s_colour_text_default = Color.WHITE;
    
    private static final long serialVersionUID = 1L;
    
    
    
    public Label(String a_text, Alignment a_align, int a_width, int a_height, Color a_color_back, Color a_color_text)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(a_color_back);
        super.setForeground(a_color_text);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    public Label(String a_text, Alignment a_align, int a_width, int a_height)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(s_colour_back_default);
        super.setForeground(s_colour_text_default);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    
    public String GetText()
    {
        return f_text;
    }
    
    public void SetText(String a_text)
    {
        f_text = a_text;
        
        super.repaint();
    }
    
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void update(Graphics g)
    {
        /* Ideas
             * The text and value should be on the same line (for simplicitly).
             * There should be a semicolon directly after the text, followed by a gap.
             * The font should be as big as possible, which is of course limited by the canvas' width and height. Start
               with the maximum height, then decrease by 1 until the text fits.
        */
        
        // Draw the background.
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
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
            g.setFont(new Font("Arial", Font.PLAIN, height));         
            
            FontMetrics l_font_metrics = g.getFontMetrics();
            
            // The highest height of a character of the current font.
            int l_height_text = l_font_metrics.getAscent();
            
            // Check if the height is too high.
            if (l_height_text > l_height_max)
            { continue; }
            
            // The width of l_text_full under the current font.
            int l_width_text = l_font_metrics.stringWidth(f_text);
            
            // Check if the width is too wide.
            if (l_width_text > l_width_max)
            { continue; }
            
            // X and Y coordinate of the text.
            int l_x = 0;
            int l_y = 0;
            
            // Set the coordinates.
            if (f_align == Alignment.Left)
            {
                l_x = l_padding_horizontal;
                l_y = l_padding_vertical + l_height_text;
            }
            else if (f_align == Alignment.Centre)
            {
                l_x = l_padding_horizontal + (l_width_max - l_width_text) / 2;
                l_y = l_padding_vertical + l_height_text;
            }
            
            // Set the colour for l_text_string.
            g.setColor(getForeground());
            
            // Draw l_text_string.
            g.drawString(f_text, l_x, l_y);
            
            break;
        }

    }
    
}
