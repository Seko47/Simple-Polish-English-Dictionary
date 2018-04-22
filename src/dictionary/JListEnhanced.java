/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import javax.swing.JList;

/**
 *
 * @author Seko
 */
public class JListEnhanced extends JList
{

    public static boolean wasChangeByDelete = true;
    public static boolean wasChangeByEdit = true;

    public JListEnhanced ( Object[] o )
    {
        super ( o );
    }

    public static void wasChange ()
    {
        JListEnhanced.wasChangeByDelete = true;
        JListEnhanced.wasChangeByEdit = true;
    }

}
