package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class SelectionSort 
    extends IntArraySorter
{
    
    public SelectionSort()
    {
        super("SelectionSort");
    }
    
    
    
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        int l_index_max = a_array.GetSize() - 1;
        
        boolean l_use_readable = true;
        
        CompareEnum l_operator = a_is_ascending ? CompareEnum.G : CompareEnum.L;
        
        int l_index_target;
        
        if (l_use_readable)
        {
            for (int l_index_unsorted_upper = l_index_max; l_index_unsorted_upper > 0; --l_index_unsorted_upper)
            {
                l_index_target = 0;
                
                for (int i = 1; i <= l_index_unsorted_upper; ++i)
                {
                    if (a_array.Compare(i, l_operator, l_index_target, true))
                    {
                        l_index_target = i;
                    }
                    
                }
                
                a_array.Swap(l_index_target, l_index_unsorted_upper, true);
                
                a_array.SetBarColour(l_index_unsorted_upper, BarColourEnum.Sorted, true);
            }
            
        }
        else
        {
            
            for (int i = 0; i <= l_index_max - 1; ++i)
            {
                l_index_target = 0;

                for (int j = 1; j <= l_index_max - i; ++j)
                {
                    if (a_array.Compare(j, l_operator, l_index_target, true))
                    {
                        l_index_target = j;
                    }
                    
                }
                
                a_array.Swap(l_index_target, l_index_max - i, true);
                
                a_array.SetBarColour(l_index_max - i, BarColourEnum.Sorted, true);
            }
            
        }

    }
    
}
