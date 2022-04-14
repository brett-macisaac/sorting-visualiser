
package SortingVisualiser;


import java.awt.Color;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;


public class MergeSort
    extends IntArraySorter
{
    private boolean f_is_ascending;
    
    Color f_merge_colour;
    
    
    
    public MergeSort()
    {
        super("MergeSort");
        
        f_merge_colour = new Color(148,0,211);
    }
    
    
    
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        f_is_ascending = a_is_ascending;
        
        SplitAndMerge(a_array, 0, a_array.GetSize() - 1);
    }
    
    
    
    private void SplitAndMerge(IntArrayPanel a_array, int a_start, int a_end)
    {
        if (a_start >= a_end)
        { return; }
        
        // Calculate the middle index.
        int l_mid = (a_start + a_end) / 2;
        
        // Highlight the lower segment (the segment that is about to be split).
        a_array.HighlightBarRange(a_start, l_mid, Color.RED);
        
        // Split and merge the lower half of the current segment (a_start to l_mid).
        // Once this returns, said lower half will have been sorted.
        SplitAndMerge(a_array, a_start, l_mid);
        
        // Highlight the upper segment (the segment that is about to be split).
        a_array.HighlightBarRange(l_mid + 1, a_end, Color.BLUE);
        
        // Continue to split and merge the upper half of the current segment (l_mid + 1 to a_end).
        // Once this returns, said upper half will have been sorted.
        SplitAndMerge(a_array, l_mid + 1, a_end);
        
        // Combine the lower (a_start to l_mid) and upper (l_mid + 1 to a_end) segments which, individually, are sorted.
        Merge(a_array, a_start, l_mid, a_end);
        
    }
    
    private void Merge(IntArrayPanel a_array, int a_start, int a_mid, int a_end)
    {   
        // Change the colours of the two segments.
        a_array.SetBarRangeColour(a_start, a_mid, Color.RED, false);
        a_array.SetBarRangeColour(a_mid + 1, a_end, Color.BLUE, true);
        
        // Create a temporary container to house the merged segment.
        int l_size_of_merger = a_end - a_start + 1; // Size of merged segment.
        int l_merger[] = new int[l_size_of_merger]; // Array to hold the merged values of lower and upper segments.

        // (a). The current indexes of the lower and upper segments, respectively.
        int l_index_lower_segment = a_start;
        int l_index_upper_segment = a_mid + 1;

        // (b). The 'current' index of lMerger.
        int l_merger_index = 0;
        
        // The comparison operator to use within the while loop.
        CompareEnum l_comparison_operator = f_is_ascending ? CompareEnum.LE : CompareEnum.GE; 
        
        // The purpose of this while loop is to populate l_merger with all elements from lower and upper segments.
        while (true) // (c).
        {
            if (l_index_lower_segment <= a_mid && l_index_upper_segment <= a_end) // (d).
            {
                if (a_array.Compare(l_index_lower_segment, l_comparison_operator, l_index_upper_segment, false)) // (e).
                {
                    l_merger[l_merger_index++] = a_array.GetValue(l_index_lower_segment++);
                }
                else // (f).
                {
                    l_merger[l_merger_index++] = a_array.GetValue(l_index_upper_segment++);
                }
                
            }
            else if (l_index_lower_segment <= a_mid) // (g).
            {
                l_merger[l_merger_index++] = a_array.GetValue(l_index_lower_segment++);
            }
            else if (l_index_upper_segment <= a_end) // (h).
            {
                l_merger[l_merger_index++] = a_array.GetValue(l_index_upper_segment++);
            }
            else // (i).
            {
                break;
            }
            
        }

        // Copy the values from l_merger into the appropriate indexes of a_array.
        for (int i = a_start; i <= a_end; ++i) 
        { 
            a_array.SetValue(i, l_merger[i - a_start], false);
            a_array.SetBarColour(i, f_merge_colour, true);
        }
        
        // Remove the colours.
        a_array.SetBarRangeColour(a_start, a_end, IntArrayPanel.BarColourEnum.Standard, false);
    }
    
}
