/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 *
 * @author Seko
 */
public class InterfaceTranslatePanel extends JSplitPane implements ActionReset, Tutorial
{

    private JPanel panelLeft;
    private JLabel labelTypeInWord;
    private JTextField textFieldTypeInWord;
    private JButton buttonTranslate;
    private JButton buttonReset;

    private JScrollPane panelRight;
    private JEditorPane editorPaneTranslation;

    public InterfaceTranslatePanel ()
    {
        super ( JSplitPane.HORIZONTAL_SPLIT );

        initComponent ();

        this.setLeftComponent ( this.panelLeft );
        this.leftComponent.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 27, 100 ) );

        this.setRightComponent ( this.panelRight );
        this.rightComponent.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 73, 100 ) );

        this.setDividerSize ( 0 );

        addAction ();
        this.textFieldTypeInWord.requestFocus ();
    }

    private void initComponent ()
    {
        this.panelLeft = new JPanel ();
        this.labelTypeInWord = new JLabel ( "Type in word:" );
        this.textFieldTypeInWord = new JTextField ();
        this.textFieldTypeInWord.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );
        this.buttonTranslate = new JButton ( "Translate" );
        this.buttonReset = new JButton ( "Reset" );

        this.panelLeft.add ( this.labelTypeInWord );
        this.panelLeft.add ( this.textFieldTypeInWord );
        this.panelLeft.add ( this.buttonTranslate );
        this.panelLeft.add ( this.buttonReset );

        this.editorPaneTranslation = new JEditorPane ();
        this.editorPaneTranslation.setContentType ( "text/html" );
        this.editorPaneTranslation.setText ( Dictionary.STARTINFO );
        this.editorPaneTranslation.setEditable ( false );
        this.panelRight = new JScrollPane ( this.editorPaneTranslation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        this.panelRight.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 73, 100 ) );
    }

    private void addAction ()
    {
        this.buttonTranslate.addActionListener ( ( ActionEvent evt ) ->
        {
            actionTranslate ();
        } );

        this.buttonReset.addActionListener ( ( ActionEvent evt ) ->
        {
            actionReset ();
        } );

        this.textFieldTypeInWord.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyPressed ( KeyEvent evt )
            {
                if ( evt.getKeyCode () == KeyEvent.VK_ENTER )
                {
                    actionTranslate ();
                }
            }

        } );
    }

    private void actionTranslate ()
    {
        if ( this.textFieldTypeInWord.getText ().length () > 0 )
        {
            String[] tab = FileOperations.findTranslation ( this.textFieldTypeInWord.getText () );

            if ( tab != null )
            {
                String text = "<HTML><font color='red' size='25'><b><center>" + InterfaceMainFrame.Wrap ( this.textFieldTypeInWord.getText ().toUpperCase ( new Locale ( "pl", "PL" ) ), true ) + "</center></b></font>"
                        + "<hr><font size='6' color='blue'><ul style='color: blue'>";
                for ( int i = 0; i < tab.length;  ++ i )
                {
                    if ( i < tab.length - 1 )
                    {
                        text += "<li>" + InterfaceMainFrame.Wrap ( tab[ i ].toUpperCase ( new Locale ( "pl", "PL" ) ), false ) + ",</li>";
                    }
                    else
                    {
                        text += "<li>" + InterfaceMainFrame.Wrap ( tab[ i ].toUpperCase ( new Locale ( "pl", "PL" ) ), false ) + "</li></ul></font></html>";
                    }
                }
                this.editorPaneTranslation.setText ( text );
            }
            else
            {
                this.editorPaneTranslation.setText ( "None in the dictionary<br>You can add a word by clicking on the \"Add\" tab." );
            }
        }
        else
        {
            this.editorPaneTranslation.setText ( "The word has not been entered in the field on the left." );
        }
    }

    @Override
    public void tutorial ()
    {
        if ( Dictionary.FIRST_TIME[ 0 ] == true )
        {
            JOptionPane.showMessageDialog ( this, "Hello!\nThis panel is for translating words.\nJust enter the word on the left and click ENTER or TRANSLATE button.\n\nThere is a RESET button on each tab, clicking it will bring the tab back to its initial state.", "Tip 1", JOptionPane.INFORMATION_MESSAGE );
            FileOperations.setSetting ( 0, false );
        }
    }

    @Override
    public void actionReset ()
    {
        this.textFieldTypeInWord.setText ( "" );
        this.textFieldTypeInWord.requestFocus ();
        this.editorPaneTranslation.setText ( Dictionary.STARTINFO );
    }

}
