/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.awt.EventQueue;

/**
 *
 * @author Seko
 */
public class Dictionary
{

    public static boolean[] FIRST_TIME =
    {
        true, true, true, true
    };

    public static final String STARTINFO = "<HTML><BODY>Author: <b>Rafał Sekular</b><br><br>"
            + "Simple Polish-English Dictionary. The program fulfills the functions of a simple database.<br>"
            + "<ul><li>Data stored in a text file (writing and reading)</li>"
            + "<li>Browsing the contents of the database</li>"
            + "<li>Adding a record at the end of the database</li>"
            + "<li>Modification of the selected record</li>"
            + "<li>Deleting the selected record</li>"
            + "<li>Search for records according to the given criterion</li>"
            + "<li>Sorting records according to the selected criterion</li></ul>"
            + "The word can have many meanings.</BODY></HTML>";

    /**
     * @param args the command line arguments
     */
    public static void main ( String[] args )
    {
        Dictionary.FIRST_TIME = FileOperations.getSettings ();

        EventQueue.invokeLater ( () ->
        {
            InterfaceMainFrame window = new InterfaceMainFrame ();
            window.actionReset ();
        } );
    }

}
