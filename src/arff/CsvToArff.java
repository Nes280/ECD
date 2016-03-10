package arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class CsvToArff {
    public String                    DATAPATH    = "./elements_projet/";
    public String[]                  ponctuation = { ",", ".", "!" };   // ,
                                                                        // "!",
                                                                        // "?"
    public Hashtable<String, String> smileyTwo;
    public Hashtable<String, String> smileyThree;
    public Hashtable<String, String> smileyTwoChar;

    public CsvToArff() {
        smileyTwo = new Hashtable();
        smileyThree = new Hashtable();
        smileyTwoChar = new Hashtable();
        smileyTwo.put( ":)", "happy" );
        smileyThree.put( ":-)", "happy" );
        smileyThree.put( "q:)", "happy" );
        smileyTwo.put( ":(", "sad" );
        smileyThree.put( ":-(", "sad" );
        smileyTwoChar.put( ":D", "cheerful" );
        smileyThree.put( ":-D", "cheerful" );
        smileyThree.put( ":o)", "happy" );
        smileyTwo.put( ";)", "happy" );
        smileyThree.put( ";-)", "happy" );
    };

    public String header() {
        return "@RELATION donnees\n@ATTRIBUTE text STRING\n@ATTRIBUTE eval {-1,1}\n@data";
    }

    public String echappementQuotes( String ligne ) {
        return ligne.replace( "\"", "\\\"" );// .replace( "'", "\\'" );

    }

    public String traitementPonctuation( String ligne ) {
        for ( int i = 0; i < ponctuation.length; i++ ) {
            ligne = ligne.replace( ponctuation[i], " " );
        }
        return ligne.replaceAll( "[ ]+", " " );
    }

    public String traitementSmiley( String ligne ) {

        /*
         * for ( Map.Entry<String, String> entry : smileyTwo.entrySet() ) {
         * ligne = ligne.replace( entry.getKey(), " " + entry.getValue() + " "
         * ); }
         */

        for ( Map.Entry<String, String> entry : smileyThree.entrySet() ) {
            ligne = ligne.replace( entry.getKey(), " " + entry.getValue() + " " );
        }

        return ligne.replaceAll( "[ ]+", " " );
    }

    public String lecture( String rep ) {
        String data = this.DATAPATH + "dataset.csv";
        String label = this.DATAPATH + "labels.csv";
        String resultat = this.header();
        try {
            // Ouverture de data
            InputStream ipsData = new FileInputStream( data );
            InputStreamReader ipsrData = new InputStreamReader( ipsData );
            BufferedReader brData = new BufferedReader( ipsrData );

            // Ouverture de label
            InputStream ipsLabel = new FileInputStream( label );
            InputStreamReader ipsrLabel = new InputStreamReader( ipsLabel );
            BufferedReader brLabel = new BufferedReader( ipsrLabel );

            String ligneData;
            String ligneLabel;
            if ( rep.equals( "p" ) ) {
                while ( ( ligneData = brData.readLine() ) != null && ( ligneLabel = brLabel.readLine() ) != null ) {
                    resultat += "\n\"" + echappementQuotes( traitementPonctuation( ligneData ) ) + "\"," + ligneLabel;

                }
            }
            if ( rep.equals( "b" ) ) {
                while ( ( ligneData = brData.readLine() ) != null && ( ligneLabel = brLabel.readLine() ) != null ) {
                    resultat += "\n\"" + echappementQuotes( ligneData ) + "\"," + ligneLabel;

                }
            }
            if ( rep.equals( "s" ) ) {
                while ( ( ligneData = brData.readLine() ) != null && ( ligneLabel = brLabel.readLine() ) != null ) {
                    resultat += "\n\"" + echappementQuotes( traitementPonctuation( traitementSmiley( ligneData ) ) )
                            + "\"," + ligneLabel;

                }
            }

            brData.close();
            brLabel.close();
        } catch ( Exception e ) {
            System.out.println( e.toString() );
        }
        return resultat;
    }

    public void ecriture( String contenu, String nomFic ) {
        try {
            FileWriter fw = new FileWriter( nomFic );
            BufferedWriter bw = new BufferedWriter( fw );
            PrintWriter fichierSortie = new PrintWriter( bw );
            fichierSortie.print( contenu );
            fichierSortie.close();
        } catch ( Exception e ) {
            System.out.println( e.toString() );
        }
    }

    public static void main( String[] args ) {
        Scanner option = new Scanner( System.in );
        System.out.println(
                "merci d'entrer l'option du fichier arff:\nb = text brut\np = sans ponctuation\ns = sans smiley et sans ponctuation." );
        String rep = option.nextLine();
        CsvToArff a = new CsvToArff();
        String contenuArff = a.lecture( rep );
        String nomFichier = "";
        if ( rep.equals( "b" ) )
            nomFichier = "Brut.arff";
        if ( rep.equals( "p" ) )
            nomFichier = "Ponctuation.arff";
        if ( rep.equals( "s" ) )
            nomFichier = "PonctuationSmiley.arff";

        a.ecriture( contenuArff, nomFichier );

        System.out.println( "ok" );

    }

}
