
package SortingVisualiser;


/*
 * Description:
     > This program visualises several common sorting algorithms via its GUI. 
     > A notable feature is the ability to step through the sorting processes at the click of a button, allowing the 
       user to better understand how the algorithms operate.
     
 * Author:
     > Brett J Macisaac.
 
 * Coding Conventions:
     > All variable names are composed of lower-case alphanumerical characters and underscores.
     > All variable names are prefixed with 'x_' to indicate its general 'type':
         - 'l_': local.
         - 'f_': field.
         - 's_': static field.
         - 'g_': global.
     
 * Problems:
     > When using merge-sort there are some instances where the largest value in the bar-graph is removed from the screen,
       which causes the layout manager to re-centre the graph in the panel.
       
 * To-do:
     > Add more thorough comments.
*/


public class MainSortingVisualiser 
{
    private static void CreateAndShowGUI() 
    {
        // The number of elements to sort.
        int l_size_bar_graph = 100;
        
        // The width (pixels) of the visual array / bar-graph.
        int l_width_bar_graph = 500;
        
        // The height (pixels) of the visual array / bar-graph.
        int l_height_bar_graph = 500;
        
        (new SortFrame("Sorting Visualiser", l_size_bar_graph, l_width_bar_graph, l_height_bar_graph)).setVisible(true);
    }

    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() 
                {
                    public void run() 
                    {
                        CreateAndShowGUI();
                    }
                    
                });
        
    }
    
}
