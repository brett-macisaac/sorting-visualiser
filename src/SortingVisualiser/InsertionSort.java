package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class InsertionSort 
    extends IntArraySorter
{
    
    public InsertionSort()
    {
        super("InsertionSort");
    }
    
    
    
    /* Sorting Algorithm
         * This operator allows an object of this class to be 'called', as would a function. The argument, aContainer, is 
           sorted (in ascedning order) using the Insertion Sort algorithm.
    
         * Notes:
             (a). Consider a segment of aContainer from index 0 to lMinUnsortedIndex - 1: this is the sorted segment at the
                    beginning of each iteration of the outer for loop. The goal of each outer for loop is to get the value at 
                    index lMinUnsortedIndex into its sorted position within the sorted segment, which is achieved via the
                    inner for-loop. Note that the first iteration of this for loop assumed the value at index 0 is already
                    sorted.
             (b). Assign the ith value of the container to lValueToInsert. At the beginning of each outer for-loop, the
                    segment of the container from 0 to l_min_unsorted_index-1 is guaranteed to be sorted (from previous iterations).
             (c). The goal of the inner for-loop is to insert lValueToInsert into the correct index of the segment of the
                    container from index 0 to lMinUnsortedIndex-1 (the sorted segment), which will be lIndexOfInsert.
             (d). If lValueOfInsert is smaller than the value at lIndexOfInsert, then lIndexOfInsert isn't yet correct: 
                    therefore, shift the value at lIndexOfInsert (aContainer[lIndexOfInsert]) one index up.
             (e). If lIndexOfInsert is zero, this means lValueToInsert must be less than all other elements in the sorted
                    segment of the container: therefore, insert the lValueToInsert at lIndexOfInsert (index 0).
             (f). If no shifts have occurred, this means lValueToInsert is already at its correct index relative to the 
                    sorted segment; therefore, do not insert it at another index.
             (g). If lValueOfInsert is larger (or equal to) the value at lIndexOfInsert (and at least one shift has 
                    occurred) then lIndexOfInsert is the correct index for lValueOfInsert: therefore, insert lValueOfInsert 
                    at lIndexOfInsert.
     */
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        // The value to insert upon each iteration of the for-loop.
        int l_value_to_insert;
        
        // The number of values that are shifted in the while loop. This is only used to highlight the shifted elements.
        int l_num_shifts = 0;
        
        // The operator to use in the while loop's condition.
        CompareEnum l_operator = a_is_ascending ? CompareEnum.G : CompareEnum.L;
        
        /* 
         * The segment of the array from index 'l_index_unsorted_min' to 'a_array.GetSize() - 1' is the unsorted segment
           of the container. 
         * Initially, l_index_unsorted_min is assigned the value 1, meaning that the value at index 0 is assumed to be sorted.
         * After each iteration of this for-loop the size of the unsorted segment is reduced by 1.
        */
        for (int l_index_unsorted_min = 1; l_index_unsorted_min < a_array.GetSize(); ++l_index_unsorted_min)
        {
            // The value to insert in this for-loop iteration is that at the lowest index of the array's unsorted segment.
            l_value_to_insert = a_array.GetValue(l_index_unsorted_min);
            
            // Highlight the value that is to be inserted into the sorted segment.
            a_array.HighlightBar(l_index_unsorted_min, BarColourEnum.Compared);
            
            // The index below that at which l_value_to_insert will be inserted ('m1' means 'minus 1').
            int l_index_of_insert_m1 = l_index_unsorted_min - 1;
     
            /* Shift the elements of the sorted segment ( [0, l_index_unsorted_min - 1] ) that are greated than 
               l_value_to_insert one index/position up, which makes space for l_value_to_insert to be inserted.  */
            while (l_index_of_insert_m1 >= 0 && a_array.Compare(l_index_of_insert_m1, l_operator, l_value_to_insert))
            {
                // Shift the value at index l_index_of_insert_m1 one index up.
                a_array.SetValue(l_index_of_insert_m1 + 1, a_array.GetValue(l_index_of_insert_m1), false);
                
                // Record the shift.
                ++l_num_shifts;
                
                // Decrement the insertion index.
                --l_index_of_insert_m1;
            }
            
            // Insert l_value_to_insert at the insertion index.
            a_array.SetValue(l_index_of_insert_m1 + 1, l_value_to_insert, false);
            
            // Highlight the value that was inserted.
            a_array.SetBarColour(l_index_of_insert_m1 + 1, BarColourEnum.Compared, false);
            
            // Highlight the values that were shifted up to accomodate for the value that was inserted.
            a_array.SetBarRangeColour(l_index_of_insert_m1 + 2, l_index_of_insert_m1 + 1 + l_num_shifts, BarColourEnum.Swapped, true);
            
            // Remove the highlights.
            a_array.SetBarColour(l_index_of_insert_m1 + 1, BarColourEnum.Standard, false);
            a_array.SetBarRangeColour(l_index_of_insert_m1 + 1, l_index_of_insert_m1 + 1 + l_num_shifts, BarColourEnum.Standard, false);
            
            // Clear the number of shifts.
            l_num_shifts = 0;
        }
        
    }

    
}
