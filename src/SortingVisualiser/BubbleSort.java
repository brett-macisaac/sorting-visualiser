package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class BubbleSort
    extends IntArraySorter
{
    
    public BubbleSort()
    {
        super("BubbleSort");
    }
    
    
    
    @Override
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        int l_index_max = a_array.GetSize() - 1;
        
        boolean l_no_swaps;
        
        boolean l_use_readable = true;
        
        CompareEnum l_operator = a_is_ascending ? CompareEnum.G : CompareEnum.L;
        
        if (l_use_readable)
        {
            for (int l_index_unsorted_upper = l_index_max; l_index_unsorted_upper > 0; --l_index_unsorted_upper)
            {
                l_no_swaps = true;
                
                for (int i = 1; i <= l_index_unsorted_upper; ++i)
                {
                    if (a_array.Compare(i - 1, l_operator, i, true))
                    {
                        a_array.Swap(i - 1, i, true);
                        
                        l_no_swaps = false;
                    }
                    
                }
                
                if (l_no_swaps)
                { 
                    a_array.SetBarRangeColour(0, l_index_unsorted_upper, BarColourEnum.Sorted, true);
                    break; 
                }
                
                a_array.SetBarColour(l_index_unsorted_upper, BarColourEnum.Sorted, true);
            }
            
        }
        else
        {
            for (int i = 0; i <= a_array.GetSize() - 2; ++i)
            {
                l_no_swaps = true;
                
                
                for (int j = 0; j <= a_array.GetSize() - 2 - i; ++j)
                {
                    if (a_array.Compare(j, l_operator, j+1, true))
                    {
                        a_array.Swap(j, j+1, true);
                        
                        l_no_swaps = false;
                    }
                    
                }
                
                if (l_no_swaps)
                { 
                    a_array.SetBarRangeColour(0, a_array.GetSize() - 2 - i + 1, BarColourEnum.Sorted, true);
                    break;
                }
                
                // Indicate which value is in its sorted position.
                a_array.SetBarColour(a_array.GetSize() - 2 - i + 1, BarColourEnum.Sorted, true);
            }
            
        }
        
    }
    
    
}
