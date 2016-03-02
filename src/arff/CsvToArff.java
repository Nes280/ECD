package arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

public class CsvToArff {
    public String    DATAPATH    = "./elements_projet/";
    public String[]  ponctuation = { ",", ".", "!" };   // , "!", "?"
    public Hashtable smiley;

    public CsvToArff() {
        smiley = new Hashtable();
        smiley.put( ":)", "happy" );
        smiley.put( ":-)", "happy" );
        smiley.put( ":(", "sad" );
        smiley.put( ":-(", "sad" );
        smiley.put( ":D", "cheerful" );
        smiley.put( ":-D", "cheerful" );
        smiley.put( ":o)", "happy" );
        smiley.put( ";)", "happy" );
        smiley.put( ";-)", "happy" );
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
        System.out.println( "merci d'entrer l'option du fichier arff:\nb = text brut\np = sans ponctuation" );
        String rep = option.nextLine();
        CsvToArff a = new CsvToArff();
        String contenuArff = a.lecture( rep );
        String nomFichier = "";
        if ( rep.equals( "b" ) )
            nomFichier = "Brut.arff";
        if ( rep.equals( "p" ) )
            nomFichier = "Ponctuation.arff";

        a.ecriture( contenuArff, nomFichier );

        System.out.println( "ok" );

    }

}
