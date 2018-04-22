/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Seko
 */
public class InterfaceMainFrame extends JFrame implements ActionReset
{

    private static Dimension SCREEN_SIZE;
    private static Dimension FRAME_SIZE;

    private JTabbedPane tabbedPane;
    private InterfaceTranslatePanel translatePanel;
    private InterfaceAddPanel addPanel;
    private InterfaceDeletePanel deletePanel;
    private InterfaceEditPanel editPanel;

    public InterfaceMainFrame ()
    {
        super ();
        Toolkit kit = Toolkit.getDefaultToolkit ();
        SCREEN_SIZE = kit.getScreenSize ();
        FRAME_SIZE = PercentToScreenSize ( 62.5, 75 );

        this.setTitle ( "Dictionary" );
        this.setResizable ( false );
        this.setSize ( FRAME_SIZE );
        this.setLocationByPlatform ( true );
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        this.setLayout ( new GridLayout () );

        initComponent ();

        addAction ();

        this.setVisible ( true );
    }

    public static Dimension PercentToScreenSize ( double percentWidth, double percentHeight )
    {
        int width = ( int ) ( ( SCREEN_SIZE.getWidth () * percentWidth ) / 100 );
        int height = ( int ) ( ( SCREEN_SIZE.getHeight () * percentHeight ) / 100 );

        return new Dimension ( width, height );
    }

    public static Dimension PercentToFrameSize ( double percentWidth, double percentHeight )
    {
        int width = ( int ) ( ( FRAME_SIZE.getWidth () * percentWidth ) / 100 );
        int height = ( int ) ( ( FRAME_SIZE.getHeight () * percentHeight ) / 100 );

        return new Dimension ( width, height );
    }

    public static String Wrap ( String word, boolean isHeader )
    {
        String noweSlowo = "";
        int width = InterfaceMainFrame.PercentToFrameSize ( 73, 100 ).width;
        int iloscZnakow;
        if ( isHeader == true )
        {
            iloscZnakow = ( ( width * 18 ) / 584 );
        }
        else
        {
            iloscZnakow = ( ( width * 25 ) / 584 );
        }

        int ktoryZnak = 1;
        for ( int i = 0; i < word.length ();  ++ i )
        {
            if ( ktoryZnak ++ == 0 )
            {
                noweSlowo += "<br>";
            }
            noweSlowo += word.charAt ( i );
            ktoryZnak %= iloscZnakow;
        }

        return noweSlowo;
    }

    private void initComponent ()
    {
        this.translatePanel = new InterfaceTranslatePanel ();
        this.addPanel = new InterfaceAddPanel ();
        this.deletePanel = new InterfaceDeletePanel ();
        this.editPanel = new InterfaceEditPanel ();

        this.tabbedPane = new JTabbedPane ();
        this.tabbedPane.addTab ( "Translator", null, this.translatePanel, "Word translator" );
        this.tabbedPane.addTab ( "Add", null, this.addPanel, "Add a new translation to the dictionary" );
        this.tabbedPane.addTab ( "Delete", null, this.deletePanel, "Delete the existing translation" );
        this.tabbedPane.addTab ( "Edit", null, this.editPanel, "Edit the existing translation" );

        this.add ( this.tabbedPane );
    }

    private void addAction ()
    {
        this.tabbedPane.addChangeListener ( ( ChangeEvent evt ) ->
        {
            actionReset ();
        } );
    }

    @Override
    public void actionReset ()
    {
        switch ( this.tabbedPane.getSelectedIndex () )
        {
            case 0:
                this.translatePanel.tutorial ();
                break;
            case 1:
                this.addPanel.tutorial ();
                break;
            case 2:
                this.deletePanel.tutorial ();
                break;
            case 3:
                this.editPanel.tutorial ();
                break;
            default:
                break;
        }
        this.translatePanel.actionReset ();
        this.addPanel.actionReset ();
        this.deletePanel.actionReset ();
        this.editPanel.actionReset ();
    }

}
