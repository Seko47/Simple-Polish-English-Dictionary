/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Seko
 */
public class FileOperations
{

    private static final File FILE_SETTINGS = new File ( "dictionarySettings.txt" );
    private static final File FILE = new File ( "dictionary.txt" );
    private static final File FILE_TMP = new File ( "dictionary.txt.backup" );

    private FileOperations ()
    {
    }

    public static String[] findTranslation ( String word )
    {
        String[] foundTranslation = null;

        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) ) )
        {
            String line;

            while ( ( line = in.readLine () ) != null )
            {
                Translation translation = new Translation ( line );

                switch ( translation.isExists ( word ) )
                {
                    case 1:
                    {
                        foundTranslation = translation.getENG ();
                        break;
                    }
                    case 2:
                    {
                        foundTranslation = translation.getPL ();
                        break;
                    }
                }
                if ( foundTranslation != null )
                {
                    break;
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return foundTranslation;
    }

    private static int nextID ()
    {
        int ID = 1;

        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) ) )
        {
            String line;

            while ( ( line = in.readLine () ) != null )
            {
                Translation translation = new Translation ( line );
                if ( translation.getID () >= ID )
                {
                    ID = translation.getID () + 1;
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return ID;
    }

    public static void saveNewTranslation ( String pl, String eng )
    {
        try ( BufferedWriter out = new BufferedWriter ( new FileWriter ( FILE, true ) ) )
        {
            Translation translation = new Translation ( Integer.toString ( nextID () ) + "     :     " + pl + "     :     " + eng );

            if ( translation.getID () == 1 )
            {
                out.append ( translation.toString () );
            }
            else
            {
                out.newLine ();
                out.append ( translation.toString () );
            }
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }
    }

    public static boolean deleteTranslation ( Translation tl )
    {
        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) );
                BufferedWriter out = new BufferedWriter ( new FileWriter ( FILE_TMP ) ) )
        {
            String line;
            boolean first = true;
            int i = 1;

            while ( ( line = in.readLine () ) != null )
            {
                Translation translation = new Translation ( line );

                if ( translation.getID () != tl.getID () )
                {
                    translation.setID ( i ++ );

                    if ( first )
                    {
                        out.write ( translation.toString () );
                        first = false;
                    }
                    else
                    {
                        out.newLine ();
                        out.write ( translation.toString () );
                    }
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        if ( FILE.delete () == false )
        {
            return false;
        }
        if ( FILE_TMP.length () <= 0 )
        {
            FILE_TMP.delete ();
        }
        else if ( FILE_TMP.renameTo ( FILE ) == false )
        {
            return false;
        }

        return true;
    }

    public static ArrayList<Translation> getAllTranslations ()
    {
        ArrayList<Translation> tl = new ArrayList<> ();

        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) ) )
        {
            String line;

            while ( ( line = in.readLine () ) != null )
            {
                tl.add ( new Translation ( line ) );
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return tl;
    }

    public static boolean updateTranslation ( Translation tl )
    {
        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) );
                BufferedWriter out = new BufferedWriter ( new FileWriter ( FILE_TMP ) ) )
        {
            String line;
            boolean first = true;
            int i = 1;

            while ( ( line = in.readLine () ) != null )
            {
                Translation translation = new Translation ( line );

                if ( first )
                {
                    first = false;
                }
                else
                {
                    out.newLine ();
                }
                if ( translation.getID () != tl.getID () )
                {
                    translation.setID ( i ++ );
                    out.write ( translation.toString () );
                }
                else
                {
                    tl.setID ( i ++ );
                    out.write ( tl.toString () );
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
            return false;
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        if ( FILE.delete () == false )
        {
            return false;
        }
        if ( FILE_TMP.length () <= 0 )
        {
            FILE_TMP.delete ();
        }
        else if ( FILE_TMP.renameTo ( FILE ) == false )
        {
            return false;
        }

        return true;
    }

    private static int phrasesAmount ( String phrase )
    {
        int i = 0;
        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) ) )
        {
            String line;
            while ( ( line = in.readLine () ) != null )
            {
                Translation tl = new Translation ( line );
                if ( tl.isPhrase ( phrase ) == true )
                {
                     ++ i;
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return i;
    }

    public static Translation[] getTranslationWithPhrase ( String phrase )
    {
        int i = 0;
        int size = phrasesAmount ( phrase );

        Translation[] translation = new Translation[ size ];

        if ( size <= 0 )
        {
            Translation[] tl = new Translation[ 1 ];
            tl[ 0 ] = new Translation ( "0     :     not     :     found" );
            return tl;
        }

        try ( BufferedReader in = new BufferedReader ( new FileReader ( FILE ) ) )
        {
            String line;

            while ( ( line = in.readLine () ) != null )
            {
                Translation tl = new Translation ( line );
                if ( tl.isPhrase ( phrase ) == true )
                {
                    translation[ i ++ ] = tl;
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException | ArrayIndexOutOfBoundsException e )
        {
            System.out.println ( e.getMessage () );
        }

        return translation;
    }

    public static boolean sortTranslation ( Sort sort, Language language )
    {
        try
        {
            boolean change;
            do
            {
                change = false;
                int index = 1;
                BufferedReader in = new BufferedReader ( new FileReader ( FILE ) );
                BufferedWriter out = new BufferedWriter ( new FileWriter ( FILE_TMP ) );

                String[] lines = new String[ 2 ];
                boolean first = true;

                lines[ 0 ] = in.readLine ();
                Translation tl1 = new Translation ( lines[ 0 ] );

                while ( ( lines[ 1 ] = in.readLine () ) != null )
                {
                    Translation tl2 = new Translation ( lines[ 1 ] );

                    switch ( sort )
                    {
                        case DESC:
                        {
                            if ( first == false )
                            {
                                out.newLine ();
                            }
                            first = false;
                            if ( tl1.isEarlierThan ( tl2, language ) == false )
                            {
                                tl1.setID ( index ++ );

                                out.write ( tl1.toString () );
                                tl1 = tl2;
                            }
                            else
                            {
                                tl2.setID ( index ++ );

                                out.write ( tl2.toString () );
                                change = true;
                            }
                            break;
                        }
                        case ASC:
                        {
                            if ( first == false )
                            {
                                out.newLine ();
                            }
                            first = false;
                            if ( tl1.isEarlierThan ( tl2, language ) == true )
                            {
                                tl1.setID ( index ++ );
                                out.write ( tl1.toString () );
                                tl1 = tl2;
                            }
                            else
                            {
                                tl2.setID ( index ++ );
                                out.write ( tl2.toString () );
                                change = true;
                            }
                            break;
                        }
                    }
                }

                if ( first == false )
                {
                    out.newLine ();
                }

                tl1.setID ( index ++ );

                out.write ( tl1.toString () );

                out.close ();
                in.close ();

                if ( FILE.delete () == false )
                {
                    return false;
                }
                if ( FILE_TMP.length () <= 0 )
                {
                    FILE_TMP.delete ();
                }
                else if ( FILE_TMP.renameTo ( FILE ) == false )
                {
                    return false;
                }
            }
            while ( change == true );
        }
        catch ( FileNotFoundException e )
        {
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return true;
    }

    public static boolean[] getSettings ()
    {
        boolean[] settings =
        {
            true, true, true, true
        };
        try ( BufferedReader in = new BufferedReader ( new FileReader ( FileOperations.FILE_SETTINGS ) ) )
        {
            for ( int i = 0; i < settings.length;  ++ i )
            {
                boolean tmp = Boolean.parseBoolean ( in.readLine () );
                settings[ i ] = tmp;
            }
        }
        catch ( FileNotFoundException e )
        {
            try ( BufferedWriter out = new BufferedWriter ( new FileWriter ( FileOperations.FILE_SETTINGS ) ) )
            {
                for ( int i = 0; i < settings.length;  ++ i )
                {
                    out.append ( Boolean.toString ( settings[ i ] ) );
                    out.newLine ();
                }
            }
            catch ( IOException ex )
            {
                System.out.println ( ex.getMessage () );
            }
            System.out.println ( e.getMessage () );
        }
        catch ( IOException e )
        {
            System.out.println ( e.getMessage () );
        }

        return settings;
    }

    public static void setSetting ( int i, boolean condition )
    {
        try
        {
            Dictionary.FIRST_TIME[ i ] = condition;
        }
        catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println ( e.getMessage () );
        }

        saveSettings ();
    }

    private static void saveSettings ()
    {
        try ( BufferedWriter out = new BufferedWriter ( new FileWriter ( FileOperations.FILE_SETTINGS ) ) )
        {
            for ( int i = 0; i < Dictionary.FIRST_TIME.length;  ++ i )
            {
                out.append ( Boolean.toString ( Dictionary.FIRST_TIME[ i ] ) );
                out.newLine ();
            }
        }
        catch ( IOException ex )
        {
            System.out.println ( ex.getMessage () );
        }
    }

}
