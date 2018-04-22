/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import javax.swing.JButton;

/**
 *
 * @author Seko
 */
public class JButtonAZ extends JButton
{

    public static Sort value;

    public JButtonAZ ()
    {
        super ( "A->Z" );
        JButtonAZ.value = Sort.ASC;
        this.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 8, 5 ) );
    }

    public String setDefaultByValue ()
    {
        if ( JButtonAZ.value == Sort.ASC )
        {
            this.setText ( "A->Z" );
        }
        else
        {
            this.setText ( "Z->A" );
        }
        return this.getText ();
    }

    public void change ()
    {
        if ( JButtonAZ.value == Sort.ASC )
        {
            this.setText ( "Z->A" );
            JButtonAZ.value = Sort.DESC;
        }
        else
        {
            this.setText ( "A->Z" );
            JButtonAZ.value = Sort.ASC;
        }
    }

}
