/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Seko
 */
public class InterfaceAddPanel extends JPanel implements ActionReset, Tutorial
{

    private JPanel panelHolder;
    private JPanel panelPL;
    private JPanel panelENG;
    private JPanel panelButton;

    private JLabel labelTypeInWordPL;
    private JTextField textFieldTypeInWordPL;
    private JLabel labelTypeInWordENG;
    private JTextField textFieldTypeInWordENG;

    private JButton buttonAdd;
    private JButton buttonReset;

    public InterfaceAddPanel ()
    {
        super ();

        initComponent ();

        addAction ();
    }

    private void initComponent ()
    {
        this.panelHolder = new JPanel ();
        this.panelHolder.setLayout ( new BoxLayout ( this.panelHolder, BoxLayout.PAGE_AXIS ) );
        this.panelPL = new JPanel ();
        this.panelPL.setLayout ( new GridLayout () );
        this.panelENG = new JPanel ();
        this.panelENG.setLayout ( new GridLayout () );
        this.panelButton = new JPanel ();

        this.labelTypeInWordPL = new JLabel ( "Type in a word in Polish:" );
        this.textFieldTypeInWordPL = new JTextField ();
        this.textFieldTypeInWordPL.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );

        this.labelTypeInWordENG = new JLabel ( "Type in a word in English:" );
        this.textFieldTypeInWordENG = new JTextField ();
        this.textFieldTypeInWordENG.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );

        this.buttonAdd = new JButton ( "Add" );
        this.buttonReset = new JButton ( "Reset" );

        this.panelPL.add ( this.labelTypeInWordPL );
        this.panelPL.add ( this.textFieldTypeInWordPL );
        this.panelENG.add ( this.labelTypeInWordENG );
        this.panelENG.add ( this.textFieldTypeInWordENG );

        this.panelButton.add ( this.buttonAdd );
        this.panelButton.add ( this.buttonReset );

        this.panelHolder.add ( this.panelPL );
        this.panelHolder.add ( this.panelENG );
        this.panelHolder.add ( this.panelButton );

        this.add ( this.panelHolder );
    }

    private void addAction ()
    {
        this.buttonAdd.addActionListener ( ( ActionEvent evt ) ->
        {
            actionAdd ();
        } );

        this.buttonReset.addActionListener ( ( ActionEvent evt ) ->
        {
            actionReset ();
        } );

        this.textFieldTypeInWordPL.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyPressed ( KeyEvent evt )
            {
                if ( evt.getKeyCode () == KeyEvent.VK_ENTER )
                {
                    actionAdd ();
                }
            }

        } );

        this.textFieldTypeInWordENG.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyPressed ( KeyEvent evt )
            {
                if ( evt.getKeyCode () == KeyEvent.VK_ENTER )
                {
                    actionAdd ();
                }
            }

        } );
    }

    private void actionAdd ()
    {
        if ( this.textFieldTypeInWordPL.getText ().length () > 0 && this.textFieldTypeInWordENG.getText ().length () > 0 )
        {
            String[] tab = FileOperations.findTranslation ( this.textFieldTypeInWordPL.getText () );
            if ( tab == null )
            {
                tab = FileOperations.findTranslation ( this.textFieldTypeInWordENG.getText () );
            }
            if ( tab != null )
            {
                JOptionPane.showMessageDialog ( this, "The entered word is already in the dictionary. If you want to edit the translation, go to the \"Edit\" tab" );
            }
            else
            {
                FileOperations.saveNewTranslation ( this.textFieldTypeInWordPL.getText ().toLowerCase ( new Locale ( "pl", "PL" ) ), this.textFieldTypeInWordENG.getText ().toLowerCase ( new Locale ( "pl", "PL" ) ) );
                JOptionPane.showMessageDialog ( this, "Added to the dictionary. To add more meanings, go to the \"Edit\" tab" );
                JListEnhanced.wasChange ();
                actionReset ();
            }
        }
        else
        {
            JOptionPane.showMessageDialog ( this, "Complete both fields!" );
        }
    }

    @Override
    public void tutorial ()
    {
        if ( Dictionary.FIRST_TIME[ 1 ] == true )
        {
            JOptionPane.showMessageDialog ( this, "Hello!\nThis panel is for adding words.\nJust enter the meaning of Polish, English and click ENTER or ADD button.", "Tip 2", JOptionPane.INFORMATION_MESSAGE );
            FileOperations.setSetting ( 1, false );
        }
    }

    @Override
    public void actionReset ()
    {
        this.textFieldTypeInWordPL.setText ( "" );
        this.textFieldTypeInWordPL.requestFocus ();
        this.textFieldTypeInWordENG.setText ( "" );
    }

}
