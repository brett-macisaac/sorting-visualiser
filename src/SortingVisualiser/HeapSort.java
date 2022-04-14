
package SortingVisualiser;

import SortingVisualiser.IntArrayPanel.BarColourEnum;
import Utils.CompareEnum;

public class HeapSort
    extends IntArraySorter
{
    
    public HeapSort()
    {
        super("HeapSort");
    }
    
    
    
    /* Auxiliary to operator() (i.e. auxiliary of the heap sort algorithm) (recursive)
         * Auxiliary of operator() (i.e. auxiliary of the heap sort algorithm).
         * Checks if the binary tree node aIndexOfParent and its direct children are in max-heap form; if they aren't, the
           appropriate values are swapped and this method is called again to ensure all nodes of the subtree beginning at
           aIndexOfParent are in max-heap form.
    
         Notes:
             * (a). Three nodes of the binary tree are considered in each call of this method, these being the one at index
                    aIndexOfParent and its two child nodes. This variable is used to hold the index of the node with the 
                    highest value.
             * (b). These two variables hold the index of the left and right children of the node at aIndexOfParent. For any 
                    node at index i in a binary tree (represented in an array), the indexes of its children can be found 
                    using corresponding equations:
                         * lIndexOfLeftChild = 2*i + 1.
                         * lIndexOfRightChild = 2*i + 2.
             * (c). Before any other comparisons can be made, the magnitude of the index of the child node must be checked to
                    determine whether or not it is within the tree. If the magnitude is higher than the tree's last node, 
                    this will break any algorithm which uses this method as an auxiliary: in addition, a range exception 
                    would be thrown if the index is higher than the last index of the array holding the tree.
             * (d). Values should only be swapped if the value of the node at index aIndexOfParent is less than either of its
                    children (in which case it's in the wrong position).
             * (e). The method must be (recursively) called again to ensure the entire sub-tree beginning at aIndexOfParent 
                    is in max-heap form: that is, the swapping of values may have caused the child nodes of the node at the 
                    swapped index to no longer be in max-heap arrangement.
     */
    public void Sort(IntArrayPanel a_array, boolean a_is_ascending)
    {
        // (a).
        int l_lowest_parent_node = (a_array.GetSize() / 2) - 1;
        for (int i = l_lowest_parent_node; i >= 0; --i)
        {
            if (a_is_ascending)
            {
                MaxHeapify(a_array, a_array.GetSize() - 1, i);
            }
            else
            {
                MinHeapify(a_array, a_array.GetSize() - 1, i);
            }
            
        }

        // (b).
        for (int l_index_of_lastNode = a_array.GetSize() - 1; l_index_of_lastNode >= 0;)
        {
            // (c).
            a_array.Swap(0, l_index_of_lastNode, true);
            
            a_array.SetBarColour(l_index_of_lastNode, BarColourEnum.Sorted, true);

            // (d).
            if (a_is_ascending)
            {
                MaxHeapify(a_array, --l_index_of_lastNode, 0);
            }
            else
            {
                MinHeapify(a_array, --l_index_of_lastNode, 0);
            }
            
        }
        
    }
    
    
    
    /* Auxiliary of Sort 
         * A recursive algorithm that checks if the binary tree node a_index_of_parent and its direct children are in 
           max-heap form; if they aren't, the appropriate values are swapped and this method is called again to ensure 
           all nodes of the subtree beginning at a_index_of_parent are in max-heap form.
    
         Notes:
             * (a). Three nodes of the binary tree are considered in each call of this method, these being the one at index
                    aIndexOfParent and its two child nodes. This variable is used to hold the index of the node with the 
                    highest value.
             * (b). These two variables hold the index of the left and right children of the node at aIndexOfParent. For any 
                    node at index i in a binary tree (represented in an array), the indexes of its children can be found 
                    using corresponding equations:
                         * lIndexOfLeftChild = 2*i + 1.
                         * lIndexOfRightChild = 2*i + 2.
             * (c). Before any other comparisons can be made, the magnitude of the index of the child node must be checked to
                    determine whether or not it is within the tree. If the magnitude is higher than the tree's last node, 
                    this will break any algorithm which uses this method as an auxiliary: in addition, a range exception 
                    would be thrown if the index is higher than the last index of the array holding the tree.
             * (d). Values should only be swapped if the value of the node at index aIndexOfParent is less than either of its
                    children (in which case it's in the wrong position).
             * (e). The method must be (recursively) called again to ensure the entire sub-tree beginning at aIndexOfParent 
                    is in max-heap form: that is, the swapping of values may have caused the child nodes of the node at the 
                    swapped index to no longer be in max-heap arrangement.
     */
    private void MaxHeapify(IntArrayPanel a_array, int a_index_of_last_node, int a_index_of_parent)
    {
        // (a).
        int l_index_of_max_value = a_index_of_parent;

        // (b).
        int l_index_of_left_child = 2 * a_index_of_parent + 1;
        int l_index_of_right_child = 2 * a_index_of_parent + 2;

        if (l_index_of_left_child <= a_index_of_last_node) // (c). If valid index.
        {
            // Reassign the max index if the left child's value is higher than that of its parent.
            if (a_array.Compare(l_index_of_left_child, CompareEnum.G, l_index_of_max_value, true))
            {
                l_index_of_max_value = l_index_of_left_child;
            }

        }

        if (l_index_of_right_child <= a_index_of_last_node) // (c). If valid index.
        {
            // Reassign the max index if the right child's value is higher than that of the current max.
            if (a_array.Compare(l_index_of_right_child, CompareEnum.G, l_index_of_max_value, true))
            {
                l_index_of_max_value = l_index_of_right_child;
            }
            
        }

        if (l_index_of_max_value != a_index_of_parent) // (d).
        {
            // Swap value of current parent with that of its highest-value child (whose value is higher than its). 
            a_array.Swap(l_index_of_max_value, a_index_of_parent, true);

            MaxHeapify(a_array, a_index_of_last_node, l_index_of_max_value); // (e).
        }
        
    }
    
    private void MinHeapify(IntArrayPanel a_array, int a_index_of_last_node, int a_index_of_parent)
    {
        // (a).
        int l_index_of_min_value = a_index_of_parent;

        // (b).
        int l_index_of_left_child = 2 * a_index_of_parent + 1;
        int l_index_of_right_child = 2 * a_index_of_parent + 2;

        if (l_index_of_left_child <= a_index_of_last_node) // (c). If valid index.
        {
            // Reassign the max index if the left child's value is higher than that of its parent.
            if (a_array.Compare(l_index_of_left_child, CompareEnum.L, l_index_of_min_value, true))
            {
                l_index_of_min_value = l_index_of_left_child;
            }

        }

        if (l_index_of_right_child <= a_index_of_last_node) // (c). If valid index.
        {
            // Reassign the max index if the right child's value is higher than that of the current max.
            if (a_array.Compare(l_index_of_right_child, CompareEnum.L, l_index_of_min_value, true))
            {
                l_index_of_min_value = l_index_of_right_child;
            }
            
        }

        if (l_index_of_min_value != a_index_of_parent) // (d).
        {
            // Swap value of current parent with that of its highest-value child (whose value is higher than its). 
            a_array.Swap(l_index_of_min_value, a_index_of_parent, true);

            MinHeapify(a_array, a_index_of_last_node, l_index_of_min_value); // (e).
        }
        
    }
    
}
