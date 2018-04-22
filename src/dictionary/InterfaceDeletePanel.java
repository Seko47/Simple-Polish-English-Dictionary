/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Seko
 */
public class InterfaceDeletePanel extends JPanel implements ActionReset, Tutorial
{

    private JLabel labelInformation;
    private JButtonPlEng buttonPlEng;
    private JButtonAZ buttonAZ;
    private JScrollPane scrollPaneList;
    private JListEnhanced listTranslation;
    private JButton buttonReset;

    private JTextField textFieldSearch;

    public InterfaceDeletePanel ()
    {
        super ();

        initComponent ();

        addAction ();
    }

    private void initComponent ()
    {
        this.labelInformation = new JLabel ( "Double-clicking will remove the translation.    Sorting: " );
        this.labelInformation.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 53, 5 ) );
        this.buttonPlEng = new JButtonPlEng ();
        this.buttonAZ = new JButtonAZ ();
        this.buttonReset = new JButton ( "Reset" );

        this.listTranslation = new JListEnhanced ( FileOperations.getAllTranslations ().toArray () );
        this.scrollPaneList = new JScrollPane ( this.listTranslation );

        this.textFieldSearch = new JTextField ();
        this.textFieldSearch.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );

        this.add ( this.labelInformation );
        this.add ( this.buttonPlEng );
        this.add ( this.buttonAZ );
        this.scrollPaneList.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 93, 73 ) );
        this.add ( this.scrollPaneList );
        this.add ( this.textFieldSearch );
        this.add ( this.buttonReset );
    }

    private void addAction ()
    {
        this.listTranslation.addMouseListener ( new MouseAdapter ()
        {

            @Override
            public void mouseClicked ( MouseEvent evt )
            {
                if ( evt.getClickCount () == 2 )
                {
                    actionDelete ();
                }
            }

        } );

        this.buttonReset.addActionListener ( ( ActionEvent evt ) ->
        {
            actionReset ();
        } );

        this.textFieldSearch.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyReleased ( KeyEvent evt )
            {
                actionSearch ();
            }

        } );

        this.buttonPlEng.addActionListener ( ( ActionEvent evt ) ->
        {
            changeButtonPlEng ();
        } );

        this.buttonAZ.addActionListener ( ( ActionEvent evt ) ->
        {
            changeButtonAZ ();
        } );
    }

    private void changeButtonPlEng ()
    {
        this.buttonPlEng.change ();
        sortList ();
        actionReset ();
    }

    private void changeButtonAZ ()
    {
        this.buttonAZ.change ();
        sortList ();
        actionReset ();
    }

    private void actionSearch ()
    {
        String phrase = this.textFieldSearch.getText ();
        if ( phrase.length () > 0 )
        {
            this.listTranslation.setListData ( FileOperations.getTranslationWithPhrase ( phrase ) );
        }
        else
        {
            this.listTranslation.setListData ( FileOperations.getAllTranslations ().toArray () );
        }
    }

    private void actionDelete ()
    {
        Translation translation = new Translation ( this.listTranslation.getSelectedValue ().toString () );
        if ( translation.getID () == 0 )
        {
            return;
        }
        String[] dialogOptions =
        {
            "Yes", "No"
        };

        int choice = JOptionPane.showOptionDialog ( this, "Are you sure you want to delete this translation?", "Deleting", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogOptions, dialogOptions[ 1 ] );

        if ( choice == 0 )
        {
            if ( FileOperations.deleteTranslation ( translation ) == true )
            {
                JListEnhanced.wasChange ();
                JOptionPane.showMessageDialog ( this, "The translation has been deleted" );
            }
            else
            {
                JOptionPane.showMessageDialog ( this, "An error occurred while deleting" );
            }
        }
        updateList ();
    }

    private void updateList ()
    {
        if ( JListEnhanced.wasChangeByDelete == true ||  ! this.buttonAZ.getText ().equalsIgnoreCase ( this.buttonAZ.setDefaultByValue () ) ||  ! this.buttonPlEng.getText ().equalsIgnoreCase ( this.buttonPlEng.setDefaultByValue () ) )
        {
            this.listTranslation.setListData ( FileOperations.getAllTranslations ().toArray () );
            JListEnhanced.wasChangeByDelete = false;
        }
    }

    private void sortList ()
    {
        this.buttonPlEng.setDefaultByValue ();
        this.buttonAZ.setDefaultByValue ();
        FileOperations.sortTranslation ( JButtonAZ.value, JButtonPlEng.value );
        JListEnhanced.wasChange ();
    }

    @Override
    public void tutorial ()
    {
        if ( Dictionary.FIRST_TIME[ 2 ] == true )
        {
            JOptionPane.showMessageDialog ( this, "Hello!\nThis panel is used to delete already existing translations.\nJust double-click to delete.", "Tip 2", JOptionPane.INFORMATION_MESSAGE );
            FileOperations.setSetting ( 2, false );
        }
    }

    @Override
    public void actionReset ()
    {
        updateList ();
        if ( this.textFieldSearch.getText ().length () > 0 )
        {
            JListEnhanced.wasChangeByDelete = true;
        }
        this.textFieldSearch.setText ( "" );
        this.textFieldSearch.requestFocus ();
        this.buttonAZ.setDefaultByValue ();
        this.buttonPlEng.setDefaultByValue ();
    }

}
