

package Utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonMaker 
{
    public static JButton CreateButton( String aTitle, ActionListener aListener, Font aFont )
    {
        return CreateButton( aTitle, aListener, null, aFont );
    }

    public static JButton CreateButton( String aTitle, ActionListener aListener, Dimension aSize, Font aFont )
    {
        JButton Result = new JButton( aTitle );

        if ( aSize != null )
        {
            Result.setPreferredSize( aSize );
        }

        if ( aFont != null )
        {
            Result.setFont( aFont );            
        }
        
        Result.addActionListener( aListener );
        
        return Result;
    }

    public static JButton CreateButton( String aTitle, ActionListener aListener, Dimension aSize )
    {
        return CreateButton( aTitle, aListener, aSize, null );
    }

    public static JButton CreateButton( String aTitle, ActionListener aListener, int aHeight )
    {
        JButton Result = new JButton( aTitle );
        Dimension lSize = Result.getPreferredSize();
        
        lSize.height = aHeight;
        
        Result.setPreferredSize( lSize );
        Result.setFont( new Font( "Arial", Font.BOLD, 24 ) );
        Result.addActionListener( aListener );
        
        return Result;
    }

    public static JButton CreateButton( String aTitle, ActionListener aListener )
    {
        return CreateButton( aTitle, aListener, null, null );
    }
    
}
