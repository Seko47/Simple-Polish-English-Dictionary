/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

/**
 *
 * @author Seko
 */
public class Translation
{

    private int ID;
    private String[] pl;
    private String[] eng;

    public Translation ( String line )
    {
        String[] tab = line.split ( "     :     " ); //5-space:5-space

        try
        {
            this.ID = Integer.parseInt ( tab[ 0 ] );
            this.pl = tab[ 1 ].split ( ", " );
            this.eng = tab[ 2 ].split ( ", " );
        }
        catch ( NumberFormatException | ArrayIndexOutOfBoundsException e )
        {
            System.out.println ( e.getMessage () );
        }
    }

    public int getID ()
    {
        return this.ID;
    }

    public String[] getPL ()
    {
        return this.pl;
    }

    public String[] getENG ()
    {
        return this.eng;
    }

    public void setID ( int nr )
    {
        this.ID = nr;
    }

    public int isExists ( String text ) //1-pl | 2-eng | 0-null
    {
        try
        {
            for ( String a : pl )
            {
                if ( text.equalsIgnoreCase ( a ) )
                {
                    return 1;
                }
            }
            for ( String a : eng )
            {
                if ( text.equalsIgnoreCase ( a ) )
                {
                    return 2;
                }
            }
        }
        catch ( NullPointerException e )
        {
            System.out.println ( e.getMessage () );
        }

        return 0;
    }

    @Override
    public String toString ()
    {
        StringBuilder text = new StringBuilder ( Integer.toString ( this.ID ) + "     :     " );

        for ( int i = 0; i < this.pl.length;  ++ i )
        {
            if ( i < this.pl.length - 1 )
            {
                text.append ( this.pl[ i ] ).append ( ", " );
            }
            else
            {
                text.append ( this.pl[ i ] ).append ( "     :     " );
            }
        }

        for ( int i = 0; i < this.eng.length;  ++ i )
        {
            if ( i < this.eng.length - 1 )
            {
                text.append ( this.eng[ i ] ).append ( ", " );
            }
            else
            {
                text.append ( this.eng[ i ] );
            }
        }

        return text.toString ();
    }

    public void addPL ( String word )
    {
        String[] newPL = Arrays.copyOf ( this.pl, this.pl.length + 1 );
        newPL[ this.pl.length ] = word;
        Arrays.sort ( newPL, Collator.getInstance ( new Locale ( "pl", "PL" ) ) );
        this.pl = newPL;
    }

    public void addENG ( String word )
    {
        String[] newENG = Arrays.copyOf ( this.eng, this.eng.length + 1 );
        newENG[ this.eng.length ] = word;
        Arrays.sort ( newENG );
        this.eng = newENG;
    }

    public void delPL ( String word )
    {
        int index = 0;
        String[] newPL = new String[ this.pl.length - 1 ];
        for ( String a : this.pl )
        {
            if ( a.equalsIgnoreCase ( word ) == false )
            {
                try
                {
                    newPL[ index ++ ] = a;
                }
                catch ( ArrayIndexOutOfBoundsException e )
                {
                    System.out.println ( e.getMessage () );
                }
            }
        }
        this.pl = newPL;
    }

    public void delENG ( String word )
    {
        int index = 0;
        String[] newENG = new String[ this.eng.length - 1 ];
        for ( String a : this.eng )
        {
            if ( a.equalsIgnoreCase ( word ) == false )
            {
                try
                {
                    newENG[ index ++ ] = a;
                }
                catch ( ArrayIndexOutOfBoundsException e )
                {
                    System.out.println ( e.getMessage () );
                }
            }
        }
        this.eng = newENG;
    }

    public void updatePL ( String oldWord, String newWord )
    {
        for ( int i = 0; i < this.pl.length;  ++ i )
        {
            if ( this.pl[ i ].equalsIgnoreCase ( oldWord ) == true )
            {
                this.pl[ i ] = newWord;
                return;
            }
        }
    }

    public void updateENG ( String oldWord, String newWord )
    {
        for ( int i = 0; i < this.eng.length;  ++ i )
        {
            if ( this.eng[ i ].equalsIgnoreCase ( oldWord ) == true )
            {
                this.eng[ i ] = newWord;
                return;
            }
        }
    }

    public boolean isPhrase ( String fraza )
    {
        for ( String a : this.pl )
        {
            if ( a.indexOf ( fraza ) >= 0 )
            {
                return true;
            }
        }
        for ( String a : this.eng )
        {
            if ( a.indexOf ( fraza ) >= 0 )
            {
                return true;
            }
        }

        return Integer.toString ( this.ID ).contains ( fraza );
    }

    public boolean isEarlierThan ( Translation tl, Language language )
    {
        switch ( language )
        {
            case PL:
            {
                String[] words =
                {
                    this.pl[ 0 ], tl.pl[ 0 ]
                };
                Arrays.sort ( words, Collator.getInstance ( new Locale ( "pl", "PL" ) ) );

                return ( this.pl[ 0 ].equalsIgnoreCase ( words[ 0 ] ) );
            }
            case ENG:
            {
                String[] words =
                {
                    this.eng[ 0 ], tl.eng[ 0 ]
                };
                Arrays.sort ( words );

                return ( this.eng[ 0 ].equalsIgnoreCase ( words[ 0 ] ) );
            }
        }
        return true;
    }

}
