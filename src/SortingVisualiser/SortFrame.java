
package SortingVisualiser;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollBar;

import Utils.ButtonMaker;
import Utils.Label;
import Utils.TextValueLabel;



public class SortFrame
    extends JFrame
        implements Runnable
{
    private IntArrayPanel f_visual_array;
    
    private TextValueLabel f_lbl_count_accesses;
    
    private TextValueLabel f_lbl_count_writes;
    
    private IntArraySorter f_sorters[];
    
    private JButton f_btn_sort;
    
    private JButton f_btn_step;
    
    private JButton f_btn_shuffle;
    
    private JScrollBar f_scr_sort_rate;
    
    private JCheckBox f_chk_step;
    
    private JCheckBox f_chk_ascending;
    
    private JComboBox<String> f_cmb_sorters;
    
    private Thread f_thread_sort;
    
    
    // The number of classes that extend (are children of) IntArraySorter.
    private static final int S_NUM_SORTERS = 10;
    
    private static final int S_MAX_SORT_RATE = 1;
    
    private static final int S_MIN_SORT_RATE = 1000;
    
    private static final int S_INCREMENT_SORT_RATE = 1;
    
    private static final long serialVersionUID = 1L;
    
    
    
    public SortFrame(String a_title, int a_size_bar_graph, int a_width_bar_graph, int a_height_bar_graph)
    {
        super(a_title);
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //super.setBackground(Color.BLACK);
        
        // Ensure the frame exits when the user closes the window (i.e. clicks the 'cross' button).
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ensure that the user can't change the frame's size.
        super.setResizable(false);
        
        // Align the window to the center of the screen (top left corner in centre of screen).
        super.setLocationRelativeTo(null); 
        
        super.getContentPane().setLayout(new GridBagLayout());
        
        // Create a constraints object, which allows for the various graphical elements to be arranged.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Set general constraints.
        l_constraints.fill = GridBagConstraints.HORIZONTAL; // Objects take up all available horizontal space.
        
        // Create the bar graph.
        f_visual_array = new IntArrayPanel(a_size_bar_graph, a_width_bar_graph, a_height_bar_graph, 
                                           Color.BLACK, new Color(210, 210, 210), Color.RED, Color.GREEN, Color.WHITE, this);
        
        // Create the count labels.
        int l_width_count_labels = (int)( a_width_bar_graph * 1.0 );
        int l_height_count_labels = (int)( a_width_bar_graph * 0.05 );
        if (l_width_count_labels < 10) { l_width_count_labels = 10; }
        if (l_height_count_labels < 10) { l_height_count_labels = 10; }
        f_lbl_count_accesses = new TextValueLabel("Accesses", 0, Label.Alignment.Left, l_width_count_labels, l_height_count_labels);
        f_lbl_count_writes = new TextValueLabel("Writes", 0, Label.Alignment.Left, l_width_count_labels, l_height_count_labels);
        
        
        // Create the 'sort' button.
        f_btn_sort = ButtonMaker.CreateButton("Sort", e -> EvtHnd_Sort());
        f_btn_sort.setEnabled(true);
        
        // Create the 'step' button.
        f_btn_step = ButtonMaker.CreateButton("Step", e -> EvtHnd_Step());
        f_btn_step.setEnabled(false);
        
        // Create the 'shuffle' button.
        f_btn_shuffle = ButtonMaker.CreateButton("Shuffle", e -> EvtHnd_Shuffle());
        f_btn_shuffle.setEnabled(true);
        
        // Create the 'step' checkbox.
        f_chk_step = new JCheckBox("Step", false);
        f_chk_step.addActionListener(e -> EvtHnd_StepCheckBox());
        
        // Create the 'ascending' checkbox.
        f_chk_ascending = new JCheckBox("Ascending", true);
        
        // Create the scrollbar.
        f_scr_sort_rate = new JScrollBar(JScrollBar.HORIZONTAL, 100, S_INCREMENT_SORT_RATE, S_MAX_SORT_RATE, S_MIN_SORT_RATE);
        
        // Create the sorting objects.
        f_sorters = new IntArraySorter[S_NUM_SORTERS];
        f_sorters[0] = new BubbleSort();
        f_sorters[1] = new CocktailShakerSort();
        f_sorters[2] = new SelectionSort();
        f_sorters[3] = new InsertionSort();
        f_sorters[4] = new QuickSort();
        f_sorters[5] = new QuickSort_RandomPivot();
        f_sorters[6] = new MergeSort();
        f_sorters[7] = new MergeSortIterative();
        f_sorters[8] = new HeapSort();
        f_sorters[9] = new CountingSort();
        
        // Create and populate the combo-box.
        f_cmb_sorters = new JComboBox<String>();
        for (IntArraySorter i : f_sorters)
        { f_cmb_sorters.addItem(i.GetName()); }
        f_cmb_sorters.setSelectedIndex(0);
        
        // Add the combobox to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0)
        super.add(f_cmb_sorters, l_constraints);
        
        // Add the count labels to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1)
        super.add(f_lbl_count_accesses, l_constraints);
        l_constraints.gridx = 0; l_constraints.gridy = 2; // (0,2)
        super.add(f_lbl_count_writes, l_constraints);
        
        // Add the bar graph to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 3; // (0,1)
        super.add(f_visual_array, l_constraints);
        
        // Add the sort button to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 4; // (0,2)
        super.add(f_btn_sort, l_constraints);
        
        // Add the SHUFFLE button to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 5; // (0,3)
        super.add(f_btn_shuffle, l_constraints);
        
        // Add the step button to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 6; // (0,4)
        super.add(f_btn_step, l_constraints);
        
        // Add the scrollbar to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 7; // (0,5)
        super.add(f_scr_sort_rate, l_constraints);
        
        l_constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        
        // Add the 'step' checkbox to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 8; // (0,6)
        super.add(f_chk_step, l_constraints);
        
        // Add the 'ascending' checkbox to the frame.
        l_constraints.gridx = 0; l_constraints.gridy = 9; // (0,7)
        super.add(f_chk_ascending, l_constraints);

        // Force layout manager to place GUI elements.
        super.pack();
    }
    
    
    @Override
    public void run()
    {
        // Get the index of the sorter selected by the user (using the combo-box).
        int l_index_sorters = 0;
        for (int i = 0; i < f_sorters.length; ++i)
        {
            if (f_sorters[i].GetName() == f_cmb_sorters.getSelectedItem())
            {
                l_index_sorters = i;
                break;
            }
            
        }
        
        // Sort the bar graph.
        try 
        { f_sorters[l_index_sorters].Sort(f_visual_array, f_chk_ascending.isSelected()); } 
        catch (IndexOutOfBoundsException e) 
        { e.printStackTrace(); }
        
        // Return the sort and shuffle controls.
        f_btn_sort.setEnabled(true);
        f_btn_shuffle.setEnabled(true);
        
        f_thread_sort = null;
    }
    
    public void IncrementCount(boolean a_access)
    {
        if (a_access)
        {
            f_lbl_count_accesses.Increment();
        }
        else
        {
            f_lbl_count_writes.Increment();
        }
        
    }
    
    public int GetSortRate()
    {
        return f_scr_sort_rate.getValue();
    }
    
    public boolean IsStepping()
    {
        return f_chk_step.isSelected();
    }
    
    
    /* Event Handler of f_btn_sort
    */
    private void EvtHnd_Sort()
    {
        // The array cannot be sorted if it's already being sorted.
        f_btn_sort.setEnabled(false);
        
        // Shouldn't be able to shuffle the array mid-sort.
        f_btn_shuffle.setEnabled(false);
        
        // Reset the graph's colours.
        f_visual_array.ResetColours();
        
        // Reset the count labels' values.
        ResetCounts();
        
        // This should already be the case.
        //f_btn_step.setEnabled(f_chk_step.isSelected());
        
        f_thread_sort = new Thread(this);
        f_thread_sort.start();
    }
    
    private void EvtHnd_Shuffle()
    {
        f_visual_array.Shuffle();
        ResetCounts();
    }
    
    private void EvtHnd_Step()
    {
        if (f_thread_sort != null)
        {
            synchronized (this) 
            {
                // Awake the thread so that the sorting can continue.
                this.notifyAll();
            }
        }
        else
        {
            // Redirect to the event handler of f_btn_sort (i.e. f_btn_step essentially becomes f_btn_sort).
            EvtHnd_Sort();
        }

    }
    
    private void EvtHnd_StepCheckBox()
    {
        // The step button should only be available when in the 'step' mode.
        f_btn_step.setEnabled(f_chk_step.isSelected());
        
        // If the checkbox was just unchecked and the sorting thread is active.
        if (!f_chk_step.isSelected() && f_thread_sort != null)
        {
            synchronized (this)
            {
                // Awake the thread so that the sorting can continue.
                this.notifyAll();
            }

        }
        
    }
    
    
    
    private void ResetCounts()
    {
        f_lbl_count_accesses.SetValue(0);
        f_lbl_count_writes.SetValue(0);
    }
   
}
