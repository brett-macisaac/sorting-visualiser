
package SortingVisualiser;

public abstract class IntArraySorter 
{
    private String f_name;
    
    
    
    public IntArraySorter(String a_name)
    {
        f_name = a_name;
    }
    
    
    
    public String GetName()
    {
        return f_name;
    }
    
    
    
    public abstract void Sort(IntArrayPanel a_array, boolean a_is_ascending);
}
