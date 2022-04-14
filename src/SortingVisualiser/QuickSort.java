
package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class QuickSort
    extends IntArraySorter
{
    private CompareEnum f_operator;
    
    
    
    public QuickSort()
    {
        super("QuickSort");
    }
    
    // Could also use this to avoid unnecessary assignment, yet then again the purpose of this program isn't to make optimised sorting algorithms, but rather to simply demonstrate and understand them.
    //private IntArrayPanel f_array;
    
    
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        f_operator = a_is_ascending ? CompareEnum.L : CompareEnum.G;
        
        Split(a_array, 0, a_array.GetSize() - 1);
    }
    
    
    
    private void Split(IntArrayPanel a_array, int a_start, int a_end)
    {
        if (a_start < a_end)
        {
            int a_index_sorted_value = SortValue(a_array, a_start, a_end);
            
            Split(a_array, a_start, a_index_sorted_value - 1);
            
            Split(a_array, a_index_sorted_value + 1, a_end);
        }
        else if (a_start == a_end)
        {
            // Indicate that the value at a_start (and a_end) is in its sorted position.
            a_array.SetBarColour(a_start, BarColourEnum.Sorted, true);
        }
        
    }
    
    private int SortValue(IntArrayPanel a_array, int a_start, int a_end)
    {
        // The index of the value that is to be placed into its sorted position.
        int l_index_pivot = a_end;

        // The index at which l_index_pivot's value will ultimately be placed.
        int l_index_of_sort = a_start;

        for (int i = a_start; i < a_end; ++i)
        {   
            if (a_array.Compare(i, f_operator, l_index_pivot, true))
            {
                // Swap current value with the one at l_index_of_sort.
                if (i != l_index_of_sort)
                { a_array.Swap(i, l_index_of_sort, true); }

                ++l_index_of_sort;
            }
            
        }

        // Move the pivot's value into its sorted position.
        if (l_index_of_sort != l_index_pivot)
        { a_array.Swap(l_index_of_sort, l_index_pivot, true); }
        
        // Indicate that the value at l_index_of_sort is in its sorted position.
        a_array.SetBarColour(l_index_of_sort, BarColourEnum.Sorted, true);

        // Return the index of the value sorted by this algorithm.
        return l_index_of_sort;
    }
    
}
