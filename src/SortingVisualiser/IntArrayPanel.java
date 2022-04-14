
package SortingVisualiser;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Utils.RNG;
import Utils.TextValueLabel;
import Utils.CompareEnum;


/* Ideas
     * Yellow for comparison; red for no swap, green for swap.
*/


public class IntArrayPanel
    extends JPanel
{
    public enum BarColourEnum
    {
        Standard, 
        Compared, 
        Swapped,
        Sorted
    }
    
    
    private BarCanvas f_bar_graph[];
    
    Color f_colour_bar_standard;
    
    Color f_colour_bar_compared;
    
    Color f_colour_bar_swapped;
    
    // The colour associated with a value/bar that is in its sorted position.
    Color f_colour_bar_sorted;
    
    // The number of comparisons associated with the most recent sort.
    int f_num_comparisons;
    
    // The number of swaps associated with the most recent sort.
    int f_num_swaps;
    
    SortFrame f_parent;
    
    
    // The maximum height a panel can be.
    private static int s_max_height = (int)( Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5 );
    
    // The maximum width a panel can be.
    private static int s_max_width = (int)( Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5 );
    
    private static int s_default_size = 10;
    
    private static final float s_padding_sides_proportion = 0.02f;
    
    private static final float s_padding_top_bottom_proportion = 0.02f;
    
    
    
    /*
         * Parameters:
             * a_size: the number of columns (desire size of f_bar_graph); in addition, [0, a_size] is the range of values.
             * a_colour_back: the background colour.
             * a_colour_front: the colour of the BarCanvas objects.
    */
    public IntArrayPanel(int a_size, int a_width, int a_height, Color a_colour_back, Color a_colour_bar_standard, 
                         Color a_colour_bar_compared, Color a_colour_bar_swapped, Color a_colour_bar_sorted, SortFrame a_parent)
    {
        super(new GridBagLayout());
        
        f_parent = a_parent;
        
        // Set the colours.
        super.setBackground(a_colour_back);
        f_colour_bar_standard = a_colour_bar_standard;
        f_colour_bar_compared = a_colour_bar_compared;
        f_colour_bar_swapped = a_colour_bar_swapped;
        f_colour_bar_sorted = a_colour_bar_sorted;
        
        // a_size must be a positive value.
        if (a_size <= 0)
        { a_size = s_default_size; }
        
        // a_width mustn't be above the max value.
        if (a_width > s_max_width)
        { a_width = s_max_width; }
        
        // a_height mustn't be above the max value.
        if (a_height > s_max_height)
        { a_height = s_max_height; }
        
        int l_padding_sides = (int)( a_width*s_padding_sides_proportion );
        int l_padding_top_bottom = (int)( a_height*s_padding_top_bottom_proportion );
        
        // Set the panel's size.
        super.setPreferredSize(new Dimension(a_width, a_height + 2*l_padding_top_bottom));
        
        // Ensure that a_size is less than or equal to the lowest limiting (max) dimension of the panel.
        int l_limiting_dimension = (a_height < a_width) ? a_height : a_width;
        if (a_size > l_limiting_dimension)
        {
            a_size = l_limiting_dimension;
        }
        
        // The width of each BarCanvas object in f_bar_graph (make as large as possible).
        BarCanvas.s_width = (int) ((a_width /*- 2*l_padding_sides*/) / a_size );
        
        // The pixels per unit of value (make as large as possible).
        BarCanvas.s_pixels_per_unit = (int) ((a_height /*- 2*l_padding_top_bottom*/) / a_size );
        
        // A constraints object to help place the elements of f_bar_graph onto the panel.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        l_constraints.fill = GridBagConstraints.NONE;
        l_constraints.anchor = GridBagConstraints.SOUTHWEST;
        l_constraints.gridy = 0; l_constraints.gridx = 0;
        l_constraints.insets = new Insets(l_padding_top_bottom, 0, l_padding_top_bottom, 0);
        
        // Create and populate f_bar_graph (and also add its element to the panel).
        f_bar_graph = new BarCanvas[a_size];
        for (int i = 0; i < f_bar_graph.length; ++i)
        {
//            if (i == 1)
//            {
//                l_constraints.insets.left = 0;
//            }
//            else if (i == f_bar_graph.length - 1)
//            {
//                l_constraints.insets.right = l_padding_sides;
//            }
            
            // Create the element.
            f_bar_graph[i] = new BarCanvas(RNG.RandomInt(1, a_size), f_colour_bar_standard);
            
            // Add the element to the panel.
            super.add(f_bar_graph[i], l_constraints);
            
            // Increment the x index so that the next element is added to the next column.
            ++l_constraints.gridx;
        }
        
        // Make sure that at least one value is the maximum, as otherwise the entire bar graph gets shifted up for some reason.
        // Will have to look into how the layout manager works on a deeper level to fix this.
        f_bar_graph[RNG.RandomInt(f_bar_graph.length - 1)].SetValue(a_size);
        
        //super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    

    
    public int GetSize()
    {
        return f_bar_graph.length;
    }
    
    public int GetValue(int a_index)
    {   
        f_parent.IncrementCount(true);
        
        int l_value = 0;
        
        try
        { l_value = f_bar_graph[a_index].GetValue(); }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        return l_value;
    }
    
    public void SetValue(int a_index, int a_value, boolean a_thread_control)
    {
        if (a_index < 0 || a_index > f_bar_graph.length - 1)
        { return; }
        
        f_parent.IncrementCount(false);
        
        f_bar_graph[a_index].SetValue(a_value);
        
        if (a_thread_control)
        {
            // Change the bar's colour to signify that its value has been changed.
            SetBarColour(a_index, BarColourEnum.Swapped, true);
            
            // Remove the colour.
            SetBarColour(a_index, BarColourEnum.Standard, false);
        }
        
    }
    
    // Swap the values of two BarCanvas objects at the specified indexes.
    public void Swap(int a_index_a, int a_index_b, boolean a_thread_control)
    {
        int l_val_a = GetValue(a_index_a);
        
        SetValue(a_index_a, f_bar_graph[a_index_b].GetValue(), false);
        SetValue(a_index_b, l_val_a, false);
        
        if (a_thread_control)
        {
            // Change the bars' colours to signify that they have been swapped.
            SetBarsColour(a_index_a, a_index_b, BarColourEnum.Swapped, true);
            
            // Remove the colours.
            SetBarsColour(a_index_a, a_index_b, BarColourEnum.Standard, false);
        }
        
    }
    
    public boolean Compare(int a_index_a, CompareEnum a_compare_type, int a_index_b, boolean a_thread_control)
    {
        f_parent.IncrementCount(true);
        
        if (a_thread_control)
        {
            // Change the bars' colours to signify that they are being compared.
            SetBarsColour(a_index_a, a_index_b, BarColourEnum.Compared, true);
            
            // Remove the colours.
            SetBarsColour(a_index_a, a_index_b, BarColourEnum.Standard, false);
        }
        
        int l_value_a = GetValue(a_index_a);
        int l_value_b = GetValue(a_index_b);
        
        if (a_compare_type == CompareEnum.E)
        {
            return l_value_a == l_value_b;
        }
        else if (a_compare_type == CompareEnum.NE)
        {
            return l_value_a != l_value_b;
        }
        else if (a_compare_type == CompareEnum.G)
        {
            return l_value_a > l_value_b;
        }
        else if (a_compare_type == CompareEnum.L)
        {
            return l_value_a < l_value_b;
        }
        else if (a_compare_type == CompareEnum.GE)
        {
            return l_value_a >= l_value_b;
        }
        else if (a_compare_type == CompareEnum.LE)
        {
            return l_value_a <= l_value_b;
        }
        
        return true;
    }
    
    public boolean Compare(int a_index, CompareEnum a_compare_type, int a_value)
    {
        int l_value = GetValue(a_index);
        
        if (a_compare_type == CompareEnum.E)
        {
            return l_value == a_value;
        }
        else if (a_compare_type == CompareEnum.NE)
        {
            return l_value != a_value;
        }
        else if (a_compare_type == CompareEnum.G)
        {
            return l_value > a_value;
        }
        else if (a_compare_type == CompareEnum.L)
        {
            return l_value < a_value;
        }
        else if (a_compare_type == CompareEnum.GE)
        {
            return l_value >= a_value;
        }
        else if (a_compare_type == CompareEnum.LE)
        {
            return l_value <= a_value;
        }
        
        return true;
    }
    
    public void HighlightBar(int a_index, BarColourEnum a_colour)
    {
        // The bar's current colour.
        Color l_colour_current = f_bar_graph[a_index].getBackground();
        
        // Highlight the bar.
        SetBarColour(a_index, a_colour, true);
        
        // Return the bar's colour to the one prior to the highlight.
        SetBarColour(a_index, l_colour_current, false);
    }
    
    public void HighlightBarRange(int a_index_start, int a_index_end, BarColourEnum a_colour)
    {
        HighlightBarRange(a_index_start, a_index_end, GetColour(a_colour));
    }
    
    public void HighlightBarRange(int a_index_start, int a_index_end, Color a_colour)
    {
        // The bars' current colour.
        // The assumption here is that all of the bars in the range have the colour of the bar at index a_index_start.
        Color l_colour_current = Color.BLACK;
        try
        { l_colour_current = f_bar_graph[a_index_start].getBackground(); }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        // Highlight the bar.
        SetBarRangeColour(a_index_start, a_index_end, a_colour, true);
        
        // Return the bar's colour to the one prior to the highlight.
        SetBarRangeColour(a_index_start, a_index_end, l_colour_current, false);
    }
    
    public void SetBarColour(int a_index, Color a_bar_colour, boolean a_thread_control)
    {
        try
        { f_bar_graph[a_index].SetColour(a_bar_colour); }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        if (a_thread_control)
        { ThreadControl(); }
    }
    
    public void SetBarColour(int a_index, BarColourEnum a_bar_colour, boolean a_thread_control)
    {   
        SetBarColour(a_index, GetColour(a_bar_colour), a_thread_control);
    }
    
    public void SetBarsColour(int a_index_a, int a_index_b, BarColourEnum a_bar_colour, boolean a_thread_control)
    {   
        SetBarsColour(a_index_a, a_index_b, GetColour(a_bar_colour), a_thread_control);
    }
    
    public void SetBarsColour(int a_index_a, int a_index_b, Color a_bar_colour, boolean a_thread_control)
    {   
        try
        { 
            f_bar_graph[a_index_a].SetColour(a_bar_colour);
            f_bar_graph[a_index_b].SetColour(a_bar_colour);
        }
        catch (IndexOutOfBoundsException e)
        { e.printStackTrace(); }
        
        if (a_thread_control)
        { ThreadControl(); }
    }
    
    public void SetBarRangeColour(int a_index_start, int a_index_end, BarColourEnum a_bar_colour, boolean a_thread_control)
    {   
        SetBarRangeColour(a_index_start, a_index_end, GetColour(a_bar_colour), a_thread_control);
    }
    
    public void SetBarRangeColour(int a_index_start, int a_index_end, Color a_bar_colour, boolean a_thread_control)
    {    
        for (int i = a_index_start; i <= a_index_end; ++i)
        {
            try
            { f_bar_graph[i].SetColour(a_bar_colour); }
            catch (IndexOutOfBoundsException e)
            { e.printStackTrace(); }
        }
        
        if (a_thread_control)
        { ThreadControl(); }
    }
    
    public void ResetColours()
    {
        // Reset the bars' colours to the standard.
        SetBarRangeColour(0, f_bar_graph.length - 1, BarColourEnum.Standard, false);
    }
    
    public void Shuffle()
    {
        for (int i = f_bar_graph.length - 1; i > 0; --i)
        {
            int l_index_random = RNG.RandomInt(i);
            
            Swap(i, l_index_random, false);
        }
        
    }
    
    public void Sort(IntArraySorter a_sorter, boolean a_is_ascending) throws Exception
    {
        // Reset the sort statistics variables.
        f_num_comparisons = 0;
        f_num_swaps = 0;
        
        // Sort *this (i.e. f_bar_graph).
        a_sorter.Sort(this, a_is_ascending);
    }
    
    
    
    private Color GetColour(BarColourEnum a_bar_colour)
    {
        if (a_bar_colour == BarColourEnum.Standard)
        {
            return f_colour_bar_standard;
        }
        else if (a_bar_colour == BarColourEnum.Compared)
        {
            return f_colour_bar_compared;
        }
        else if (a_bar_colour == BarColourEnum.Swapped)
        {
            return f_colour_bar_swapped;
        }
        else if (a_bar_colour == BarColourEnum.Sorted)
        {
            return f_colour_bar_sorted;
        }
        
        return Color.WHITE;
    }
    
    private void ThreadControl()
    {
        // Maybe an auxiliary for this, as it will be just be reused in every method.
        try 
        {
            if (f_parent.IsStepping())
            {
                // Wait until the 'step' button is pressed.
                //Thread.currentThread().wait();
                synchronized (f_parent) 
                {
                    f_parent.wait();
                }

            }
            else
            {
                Thread.currentThread().sleep(f_parent.GetSortRate());
            }
            
        } catch (InterruptedException e) 
        { }
        
    }
    
    
}
