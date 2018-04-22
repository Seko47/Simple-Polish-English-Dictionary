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
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Seko
 */
public class InterfaceEditPanel extends JPanel implements ActionReset, Tutorial
{

    private JLabel labelInformation;
    private JButtonPlEng buttonPlEng;
    private JButtonAZ buttonAZ;
    private JScrollPane scrollPaneList;
    private JListEnhanced list;
    private JButton buttonReset;

    private JTextField textFieldSearch;

    private JLabel labelInfoPL;
    private JScrollPane scrollPanePL;
    private JList listPL;
    private JPanel panelCenter;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JLabel labelInfoENG;
    private JScrollPane scrollPaneENG;
    private JList listENG;
    private JTextField textFieldTypeInWord;
    private JComboBox comboBoxChoice;
    private JButton buttonExecute;

    public InterfaceEditPanel ()
    {
        super ();

        initComponent ();

        addAction ();
    }

    private void initComponent ()
    {
        this.list = new JListEnhanced ( FileOperations.getAllTranslations ().toArray () );
        this.scrollPaneList = new JScrollPane ( this.list );
        this.textFieldSearch = new JTextField ();
        this.textFieldSearch.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );

        this.scrollPaneList.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 93, 73 ) );

        this.listPL = new JList ();
        this.labelInfoPL = new JLabel ( "Polish meanings" );
        this.scrollPanePL = new JScrollPane ( this.listPL );
        this.scrollPanePL.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 30, 75 ) );
        this.panelLeft = new JPanel ();
        this.panelLeft.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 30, 80 ) );
        this.panelLeft.add ( this.labelInfoPL );
        this.panelLeft.add ( this.scrollPanePL );

        this.panelCenter = new JPanel ();
        this.panelCenter.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 30, 35 ) );
        this.textFieldTypeInWord = new JTextField ();
        this.textFieldTypeInWord.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 25, 5 ) );

        this.listENG = new JList ();
        this.labelInfoENG = new JLabel ( "English meanings" );
        this.scrollPaneENG = new JScrollPane ( this.listENG );
        this.scrollPaneENG.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 30, 75 ) );
        this.panelRight = new JPanel ();
        this.panelRight.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 30, 80 ) );
        this.panelRight.add ( this.labelInfoENG );
        this.panelRight.add ( this.scrollPaneENG );

        String[] choice =
        {
            "Add Polish meaning",
            "Add English meaning"
        };

        this.comboBoxChoice = new JComboBox ( choice );
        this.buttonExecute = new JButton ( "Execute" );
        this.labelInformation = new JLabel ( "Double-clicking will edit the translation.          Sorting: " );
        this.labelInformation.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 53, 5 ) );
        this.buttonPlEng = new JButtonPlEng ();
        this.buttonAZ = new JButtonAZ ();
        this.buttonReset = new JButton ( "Reset" );

        this.add ( this.labelInformation );
        this.add ( this.buttonPlEng );
        this.add ( this.buttonAZ );
        this.add ( this.scrollPaneList );

        this.panelCenter.add ( this.comboBoxChoice );
        this.panelCenter.add ( this.textFieldTypeInWord );
        this.panelCenter.add ( this.buttonExecute );

        this.add ( this.panelLeft );
        this.add ( this.panelCenter );
        this.add ( this.panelRight );

        this.add ( this.textFieldSearch );
        this.add ( this.buttonReset );
        setVisibleList ();
    }

    private void setVisibleList ()
    {
        this.labelInformation.setVisible ( true );
        this.scrollPaneList.setVisible ( true );
        this.textFieldSearch.setVisible ( true );
        this.buttonAZ.setVisible ( true );
        this.buttonPlEng.setVisible ( true );
        this.buttonReset.setVisible ( true );

        this.panelLeft.setVisible ( false );
        this.panelCenter.setVisible ( false );
        this.panelRight.setVisible ( false );
    }

    private void setVisibleEdit ()
    {
        this.labelInformation.setVisible ( false );
        this.buttonAZ.setVisible ( false );
        this.buttonPlEng.setVisible ( false );
        this.scrollPaneList.setVisible ( false );
        this.textFieldSearch.setVisible ( false );

        this.panelLeft.setVisible ( true );
        this.panelCenter.setVisible ( true );
        this.panelRight.setVisible ( true );
        this.buttonReset.setVisible ( true );
    }

    private void addAction ()
    {
        this.buttonReset.addActionListener ( ( ActionEvent evt ) ->
        {
            actionReset ();
        } );

        this.list.addMouseListener ( new MouseAdapter ()
        {

            @Override
            public void mouseClicked ( MouseEvent evt )
            {
                if ( evt.getClickCount () == 2 )
                {
                    actionChoice ();
                }
            }

        } );

        this.buttonExecute.addActionListener ( ( ActionEvent evt ) ->
        {
            actionExecute ();
        } );

        this.textFieldTypeInWord.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyPressed ( KeyEvent evt )
            {
                if ( evt.getKeyCode () == KeyEvent.VK_ENTER )
                {
                    actionExecute ();
                }
            }

        } );

        this.textFieldSearch.addKeyListener ( new KeyAdapter ()
        {

            @Override
            public void keyReleased ( KeyEvent evt )
            {
                actionSearch ();
            }

        } );

        this.listPL.addMouseListener ( new MouseAdapter ()
        {

            @Override
            public void mouseClicked ( MouseEvent evt )
            {
                if ( evt.getClickCount () == 2 )
                {
                    actionEditOrDelete ( Language.PL );
                }
            }

        } );

        this.listENG.addMouseListener ( new MouseAdapter ()
        {

            @Override
            public void mouseClicked ( MouseEvent evt )
            {
                if ( evt.getClickCount () == 2 )
                {
                    actionEditOrDelete ( Language.ENG );
                }
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
            this.list.setListData ( FileOperations.getTranslationWithPhrase ( phrase ) );
        }
        else
        {
            this.list.setListData ( FileOperations.getAllTranslations ().toArray () );
        }
    }

    private void actionExecute ()
    {
        String word = this.textFieldTypeInWord.getText ().toLowerCase ( new Locale ( "pl", "PL" ) );
        if ( word.length () > 0 )
        {
            int index = this.comboBoxChoice.getSelectedIndex ();
            Translation translation = new Translation ( this.list.getSelectedValue ().toString () );

            int ID = this.list.getSelectedIndex ();

            switch ( index )
            {
                case 0:
                {
                    if ( translation.isExists ( word ) != 1 )
                    {
                        translation.addPL ( word );
                        FileOperations.updateTranslation ( translation );
                        JListEnhanced.wasChange ();
                        this.textFieldTypeInWord.setText ( "" );
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( this, "This meaning already exists" );
                    }
                    break;
                }
                case 1:
                {
                    if ( translation.isExists ( word ) != 2 )
                    {
                        translation.addENG ( word );
                        FileOperations.updateTranslation ( translation );
                        JListEnhanced.wasChange ();
                        this.textFieldTypeInWord.setText ( "" );
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( this, "This meaning already exists" );
                    }
                    break;
                }
            }
            this.listPL.setListData ( translation.getPL () );
            this.listENG.setListData ( translation.getENG () );
            updateList ();
            this.list.setSelectedIndex ( ID );
        }
        else
        {
            JOptionPane.showMessageDialog ( this, "First enter the word" );
        }
    }

    private void actionEditOrDelete ( Language language )
    {
        try
        {
            String[] buttons =
            {
                "Nothing", "Edit", "Delete"
            };
            int choice = JOptionPane.showOptionDialog ( this, "What you want to do?", "What you want to do?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[ 0 ] );

            Translation translation = new Translation ( this.list.getSelectedValue ().toString () );

            int ID = this.list.getSelectedIndex ();

            switch ( language )
            {
                case PL:
                {
                    String text = this.listPL.getSelectedValue ().toString ();
                    switch ( choice )
                    {
                        case 0:
                        {
                            return;
                        }
                        case 1:
                        {
                            String newValue = null;
                            boolean correct = true;
                            do
                            {
                                correct = true;

                                newValue = JOptionPane.showInputDialog ( this, "Provide a new meaning for the selected word.", "Change the old meaning.", JOptionPane.DEFAULT_OPTION, null, null, text ).toString ().toLowerCase ( new Locale ( "pl", "PL" ) );

                                if ( newValue.length () <= 0 )
                                {
                                    JOptionPane.showMessageDialog ( this, "You must write something!" );
                                    correct = false;
                                }
                                else if ( translation.isExists ( newValue ) == 1 && newValue.equalsIgnoreCase ( text ) == false )
                                {
                                    JOptionPane.showMessageDialog ( this, "Such a word already exists. Provide others." );
                                    correct = false;
                                }
                            }
                            while ( correct == false );
                            translation.updatePL ( text, newValue );
                            FileOperations.updateTranslation ( translation );
                            JListEnhanced.wasChange ();
                            this.listPL.setListData ( translation.getPL () );

                            break;
                        }
                        case 2:
                        {
                            if ( translation.getPL ().length <= 1 )
                            {
                                JOptionPane.showMessageDialog ( this, "Only one meaning remains. To delete, use the \"Delete\" tab." );
                                return;
                            }
                            if ( translation.isExists ( text ) == 1 )
                            {
                                translation.delPL ( text );
                                FileOperations.updateTranslation ( translation );
                                JListEnhanced.wasChange ();
                                this.listPL.setListData ( translation.getPL () );
                            }
                            break;
                        }
                    }
                    updateList ();
                    this.list.setSelectedIndex ( ID );
                    break;
                }
                case ENG:
                {
                    String text = this.listENG.getSelectedValue ().toString ();
                    switch ( choice )
                    {
                        case 0:
                        {
                            return;
                        }
                        case 1:
                        {
                            String newValue = null;
                            boolean correct = true;
                            do
                            {
                                correct = true;

                                newValue = JOptionPane.showInputDialog ( this, "Provide a new meaning for the selected word.", "Change the old meaning.", JOptionPane.DEFAULT_OPTION, null, null, text ).toString ().toLowerCase ( new Locale ( "pl", "PL" ) );

                                if ( newValue.length () <= 0 )
                                {
                                    JOptionPane.showMessageDialog ( this, "You must write something!" );
                                    correct = false;
                                }
                                else if ( translation.isExists ( newValue ) == 2 && newValue.equalsIgnoreCase ( text ) == false )
                                {
                                    JOptionPane.showMessageDialog ( this, "Such a word already exists. Provide others." );
                                    correct = false;
                                }
                            }
                            while ( correct == false );
                            translation.updateENG ( text, newValue );
                            FileOperations.updateTranslation ( translation );
                            JListEnhanced.wasChange ();
                            this.listENG.setListData ( translation.getENG () );
                            break;
                        }
                        case 2:
                        {
                            if ( translation.getENG ().length <= 1 )
                            {
                                JOptionPane.showMessageDialog ( this, "Only one meaning remains. To delete, use the \"Delete\" tab." );
                                return;
                            }
                            if ( translation.isExists ( text ) == 2 )
                            {
                                translation.delENG ( text );
                                FileOperations.updateTranslation ( translation );
                                JListEnhanced.wasChange ();
                                this.listENG.setListData ( translation.getENG () );
                            }
                            else
                            {
                                JOptionPane.showMessageDialog ( this, "To znaczenie nie istnieje" );
                            }
                            break;
                        }
                    }
                    updateList ();
                    this.list.setSelectedIndex ( ID );
                    break;
                }
            }
        }
        catch ( NullPointerException e )
        {
            System.out.println ( e.getMessage () );
        }
    }

    private void actionChoice ()
    {
        Translation translation = new Translation ( this.list.getSelectedValue ().toString () );
        if ( translation.getID () == 0 )
        {
            return;
        }
        setVisibleEdit ();
        this.listPL.setListData ( translation.getPL () );
        this.listENG.setListData ( translation.getENG () );
    }

    private void updateList ()
    {
        if ( JListEnhanced.wasChangeByEdit == true ||  ! this.buttonAZ.getText ().equalsIgnoreCase ( this.buttonAZ.setDefaultByValue () ) ||  ! this.buttonPlEng.getText ().equalsIgnoreCase ( this.buttonPlEng.setDefaultByValue () ) )
        {
            this.list.setListData ( FileOperations.getAllTranslations ().toArray () );
            JListEnhanced.wasChangeByEdit = false;
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
        if ( Dictionary.FIRST_TIME[ 3 ] == true )
        {
            JOptionPane.showMessageDialog ( this, "Hello!\nThis panel is used to edit existing words.\nJust double-click to go to editing.", "Tip 3", JOptionPane.INFORMATION_MESSAGE );
            FileOperations.setSetting ( 3, false );
        }
    }

    @Override
    public void actionReset ()
    {
        updateList ();
        this.textFieldTypeInWord.setText ( "" );
        this.comboBoxChoice.setSelectedIndex ( 0 );
        if ( this.textFieldSearch.getText ().length () > 0 )
        {
            JListEnhanced.wasChangeByEdit = true;
        }
        this.textFieldSearch.setText ( "" );
        this.textFieldSearch.requestFocus ();
        this.buttonAZ.setDefaultByValue ();
        this.buttonPlEng.setDefaultByValue ();
        setVisibleList ();
    }

}
