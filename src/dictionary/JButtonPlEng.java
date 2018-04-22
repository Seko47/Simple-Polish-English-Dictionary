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
public class JButtonPlEng extends JButton
{

    public static Language value;

    public JButtonPlEng ()
    {
        super ( "PL" );
        JButtonPlEng.value = Language.PL;
        this.setPreferredSize ( InterfaceMainFrame.PercentToFrameSize ( 7.3, 5 ) );
    }

    public String setDefaultByValue ()
    {
        if ( JButtonPlEng.value == Language.PL )
        {
            this.setText ( "PL" );
        }
        else
        {
            this.setText ( "ENG" );
        }
        return this.getText ();
    }

    public void change ()
    {
        if ( JButtonPlEng.value == Language.PL )
        {
            this.setText ( "ENG" );
            JButtonPlEng.value = Language.ENG;
        }
        else
        {
            this.setText ( "PL" );
            JButtonPlEng.value = Language.PL;
        }
    }

}
