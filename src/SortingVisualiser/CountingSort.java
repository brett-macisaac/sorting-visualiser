
package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;

/*
     * Credits:
         * https://www.geeksforgeeks.org/counting-sort/#:~:text=Counting%20sort%20is%20a%20sorting,the%20help%20of%20an%20example.
             > This is a good resource which helped me write this algorithm.
*/
public class CountingSort
    extends IntArraySorter
{
    public CountingSort()
    {
        super("CountingSort");
    }
    
    
    
    /*
         * Notes:
             (a). The range of values in an IntArrayPanel panel is guaranteed to be between 1 and a_array.GetSize(); as 
                  such, the size of this container must be a_array.GetSize() + 1. e.g. if a_array.GetSize() is 100, 
                  l_counts will range from index 0 to 100. In most cases, a loop will be run to find the maximum value in 
                  the array, which will the be used to instantiate the count array at that size.
                      e.g. consider the array {3, 1, 4, 5, 3, 6}: the size of the array is 6, and the range of values is
                           [1, 6]. The l_counts array would be { 0, 1, 0, 2, 1, 1, 1 }:
                               * index: 0 1 2 3 4 5 6
                               * count: 0 1 0 2 1 1 1
             (b). This process makes each element of l_counts store not only the count of its corresponding index, but 
                  the count of all elements at lower indexes.
                       e.g. if l_counts is { 0, 1, 0, 2, 1, 1, 1 } (from note (a)), the modified version will be 
                            { 0, 1, 1, 3, 4, 5, 6 }.
             (c). The values of a_array must be sorted into their correct positions using an auxiliary/temporary array,
                  as otherwise the algorithm would overwrite the values of a_array as it places them at their correct indexes.
             (d). Again consider the array {3, 1, 4, 5, 3, 6} and its corresponding l_counts array, { 0, 1, 1, 3, 4, 5, 6 }.
                  The first element, 3, corresponds to l_counts[3] = 3; this means that 3 is the third element in the 
                  sorted array, which corresponds to index 3 - 1 = 2. 
    */
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        // (a). An array to store the count of each value in a_array.
        int l_counts[] = new int[a_array.GetSize() + 1];
        
        // Record into l_counts the number of each value in a_array.
        for (int i = 0; i < a_array.GetSize(); ++i)
        { 
            ++l_counts[a_array.GetValue(i)];
        }
        
        // (b). Modify l_counts so that each element also stores the sum of all lower counts.
        for (int i = 1; i < l_counts.length; ++i)
        { l_counts[i] += l_counts[i - 1]; }
        
        // (c). An array to store the values of a_array in sorted order.
        int l_array_sorted[] = new int[a_array.GetSize()];
        
        // (d). Use l_counts to place each element into its correct position.
        for (int i = 0; i < a_array.GetSize(); ++i)
        { 
            // Get the index at which value a_array.GetValue(i) will be placed (i.e. its sorted position).
            int l_index_sorted = l_counts[a_array.GetValue(i)] - 1;
            
            l_array_sorted[l_index_sorted] = a_array.GetValue(i);
            
            // Decrease the (cumulative) count associated with value a_array.GetValue(i).
            --l_counts[a_array.GetValue(i)];
        }
        
        // Copy the (sorted) values of l_array_sorted into a_array.
        for (int i = 0; i < a_array.GetSize(); ++i)
        {
            // Place a_array.GetValue(i) into its sorted position.
            a_array.SetValue(i, l_array_sorted[i], false);
            
            // Indicate that the value at l_index_sorted is now in its sorted position.
            a_array.SetBarColour(i, BarColourEnum.Sorted, true);
        }
        
    }
    
    
}
