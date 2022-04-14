package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class CocktailShakerSort
    extends IntArraySorter
{
    
    public CocktailShakerSort()
    {
        super("CocktailShakerSort");
    }
    
    
    
    /* Sorting algorithm
         * This algorithm sorts a_array in ascending or descending order.
         * Each iteration of the while loop results in the highest and lowest unsorted elements being placed in their correct
           positions. In essence, it is a bi-directional Bubble Sort algorithm: that is, one instance of Bubble-Sort in one 
           direction, and another in the other.
         
         Notes:
             (a). These two variables are the min and max indexes of the segment of a_array that is unsorted.
             (b). If the while loop condition isn't true, this means the 'unsorted' portion of the container has a size 
                  of 0, which means the container is sorted.
             (c). Get the highest value of the unsorted segment of the container and put it in the correct position (index
                  l_max_unsorted_index).
             (d). Decrement l_max_unsorted_index because the value at index l_max_unsorted_index  is now in its sorted 
                  position.
             (e). Get the lowest value of the unsorted segment of the container and put it in correct position (index 
                  l_min_unsorted_index).
             (f). Decrement l_min_unsorted_index because aContainer[l_min_unsorted_index] is now in its sorted position 
                  (don't want to iterate over it again).
    */
    @Override
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        /* These two variables are the min and max indexes of the segment of a_array that is unsorted. Initially, 
           the entire array is unsorted. i.e. the unsorted segment ranges from index l_index_unsorted_lower to 
           l_index_unsorted_upper
        */
        int l_index_unsorted_lower = 0; // The lowest index of the container's unsorted segment.
        int l_index_unsorted_upper = a_array.GetSize() - 1; // The highest index of the container's unsorted segment.
        
        /* A flag that, when true, indicates that no swaps occurred during one iteration of the below while loop, 
           meaning that the array is sorted. 
        */
        boolean l_no_swaps;
        
         /* Iterate until the unsorted segment has a size of zero. The size of the unsorted segment is 
            'l_index_unsorted_upper - l_index_unsorted_lower + 1', meaning that if 
            l_index_unsorted_lower < l_index_unsorted_upper, the size is 0 (or less).
         */
         while (l_index_unsorted_lower < l_index_unsorted_upper)
         {
             // Reset the 'no swaps' flag to true; if a swap occurs, it's set to false.
             l_no_swaps = true;
             
             /* Get the highest value of the unsorted segment of the container and put it in the correct position 
                (index l_index_unsorted_upper).
             */
             for (int i = l_index_unsorted_lower; i < l_index_unsorted_upper; ++i) // Min to max (ascend).
             {
                 CompareEnum l_operator = a_is_ascending ? CompareEnum.G : CompareEnum.L;
                 if (a_array.Compare(i, l_operator, i + 1, true))
                 {
                     a_array.Swap(i, i + 1, true);
                     l_no_swaps = false;
                 }
                 
             }
             a_array.SetBarColour(l_index_unsorted_upper, BarColourEnum.Sorted, true);
             --l_index_unsorted_upper; // Decrease the size of the unsorted segment by 1.
    
             /* Get the lowest value of the unsorted segment of the container and put it in the correct position 
               (index l_index_unsorted_lower).
             */
             for (int i = l_index_unsorted_upper; i > l_index_unsorted_lower; --i) // Max to min (descend).
             {
                 CompareEnum l_operator = a_is_ascending ? CompareEnum.L : CompareEnum.G;
                 if (a_array.Compare(i, l_operator, i - 1, true))
                 { 
                     a_array.Swap(i, i - 1, true);
                     l_no_swaps = false;
                 }
                 
             }
             a_array.SetBarColour(l_index_unsorted_lower, BarColourEnum.Sorted, true);
             ++l_index_unsorted_lower; // Decrease the size of the unsorted segment by 1.
             
             // If no swaps occurred, the array is sorted; therefore, end the loop.
             if (l_no_swaps)
             { 
                 a_array.SetBarRangeColour(l_index_unsorted_lower - 1, l_index_unsorted_upper + 1, BarColourEnum.Sorted, true);
                 break; 
             }
             
         }
        
    }
    
}
