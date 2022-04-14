
package SortingVisualiser;


import java.awt.Color;

import Utils.CompareEnum;


public class MergeSortIterative
    extends IntArraySorter
{
    private boolean f_is_ascending;
    
    Color f_merge_colour;
    
    
    
    public MergeSortIterative()
    {
        super("MergeSortIterative");
        
        f_merge_colour = new Color(148,0,211);
    }
    
    
    
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        f_is_ascending = a_is_ascending;
        
        // (a).
        int l_segment_size; // Current size of segment to split and merge (range: 2 to l_max_segment_size).
        int l_start; // First index of segment (first index of lower half).
        int l_mid; // Middle index of segment (last index of lower half, first index of lower half).
        int l_end; // Last index of segment (last index of upper half).

        // (b). Not necessary to make these variables, but it does help with readability.
        int l_container_max_index = a_array.GetSize() - 1;
        int l_container_size = a_array.GetSize();

        // (c). Calculate and store the maximum length of a segment.
        int l_max_segment_size = 1;
        while (l_max_segment_size < l_container_size)
        { l_max_segment_size *= 2; }

        for (l_segment_size = 2; l_segment_size <= l_max_segment_size; l_segment_size *= 2) // (d).
        {
            for (l_start = 0; l_start <= l_container_max_index - (l_segment_size / 2); l_start += l_segment_size) // (e).
            {
                // (f). Calculate middle index of segment lStart to lEnd (max index of lower half).
                l_mid = l_start + (l_segment_size / 2) - 1;

                // (g). Calculate max index of segment lStart to lEnd (max index of upper half).
                int l_end_candidate = l_start + l_segment_size - 1;
                if (l_end_candidate < l_container_max_index)
                {
                    l_end = l_end_candidate;
                }
                else
                {
                    l_end = l_container_max_index;
                }

                // Combine the lower (lStart to lMid) and upper (lMid + 1 to lEnd) halves of the current segment.
                Merge(a_array, l_start, l_mid, l_end);
            }
            
        }
        
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
